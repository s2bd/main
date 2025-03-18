const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "image",
        aliases: ["img"],
        category: "hidden",
        description: "Fetches a random image from Reddit.",
        usage: `(topic)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args);
	if (subreddit.length<1){
          message.channel.send('🚫️ No topics provided!');
        } else {
        const curMeme = RedditImageFetcher.fetch({
        type: 'custom', subreddit: [subreddit]}).then(result => {
        const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter(`Dolly Image Service`);
        message.channel.send({embeds : [embed]});
        });
        } 
    }
}
