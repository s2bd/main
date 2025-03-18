const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "beg",
        aliases: ["plead","begg"],
        category: ["economy"],
        description: "Beg for some money.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	let amount = Math.floor(Math.random()*10);
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        userData.job = {"icon":":person_bowing:","id":"beggar"}; // set new job
        
         if (amount % 2==0 && amount > 0){ 
          userData.daycoins = userData.daycoins + amount; // add to account
          fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update user's JSON file
          console.log(`${amount}`);
          message.channel.send(`A kind stranger donated <:daycoin:980178737553342525>\`${amount}\` to you.`);
         } else if(amount % 9==0){
          amount = amount * 10;
          userData.daycoins = userData.daycoins + amount; // add to account
          fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update user's JSON file
          console.log(`${amount}`);
          message.channel.send(`A very generous stranger donated <:daycoin:980178737553342525>\`${amount}\` to you`)
          } else if(amount == 0){
          console.log(`${amount}`);
          message.channel.send(`You waited but unfortunately no one noticed you. No donations this time.`)
          } else {
          userData.daycoins = userData.daycoins + amount; // add to account
          fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update user's JSON file
          console.log(`${amount}`);
          message.channel.send(`A kind stranger donated <:daycoin:980178737553342525>\`${amount}\` to you. Save it up for something nice!`)
         }
        } else {
        message.channel.send(`You begged for money, but sadly no one responded. Because you do not have citizenship status. Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        
        }
}
}
        
