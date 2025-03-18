const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
module.exports = {
    config: {
        name: "daily",
        aliases: ["dailycoins","coinsdaily"],
        category: ["economy"],
        description: "Collect your daily coin rewards",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        currentDate = (new Date()).getTime();
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        console.log(currentDate - userData.lastDaily);
        if(currentDate - userData.lastDaily > 8640000){
          if(userData.premium){
            userData.daycoins = userData.daycoins + 500; // daycoins
            userData.nytecoins = userData.nytecoins + 10; // nytecoins
            userData.lastDaily = (new Date()).getTime(); // update last daily
            console.log(`${message.author} from ${message.channel.guild.name} claimed their PREMIUM daily rewards!`);
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update user's JSON file
            message.channel.send(`${message.author.tag} claimed <:daycoin:980178737553342525> \`500\` and <:nytecoin:980178777118216192> \`10\`!`)
          } else {
            const diamondemoji = client.emojis.cache.find(emoji => emoji.name === 'diamond');
            const premium = new MessageEmbed().setColor(`#ffd700`).setDescription(`**Upgrade to Premium!**\nGet **2x** the number of daily <:daycoin:980178737553342525> plus **10** <:nytecoin:980178777118216192> every day!`)
            userData.daycoins = userData.daycoins + 250; // daycoins
            userData.lastDaily = (new Date()).getTime(); // update last daily
            console.log(`${message.author} from ${message.channel.guild.name} claimed their STANDARD daily rewards!`);
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update user's JSON file
            message.channel.send(`${message.author.tag} claimed <:daycoin:980178737553342525> \`250\`!`);
            message.channel.send({embeds: [premium]});
          }
        } else {
          return message.channel.send(`Sorry, you already claimed today's daily reward!`)
        }
      } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
      }
}
}
        
