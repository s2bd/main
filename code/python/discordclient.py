import discord
import asyncio
import urwid
import threading

class DiscordClient(discord.Client):
    def __init__(self, app):
        intents = discord.Intents.default()
        super().__init__(intents=intents)
        self.app = app
        self.messages = []

    async def on_ready(self):
        print('Bot logged in as:', self.user)
        self.app.set_status("Logged in as {}".format(self.user))

    async def on_message(self, message):
        self.messages.append(f'{message.author}: {message.content}')
        self.app.update_message_list()

class DiscordCLIApp:
    def __init__(self):
        self.header = urwid.Text("Discord Client - CLI", align='center')
        self.status = urwid.Text("", align='center')
        self.message_list = urwid.ListBox(urwid.SimpleListWalker([]))
        self.footer = urwid.Text("Press 'q' to quit", align='center')

        self.main_layout = urwid.Pile([
            self.header,
            urwid.Divider(),
            self.status,
            urwid.Divider(),
            self.message_list,
            urwid.Divider(),
            self.footer
        ])

        self.loop = urwid.MainLoop(
            urwid.Filler(self.main_layout, valign='top'),
            unhandled_input=self.exit_on_q
        )

        self.client = DiscordClient(self)

    def exit_on_q(self, key):
        if key in ('q', 'Q'):
            raise urwid.ExitMainLoop()

    def set_status(self, text):
        self.status.set_text(text)

    def update_message_list(self):
        lines = [urwid.Text(message) for message in self.client.messages]
        self.message_list.body[:] = lines

    def run(self):
        self.loop.run()

async def main():
    app = DiscordCLIApp()
    token = input("Enter your bot token: ")

    try:
        await app.client.start(token)
    except KeyboardInterrupt:
        await app.client.close()

if __name__ == "__main__":
    asyncio.run(main())
