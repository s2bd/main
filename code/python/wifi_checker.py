import subprocess
import gi
import os

gi.require_version("Gtk", "3.0")
from gi.repository import Gtk, Pango

# Function to retrieve Wi-Fi network names (SSID) along with passwords on Ubuntu/Linux
def get_wifi_networks_linux():
    networks = []
    try:
        output = subprocess.check_output(
            ["sudo", "grep", "-r", "psk=", "/etc/NetworkManager/system-connections"],
            stderr=subprocess.DEVNULL,
        )
        lines = output.decode("utf-8").split("\n")
        for line in lines:
            if "psk=" in line:
                ssid = line.split("/")[4]
                password = line.split("psk=")[1].strip()
                networks.append({"ssid": ssid, "password": password})
    except subprocess.CalledProcessError:
        show_error_and_exit("Permission denied or command failed. Please run the script using 'sudo python3 %s'." % os.path.basename(__file__))
    except Exception as e:
        show_error_and_exit(str(e))
    return networks

# Function to display an error message and exit
def show_error_and_exit(message):
    dialog = Gtk.MessageDialog(
        transient_for=None,
        modal=True,
        buttons=Gtk.ButtonsType.OK,
        message_type=Gtk.MessageType.ERROR,
        text="Error",
    )
    dialog.format_secondary_text(message)
    dialog.run()
    dialog.destroy()
    Gtk.main_quit()

# Function to retrieve Wi-Fi network names (SSID) along with passwords based on the operating system
def get_wifi_networks():
    try:
        output = subprocess.check_output(["nmcli", "-v"], stderr=subprocess.DEVNULL)
        return get_wifi_networks_linux()
    except FileNotFoundError:  # nmcli command not found
        return [{"ssid": "Unsupported OS", "password": ""}]

# GTK Application
class WifiApp(Gtk.Window):
    def __init__(self):
        super().__init__(title="Wi-Fi Networks")
        self.set_border_width(10)
        self.set_default_size(400, 300)

        self.grid = Gtk.Grid()
        self.add(self.grid)

        self.label = Gtk.Label(label="Wi-Fi Networks:")
        self.label.set_margin_bottom(10)
        self.label.set_xalign(0)
        self.grid.attach(self.label, 0, 0, 1, 1)

        self.text_view = Gtk.TextView()
        self.text_view.set_editable(False)
        self.text_view.set_wrap_mode(Gtk.WrapMode.WORD)
        self.text_view.set_cursor_visible(False)

        # Set font for the text view
        context = self.text_view.get_pango_context()
        font = Pango.FontDescription("Arial 12")
        self.text_view.modify_font(font)

        self.text_buffer = self.text_view.get_buffer()

        scrolled_window = Gtk.ScrolledWindow()
        scrolled_window.set_vexpand(True)
        scrolled_window.add(self.text_view)
        self.grid.attach(scrolled_window, 0, 1, 2, 1)

        self.populate_text_view()

    def populate_text_view(self):
        wifi_networks = get_wifi_networks()
        start_iter = self.text_buffer.get_start_iter()

        for network in wifi_networks:
            self.text_buffer.insert(start_iter, f"SSID: {network['ssid']}\n")

            label_tag = self.text_buffer.create_tag("label", weight=Pango.Weight.BOLD)
            self.text_buffer.insert_with_tags(start_iter, "    Password: ", label_tag)
            
            password_tag = self.text_buffer.create_tag("password", foreground="green")
            self.text_buffer.insert_with_tags(start_iter, f"{network['password']}\n\n", password_tag)

def main():
    app = WifiApp()
    app.connect("destroy", Gtk.main_quit)
    app.show_all()
    Gtk.main()

if __name__ == "__main__":
    main()
