const { MessageEmbed } = require ('discord.js');
const ms = require('pretty-ms');
const os = require('node-os-utils');
const cpustat = require('cpu-stat');
const Discord = require('discord.js');
const ops = require('os');

module.exports = {
    config: {
    	name: 'botinfo',
    	aliases: ['serverinfo','stats','stat','server'],
    	usage: '(botinfo)',
    	category: 'utility',
    	description: "Displays server info",
    	cooldown: 5 * 1000,
    	accessableby: "Members",
    },
    run: async (client, message, args) => {
        const cpu = await os.cpu.usage();
        const ram = await os.mem.info();
        const uptime = ms(client.uptime);

        let embed = new MessageEmbed()
            .setColor('#000001')
            .setTitle('Information about ' + client.user.username)
            .setFooter('Data retrieved from the server running this bot')
            .setThumbnail(client.user.displayAvatarURL())
            .addField("Memory Usage (Bot)", `\`${(process.memoryUsage().heapUsed / 1024 / 1024).toFixed(2)}/ ${(ops.totalmem() / 1024 / 1024).toFixed(2)}MB\``, true)
                        .addField("\u200b", `\u200b`, true)
                        .addField("Platform", `\`\`${ops.platform()}\`\``, true)
                        .addField("Servers", `\`Total: ${client.guilds.cache.size} Servers\``, true)
                        .addField("\u200b", `\u200b`, true)
                        .addField("Discord.js", `\`v${Discord.version}\``, true)
                        .addField("API Latency", `\`${client.ws.ping}ms\``, true)
                        .addField("\u200b", `\u200b`, true)
                        .addField("Node.js", `\`${process.version}\``, true)
                        .addField("CPU", `\`\`\`md\n${ops.cpus().map((i) => `${i.model}`)[0]}\`\`\``)
            .setDescription('```\nCPU Usage  : ' + cpu + ' %' +
                '\nTotal RAM Usage  : ' + Math.ceil(ram.usedMemMb) + '/' + Math.ceil(ram.totalMemMb) + ' MB' +
                '\nUptime     : ' + uptime +
                '\nPing       : ' + client.ws.ping + ' ms\n```')
            .setTimestamp();

        return message.channel.send({ embeds: [embed] });
    }
};
