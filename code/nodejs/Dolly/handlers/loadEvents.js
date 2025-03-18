const { readdirSync } = require('fs');
const delay = require('delay');
const chalk = require('chalk');
module.exports = async (client, message) => {
    const load = dirs => {
        const events = readdirSync(`./events/${dirs}/`).filter(d => d.endsWith('.js'));
        for (let file of events) {
            const evt = require(`../events/${dirs}/${file}`);
            let ename = file.split('.')[0];
            client.on(ename, evt.bind(null, client));
        };
    };
    ["client","guild"].forEach(x => load(x));
    await delay(4000);
    console.log(chalk.greenBright(`[SYSTEM] Events loaded ✅️`));
};
