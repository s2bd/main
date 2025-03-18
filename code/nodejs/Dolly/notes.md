# Notes

## Gacha / Card Collection
Items in Gacha folder, commands in gacha folder.

## Use less comments!
Just realized today that the number of comments can affect the number of lines read by the parser, thus affecting quality of the buffer.

## Run & Test Simultaneously
Keep the old version console running with the same token. In this way, the bot doesn't need to shut down.

## When adding new Cmd Categories
Need to update /handlers/loadCommands.js, mention the category within the cmd exports.

## Embeds' Color Palette 
For the embeds, a brighter tone represents more severity/importance,
e.g. if a song is added, the color is green. If a playlist is added, the color is a brighter green, implying that the bot is happy that it is being used! (Since a playlist contains a large number of songs, which means a longer duration of being used)
Similarly, the same occurs for errors. The brighter the red, the more critical it is.
As for information, it is usually blue. If something is asked about the bot or developer, then it is a brighter blue.
#### Colors :
#5865f2 (blurple, Discord theme) for general
#0987ad (blue) for information
#aa0000 (red, dull) for low priority errors
#ff0000 (red, bright) for high priority errors
#09ad45 (green, dull) for successful attempts
#00e6540 (green, bright) for happy successful attempts
