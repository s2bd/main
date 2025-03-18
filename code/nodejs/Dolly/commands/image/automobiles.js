const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const RedditImageFetcher = require('reddit-image-fetcher');

module.exports = {
    config: {
        name: "automobiles", // name
        aliases: ["cars","car","gari","gaari","voogai","vugai","vroom","automobile"], // alt name
        category: "image",
        description: "Fetches random car images", // random what?
        usage: `(command)`,
        accessableby: "Members"
    },
    run: async (client, message, args) => {
    	const subreddit = String(args); // adjust subreddit below
        const curMeme = RedditImageFetcher.fetch({
        type: 'custom', subreddit: ['car','cars','carporn','cartalk','lamborghini','supralovers','toyota','nissan','ferrari','carshitposting']}).then(result => {
        const embed = new MessageEmbed().setColor('#5865f2').setTitle(String(result[0].title)).setDescription(`From \`r/`+String(result[0].subreddit)+`\``).setURL(String(result[0].postLink)).setImage(String(result[0].image)).setFooter(`Dolly Image Service`);
        message.channel.send({embeds : [embed]});
        });
    }
}
