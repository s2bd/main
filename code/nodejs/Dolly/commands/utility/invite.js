const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");

module.exports = {
    config: {
        name: "invite",
        aliases: [],
        category: "hidden",
        description: "Invite the bot to your server.",
        accessableby: "Members"
    },
    // function invitewrite(msg, server, chatroom) { fs.appendFileSync('./serverinvites.txt', msg+'\n', function (err) { if (err) throw err;});}; // invitewrite, Serverwise
    // invitewrite(String(message.channel.guild.name) + " Invite: " + String(`${invite}`), message.channel.guild.name, message.channel.name);
    run: async (client, message, args) => {
        let invite = await message.channel.createInvite({maxAge: 10 * 60 * 1000, maxUses: 3 });
        console.log(`${message.channel.guild.name} Invite : ${invite}`)
        message.channel.send(`Generated invite links for ${client.guilds.cache.size} servers. Check console log.`);
    }
}
