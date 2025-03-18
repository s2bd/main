// config.h
#ifndef CONFIG_H
#define CONFIG_H

#include <string>

struct Config {
    std::string token;
    std::string prefix;
    bool leaveIdle;
    int leaveIdleCountdown;
    bool leaveFinish;
    bool leaveStop;
    uint64_t webhookId;
    std::string webhookToken;
};

Config loadConfig() {
    Config config;
    config.token = "";
    config.prefix = "!"; // Replace with your desired prefix
    config.leaveIdle = true;
    config.leaveIdleCountdown = 60;
    config.leaveFinish = false;
    config.leaveStop = false;
    config.webhookId = 0;
    config.webhookToken = ""; // Replace with your webhook token
    return config;
}

#endif // CONFIG_H
