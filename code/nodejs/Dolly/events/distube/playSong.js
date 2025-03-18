const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = async (client, queue, track) => {
    var newQueue = client.distube.getQueue(queue.id);
    var data = disspace(newQueue, track);
    const nowplay = await queue.textChannel.send(data);
    const filter = (message) => {
        if(message.guild.me.voice.channel && message.guild.me.voice.channelId === message.member.voice.channelId) return true;
        else {
            message.reply({ content: "You need to be in the same voice channel!", ephemeral: true });
        }
    };
    const collector = nowplay.createMessageComponentCollector({filter, time:120000});
    collector.on('collect', async (message) => {
        const id = message.customId;
        const queue = client.distube.getQueue(message.guild.id);
        if(id === "pause") {
            if (!queue) {
                collector.stop();
            }
            if (queue.paused) {
                await client.distube.resume(message.guild.id);
                const embed = new MessageEmbed()
                .setDescription(`
                :play_pause: Audio has been \` resumed \`
                `)
                .setColor('#0987ad');

                message.reply({ embeds: [embed], ephemeral: true });
            } else {
                await client.distube.pause(message.guild.id);
                const embed = new MessageEmbed()
                .setDescription(`
                :play_pause: Audio has been \` paused \`
                `)
                .setColor('#0987ad')
                
                message.reply({ embeds: [embed], ephemeral: true });
            
            }
        } else if (id === "skip") {
            if(!queue) {
                collector.stop();
            }
            if (queue.songs.length === 1) {
                const embed = new MessageEmbed()
                .setDescription(`
                :warning: There are no more songs in the queue
                `)
                .setColor('#aa0000')

                message.reply({ embeds: [embed], ephemeral: true });
            } else {
                await client.distube.skip(message).then(song => {
                    const embed = new MessageEmbed()
                    .setDescription(`
                    :fast_forward: Song has been skipped
                    `)
                    .setColor('#0987ad')

                    nowplay.edit({ components: [] });
                    message.reply({ embeds: [embed], ephemeral: true });
                });
            }
        } else if (id === "stop") {
            if(!queue) {
                collector.stop();
            }
            await client.distube.stop(message.guild.id);
            const embed = new MessageEmbed()
            .setDescription(`
            :octagonal_sign: Song has been stopped
            `)
            .setColor('#0987ad')
            await nowplay.edit({ components: []});
            message.reply({ embeds: [embed], ephemeral: true });
        } else if (id === "loop") {
            if (!queue) {
                collector.stop();
            }
            if (queue.repeatMode === 0) {
                client.distube.setRepeatMode(message.guild.id, 1);
                const embed = new MessageEmbed()
                .setDescription(`
                :repeat_one: Single repeat ON
                `)
                .setColor('#0987ad')

                message.reply({ embeds : [embed], ephemeral: true });
            } else {
                client.distube.setRepeatMode(message.guild.id,0);
                const embed = new MessageEmbed()
                .setDescription(`
                :repeat_one: Single repeat OFF
                `)
                .setColor('#0987ad')

                message.reply({ embeds : [embed], ephemeral: true });
            }
        } else if (id === "previous") {
            if (!queue) {
                collector.stop();
            }
            if (queue.previousSongs.length == 0) {
                const embed = new MessageEmbed()
                .setDescription(`
                :warning: There are no previous songs
                `)
                .setColor('#aa0000')
                
                message.reply({ embeds: [embed], ephemeral: true })
                
            } else {
                await client.distube.previous(message)
                const embed = new MessageEmbed()
                .setDescription(`
                :track_previous: Previous song selected
                `)
                .setColor('#0987ad')

                nowplay.edit({ components: []});
                message.reply({ embeds: [embed], ephemeral: true})
            }
        }
    
    });
}

function disspace(nowQueue, nowTrack) {
    const embed = new MessageEmbed()
    .setAuthor({ name: 'Dolly Music Player', iconURL: 'https://c.tenor.com/qDiHoxX2pvoAAAAC/discord-logo.gif'})
    .setThumbnail(nowTrack.thumbnail)
    .setDescription(`
    **Title:** [${nowTrack.name}](${nowTrack.url})
    `)
    .addField(`Artist/channel:`,`[${nowTrack.uploader.name}](${nowTrack.uploader.url})`, true)
    .addField(`Requested by:`,`${nowTrack.user}`,true)
    .addField(`Audio volume:`, `${nowQueue.volume}%`,true)
    .addField(`Filters:`, `${nowQueue.filters.join(", ") || "None"}`,true)
    .addField(`Autoplay:`, `${nowQueue.autoplay ? "Enabled" : "Disabled"}`, true)
    .addField(`Duration:`, `${nowQueue.formattedDuration}`, true)
    .addField(`Track time: \`[0:00 / ${nowTrack.formattedDuration}]\``,
    `\`\`\`📀️ 🎶──────────────────────────────\`\`\``)
    .setTimestamp()
    const row = new MessageActionRow()
    .addComponents(
        new MessageButton()
        .setCustomId("previous")
        .setEmoji("⏮️")
        .setStyle("PRIMARY")
    )
    .addComponents(
        new MessageButton()
        .setCustomId("pause")
        .setLabel("PAUSE ⏯")
        .setStyle("PRIMARY")
    )
    .addComponents(
        new MessageButton()
        .setCustomId("stop")
        .setEmoji("✖")
        .setStyle("DANGER")
    )
    .addComponents(
        new MessageButton()
        .setCustomId("loop")
        .setLabel("LOOP 🔄")
        .setStyle("PRIMARY")
    )
    .addComponents(
        new MessageButton()
        .setCustomId("skip")
        .setEmoji("⏭️")
        .setStyle("PRIMARY")
    )
    
    return { embeds: [embed], components: [row]};
}
