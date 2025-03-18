const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "balance",
        aliases: ["bal","account"],
        category: ["currency"],
        description: "Displays current account balance.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        const diamondemoji = client.emojis.cache.find(emoji => emoji.name === 'diamond');
        const accountType = userData.premium ? `${diamondemoji} premium` : `:bust_in_silhouette: standard`
        const inventory = [];
        for(var i=0; i < userData.inventory.length; i++){
          inventory.push(userData.inventory[i].id);
        }
        embed = new MessageEmbed().setColor('#5865f2').setTitle(`${message.author.username}#${message.author.discriminator}'s Account`).addField(`DayCoins:  `,`<:daycoin:980178737553342525> \`${userData.daycoins}\``,true).addField(`NyteCoins:  `,`<:nytecoin:980178777118216192> \`${userData.nytecoins}\``,true).addField(`Membership:  `,`<:patreon:980179304015085588> ${userData.membership}`,true).addField("Wanted Lvl:", `🚨️ \`${userData.wantedlvl}\``, true).addField(`Occupation:`,`${userData.job.icon} ${userData.job.id}`,true).addField(`Type:  `,`${accountType}`,true).addField(`🗄️ Quick Inventory:`,`\`\`\`\n${inventory}\`\`\``,true) // prepare embed
        message.channel.send({embeds: [embed]}); // send embed
        } else {
        message.channel.send(`❌️ Account not found! Creating new account...`)
        let newAcc = {
        	id: `${message.author.id}`,
        	name: `${message.author.username}`,
        	hash: `${message.author.discriminator}`,
        	daycoins: 0,
        	nytecoins: 0,
        	membership: `none`,
        	premium: false,
        	totalcommands: 0,
        	protection: 0,
        	job: {"icon":"","id":"none"},
        	inventory: [],
        	cards: [],
        	friends: [],
        	weapons: [],
        	hasBlade: false,
        	hasGun: false,
        	hasBomb: false,
        	garage: [],
        	hasVehicle: false,
        	badges: [],
        	wantedlvl: 0,
        	thefts: 0,
        	robberies: 0,
        	kidnaps: 0,
        	arrests: 0,
        	inPrison: false,
        	lastDaily: (new Date()).getTime(),
          }
        let newAccData = JSON.stringify(newAcc);
        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,newAccData);
        message.channel.send(`✅️ Account created. Try again.`)
        }
    }
}
