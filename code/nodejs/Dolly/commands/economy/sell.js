const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "sell",
        aliases: ["sellitem","itemsell"],
        category: ["economy"],
        description: "Sell items you no longer need!",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
          const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
          let userData = JSON.parse(rawData); // translate JSON
          itemchosen = String(args[0]);
          if(String(args[1])=="all"){
          var total = 0;
          var count = 0;
          for(var i=0; i < userData.inventory.length; i++){
            if(userData.inventory[i].id===itemchosen){
        	  userData.daycoins += userData.inventory[i].price;
        	  total += userData.inventory[i].price;
        	  count += 1;
        	  userData.inventory.splice(i,i+1);
            }
          }
          fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
          console.log(`${message.author.tag} sold all their ${itemchosen}! for ${total} daycoins!`)
          const itemssold = new MessageEmbed().setDescription(`**Items sold!**\nYou sold ${count}x ${itemchosen} for <:daycoin:980178737553342525> ${total}`);
          return message.channel.send({embeds: [itemssold]});
          }
          for(var i=0; i < userData.inventory.length; i++){
           if(userData.inventory[i].id==itemchosen){
           itemindex = i;
          }
          }
          i = itemindex;
          console.log(itemchosen);
          console.log(itemindex);
          console.log(userData.inventory[itemindex]);
          //if(userData.inventory[i].id==itemchosen){
              const price = userData.inventory[i].price;
              const embed = new MessageEmbed().setTitle(`${message.author.tag}`).setDescription(`Are you sure you want to sell ${itemchosen} for <:daycoin:980178737553342525> ${userData.inventory[i].price}?`)
              const confirm_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('yes').setLabel('Yes').setStyle('DANGER'),new MessageButton().setCustomId('no').setLabel('No').setStyle('SECONDARY'));
              const confirm = await message.channel.send({embeds: [embed],components: [confirm_buttons]});
              const collector = await confirm.createMessageComponentCollector();
              collector.on('collect', async (interaction) => {
                if (!interaction.deferred) await interaction.deferUpdate();
                if (interaction.customId === 'yes') {
                  const itemsold = new MessageEmbed().setDescription(`**Item sold!**\nYou sold ${itemchosen} for <:daycoin:980178737553342525> ${price}`);
        	  confirm.edit({embeds: [itemsold], components:[]});
        	  userData.inventory.splice(i,i); // remove item from inventory
        	  userData.daycoins += price;
    	          fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
    	          console.log(`${message.author.tag} sold ${itemchosen}! for ${price} daycoins!`)
                } else if(interaction.customId === 'no'){
                  const salecancel = new MessageEmbed().setDescription(`🚫️ Cancelled!`);
                  confirm.edit({embeds: [salecancel]});
                }
             });
             collector.on('end', () => {
             return message.channel.send(`Timeout!`);
          });
        //}
        //};
        //message.channel.send(`You don't have that item in your inventory! Type \`${client.prefix}inventory\` to check for the item ID.`);
} else {
  message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
  }
}
}       
