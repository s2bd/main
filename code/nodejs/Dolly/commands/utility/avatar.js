const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = {
    config: {
        name: "avatar",
        aliases: ["av","snipe","whois","ava"],
        category: ["utility"],
        description: "Kicks members out",
        accessableby: "Admins/Mods"
    },
    run: async (client, message, args) => {
        const tagged = message.mentions.members.first();
        const victim = message.guild.members.cache.get(tagged.user.id);
        const avatar = victim.user.avatarURL()+"?size=4096";
        const embed = new MessageEmbed().setTitle(`${victim.user.username}#${victim.user.discriminator}`).setImage(`${avatar}`)
        //console.log(tagged);
        return message.channel.send({embeds: [embed]});
}
}
