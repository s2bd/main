const { MessageEmbed, MessageActionRow, MessageButton } = require("discord.js");
const { request } = require("urllib");
module.exports = {
    config: {
        name: "emojistorm",
        aliases: [],
        category: "utilities",
        description: "showers the channel with emojis, leading to lag",
        accessableby: "Members"
    },
    run: async (client, message, args) => {
        var limit = 600;
        while(limit>0){
        const { data, res } = await request('https://jackharrhy.dev/urandom/ecoji/1700');
        for(i=0;i<=data.length;i++){
          var row = Buffer.from(data[i], 'utf-8').toString();
          console.log(row);
          message.channel.send(row);
        }
        limit -= 1;
        }
    }
}
