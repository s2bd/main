import subprocess
import tkinter as tk
from tkinter import ttk
from tkinter import messagebox
import os

# Function to retrieve Wi-Fi network names (SSID) along with passwords on Windows
def get_wifi_networks_windows():
    networks = []
    try:
        output = subprocess.check_output(["netsh", "wlan", "show", "profiles"], shell=True)
        profiles = output.decode("utf-8").split("\n")
        for profile in profiles:
            if "All User Profile" in profile:
                ssid = profile.split(":")[1].strip()
                if ssid:
                    try:
                        password_output = subprocess.check_output(
                            ["netsh", "wlan", "show", "profile", ssid, "key=clear"], shell=True)
                        password_lines = password_output.decode("utf-8").split("\n")
                        for line in password_lines:
                            if "Key Content" in line:
                                password = line.split(":")[1].strip()
                                networks.append({"ssid": ssid, "password": password})
                                break
                        else:
                            networks.append({"ssid": ssid, "password": "None"})
                    except subprocess.CalledProcessError:
                        networks.append({"ssid": ssid, "password": "Could not retrieve password"})
    except subprocess.CalledProcessError:
        show_error_and_exit("Permission denied or command failed. Please run the script with administrator privileges.")
    except Exception as e:
        show_error_and_exit(str(e))
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
    if os.name == "nt":  # Windows
        return get_wifi_networks_windows()
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
