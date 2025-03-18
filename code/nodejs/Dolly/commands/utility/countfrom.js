const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const delay = require("delay");

module.exports = {
    config: {
        name: "countfrom",
        aliases: ["countstart"],
        category: "utility",
        description: "Counts from the number specified upto infinity",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        let count=parseInt(args[0]) || 0;
        let limit= 9999999999999999999999999;
        if (message.member.permissions.has('ADMINISTRATOR') || message.author.id == "784119755111661569") {
        for (count; count < limit; count++){
        await delay(2000);
        message.channel.send(`${count}`);
        }
        } else {
        return message.channel.send('🚫️ Denied. You are not an administrator!');
        }
}
}
