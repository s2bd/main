const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = {
    config: {
        name: "name of command to show in help",
        aliases: ["alternate ways to issue this cmd"],
        category: ["must be same as folder"],
        description: "what to show in help menu",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        // body of function
        /* example :
        const embed = new MessageEmbed()
        .setColor("#5865f2")
        .setAuthor({ name: "Invite!"})
        .setDescription("```Invite me to your server!```")
        .setTimestamp()
        .setFooter({ text: `Requested by ${message.author.tag}`, iconURL: message.author.displayAvatarURL()});

        const row = new MessageActionRow()
            .addComponents(
                new MessageButton()
                    .setLabel("Invite")
                    .setURL(`https://discord.com/api/oauth2/authorize?client_id=${client.user.id}&permissions=1644971949559&scope=bot`)
                    .setEmoji("🔗")
                    .setStyle("LINK")
            )
        
        message.channel.send({ embeds: [embed], components: [row] });
        */
    }
}