import subprocess
import tkinter as tk
from tkinter import ttk
from tkinter import messagebox
import os

# Function to retrieve Wi-Fi network names (SSID) along with passwords on Linux
def get_wifi_networks_linux():
    networks = []
    try:
        # Get the list of networks
        output = subprocess.check_output(["nmcli", "-t", "-f", "SSID,SECURITY", "device", "wifi"], universal_newlines=True)
        for line in output.splitlines():
            ssid, security = line.split(':')
            networks.append({"ssid": ssid, "password": "Protected" if security else "Open"})
    except subprocess.CalledProcessError:
        show_error_and_exit("Could not retrieve Wi-Fi networks.")
    return networks

# Function to display an error message and exit
def show_error_and_exit(message):
    root = tk.Tk()
    root.withdraw()  # Hide the root window
    messagebox.showerror("Error", message)
    root.destroy()
    exit()

# Function to retrieve Wi-Fi network names (SSID) along with passwords based on the operating system
def get_wifi_networks():
    if os.name == "posix":  # Linux
        return get_wifi_networks_linux()
    else:
        return [{"ssid": "Unsupported OS", "password": ""}]

# Create a GUI window
root = tk.Tk()
root.title("Wi-Fi Networks")

# Retrieve Wi-Fi networks with passwords
wifi_networks = get_wifi_networks()

# Create a text widget to display Wi-Fi networks with passwords
text_widget = tk.Text(root, width=60, height=20, font=("Arial", 12))
text_widget.grid(row=0, column=0, padx=10, pady=10)

# Insert Wi-Fi networks and passwords into the text widget
for network in wifi_networks:
    text_widget.insert(tk.END, f"SSID: {network['ssid']}\n")
    text_widget.insert(tk.END, f"    Password: ", "label")
    text_widget.insert(tk.END, f"{network['password']}\n\n", "password")

# Configure text tags for styling
text_widget.tag_config("label", font=("Arial", 12, "bold"))
text_widget.tag_config("password", foreground="green")

# Disable text editing
text_widget.config(state=tk.DISABLED)

root.mainloop()

