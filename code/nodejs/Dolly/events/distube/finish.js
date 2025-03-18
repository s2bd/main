const { MessageEmbed } = require("discord.js");
module.exports = async (client, queue) => {
    const embed = new MessageEmbed()
    .setDescription(`:information_source: The song has \`ended\``).setColor('#0987ad')

    queue.textChannel.send({ embeds: [embed]})
}
