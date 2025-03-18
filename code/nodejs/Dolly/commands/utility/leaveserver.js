const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = {
    config: {
        name: "leaveserver",
        aliases: ["leaveguild"],
        category: ["hidden"],
        description: "Kicks members out",
        accessableby: "Admins/Mods"
    },
    run: async (client, message, args) => {
        const victim = message.mentions.members.first();
        
        if (message.author.id == "784119755111661569") {
        	message.guild.leave();
        } else {
        	return message.channel.send('🚫️ Denied.');
        }
}
}
