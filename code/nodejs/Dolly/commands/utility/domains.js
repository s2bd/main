const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");

module.exports = {
    config: {
        name: "muxdomains",
        aliases: [],
        category: "hidden",
        description: "shows the list of approved Mux domains",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        const embed = new MessageEmbed().setColor('#5865f2').setDescription('```\nasenturisk.com\ndewanmukto.com\nmuxworks.com\nmuxsites.com\namrellasocial.com```');
        message.channel.send({embeds : [embed]});
    }
}
