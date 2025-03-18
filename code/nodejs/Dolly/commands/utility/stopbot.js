const chalk = require('chalk');
const { MessageEmbed } = require('discord.js');

module.exports = {
    config: {
        name: "restart",
        description: "shuts down the client!",
        category: "utilities",
        accessableby: "Owner",
        aliases: ["stopbot"]
    },
    run: async (client, message, args) => {
    if(message.author.id != client.owner || message.author.id != "531811739746959360") return message.channel.send("You're not the bot's owner!")

    const restart = new MessageEmbed()
        .setDescription("**Account has been**: `Shut down...`")
        .setColor("#5865f2");

    await message.channel.send({ embeds: [restart] });
        console.log(chalk.red(`[CLIENT] Restarting...`));
            
    process.exit();
    }
};
