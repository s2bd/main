const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "cardroll",
        aliases: ["gacha","roll","gacharoll","gachacards"],
        category: ["gacha"],
        description: "Rolls some gacha, collect cards for battles",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
          const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
          let userData = JSON.parse(rawData); // translate JSON
          const common = JSON.parse(fs.readFileSync('Gacha/common.json'));
          const uncommon = JSON.parse(fs.readFileSync('Gacha/uncommon.json'));
          const rare = JSON.parse(fs.readFileSync('Gacha/rare.json'));
          const epic = JSON.parse(fs.readFileSync('Gacha/epic.json'));
          const legendary = JSON.parse(fs.readFileSync('Gacha/legendary.json'));
          const nav_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('roll').setLabel('<:daycoin:980178737553342525> 600').setStyle('SECONDARY'),new MessageButton().setCustomId('rollx10').setLabel('<:daycoin:980178737553342525> 6000').setStyle('SECONDARY'),new MessageButton().setCustomId('dismiss').setLabel('❌️ Dismiss').setStyle('DANGER'));
          const card_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('claim').setLabel(`Claim!`).setStyle('SUCCESS'),new MessageButton().setCustomId('skip').setLabel(`Skip!`).setStyle(`SECONDARY`));

              var lucktoken = Math.floor(Math.random() * 1000);
              if(lucktoken >= 500){
                var index = Math.floor(Math.random() * common.items.length);
                if(common.items[index].isPerson){
                  var card_embed = new MessageEmbed().setTitle(`${common.items[index].name}`).setColor('#ebebeb').setImage(`${common.items[index].image}`).setDescription(`${common.items[index].info}\nHP: \`${common.items[index].health}\`\t<:daycoin:980178737553342525> ${common.items[index].price}\nATK: \`${common.items[index].attack}\`\tType: ${common.items[index].attackType}\nDEF: \`${common.items[index].defense}\`\tType: ${common.items[index].defenseType}\nRace: \`${common.items[index].race}\`\tClass: \`${common.items[index].class}\``).addField(`Gender:`,`${common.items[index].gender}`,true).setFooter({text:`${common.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(common.items[index].price > userData.daycoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.daycoins = userData.daycoins - common.items[index].price; // deduct amount
                        userData.cards.push(common.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${common.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${common.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                } else if(common.items[index].isItem){
                var card_embed = new MessageEmbed().setTitle(`${common.items[index].name}`).setColor('#ebebeb').setImage(`${common.items[index].image}`).setDescription(`${common.items[index].info}\n\<:daycoin:980178737553342525> ${common.items[index].price}\nClass: \`${common.items[index].class}\``).addField(`Rarity:`,`Common`,true).setFooter({text:`${common.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(common.items[index].price > userData.daycoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.daycoins = userData.daycoins - common.items[index].price; // deduct amount
                        userData.cards.push(common.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${common.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${common.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                }
                
                
              
              } else if(lucktoken >= 200 && lucktoken < 500 ){
                var index = Math.floor(Math.random() * uncommon.items.length);
                if(uncommon.items[index].isPerson){
                  var card_embed = new MessageEmbed().setTitle(`${uncommon.items[index].name}`).setColor('#00e7f7').setImage(`${uncommon.items[index].image}`).setDescription(`${uncommon.items[index].info}\nHP: \`${uncommon.items[index].health}\`\t<:daycoin:980178737553342525> ${uncommon.items[index].price}\nATK: \`${uncommon.items[index].attack}\`\tType: ${uncommon.items[index].attackType}\nDEF: \`${uncommon.items[index].defense}\`\tType: ${uncommon.items[index].defenseType}\nRace: \`${uncommon.items[index].race}\`\tClass: \`${uncommon.items[index].class}\``).addField(`Gender:`,`${uncommon.items[index].gender}`,true).setFooter({text:`${uncommon.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(uncommon.items[index].price > userData.daycoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.daycoins = userData.daycoins - uncommon.items[index].price; // deduct amount
                        userData.cards.push(uncommon.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${uncommon.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${uncommon.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                } else if(uncommon.items[index].isItem){
                var card_embed = new MessageEmbed().setTitle(`${uncommon.items[index].name}`).setColor('#00e7f7').setImage(`${uncommon.items[index].image}`).setDescription(`${uncommon.items[index].info}\n\<:daycoin:980178737553342525> ${uncommon.items[index].price}\nClass: \`${uncommon.items[index].class}\``).addField(`Rarity:`,`Common`,true).setFooter({text:`${uncommon.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(uncommon.items[index].price > userData.daycoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.daycoins = userData.daycoins - uncommon.items[index].price; // deduct amount
                        userData.cards.push(uncommon.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${uncommon.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${uncommon.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                }
              
              } else if(lucktoken >= 100 && lucktoken < 200 ){
                var index = Math.floor(Math.random() * rare.items.length);
                if(rare.items[index].isPerson){
                  var card_embed = new MessageEmbed().setTitle(`${rare.items[index].name}`).setColor('#5b00f7').setImage(`${rare.items[index].image}`).setDescription(`${rare.items[index].info}\nHP: \`${rare.items[index].health}\`\t<:nytecoin:980178777118216192> ${rare.items[index].price}\nATK: \`${rare.items[index].attack}\`\tType: ${rare.items[index].attackType}\nDEF: \`${rare.items[index].defense}\`\tType: ${rare.items[index].defenseType}\nRace: \`${rare.items[index].race}\`\tClass: \`${rare.items[index].class}\``).addField(`Gender:`,`${rare.items[index].gender}`,true).setFooter({text:`${rare.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(rare.items[index].price > userData.nytecoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.nytecoins = userData.nytecoins - rare.items[index].price; // deduct amount
                        userData.cards.push(rare.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${rare.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${rare.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                } else if(rare.items[index].isItem){
                var card_embed = new MessageEmbed().setTitle(`${rare.items[index].name}`).setColor('#5b00f7').setImage(`${rare.items[index].image}`).setDescription(`${rare.items[index].info}\n\<:nytecoin:980178777118216192> ${rare.items[index].price}\nClass: \`${rare.items[index].class}\``).addField(`Rarity:`,`Common`,true).setFooter({text:`${rare.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(rare.items[index].price > userData.nytecoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.nytecoins = userData.nytecoins - rare.items[index].price; // deduct amount
                        userData.cards.push(rare.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${rare.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${rare.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                }
              
              } else if(lucktoken >= 50 && lucktoken < 100 ){
                var index = Math.floor(Math.random() * epic.items.length);
                if(epic.items[index].isPerson){
                  var card_embed = new MessageEmbed().setTitle(`${epic.items[index].name}`).setColor('#7704bf').setImage(`${epic.items[index].image}`).setDescription(`${epic.items[index].info}\nHP: \`${epic.items[index].health}\`\t<:nytecoin:980178777118216192> ${epic.items[index].price}\nATK: \`${epic.items[index].attack}\`\tType: ${epic.items[index].attackType}\nDEF: \`${epic.items[index].defense}\`\tType: ${epic.items[index].defenseType}\nRace: \`${epic.items[index].race}\`\tClass: \`${epic.items[index].class}\``).addField(`Gender:`,`${epic.items[index].gender}`,true).setFooter({text:`${epic.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(epic.items[index].price > userData.nytecoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.nytecoins = userData.nytecoins - epic.items[index].price; // deduct amount
                        userData.cards.push(epic.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${epic.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${epic.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                } else if(epic.items[index].isItem){
                var card_embed = new MessageEmbed().setTitle(`${epic.items[index].name}`).setColor('#7704bf').setImage(`${epic.items[index].image}`).setDescription(`${epic.items[index].info}\n\<:nytecoin:980178777118216192> ${epic.items[index].price}\nClass: \`${epic.items[index].class}\``).addField(`Rarity:`,`Common`,true).setFooter({text:`${epic.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(epic.items[index].price > userData.nytecoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.nytecoins = userData.nytecoins - epic.items[index].price; // deduct amount
                        userData.cards.push(epic.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${epic.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${epic.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                }
              
              } else if(lucktoken >= 5 && lucktoken < 50 ){
                var index = Math.floor(Math.random() * legendary.items.length);
                if(legendary.items[index].isPerson){
                  var card_embed = new MessageEmbed().setTitle(`${legendary.items[index].name}`).setColor('#ffc105').setImage(`${legendary.items[index].image}`).setDescription(`${legendary.items[index].info}\nHP: \`${legendary.items[index].health}\`\t<:nytecoin:980178777118216192> ${legendary.items[index].price}\nATK: \`${legendary.items[index].attack}\`\tType: ${legendary.items[index].attackType}\nDEF: \`${legendary.items[index].defense}\`\tType: ${legendary.items[index].defenseType}\nRace: \`${legendary.items[index].race}\`\tClass: \`${legendary.items[index].class}\``).addField(`Gender:`,`${legendary.items[index].gender}`,true).setFooter({text:`${legendary.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(legendary.items[index].price > userData.nytecoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.nytecoins = userData.nytecoins - legendary.items[index].price; // deduct amount
                        userData.cards.push(legendary.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${legendary.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${legendary.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                } else if(legendary.items[index].isItem){
                var card_embed = new MessageEmbed().setTitle(`${legendary.items[index].name}`).setColor('#ffc105').setImage(`${legendary.items[index].image}`).setDescription(`${legendary.items[index].info}\n\<:nytecoin:980178777118216192> ${legendary.items[index].price}\nClass: \`${legendary.items[index].class}\``).addField(`Rarity:`,`Common`,true).setFooter({text:`${legendary.items[index].source}`});
                  var cardMenu = await message.channel.send({embeds: [card_embed], components: [card_buttons]});
                  const cardcollector = cardMenu.createMessageComponentCollector();
                  cardcollector.on('collect', async (interaction) => {
                    if (!interaction.deferred) await interaction.deferUpdate();
                    if (interaction.customId === 'claim') {
                      if(legendary.items[index].price > userData.nytecoins){
                        return message.channel.send(`Sorry, you don't have enough money!`);
                      } else {
                        userData.nytecoins = userData.nytecoins - legendary.items[index].price; // deduct amount
                        userData.cards.push(legendary.items[index]); // add to cards
                        fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
                         console.log(`${message.author.tag} claimed ${legendary.items[index].name}!`);
                         cardMenu.delete();
                         return message.channel.send(`${message.author.tag} claimed ${legendary.items[index].name}!`);
                      }
                    } else if(interaction.customId === 'skip'){
                      cardMenu.delete()
                    }
                  });
                }
              
              } else {
                return message.channel.send(`Gacha Failed!`);
              
              }
              
          
        } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        }
    }
}
