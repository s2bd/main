const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const fs = require('fs');
module.exports = {
    config: {
        name: "jobs",
        aliases: ["joblist","job"],
        category: ["economy"],
        description: "Find and select a job!",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        if (fs.existsSync('UserAccs/'+`${message.author.id}.json`)){
        const rawData = fs.readFileSync('UserAccs/'+`${message.author.id}.json`); // find and read JSON
        let userData = JSON.parse(rawData); // translate JSON
        const Basic = JSON.parse(fs.readFileSync('Jobs/basic.json'));
        const Law = JSON.parse(fs.readFileSync('Jobs/law.json'));
        const Specialist = JSON.parse(fs.readFileSync('Jobs/specialist.json'));
        //const Creative = JSON.parse(fs.readFileSync('Jobs/creative.json'));
        const embed0 = new MessageEmbed().setColor('#5865f2').setTitle(`JOB BOARD`).setDescription(`Welcome to the job board! Find your ideal job and work for it everyday! No resume, no interviews required! Simply apply and get hired. Continue by hitting one of those buttons below.`).setImage(`https://pixeljoint.com/files/icons/full/sk_pixeloffice_20070912.gif`);
        const nav_buttons = new MessageActionRow().addComponents(new MessageButton().setCustomId('basic').setLabel('👷️ Basic').setStyle('PRIMARY'), new MessageButton().setCustomId('law').setLabel('👮️ Law').setStyle('PRIMARY'),new MessageButton().setCustomId('specialist').setLabel('👨‍💻️ Specialist').setStyle('PRIMARY'),new MessageButton().setCustomId('creative').setLabel('👨‍🎨️ Creative').setStyle('PRIMARY').setDisabled(true));
        const embed1 = new MessageEmbed().setColor('#ff00ff').setTitle(`BASIC JOBS`).setDescription(`Type the command specified in the 'Apply' section to select the job`);
        const embed2 = new MessageEmbed().setColor('#00ffff').setTitle(`LAW JOBS`).setDescription(`Type the command specified in the 'Apply' section to select the job`);
        const embed3 = new MessageEmbed().setColor('#ffff00').setTitle(`SPECIALIST JOBS`).setDescription(`Type the command specified in the 'Apply' section to select the job`);
         Basic.jobs.forEach(job => {
         		embed1.addField(`${job.name}`,`**Pay:** ${job.pay}\n**Info:** ${job.info}\n**Apply:**\`${client.prefix}apply ${job.id}\`\n`,true)
        });
        Law.jobs.forEach(job => {
         		embed2.addField(`${job.name}`,`**Pay:** ${job.pay}\n**Info:** ${job.info}\n**Apply:**\`${client.prefix}apply ${job.id}\`\n`,true)
        });
        Specialist.jobs.forEach(job => {
         		embed3.addField(`${job.name}`,`**Pay:** ${job.pay}\n**Info:** ${job.info}\n**Apply:**\`${client.prefix}apply ${job.id}\`\n`,true)
        });
        //Creative.jobs.forEach(job => {
         		//embed3.addField(`${job.name}`,`**Pay:** ${job.pay}\n**Info:** ${job.info}\n**Apply:**\`${client.prefix}apply ${job.id}\`\n`,true)
        //});
        const jobMenu = await message.channel.send({embeds: [embed0], components: [nav_buttons]});
        const collector = await jobMenu.createMessageComponentCollector();
        collector.on('collect', async (interaction) => {
        if (!interaction.deferred) await interaction.deferUpdate();
        if (interaction.customId === 'basic') {
              jobMenu.edit({embeds: [embed1], components: [nav_buttons]});
        } else if (interaction.customId === 'law') {
              jobMenu.edit({embeds: [embed2], components: [nav_buttons]});
        } else if (interaction.customId === 'specialist') {
              jobMenu.edit({embeds: [embed3], components: [nav_buttons]});
        //} else if (interaction.customId === 'creative') {
              //jobMenu.edit({embeds: [embed4], components: [nav_buttons]});
        }
        });
          
        
      } else {
        message.channel.send(`It appears that you don't have an account yet! Use \`${client.prefix}createacc\` to register yourself to the Dolly database!`);
      }
}
}
        
