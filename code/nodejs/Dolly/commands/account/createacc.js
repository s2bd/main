const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "createacc",
        aliases: ["newacc","createaccount","newaccount"],
        category: ["currency"],
        description: "Creates a new account for Dolly's economy",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){

        message.channel.send(`🚫️ You already have an account!`); // send embed
        } else {
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
        message.channel.send(`✅️ Account created.`)
        }
    }
}
