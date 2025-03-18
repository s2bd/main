const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = {
    config: {
        name: "serverwelcome",
        aliases: [],
        category: "hidden",
        description: "",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        const embed1 = new MessageEmbed()
        .setColor("#5865f2")
        .setTitle("Welcome!")
        .setDescription("Here at this server, the tech fellows from Mux have dug their roots in to serve our customers and fans with ease.").setThumbnail(client.user.displayAvatarURL({ dynamic: true, size: 2048 }));
        const embed2 = new MessageEmbed().setColor('#5865f2').setDescription('The rules are simple. Anyone with enough decency should know how to behave in a Discord server. If not, feel free to check [the Discord Community Guidelines](https://discord.com/guidelines)');
        const embed3 = new MessageEmbed().setColor('#5865f2').setDescription('And ofcourse, feel free to let us know how we could improve your experience here. Staying in this server is not mandatory, but we would always miss you while you\'re away. :smiling_face_with_tear:');
        message.channel.send({ embeds: [embed1]});
        message.channel.send({ embeds: [embed2]});
        message.channel.send({ embeds: [embed3]});
    }
}
