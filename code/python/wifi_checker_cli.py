import subprocess
import urwid
import threading

# Function to retrieve Wi-Fi network names (SSID) along with passwords on Linux
def get_wifi_networks_linux():
    networks = []
    try:
        output = subprocess.check_output(["nmcli", "-t", "-f", "name,uuid", "connection", "show"], text=True)
        profiles = output.split("\n")
        for profile in profiles:
            if profile:
                ssid, uuid = profile.split(":")
                if ssid:
                    try:
                        password_output = subprocess.check_output(["nmcli", "-s", "-g", "802-11-wireless-security.psk", "connection", "show", uuid], text=True)
                        password = password_output.strip()
                        if not password:
                            password = "None"
                        networks.append({"ssid": ssid, "password": password})
                    except subprocess.CalledProcessError:
                        networks.append({"ssid": ssid, "password": "Could not retrieve password"})
    except subprocess.CalledProcessError:
        return [{"ssid": "Error", "password": "Permission denied or command failed."}]
    except Exception as e:
        return [{"ssid": "Error", "password": str(e)}]
    return networks

# Function to retrieve Wi-Fi network names (SSID) along with passwords
def get_wifi_networks():
    # Assuming Linux environment
    return get_wifi_networks_linux()

# urwid application code
class WifiCheckerApp:
    def __init__(self):
        self.header = urwid.Text("Muxday - Wifi Checker", align='center')
        self.scan_button = urwid.Button("Rescan", on_press=self.rescan_networks)
        self.network_list = urwid.Text("")
        self.progress = urwid.Text("Scanning...")
        self.footer = urwid.Text("©️ Muxday Solutions 2024", align='center')

        self.main_layout = urwid.Pile([
            self.header,
            urwid.Divider(),
            urwid.AttrMap(self.scan_button, None, focus_map='reversed'),
            urwid.Divider(),
            self.progress,
            urwid.Divider(),
            self.network_list,
            urwid.Divider(),
            self.footer
        ])
        
        self.main_loop = urwid.MainLoop(
            urwid.Filler(self.main_layout, valign='top'),
            unhandled_input=self.exit_on_q
        )

        self.rescan_networks()

    def exit_on_q(self, key):
        if key in ('q', 'Q'):
            raise urwid.ExitMainLoop()

    def update_network_list(self, networks):
        lines = [f"SSID: {net['ssid']}\n    Password: {net['password']}" for net in networks]
        self.network_list.set_text("\n\n".join(lines))
        self.progress.set_text("")

    def scan_wifi_networks(self):
        networks = get_wifi_networks()
        # Use urwid's set_alarm_in to call the update function on the main loop
        self.main_loop.set_alarm_in(0, lambda loop, data: self.update_network_list(networks))

    def rescan_networks(self, button=None):
        self.progress.set_text("Scanning...")
        self.network_list.set_text("")
        threading.Thread(target=self.scan_wifi_networks).start()

    def run(self):
        self.main_loop.run()

if __name__ == "__main__":
    app = WifiCheckerApp()
    app.run()
