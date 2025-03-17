import tkinter as tk
from tkinter import ttk, scrolledtext
from urllib.parse import urlparse, parse_qs
import requests
from bs4 import BeautifulSoup
import folium
import os
from threading import Thread

# Function to get bus data from the JSON file
def get_bus_data():
    url = "https://www.metrobus.co.ca/api/timetrack/json/"
    try:
        headers = {
    "User-Agent": "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.124 Safari/537.36"
}
        response = requests.get(url, headers=headers)
        #response = requests.get(url)
        response.raise_for_status()  # Raise an error for bad responses
        bus_data = response.json()  # Parse JSON response
        
        formatted_data = {}
        for bus in bus_data:
            route = bus.get("current_route", "Unknown")
            formatted_data[route] = {
                "lat": float(bus["bus_lat"]),
                "lon": float(bus["bus_lon"]),
                "position_time": bus["position_time"],
                "current_location": bus.get("current_location", "Unknown"),
                "deviation": bus.get("deviation", "Unknown"),
            }
        return formatted_data
    except requests.RequestException as e:
        print(f"Error fetching bus data: {e}")
        return {}



# Function to create the map and save it
def create_map(buses):
    # Create a Folium map centered on St. John's, NL
    map_center = [47.5615, -52.7126]  # Coordinates for St. John's, NL
    bus_map = folium.Map(location=map_center, zoom_start=13)
    
    # Add bus locations as markers on the map
    for route, details in buses.items():
        # Extract the first part of the route (e.g., 10 from "10-6")
        route_number = route.split('-')[0]  # Get the first part before '-'

        # Use DivIcon to create the marker with a background location pin and bus number overlay
        folium.Marker(
            location=[details["lat"], details["lon"]],
            popup=(  # This is still showing additional information about the bus
                f"Route: {route}<br>"
                f"Location: {details['current_location']}<br>"
                f"Last Updated: {details['position_time']}<br>"
                f"Status: {details['deviation']}"
            ),
            icon=folium.DivIcon(
                html=(
                    f'<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.0.0-beta3/css/all.min.css" rel="stylesheet">'
                    f'<div style="position: relative;">'
                    f'<i class="fa-solid fa-location-pin" style="font-size: 24px; color: blue;"></i>'  # Location pin icon
                    f'<div style="position: absolute; top: 0; left: 0; font-size: 18px; color: white; font-weight: bold;'
                    f'   text-align: center; padding-top: 4px;">{route_number}</div>'  # Bus number overlay
                    f'</div>'
                ),
            ),
        ).add_to(bus_map)
    
    # Save the map to an HTML file
    map_file = "bus_map.html"
    bus_map.save(map_file)
    return map_file


# Function to display raw JSON data in the GUI
def update_raw_data_display(buses):
    raw_data_textbox.config(state="normal")  # Enable editing
    raw_data_textbox.delete(1.0, tk.END)  # Clear the previous content
    for route, details in buses.items():
        raw_data_textbox.insert(
            tk.END,
            f"Route: {route}\n"
            f"  Latitude: {details['lat']}\n"
            f"  Longitude: {details['lon']}\n"
            f"  Last Updated: {details['position_time']}\n"
            f"  Location: {details['current_location']}\n"
            f"  Status: {details['deviation']}\n\n",
        )
    raw_data_textbox.config(state="disabled")  # Disable editing


# Function to refresh the map and data periodically
def refresh_data():
    # Fetch the latest bus data
    buses = get_bus_data()

    # Update the map
    create_map(buses)

    # Update the raw data display
    update_raw_data_display(buses)

    # Schedule the next refresh
    root.after(10000, refresh_data)  # Refresh every 10 seconds

# Create the Tkinter GUI
root = tk.Tk()
root.title("Real-Time Bus Tracker")

# Top frame for raw JSON data display
top_frame = ttk.Frame(root, padding="10")
top_frame.grid(row=0, column=0, sticky="NSEW")

raw_data_label = ttk.Label(top_frame, text="Raw Bus Data:")
raw_data_label.grid(row=0, column=0, padx=5, pady=5)

raw_data_textbox = scrolledtext.ScrolledText(top_frame, wrap=tk.WORD, height=20, width=50)
raw_data_textbox.grid(row=1, column=0, padx=5, pady=5)
raw_data_textbox.config(state="disabled")  # Initially disable editing

# Bottom frame for map controls
bottom_frame = ttk.Frame(root, padding="10")
bottom_frame.grid(row=1, column=0, sticky="NSEW")

# Button to open the map in the default web browser
map_button = ttk.Button(bottom_frame, text="Show Map", command=lambda: os.system("start bus_map.html"))
map_button.grid(row=0, column=0, padx=10, pady=10)

# Start the periodic data refresh
refresh_data()

# Run the application
root.mainloop()
