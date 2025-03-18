const { MessageEmbed, MessageActionRow, MessageButton, WebhookClient } = require("discord.js");
const {WEBHOOK_ID, WEBHOOK_TOKEN} = require('../../config.js');
module.exports = {
    config: {
        name: "webhooksay",
        aliases: ["webhook","botsay"],
        category: ["social"],
        description: "Test feature for webhooks",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
          let webhook = message.channel.createWebhook('Dolly', {
          avatar: 'https://media.discordapp.net/attachments/817313984264536114/980151026701193299/Dolly_webhook_icon.png?width=490&height=490', }); 
          
         //webhook.edit({name: 'Dolly',avatar:'https://media.discordapp.net/attachments/817313984264536114/980151026701193299/Dolly_webhook_icon.png?width=490&height=490'})
         
         

        const embed = new MessageEmbed()
        .setColor("#5865f2")
        .setAuthor({ name: `${client.tag}`})
        .setDescription(`${message.content}`)
        .setTimestamp();
        
        message.delete().catch(err => {})
        webhook.send({content: 'Testing...', username: 'Dolly', avatarURL: 'https://media.discordapp.net/attachments/817313984264536114/980151026701193299/Dolly_webhook_icon.png?width=490&height=490', embeds: [embed]}).catch(err => {})

    }
}
