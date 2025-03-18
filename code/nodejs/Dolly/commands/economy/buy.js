const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "buy",
        aliases: ["buyitem","purchase","acquire"],
        category: ["economy"],
        description: "Buy an item listed on the shop.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        const toolData = JSON.parse(fs.readFileSync('Shop/tools.json'));
        const weaponData = JSON.parse(fs.readFileSync('Shop/weapons.json'));
        //const vehicleData = JSON.parse(fs.readFileSync('Shop/vehicles.json'));
        const protectionData = JSON.parse(fs.readFileSync('Shop/protection.json'));
        //const blackmarketData = JSON.parse(fs.readFileSync('Shop/blackmarket.json'));
        //const nftData = JSON.parse(fs.readFileSync('Shop/nfts.json'));

        itemchosen = String(args[0]);
       
        for (var i=0; i < toolData.items.length; i++){
          if(toolData.items[i].id == itemchosen){
            if(toolData.items[i].price > userData.daycoins){
              return message.channel.send(`Sorry, you don't have enough money!`);
            } else {
              if(toolData.items[i].stock < 1){
                return message.channel.send(`Sorry, that item isn't available at the moment.`);
              } else {
            userData.daycoins = userData.daycoins - toolData.items[i].price; // deduct amount
            userData.inventory.push({id:toolData.items[i].id,icon:toolData.items[i].icon,dura:50,price:toolData.items[i].price * 0.5}); // add to inventory
            fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            toolData.items[i].stock = toolData.items[i].stock - 1; // deduct stock
            fs.writeFileSync(`Shop/tools.json`,JSON.stringify(toolData));// update stock
            console.log(`${message.author.tag} purchased ${toolData.items[i].name}!`);
            return message.channel.send(`${message.author.tag} purchased ${toolData.items[i].name}!`);
            }
          }
        }
        }
        
        for (var i=0; i < weaponData.items.length; i++){
          if(weaponData.items[i].id == itemchosen){
            if(weaponData.items[i].price > userData.daycoins){
              return message.channel.send(`Sorry, you don't have enough money!`);
            } else {
              if(weaponData.items[i].stock < 1){
                return message.channel.send(`Sorry, that item isn't available at the moment.`);
              } else {
                if(weaponData.items[i].id=="pistol" || weaponData.items[i].id=="smg" || weaponData.items[i].id=="shotgun"){
                	userData.daycoins = userData.daycoins - weaponData.items[i].price; // deduct amount
            		userData.inventory.push({id:weaponData.items[i].id,icon:weaponData.items[i].icon,dura:50,price:weaponData.items[i].price * 0.5}); // add to inventory
            		userData.hasGun = true;
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		weaponData.items[i].stock = weaponData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/weapons.json`,JSON.stringify(weaponData));// update stock
            		console.log(`${message.author.tag} purchased ${weaponData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${weaponData.items[i].name}!`);
                } else if(weaponData.items[i].id=="knife"){
                	userData.daycoins = userData.daycoins - weaponData.items[i].price; // deduct amount
            		userData.inventory.push({id:weaponData.items[i].id,icon:weaponData.items[i].icon,dura:50,price:weaponData.items[i].price * 0.5}); // add to inventory
            		userData.hasBlade = true;
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		weaponData.items[i].stock = weaponData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/weapons.json`,JSON.stringify(weaponData));// update stock
            		console.log(`${message.author.tag} purchased ${weaponData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${weaponData.items[i].name}!`);
                } else {
                	userData.daycoins = userData.daycoins - weaponData.items[i].price; // deduct amount
            		userData.inventory.push({id:weaponData.items[i].id,icon:weaponData.items[i].icon,dura:50,price:weaponData.items[i].price * 0.5}); // add to inventory
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		weaponData.items[i].stock = weaponData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/weapons.json`,JSON.stringify(weaponData));// update stock
            		console.log(`${message.author.tag} purchased ${weaponData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${weaponData.items[i].name}!`);
                }
            }
          }
        }
        }
        for (var i=0; i < protectionData.items.length; i++){
          if(protectionData.items[i].id == itemchosen){
            if(protectionData.items[i].price > userData.daycoins){
              return message.channel.send(`Sorry, you don't have enough money!`);
            } else {
              if(protectionData.items[i].stock < 1){
                return message.channel.send(`Sorry, that item isn't available at the moment.`);
              } else {
                if(protectionData.items[i].id=="metalvault"){
                	userData.daycoins = userData.daycoins - protectionData.items[i].price; // deduct amount
            		userData.inventory.push({id:protectionData.items[i].id,icon:protectionData.items[i].icon,dura:50,price:protectionData.items[i].price * 0.5}); // add to inventory
            		userData.protection = 1; // update protection level
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		protectionData.items[i].stock = protectionData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/protection.json`,JSON.stringify(protectionData));// update stock
            		console.log(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
                } else if(protectionData.items[i].id=="underwatervault"){
                	userData.daycoins = userData.daycoins - protectionData.items[i].price; // deduct amount
            		userData.inventory.push({id:protectionData.items[i].id,icon:protectionData.items[i].icon,dura:50,price:protectionData.items[i].price * 0.5}); // add to inventory
            		userData.protection = 2; // update protection level
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		protectionData.items[i].stock = protectionData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/protection.json`,JSON.stringify(protectionData));// update stock
            		console.log(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
                } else if(protectionData.items[i].id=="guardedvault"){
                	userData.daycoins = userData.daycoins - protectionData.items[i].price; // deduct amount
            		userData.inventory.push({id:protectionData.items[i].id,icon:protectionData.items[i].icon,dura:50,price:protectionData.items[i].price * 0.5}); // add to inventory
            		userData.protection = 3; // update protection level
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		protectionData.items[i].stock = protectionData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/protection.json`,JSON.stringify(protectionData));// update stock
            		console.log(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
                } else if(protectionData.items[i].id=="militaryvault"){
                	userData.daycoins = userData.daycoins - protectionData.items[i].price; // deduct amount
            		userData.inventory.push({id:protectionData.items[i].id,icon:protectionData.items[i].icon,dura:50,price:protectionData.items[i].price * 0.5}); // add to inventory
            		userData.protection = 4; // update protection level
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		protectionData.items[i].stock = protectionData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/protection.json`,JSON.stringify(protectionData));// update stock
            		console.log(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
                } else {
                	userData.daycoins = userData.daycoins - protectionData.items[i].price; // deduct amount
            		userData.inventory.push({id:protectionData.items[i].id,icon:protectionData.items[i].icon,dura:50,price:protectionData.items[i].price * 0.5}); // add to inventory
            		fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
            		protectionData.items[i].stock = protectionData.items[i].stock - 1; // deduct stock
            		fs.writeFileSync(`Shop/protection.json`,JSON.stringify(protectionData));// update stock
            		console.log(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
            		return message.channel.send(`${message.author.tag} purchased ${protectionData.items[i].name}!`);
                }
            }
          }
        }
        }     
        message.channel.send(`Sorry, couldn't find that item! Try browsing the marketplace with \`${client.prefix}shop\` to check if the item exists or retry by typing the item name without any spaces, e.g. \`${client.prefix}buy metalvault\``);
        } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        }
 }
 }
        
