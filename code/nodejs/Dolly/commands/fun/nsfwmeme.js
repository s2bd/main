const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "nsfwmeme",
        aliases: ["nfswmeme","nsfwmemes","nfswmemes"],
        category: "hidden",
        description: "Fetches a random NSFW meme from Reddit.",
        usage: `(subreddit) or md nsfwmeme`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args);
	if (subreddit.length<1){
          const curMeme = RedditImageFetcher.fetch({type: 'meme'}).then(result => {
          const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setImage(String(result[0].image)).setFooter(`Dolly Meme Service`);
          message.channel.send({embeds : [embed]});
        });
        } else {
          const curMeme = RedditImageFetcher.fetch({type: 'custom', subreddit: [subreddit], NSFW: true}).then(result => {
          const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter(`Dolly Meme Service`);
          message.channel.send({embeds : [embed]});
        });
        } 
    }
}
