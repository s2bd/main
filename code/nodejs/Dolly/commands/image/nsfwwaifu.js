const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "nfswwaifu", // name
        aliases: ["nsfwwaifu","nfswanimewaifu","nsfwanimewaifu","nfswanimegirl","nsfwanimegirl","nfswanimegirls","nsfwanimegirls"], // alt name
        category: "hidden",
        description: "Fetches random NFSW anime waifu images/memes", // random what?
        usage: `(command)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args); // adjust subreddit below
        const curMeme = RedditImageFetcher.fetch({
        type: 'custom', subreddit: ['animegirlsfullynaked','animewaifus','animensfw_', 'animensfw11','mangansfw11'],
         NFSW:true }).then(result => {
        const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter(`Dolly Image Service`);
        if (message.channel.nsfw) {
        message.channel.send({embeds : [embed]});
        } else {
        message.channel.send('🔞️ This is not an NSFW channel!');
        }
        });
    }
}
