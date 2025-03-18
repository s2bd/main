const { MessageEmbed } = require("discord.js");
module.exports = async (client, queue, song) => {
    let embed = new MessageEmbed()
    .setDescription(
        `**Added [${song.name}](${song.url}) to queue** \n
        Duration: \`${song.formattedDuration}\` \n`)
        .setFooter(`Requested by ${song.user}`)
        .setColor('#09ad45')
    queue.textChannel.send({ embeds : [embed] })
}
