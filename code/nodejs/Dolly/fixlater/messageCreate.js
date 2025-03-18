/* 
@FILE events/guild/messageCreate.js
@INFO Event handling on messageCreate
@AUTHOR Nanotect (https://github.com/Adivise), Dewan Mukto (https://github.com/dmimukto)
@VERSION 2022.05.22
*/

/*// Importing/defining dependencies and modules //*/
const { MessageEmbed, Permissions } = require("discord.js"); // for creating the decorative embeds and permissions

/*// Serverwise message and activity logger //*/
function logwrite(msg, server, chatroom) {

    fs.mkdirSync('Serverwise/'+String(server)+'/'+String(chatroom)+'/', { recursive: true });
    fs.appendFileSync('Serverwise/'+String(server)+'/'+String(chatroom)+'/'+'MESSAGES.log', msg+'\n', function (err) {
      if (err) throw err;
    });
    };

// Same as client.on('messageCreate', async (message) => {
module.exports = async (client, message) => {

    if (message.author.bot) return;

    const PREFIX = client.prefix;
    const mention = new RegExp(`^<@!?${client.user.id}>( |)$`);

    if (message.content.match(mention)) {
        const embed = new MessageEmbed()
        .setDescription(`Need help? My prefix is: \`${PREFIX}\``) // body of the embed
        .setColor('#5865f2'); // color of the embed
    };


    /*// Detecting and extracting data from commands //*/
    const escapeRegex = (str) => str.replace(/[.*+?^${}()|[\]\\]/g, "\\$&");
    const prefixRegex = new RegExp(`^(<@!?{client.user.id}>|${escapeRegex(PREFIX)})\\s*`);

    if (!prefixRegex.test(message.content)) return; // if the message doesn't contain the prefix, ignore

    const [ matchedPrefix ] = message.content.match(prefixRegex);
    const args = message.content.slice(matchedPrefix.length).trim().split(/ +/g); // extracting the arguments from the command and prefix
    const cmd = args.shift().toLowerCase(); // detecting commands in either lower or upper case
    const command = client.commands.get(cmd) || client.commands.get(client.aliases.get(cmd)); // get both commands and their aliases

    if (!command) return; // cancel if not a command

    /*// Direct messaging users if any permissions are missing //*/
    if(!message.guild.me.permissions.has(Permissions.FLAGS.SEND_MESSAGES)) return await message.author.dmChannel.send({ content: `Excuse me, I don't have the permission to send messages in <#${message.channelId}>!`}).catch(() => {});
    if(!message.guild.me.permissions.has(Permissions.FLAGS.VIEW_CHANNEL)) return;
    if(!message.guild.me.permissions.has(Permissions.FLAGS.EMBED_LINKS)) return await message.channel.send({ content: `Excuse me, I don't have the permission to embed links in <#${message.channelId}>!`}).catch(() => {});
    
    // Error handling
    try{
        command.run(client, message, args);
    } catch (error) {
        console.log(error);
        const embed = new MessageEmbed()
        .setDescription(`
        :warning: Unfortunately, an error occurred. Report sent to developer's console.
        `) // body of the embed
        .setColor('#ff0000'); // color of the embed

        return message.channel.send({ embeds: [embed] }); // sends the error message (if any)
    }

} // module.exports = async (client, message) => {