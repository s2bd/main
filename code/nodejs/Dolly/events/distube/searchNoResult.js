const { MessageEmbed } = require("discord.js");
module.exports = async (client, query, queue) => {
    const embed = new MessageEmbed()
    .setDescription(`No results found for ${query} :frowning:`)
    .setColor('#aa0000')
    queue.textChannel.send({ embeds: [embed] })
}
