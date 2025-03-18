const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "meme",
        aliases: ["memes"],
        category: "fun",
        description: "Fetches a random meme from Reddit.",
        usage: `(command)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
          const curMeme = RedditImageFetcher.fetch({type: 'meme'}).then(result => {
          const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setImage(String(result[0].image)).setFooter({text:`Dolly Meme Service`});
          message.channel.send({embeds : [embed]});
        }); 
    }
}
