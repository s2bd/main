const botClient = require("./core");
const client = new botClient();
client.connect();
module.exports = client;
