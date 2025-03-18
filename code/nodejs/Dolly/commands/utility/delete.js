const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
module.exports = {
    config: {
        name: "delete",
        aliases: ["deletemsg","deletemsgs","purge"],
        category: ["utility"],
        description: "Deletes messages specified by an amount 2-100",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        const limit = parseInt(args[0]);
        if (message.member.permissions.has('ADMINISTRATOR') || message.author.id == "531811739746959360") {
        if (!limit) { message.channel.send('Specify the number of messages to delete!'); message.delete();};
        if (limit < 1) { return message.channel.send(`${message.author} are you being serious? 😑️`) };
        message.channel.bulkDelete(limit).catch(err => {
        message.channel.send(`For Discord API reasons, a maximum of 100 messages can be deleted at a time.`)
        });
        setTimeout(()=>{
        message.channel.send(`Deleted \`${limit}\` messages ✅️`);
        }, 2000)
        
        } else {
        
        return message.channel.send('🚫️ Denied. You are not an administrator!');
        }
}
}
