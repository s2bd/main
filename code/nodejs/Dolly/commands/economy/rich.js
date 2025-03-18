const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "rich",
        aliases: ["richlist","leaderboard","richranking","richrank"],
        category: ["currency"],
        description: "Displays ranking of top 10 richest users in a server.",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        message.channel.send(`Sorry, this feature isn't working properly yet. Stay tuned for updates.`);
        /*var users = new Array();
        var addedUsers = [...users];
        const server = message.guild;
        server.members.fetch().then(members => {
          members.forEach(member => {
            console.log(member.user.id);
            if (fs.existsSync('UserAccs/'+`${member.user.id}.json`)){
              var rawData = fs.readFileSync('UserAccs/'+`${member.user.id}.json`);
              var userData = JSON.parse(rawData);
              console.log(member.user.id+" added!");
              var feed = {name: userData.name, daycoins: userData.daycoins};
              addedUsers = [...feed, ...addedUsers];
            }
           });
         });
         console.log(addedUsers[0]);
         function compareDayCoins(a,b){
           if (a.daycoins > b.daycoins) {
            return -1;
           }
           if (a.daycoins < b.daycoins) {
             return 1;
           }
           return 0;
         }
         users.sort(compareDayCoins);
         console.log(addedUsers);
         var toptenusers = addedUsers.slice(0,10);
         console.log(toptenusers);
         const embed = new MessageEmbed().setColor('#5865f2').setTitle(`Top 10 richest users in ${message.channel.guild.name}`).setDescription(`1) ${tontenusers[0].username}\n2) ${tontenusers[1].username}\n3) ${tontenusers[2].username}\n4) ${tontenusers[3].username}\n5) ${tontenusers[4].username}\n6) ${tontenusers[5].username}\n7) ${tontenusers[6].username}\n8) ${tontenusers[7].username}\n9) ${tontenusers[8].username}\n10) ${tontenusers[9].username}\n`);
         message.channel.send({embeds: [embed]});*/
    }
}
