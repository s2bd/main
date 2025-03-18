const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
module.exports = {
    config: {
        name: "apply",
        aliases: ["applyjob","jobapply"],
        category: ["economy"],
        description: "Apply for a job!",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
          let userData = JSON.parse(fs.readFileSync('UserAccs/'+`${message.author.id}.json`)); // find, read & translate user JSON
          const Basic = JSON.parse(fs.readFileSync('Jobs/basic.json'));
          const Law = JSON.parse(fs.readFileSync('Jobs/law.json'));
          const Specialist = JSON.parse(fs.readFileSync('Jobs/specialist.json'));
          //const Creative = JSON.parse(fs.readFileSync('Jobs/creative.json'));
          jobchosen = String(args[0]);
          for (var i=0; i < Basic.jobs.length; i++){
          if(Basic.jobs[i].id == jobchosen){
            if(Basic.jobs[i].isPremium && !userData.premium){
               const premium = new MessageEmbed().setColor(`#ffd700`).setDescription(`**Upgrade to Premium!**\nGet the job you desire!`)
               return message.channel.send({text:`${Basic.jobs[i].name} is reserved for premium members only!`,embeds:[premium]});
            } else {
              if(userData.inPrison){
                return message.channel.send(`Sorry, prisoners cannot choose any jobs!`);
              } else {
              userData.job.id = Basic.jobs[i].id; // set new job
              userData.job.icon = Basic.jobs[i].icon; // set new job icon
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
              console.log(`${message.author.tag} applied to be ${Basic.jobs[i].name}!`);
              return message.channel.send(`${message.author.tag} became a ${Basic.jobs[i].name}!`);
            }
          }
        }
        }
        for (var i=0; i < Law.jobs.length; i++){
          if(Law.jobs[i].id == jobchosen){
            if(Law.jobs[i].isPremium && !userData.premium){
               const premium = new MessageEmbed().setColor(`#ffd700`).setDescription(`**Upgrade to Premium!**\nGet the job you desire!`)
               return message.channel.send({text:`${Law.jobs[i].name} is reserved for premium members only!`,embeds:[premium]});
            } else {
              if(userData.inPrison){
                return message.channel.send(`Sorry, prisoners cannot choose any jobs!`);
              } else if(userData.wantedlvl > 0){
                return message.channel.send(`Sorry, you have criminal records and thus cannot apply to work in law enforcement.`)
              } else {
              userData.job.id = Law.jobs[i].id; // set new job
              userData.job.icon = Law.jobs[i].icon; // set new job icon
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
              console.log(`${message.author.tag} applied to be ${Law.jobs[i].name}!`);
              return message.channel.send(`${message.author.tag} became a ${Law.jobs[i].name}!`);
            }
          }
        }
        }
        for (var i=0; i < Specialist.jobs.length; i++){
          if(Specialist.jobs[i].id == jobchosen){
            if(Specialist.jobs[i].isPremium && !userData.premium){
               const premium = new MessageEmbed().setColor(`#ffd700`).setDescription(`**Upgrade to Premium!**\nGet the job you desire!`)
               return message.channel.send({text:`${Specialist.jobs[i].name} is reserved for premium members only!`,embeds:[premium]});
            } else {
              if(userData.inPrison){
                return message.channel.send(`Sorry, prisoners cannot choose any jobs!`);
              } else {
              userData.job.id = Specialist.jobs[i].id; // set new job
              userData.job.icon = Specialist.jobs[i].icon; // set new job icon
              fs.writeFileSync('UserAccs/'+`${message.author.id}.json`,JSON.stringify(userData)); // update account
              console.log(`${message.author.tag} applied to be ${Specialist.jobs[i].name}!`);
              return message.channel.send(`${message.author.tag} became a ${Specialist.jobs[i].name}!`);
            }
          }
        }
        }
          message.channel.send(`Sorry, couldn't find that job! Try browsing the job board with \`${client.prefix}jobs\` to check if the job exists or retry by typing the job name as it is displayed on the job board.`);
      } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
      }
}
}
        
