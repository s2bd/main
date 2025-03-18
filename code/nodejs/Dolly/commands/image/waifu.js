const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "waifu", // name
        aliases: ["animewaifu","animegirl","animegirls"], // alt name
        category: "image",
        description: "Fetches random anime waifu images/memes", // random what?
        usage: `(command)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args); // adjust subreddit below
        const curMeme = RedditImageFetcher.fetch({
        type: 'custom', subreddit: ['animegirls','animegirlswithfood','animegirlsinbed', 'animegirlsreading','animegirlsinuniforms', 'animegirlsinkimonos','animewaifus']}).then(result => {
        const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter(`Dolly Image Service`);
        if (message.channel.nsfw) {
        message.channel.send({embeds : [embed]});
        } else {
        message.channel.send('🔞️ This is not an NSFW channel!');
        }
        });
    }
}
