import tkinter as tk
from tkinter import ttk
from tkinter import messagebox
import threading
import requests
from tkhtmlview import HTMLLabel

def start_request():
    global running, thread, total_counter
    if not running:
        url = url_entry.get()
        if url:
            running = True
            total_counter = 0
            total_counter_label.config(text=f"Total requests: {total_counter}")
            start_button.config(state="disabled")
            stop_button.config(state="normal")
            thread = threading.Thread(target=do_request, args=(url,))
            thread.daemon = True
            thread.start()
        else:
            messagebox.showerror("Error", "Please enter a URL")

def stop_request():
    global running
    if running:
        running = False
        start_button.config(state="normal")
        stop_button.config(state="disabled")

def do_request(url):
    global total_counter
    while running:
        try:
            response = requests.get(url)
            # Display response in the HTML viewer
            response_html = response.text
            response_html_panel.set_html(response_html)
            total_counter += 1
            total_counter_label.config(text=f"Total requests: {total_counter}")
        except Exception as e:
            print("Error:", e)
            messagebox.showerror("Error", str(e))
            stop_request()

def create_gui():
    root = tk.Tk()
    root.title("Net Set Go")
    root.geometry("550x500")  # Increased height for the new panel

    global running, thread, total_counter_label, url_entry, start_button, stop_button, total_counter, response_html_panel

    style = ttk.Style()
    style.configure("TButton", font=("Arial", 14))

    label = ttk.Label(root, text="Enter URL:", font=("Arial", 14))
    label.place(x=20, y=20)

    url_entry = ttk.Entry(root, font=("Arial", 14), width=40)
    url_entry.place(x=130, y=20)

    start_button = ttk.Button(root, text="Start", command=start_request, style="TButton", width=10)
    start_button.place(x=130, y=60)

    stop_button = ttk.Button(root, text="Stop", command=stop_request, style="TButton", width=10, state="disabled")
    stop_button.place(x=250, y=60)

    total_counter_label = ttk.Label(root, text="Total requests: 0", font=("Arial", 14))
    total_counter_label.place(x=20, y=100)

    # Frame to contain HTML viewer
    html_frame = ttk.Frame(root, relief="raised", borderwidth=1)
    html_frame.place(x=20, y=140, width=500, height=300)

    # HTML viewer widget for displaying HTML response
    response_html_panel = HTMLLabel(html_frame, html="<p>No response yet.</p>", width=70, height=20)
    response_html_panel.pack(fill="both", expand=True)

    running = False
    thread = None
    total_counter = 0

    root.mainloop()

create_gui()
