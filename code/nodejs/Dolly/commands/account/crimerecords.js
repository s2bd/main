const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');

module.exports = {
    config: {
        name: "crimerecords",
        aliases: ["crime","crimes","criminalrecords"],
        category: ["currency"],
        description: "Displays stats and records of all criminal activities",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        if(userData.wantedlvl==1){
            var userStatus = "Street punk";
          } else if(userData.wantedlvl==2){
            var userStatus = "Urban thug";
          } else if(userData.wantedlvl==3){
            var userStatus = "Feared gangster";
          } else if(userData.wantedlvl==4){
            var userStatus = "Mafia member";
          } else if(userData.wantedlvl==5){
            var userStatus = "State criminal";
          } else if(userData.wantedlvl==6){
            var userStatus = "International fugitive";
          } else {
            var userStatus = "Clean citizen";
        }
        embed = new MessageEmbed().setColor('#5865f2').setTitle(`${message.author.username}#${message.author.discriminator}'s Crime Records`).addField(`Thefts: `,`:ninja: \`${userData.thefts}\``,true).addField(`Robberies: `,`:boom: \`${userData.robberies}\``,true).addField(`Kidnappings: `,`:placard: \`${userData.kidnaps}\``,true).addField("Times arrested: ", `:oncoming_police_car: \`${userData.arrests}\``, true).addField(`Current status: `,`\`\`\`\n${userStatus}\`\`\``,true) // prepare embed
        message.channel.send({embeds: [embed]}); // send embed
        } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
        }
    }
}
