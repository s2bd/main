const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "reddit",
        aliases: ["redit","redd"],
        category: "fun",
        description: "Fetches a random image/post from the subreddit specified.",
        usage: `(subreddit)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args);
	if (subreddit.length<1){
          message.channel.send(`🚫️ Please specify a correct subreddit. E.g. \`${client.prefix}reddit mildlyinteresting\``);
        
        } else {
          const curMeme = RedditImageFetcher.fetch({type: 'custom', subreddit: [subreddit], NSFW: false}).then(result => {
          const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter({ text:`Dolly Meme Service`});
          message.channel.send({embeds : [embed]});
        });
        } 
    }
}
