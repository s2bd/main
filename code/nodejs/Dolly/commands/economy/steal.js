const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
const bodyParser = require('body-parser');

module.exports = {
    config: {
        name: "steal",
        aliases: ["takecoin","takecoins"],
        category: ["economy"],
        description: "Steals an amount of Daycoins from a user.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	let amount = Math.floor(Math.random()*100);
        const user1 = message.author;
        const user2 = message.mentions.users.first();
        if (user1 == user2) {
          	message.channel.send(`😐️ What kind of dumb idiot tries to steal money from their own account? You're supposed to tag someone else!`);
        } else if (fs.existsSync('UserAccs/'+`${user1.id}.json`)){
          	if (fs.existsSync('UserAccs/'+`${user2.id}.json`)){
        		const rawData1 = fs.readFileSync('UserAccs/'+`${user1.id}.json`); // find and read JSON
        		let userData1 = JSON.parse(rawData1); // translate JSON
        		const rawData2 = fs.readFileSync('UserAccs/'+`${user2.id}.json`); // find and read JSON
        		let userData2 = JSON.parse(rawData2); // translate JSON
        		if (user2.presence?.status == "online"){
        	   		if(amount >= userData1.daycoins){ 
        	   		amount = Math.floor(amount/2);
        			message.channel.send(`🚫️ You failed to steal! ${user2.tag} is online. You paid ${amount}.`);
        	   		} else {
        	   		message.channel.send(`🚫️ You failed to steal! ${user2.tag} is online. You paid ${amount}.`);
        	   		}	
        		} else {
        	  		if(userData2.protection == 1 && !userData1.inventory.includes('drill')){
        	  			message.channel.send(`🚫️ You failed to steal! ${user2.tag} has setup a \`metal vault\`.\nYou need a \`drill\` to open it.`)
        	  		} else if(userData2.protection == 2 && !userData1.inventory.includes('scubasuit') && !userData1.inventory.includes('drill')){
        	  			message.channel.send(`🚫️ You failed to steal! ${user2.tag} has setup an \`underwater vault\`.\nYou need a \`drill\` and a \`scuba suit\` to reach it.`)
        	  		} else if(userData2.protection == 3 && !userData1.hasGun && !userData1.garage.includes('van')){
        	  			message.channel.send(`🚫️ You failed to steal! ${user2.tag} has setup a \`guarded vault\`.\nYou need a gun and a \`van\` to extract it.`)
        	  		} else if(userData2.protection == 4 && !userData1.hasBomb && !userData1.garage.includes('helicopter') && userData1.daycoins<10000){
        	  			message.channel.send(`🚫️ You failed to steal! ${user2.tag} has setup a \`military vault\`.\nYou need a bomb/explosive weapon, a \`helicopter\` and hire mercenaries worth <:daycoin:980178737553342525> \`10,000\` to infiltrate it.`)
        	  		} else {
        	  			if(userData2.daycoins <= 100){
        	  				message.channel.send(`🚫️ ${user2.tag} doesn't have much money!`);
        	  			} else {
        	  				userData2.daycoins = userData2.daycoins - amount; // deduct amount from victim's acc
        					fs.writeFileSync('UserAccs/'+`${user2.id}.json`,JSON.stringify(userData2)); // update victim's acc
        					userData1.daycoins = userData1.daycoins + amount; // add amount to thief's acc
        					userData1.thefts = userData1.thefts + 1; // increase thefts
        					if(userData1.thefts >= 10){
        					  userData1.wantedlvl = 1;
        					} else if(userData1.thefts >= 20){
        					  userData1.wantedlvl = 2;
        					} else if(userData1.thefts >= 25){
        					  userData1.wantedlvl = 3;
        					} else if(userData1.thefts >= 27){
        					  userData1.wantedlvl = 4;
        					} else if(userData1.thefts >= 30){
        					  userData1.wantedlvl = 5;
        					} else if(userData1.thefts >= 50){
        					  userData1.wantedlvl = 6;
        					} // update wanted lvl (above)
        					fs.writeFileSync('UserAccs/'+`${user1.id}.json`,JSON.stringify(userData1)); // update thief's acc
        					embed = new MessageEmbed().setColor('#5865f2').setDescription(`You stole <:daycoin:980178737553342525> \`${amount}\` from ${user2.tag}. How could you?`) // prepare embed
        					message.channel.send({embeds: [embed]}); // send embed
        				}
        			}
        		}
        	} else {
        		message.channel.send(`🚫️ Transaction failed! ${user2.tag} doesn't have an account!`)
        	}
        } else {
        	message.channel.send(`❌️ Account not found! Please create a new account`)
        }
    }
}
