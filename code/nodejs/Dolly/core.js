const { Intents, Client, Collection } = require("discord.js");
const { DisTube } = require('distube');
const { SoundCloudPlugin } = require('@distube/soundcloud');
const { SpotifyPlugin } = require('@distube/spotify');
class botClient extends Client {
    constructor() {
        super({shards: "auto",allowedMentions: {parse: ["roles", "users", "everyone"],repliedUser: false,},intents: [Intents.FLAGS.GUILDS,Intents.FLAGS.GUILD_MEMBERS,Intents.FLAGS.GUILD_VOICE_STATES,Intents.FLAGS.GUILD_MESSAGES,],});
        process.on('unhandledRejection', bug => console.log(bug));
        process.on('uncaughtException', error => console.log(error));
        this.config = require('./config.js');
        this.prefix = this.config.PREFIX;
        this.token = this.config.TOKEN;
        const client = this;
        this.distube = new DisTube(client, {
            searchSongs: 0,
            searchCooldown: 30,
            leaveOnEmpty: this.config.LEAVE_IDLE,
            emptyCooldown: this.config.LEAVE_IDLE_COUNTDOWN,
            leaveOnFinish: this.config.LEAVE_FINISH,
            leaveOnStop: this.config.LEAVE_STOP,
            plugins: [
                new SoundCloudPlugin(),
                new SpotifyPlugin({
                    emitEventsAfterFetching: true
                })],
        });
        ["aliases","commands"].forEach(x => client[x] = new Collection());
        ["loadCommands", "loadEvents", "loadDistube"].forEach(x => require(`./handlers/${x}`)(client));   
    }
    connect() {
        return super.login(this.token);
    };
};
module.exports = botClient;
