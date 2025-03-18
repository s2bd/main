const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "transfer",
        aliases: ["sendcoin","sendcoins"],
        category: ["currency"],
        description: "Displays current account balance.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	let amount = 0;
    	if (Number.isInteger(parseInt(args[1]))){
    	  amount = parseInt(args[1]);
    	  //message.channel.send(`You got the format wrong, my friend. It's \`${client.prefix}transfer <user> <amount>\``);
    	} else if(Number.isInteger(parseInt(args[0]))){
    	  amount = parseInt(args[0]);
    	} else {
    	  message.channel.send(`You got the format wrong, my friend. It's \`${client.prefix}transfer <user> <amount>\``);
    	}
        const user1 = message.author;
        const user2 = message.mentions.users.first();
        if (user1 == user2) {
          message.channel.send(`😐️ What kind of dumb idiot sends money to their own account? You're supposed to tag someone else!`);
        } else if (fs.existsSync('UserAccs/'+`${user1.id}.json`)){
          if (fs.existsSync('UserAccs/'+`${user2.id}.json`)){
        	const rawData1 = fs.readFileSync('UserAccs/'+`${user1.id}.json`); // find and read JSON
        	let userData1 = JSON.parse(rawData1); // translate JSON
        	const rawData2 = fs.readFileSync('UserAccs/'+`${user2.id}.json`); // find and read JSON
        	let userData2 = JSON.parse(rawData2); // translate JSON
        	if (amount > userData1.daycoins){ 
        		message.channel.send(`🚫️ Transaction failed! You don't have that many coins!`);
        	} else {
        	userData1.daycoins = userData1.daycoins - amount; // deduct amount
        	fs.writeFileSync('UserAccs/'+`${user1.id}.json`,JSON.stringify(userData1)); // update sender's JSON file
        	userData2.daycoins = userData2.daycoins + amount; // add amount
        	fs.writeFileSync('UserAccs/'+`${user2.id}.json`,JSON.stringify(userData2)); // update recipient's JSON file
        	embed = new MessageEmbed().setColor('#5865f2').setDescription(`Transferred <:daycoin:980178737553342525> \`${amount}\` to ${user2.tag}`) // prepare embed
        	message.channel.send({embeds: [embed]}); // send embed
        	}
        } else {
        	
        	message.channel.send(`🚫️ Transaction failed! Receiver doesn't have an account!`)
        	
        	}
        } else {
        	message.channel.send(`❌️ Account not found! Please create a new account`)
        }
      
    }
}
