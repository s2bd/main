const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "foxes", // name
        aliases: ["fox","foxxy","foxie","foxxie","foxy"], // alt name
        category: "image",
        description: "Fetches random fox images", // random what?
        usage: `(command)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args); // adjust subreddit below
        const curMeme = RedditImageFetcher.fetch({
        type: 'custom', subreddit: ['foxes','foxeswithwatermelons', 'foxloaf']}).then(result => {
        const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter(`Dolly Image Service`);
        message.channel.send({embeds : [embed]});
        });
    }
}
