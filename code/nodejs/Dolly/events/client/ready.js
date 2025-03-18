const figlet = require('figlet');
const chalk = require('chalk');
module.exports = async (client) => {
    figlet(client.user.tag, function(err, data) {
        if (err) {
            console.log('Something went wrong!');
            console.dir(err);
            return;
        }
        console.log(chalk.red.bold(data));
    });
    let servers = client.guilds.cache.size;
    let users = client.users.cache.size;
    let channels = client.channels.cache.size;
    const activities = [
    	`${servers} servers saying "🇵🇸️  Free Palestine!"`,
        `${servers} servers saying "🇮🇱️  Stop Israel!"`,
        `${servers} servers support 🇵🇸️. Does yours?`,
        /*
        `${client.prefix}help | 🇵🇸️  Free Palestine!`,
        `${client.prefix}help | 🇮🇱️  Stop Israel!`,
        `${client.prefix}help | ${channels} support 🇵🇸️`,
        `${client.prefix}help | 🇵🇸️  Free Palestine!`,
        `${client.prefix}help | 🇮🇱️  Stop Israel!`,
        `${client.prefix}help | ${servers} servers support 🇵🇸️`,
        `${client.prefix}help | 🇵🇸️  Free Palestine!`,
        `${client.prefix}help | 🇮🇱️  Stop Israel!`,
        `${client.prefix}help | ${users} users support 🇵🇸️`,
        */
    ]
    setInterval(() => {
        client.user.setActivity(`${activities[
            Math.floor(Math.random() * activities.length)
        ]}`,
         {type: 'WATCHING'});
    },
    1000);
}
