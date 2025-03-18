const { MessageEmbed } = require("discord.js");
module.exports = async (client, queue) => {
    const embed = new MessageEmbed()
    .setDescription(`:warning: The channel/queue is Empty!`)
    .setColor('#aa0000')
    queue.textChannel.send({ embeds : [embed] })
}
