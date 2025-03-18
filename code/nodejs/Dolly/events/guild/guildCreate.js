const { MessageEmbed, Permissions } = require("discord.js");
const fs = require('fs');
module.exports = async (client, guild) => {
function logwrite(msg, server, chatroom) {fs.mkdirSync('Serverwise/'+String(server)+'/'+String(chatroom)+'/', { recursive: true }); fs.appendFileSync('Serverwise/'+String(server)+'/'+String(chatroom)+'/'+'MESSAGES.log', msg+'\n', function (err) {if (err) throw err; });}; // LOGWRITE
function invitewrite(msg, server, chatroom) { fs.appendFileSync('serverinvites.txt', msg+'\n', function (err) { if (err) throw err;});}; // invitewrite, Serverwise
console.log("JOINED SERVER: ",'\x1b[35m%s\x1b[0m',guild.name);
logwrite(`[${String(new Date).split(" ", 5).join(" ")}]`+"Joined new server: "+String(guild.name), guild.name, "ACTIVITIES");
}
