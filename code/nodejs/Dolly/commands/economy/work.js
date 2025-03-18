const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
module.exports = {
    config: {
        name: "work",
        aliases: ["workjob","jobwork","wokr"],
        category: ["economy"],
        description: "Work for your job, earn money",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
          let userData = JSON.parse(fs.readFileSync('UserAccs/'+`${message.author.id}.json`)); // find, read & translate user JSON
          const Basic = JSON.parse(fs.readFileSync('Jobs/basic.json'));
          const Law = JSON.parse(fs.readFileSync('Jobs/law.json'));
          const Specialist = JSON.parse(fs.readFileSync('Jobs/specialist.json'));
          //const Creative = JSON.parse(fs.readFileSync('Jobs/creative.json'));
          switch(userData.job.id){
            case "constructionworker":
              var wage = 25;
              //var embed = new MessageEmbed().setTitle(`${message.author.tag}'s work`).setDescription(``)
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "cook":
              var wage = 14;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "farmer":
              var wage = 30;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "teacher":
              var wage = 40;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "factoryworker":
              var wage = 20;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "mechanic":
              var wage = 19;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "police":
              var wage = 15;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "detective":
              var wage = 60;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "lawyer":
              var wage = 24;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "judge":
              var wage = 96;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "swat":
              var wage = 20;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "fbi":
              var wage = 3;
              userData.nytecoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} nytecoins!`)
              message.channel.send(`Success! You earned ${wage} <:nytecoin:980178777118216192>!`);
              break;
            case "professor":
              var wage = 48;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "developer":
              var wage = 30;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "business":
              var wage = 1;
              userData.nytecoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} nytecoins!`)
              message.channel.send(`Success! You earned ${wage} <:nytecoin:980178777118216192>!`);
              break;
            case "scientist":
              var wage = 50;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "firefighter":
              var wage = 200;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "pilot":
              var wage = 1;
              userData.nytecoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} nytecoins!`)
              message.channel.send(`Success! You earned ${wage} <:nytecoin:980178777118216192>!`);
              break;
            case "astronaut":
              var wage = 250;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            case "doctor":
              var wage = 120;
              userData.daycoins += wage;
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData));
              console.log(`${message.author.tag} successfully completed their task of ${userData.job.id} and earned ${wage} daycoins!`)
              message.channel.send(`Success! You earned ${wage} <:daycoin:980178737553342525>!`);
              break;
            default:
              message.channel.send(`You don't have a valid job currently. Try browsing the job board with \`${client.prefix}jobs\` and pick one!`)          
          }
      } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
      }
}
}
        
