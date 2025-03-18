const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "fish",
        aliases: ["catchfish","fishing"],
        category: ["economy"],
        description: "Fish in the nearest waterbody you can find!",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        //var no_fishingrod = true;
    	let lucktoken = Math.floor(Math.random()*100);
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        //userData.inventory.forEach(inventory =>{
          //if(inventory.id.includes("fishingrod")){
           // no_fishingrod: false;
         // }
        //});
        //if(no_fishingrod){
          //return message.channel.send(`You don't have a fishing rod! Purchase one from the tool shop. Type \`${client.prefix}shop\`.`);
        //}
        for(var i=0; i < userData.inventory.length; i++){
          if(userData.inventory[i].id=="fishingrod"){
          if(lucktoken >= 50){
            var fish = {id:"salmon",icon:"<:salmon:981885773441806376>",price:20}
            var embed = new MessageEmbed().setDescription(`You caught a **${fish.id}**!\n\n<:arrowintobox:981859478603456563> Stored into inventory.\n You can sell the fish for <:daycoin:980178737553342525> ${fish.price}.`).setImage('https://media.fisheries.noaa.gov/styles/original/s3/dam-migration/640x427-coho-salmon.png');
            const confirm_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('yes').setLabel('Yes').setStyle('DANGER'),new MessageButton().setCustomId('no').setLabel('No').setStyle('SECONDARY'));
            userData.inventory[i].dura -= 1; // decrease durability
            if(userData.inventory[i].dura <= 0){
              message.channel.send(`Oops! Your fishing rod lost its durability and broke! Time to buy a new one!`);
              if(i=0){userData.inventory.splice(0,1);
              }else{userData.inventory.splice(i-1,i);
              }
              }
            userData.inventory.push(fish);
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
            console.log(`${message.author.tag} caught a ${fish.id}!`)
            return message.channel.send({embeds: [embed]});
          } else if(lucktoken >= 25 && lucktoken < 50){
            var fish = {id:"tuna",icon:"<:tuna:981885808007065620>",price:40}
            var embed = new MessageEmbed().setDescription(`You caught a **${fish.id}**!\n\n<:arrowintobox:981859478603456563> Stored into inventory.\n You can sell the fish for <:daycoin:980178737553342525> ${fish.price}.`).setImage('https://media.fisheries.noaa.gov/styles/original/s3/dam-migration/640x427-western-atlantic-bluefin-tuna.png');
            userData.inventory[i].dura -= 1; // decrease durability
            if(userData.inventory[i].dura <= 0){
              message.channel.send(`Oops! Your fishing rod lost its durability and broke! Time to buy a new one!`);
              userData.inventory.splice(i-1,i);
              }
            userData.inventory.push(fish);
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
            console.log(`${message.author.tag} caught a ${fish.id}!`)
            return message.channel.send({embeds: [embed]});
          } else if(lucktoken >= 10 && lucktoken < 25){
            var fish = {id:"cod",icon:"<:cod:981885839866986556>",price:80}
            var embed = new MessageEmbed().setDescription(`You caught a **${fish.id}**!\n\n<:arrowintobox:981859478603456563> Stored into inventory.\n You can sell the fish for <:daycoin:980178737553342525> ${fish.price}.`).setImage('https://media.fisheries.noaa.gov/styles/original/s3/dam-migration/640x427-atlantic-cod.png');
            userData.inventory[i].dura -= 1; // decrease durability
            if(userData.inventory[i].dura <= 0){
              message.channel.send(`Oops! Your fishing rod lost its durability and broke! Time to buy a new one!`);
              userData.inventory.splice(i-1,i);
              }
            userData.inventory.push(fish);
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
            console.log(`${message.author.tag} caught a ${fish.id}!`)
            return message.channel.send({embeds: [embed]});
          } else if(lucktoken >= 5 && lucktoken < 10){
            var fish = {id:"hilsa",icon:"<:hilsa:981885867977232444>",price:160}
            var embed = new MessageEmbed().setDescription(`You caught a **${fish.id}**!\n\n<:arrowintobox:981859478603456563> Stored into inventory.\n You can sell the fish for <:daycoin:980178737553342525> ${fish.price}.`).setImage('http://tekno-yem.com/data/upload/balik-urun.png');
            userData.inventory[i].dura -= 1; // decrease durability
            if(userData.inventory[i].dura <= 0){
              message.channel.send(`Oops! Your fishing rod lost its durability and broke! Time to buy a new one!`);
              userData.inventory.splice(i-1,i);
              }
            userData.inventory.push(fish);
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
            console.log(`${message.author.tag} caught a ${fish.id}!`)
            return message.channel.send({embeds: [embed]});
          } else{
            var embed = new MessageEmbed().setDescription(`Oops! You couldn't catch anything this time.`);
            userData.inventory[i].dura -= 1; // decrease durability
            if(userData.inventory[i].dura <= 0){
              message.channel.send(`Oops! Your fishing rod lost its durability and broke! Time to buy a new one!`);
              userData.inventory.splice(i-1,i);
              }
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
            console.log(`${message.author.tag} tried to fish, but caught nothing!`)
            return message.channel.send({embeds: [embed]});
          }
        }
        //return message.channel.send(`You don't have a fishing rod! Purchase one from the tool shop. Type \`${client.prefix}shop\`.`);
        }
} else {
  message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
  }
}
}       
