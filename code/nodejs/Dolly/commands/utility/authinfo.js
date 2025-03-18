const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");

module.exports = {
    config: {
        name: "authinfo",
        aliases: [],
        category: "utilities",
        description: "displays author info.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        const embed = new MessageEmbed()
        .setColor("#5865f2")
        .setAuthor({ name: "Dolly"})
        .setDescription("```Created by Dewan Mukto```")
        .setFooter('use `md botinfo` for server status & more')
        .setTimestamp();

        const row = new MessageActionRow()
            .addComponents(
                new MessageButton()
                    .setLabel("Contact")
                    .setURL(`https://discord.com/invite/GqHJmagG4k`)
                    .setStyle("LINK")
            )
        
        message.channel.send({ embeds: [embed], components: [row] });
    }
}
