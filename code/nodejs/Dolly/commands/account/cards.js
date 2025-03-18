const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
module.exports = {
    config: {
        name: "cards",
        aliases: ["mycards","mycard","deck","mydeck"],
        category: ["gacha"],
        description: "Displays your collection of cards",
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
          const nav_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('prev').setEmoji('⬅').setStyle('PRIMARY'),new MessageButton().setCustomId('sell').setLabel('Sell').setStyle('DANGER').setDisabled(true),new MessageButton().setCustomId('next').setEmoji('➡').setStyle('PRIMARY'));
          var cards = userData.cards;
          var card = 0;
          if (cards.length == 0) return message.channel.send(`You don't have any cards! Use \`${client.prefix}gacharoll\` to begin collecting some!`);
          var embed = new MessageEmbed()
          .setTitle(`${message.author.tag}'s Cards | Card no.${card}\n${cards[card].name}`)
          .setColor('#5865f2')
          .setImage(`${cards[card].image}`)
          .setDescription(`${cards[card].info}\nHP: \`${cards[card].health}\`\t ${cards[card].rarity}\nATK: \`${cards[card].attack}\`\tType: ${cards[card].attackType}\nDEF: \`${cards[card].defense}\`\tType: ${cards[card].defenseType}\nRace: \`${cards[card].race}\`\tClass: \`${cards[card].class}\``).addField(`Gender:`,`${cards[card].gender}`,true)
          .setFooter({text:`${cards[card].source}`});
         const currentPage = await message.channel.send({embeds: [embed],components: [nav_buttons],allowedMentions: {repliedUser: false }});
    const collector = await currentPage.createMessageComponentCollector();
    collector.on('collect', async (interaction) => {
        if (!interaction.deferred) await interaction.deferUpdate();
        if (interaction.customId === 'prev') {
            card = card > 0 ? --card: cards.length -1;
        //} else if (interaction.customId === 'sell') {
            //if()
        } else if (interaction.customId === 'next') {
            card = card + 1 < cards.length ? ++card : 0;
        }
        var embed = new MessageEmbed()
          .setTitle(`${message.author.tag}'s Cards | Card no.${card}\n${cards[card].name}`)
          .setColor('#5865f2')
          .setImage(`${cards[card].image}`)
          .setDescription(`${cards[card].info}\nHP: \`${cards[card].health}\`\t<:daycoin:980178737553342525> ${cards[card].price}\nATK: \`${cards[card].attack}\`\tType: ${cards[card].attackType}\nDEF: \`${cards[card].defense}\`\tType: ${cards[card].defenseType}\nRace: \`${cards[card].race}\`\tClass: \`${cards[card].class}\``).addField(`Gender:`,`${cards[card].gender}`,true)
          .setFooter({text:`${cards[card].source}`});
        currentPage.edit({embeds: [embed],components: [nav_buttons] })
    	});
    	collector.on('end', () => {
        	currentPage.edit({embeds: [embed],components: [nav_buttons] })
    	});
    	return currentPage;        
     } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        }
    
    }
}
