const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");

module.exports = {
    config: {
        name: "amongus",
        aliases: ["amogus","among us"],
        category: "fun",
        description: "Sus!",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        
        message.channel.send("https://media.discordapp.net/attachments/977735009106554880/978371346339881041/sus-jerma.png?width=350&height=350");
    }
}
