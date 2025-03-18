const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = {
    config: {
        name: "ban",
        aliases: ["bann","banuser","shun","exile"],
        category: ["utility"],
        description: "Bans members out",
        accessableby: "Admins/Mods"
    },
    run: async (client, message, args) => {
        const victim = message.mentions.members.first();
        
        if (message.member.permissions.has('ADMINISTRATOR') || message.member.permissions.has('KICK_MEMBERS') || message.author.id == "784119755111661569") {
        	if (!victim) { return message.channel.send('Tag the user to ban!'); message.delete();
        	}else if (victim==message.author.id) { return message.channel.send(`${message.author} are you being serious? 😑️`)
        	}else if (!victim.kickable || victim == "<@784119755111661569>") { return message.channel.send(`❌️ That person cannot be banned by you. Find someone stronger!`); 
        	}else {
        		const victimtag = victim.username + '#' + victim.discriminator;
        		victim.ban();
        		setTimeout(()=>{
        		message.channel.send(`Exiled \`${victim}\` from this server ✅️`); }, 200)};
        } else {
        
        return message.channel.send('🚫️ Denied. You don\'t have permissions to ban users!');
        }
}
}
