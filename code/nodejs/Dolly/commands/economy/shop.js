const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "shop",
        aliases: ["store","items","market"],
        category: ["economy"],
        description: "Explore the Dolly marketplace.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        const nav_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('tools').setLabel('🛠️ Tools').setStyle('PRIMARY'),new MessageButton().setCustomId('weapons').setLabel('⚔️ Weapons').setStyle('PRIMARY'),new MessageButton().setCustomId('protection').setLabel('🛡️ Protection').setStyle('PRIMARY'));
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        const toolData = JSON.parse(fs.readFileSync('Shop/tools.json'));
        const weaponData = JSON.parse(fs.readFileSync('Shop/weapons.json'));
        //const vehicleData = JSON.parse(fs.readFileSync('Shop/vehicles.json'));
        const protectionData = JSON.parse(fs.readFileSync('Shop/protection.json'));
        //const blackmarketData = JSON.parse(fs.readFileSync('Shop/blackmarket.json'));
        //const nftData = JSON.parse(fs.readFileSync('Shop/nfts.json'));
        //const buttonid = message.customId;
        const embed0 = new MessageEmbed().setColor('#5865f2').setTitle(`SHOP MENU`).setDescription(`Welcome to the central marketplace! If this is your first time browsing through, feel free to take your time to look around. There are multiple categories of items to choose from. Each serves a different purpose (obviously!). Please note that ${client.tag} doesn't offer any refunds for items that you didn't want to purchase. Play it fair, play it safe. Continue by hitting one of those buttons below.`).setImage(`https://media.discordapp.net/attachments/980533280510214164/980543249188020234/goldpile.gif?width=469&height=178`);
        const embed1 = new MessageEmbed().setColor('#ff0000').setTitle(`TOOLS SHOP`).setDescription(`Type \`${client.prefix}buy <item name>\` to purchase the item`);
        const embed2 = new MessageEmbed().setColor('#00ff00').setTitle(`WEAPONS SHOP`).setDescription(`Type \`${client.prefix}buy <item name>\` to purchase the item`);
        const embed3 = new MessageEmbed().setColor('#0000ff').setTitle(`PROTECTION SHOP`).setDescription(`Type \`${client.prefix}buy <item name>\` to purchase the item`);
        toolData.items.forEach(item => {
        	if(item.stock==0){
         		embed1.addField(`${item.name}`,`**Price:** <:daycoin:980178737553342525> ${item.price}\n**Info:** ${item.info}\n**Rarity:** ${item.rarity}\n**Stock:** Out-of-stock`,true)
        	} else {
         		embed1.addField(`${item.name}`,`**Price:** <:daycoin:980178737553342525> ${item.price}\n**Info:** ${item.info}\n**Rarity:** ${item.rarity}\n**Stock:** ${item.stock}`,true)
        	} 
        });
        weaponData.items.forEach(item => {
        	if(item.stock==0){
        		embed2.addField(`${item.name}`,`**Price:** <:daycoin:980178737553342525> ${item.price}\n**Info:** ${item.info}\n**Rarity:** ${item.rarity}\n**Stock:** Out-of-stock`,true)
        	} else {
         		embed2.addField(`${item.name}`,`**Price:** <:daycoin:980178737553342525> ${item.price}\n**Info:** ${item.info}\n**Rarity:** ${item.rarity}\n**Stock:** ${item.stock}`,true)
        	} 
        });
        protectionData.items.forEach(item => {
        	if(item.stock==0){
        		embed3.addField(`${item.name}`,`**Price:** <:daycoin:980178737553342525> ${item.price}\n**Info:** ${item.info}\n**Rarity:** ${item.rarity}\n**Stock:** Out-of-stock`,true)
        	} else {
         		embed3.addField(`${item.name}`,`**Price:** <:daycoin:980178737553342525> ${item.price}\n**Info:** ${item.info}\n**Rarity:** ${item.rarity}\n**Stock:** ${item.stock}`,true)
        	} 
        });
        const shopMenu = await message.channel.send({embeds: [embed0], components: [nav_buttons]});
        const collector = await shopMenu.createMessageComponentCollector();
        collector.on('collect', async (interaction) => {
        if (!interaction.deferred) await interaction.deferUpdate();
        if (interaction.customId === 'tools') {
              shopMenu.edit({embeds: [embed1], components: [nav_buttons]});
        } else if (interaction.customId === 'weapons') {
              shopMenu.edit({embeds: [embed2], components: [nav_buttons]});
        } else if (interaction.customId === 'protection') {
              shopMenu.edit({embeds: [embed3], components: [nav_buttons]});
        }
        });
        //message.channel.send({embeds: [embed1], components: [nav_buttons]}); 
        //message.channel.send({embeds: [embed1, embed2], components: [nav_buttons]});
        } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        }
}
}
        
