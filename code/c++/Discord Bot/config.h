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
    config.token = "OTc3MjYyOTUyMzU0ODI4MzA4.GVV0Qu.OadGqegq7EbKaHp6W1F-EZAZQISh9IayDPwqsE"; // Replace with your token
    config.prefix = "!"; // Replace with your desired prefix
    config.leaveIdle = true;
    config.leaveIdleCountdown = 60;
    config.leaveFinish = false;
    config.leaveStop = false;
    config.webhookId = 980158201297248257;
    config.webhookToken = "vMjZOZY4R2Czxaxp2w9Dlzuudnnt90Srm1nlFsxZyMual96s9W3MFFFhtn3tU0mMgUQJ"; // Replace with your webhook token
    return config;
}

#endif // CONFIG_H
