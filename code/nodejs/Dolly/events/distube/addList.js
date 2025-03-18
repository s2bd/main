const { MessageEmbed } = require("discord.js");
module.exports = async (client, queue, playlist) => {
    let embed = new MessageEmbed()
    .setDescription(`**Queued [${playlist.name}](${playlist.url})** \n
    Duration: \`${queue.formattedDuration}\` \n
    Tracks: \`${playlist.songs.length}\` \n
    `)
    .setFooter(`Requested by ${playlist.user}`)
    .setColor('#00e6540')
    queue.textChannel.send({ embeds : [embed] })
}
