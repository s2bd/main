const { MessageActionRow, MessageButton } = require('discord.js')
module.exports = async (client, message, pages, timeout, queueLength, queueDuration) => {
    if (!message && !message.channel) throw new Error('The channel cannot be accessed!');
    if (!pages) throw new Error(`No pages available at the moment.`);
    const button_prev_page = new MessageButton()
    .setCustomId('back')
    .setLabel('⬅')
    .setStyle('SECONDARY');
    const button_next_page = new MessageButton()
    .setCustomId('next')
    .setLabel('➡')
    .setStyle('SECONDARY');
    const row = new MessageActionRow()
    .addComponents(button_prev_page, button_next_page);
    //const prev_disabled = new MessageActionRow()
    //.addComponents(button_prev_page.setDisabled(true), button_next_page);
    //const next_disabled = new MessageActionRow()
    //.addComponents(button_prev_page, button_next_page.setDisabled(true));
    //const both_disabled = new MessageActionRow()
    //.addComponents(button_prev_page.setDisabled(true), button_next_page.setDisabled(true));
    let page = 0;
    const currentPage = await message.channel.send({
         embeds: [pages[page].setFooter({
             text: `Page ${page + 1} of ${pages.length} 
             | ${queueLength} tracks | ${queueDuration}`})],
         components: [row], 
         allowedMentions: {repliedUser: false }});
    if (pages.length == 0) return;
    const filter = (interaction) => interaction.user.id === message.author.id ? true : false && interaction.deferUpdate();
    const collector = await currentPage.createMessageComponentCollector({ filter, time: timeout});
    collector.on('collect', async (interaction) => {
        if (!interaction.deferred) await interaction.deferUpdate();
        if (interaction.customId === 'back') {
            page = page > 0 ? --page: pages.length -1;
        } else if (interaction.customId === 'next') {
            page = page + 1 < pages.length ? ++page : 0;
        }
        currentPage.edit({ 
         embeds: [pages[page].setFooter({ 
             text: `Page ${page + 1} of ${pages.length} 
             | ${queueLength} tracks | ${queueDuration}`})],
        components: [row] })
    });
    collector.on('end', () => {
        currentPage.edit({ 
            embeds: [pages[page].setFooter({ 
                text: `Page ${page + 1} of ${pages.length} 
                | ${queueLength} tracks | ${queueDuration}`})],
           components: [row] })
    });
    return currentPage;
};
