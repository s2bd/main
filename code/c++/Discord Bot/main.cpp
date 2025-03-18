#include <dpp/dpp.h>
#include <iostream>
#include "config.h"

class BotClient {
public:
    BotClient(const Config& config) : bot(config.token) {
        this->config = config;
        bot.on_message_create([this](const dpp::message_create_t& event) {
          //  this->onMessage(event);
        });
    }

    void connect() {
        bot.start(dpp::st_wait); // Connect the bot and wait for events
    }

private:
    dpp::cluster bot;
    Config config;

    /*
    void onMessage(const dpp::message_create_t& event) {
        // Ignore own messages
        if (event.author.id == bot.me.id) return; 
        
        // Check if the message starts with the prefix
        if (event.msg.content.starts_with(config.prefix)) {
            std::string command = event.msg.content.substr(config.prefix.length());

            // Example command handling
            if (command == "ping") {
                bot.message_create(dpp::message(event.channel_id, "Pong!")); // Reply to the channel
            }
            // Add more commands here...
        }
    }
    */
};

int main() {
    Config config = loadConfig(); // Assume loadConfig is defined to read your config
    BotClient client(config); // Create bot client instance
    client.connect(); // Start the bot
    return 0;
}
