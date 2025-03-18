const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "inventory",
        aliases: ["inv","bag","pocket","pockets"],
        category: ["account"],
        description: "Displays current account inventory.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        embed = new MessageEmbed().setColor('#5865f2').setTitle(`${message.author.username}#${message.author.discriminator}'s Inventory`).setDescription(`For checking currency, type \`${client.prefix}balance\``)
        for(var i=0; i < userData.inventory.length; i++){
          embed.addField(`ID: ${userData.inventory[i].id} ${userData.inventory[i].icon}`,`Durability: ${userData.inventory[i].dura}\nSelling price: <:daycoin:980178737553342525> ${userData.inventory[i].price}`,true);
        }
        message.channel.send({embeds: [embed]}); // send embed
        } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        }
    }
}
