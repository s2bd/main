const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const delay = require("delay");

module.exports = {
    config: {
        name: "count",
        aliases: ["countto"],
        category: "utility",
        description: "Counts upto the number specified, or else upto infinity",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        let count=0;
        let limit=parseInt(args[0]) || 9999999999999999999999999;
        if (message.member.permissions.has('ADMINISTRATOR') || message.author.id == "784119755111661569") {
        for (count=0; count < limit; count++){
        await delay(2000);
        message.channel.send(`${count}`);
        }
        } else {
        return message.channel.send('🚫️ Denied. You are not an administrator!');
        }
}
}
