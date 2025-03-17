import tkinter as tk
from tkinter import messagebox, scrolledtext
import threading
import random
import re
import sys
import os
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from selenium.webdriver.common.by import By
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from webdriver_manager.chrome import ChromeDriverManager

COUNT = 0

print('Launching bot...')

def random_text(length=10):
    return ''.join(random.choices('abcdefghijklmnopqrstuvwxyz', k=length))

def fill_form(form_url):
    global COUNT
    driver.get(form_url)

    # Fill short answer inputs
    text_inputs = driver.find_elements(By.XPATH, "//input[@type='text']")
    for field in text_inputs:
        field.send_keys(random_text())

    # Fill long answer inputs (textarea fields)
    long_text_inputs = driver.find_elements(By.XPATH, "//textarea")
    for field in long_text_inputs:
        field.send_keys(random_text(50))  # Fill with longer random text

    # Select a random radio button
    radio_groups = driver.find_elements(By.XPATH, "//div[@role='radiogroup']")
    for group in radio_groups:
        radio_options = group.find_elements(By.XPATH, ".//div[@role='radio']")
        if radio_options:
            random.choice(radio_options).click()

    # Select random checkboxes
    checkboxes = driver.find_elements(By.XPATH, "//div[@role='checkbox']")
    for checkbox in checkboxes:
        if random.choice([True, False]):
            checkbox.click()

    # Click the submit button
    try:
        submit_button = WebDriverWait(driver, 2).until(
            EC.element_to_be_clickable((By.XPATH, "//span[text()='\u099c\u09ae\u09be \u09a6\u09bf\u09a8']"))
        )
        submit_button.click()
        COUNT += 1
        console_log("Form submitted successfully. "+ "["+str(COUNT)+"]")
    except Exception as e:
        console_log(f"Could not submit form: {e}")


def start_bot():
    url = url_entry.get()
    if not re.match(r"^(https?://)?(docs\.google\.com/forms|forms\.gle)/.*", url):
        messagebox.showerror("Invalid URL", "Please enter a valid Google Form URL.")
        return
    global running
    running = True
    threading.Thread(target=bot_loop, args=(url,), daemon=True).start()

def bot_loop(url):
    try:
        while running:
            fill_form(url)
    except Exception as e:
        console_log(f"Error: {e}")

def stop_bot():
    global running, COUNT
    running = False
    console_log("Bot stopped.")
    COUNT = 0

def console_log(message):
    console_output.insert(tk.END, message + '\n')
    console_output.see(tk.END)

def restart_app():
    python = sys.executable
    os.execl(python, python, *sys.argv)

def on_closing():
    stop_bot()
    driver.quit()
    root.destroy()

# Setup WebDriver
service = Service(ChromeDriverManager().install())
driver = webdriver.Chrome(service=service)
running = False

# GUI Setup
root = tk.Tk()
root.title("Oozbok")
root.geometry("650x500")
root.iconbitmap('yip.ico')

title_label = tk.Label(root, text="OOZBOK", font=("Comic Sans", 24, "bold"), fg="#333")
title_label.pack(pady=10)
brand_label = tk.Label(root, text="by Mux AI", font=("Times New Roman", 14), fg="#060606")
brand_label.pack()
version_label = tk.Label(root, text="v2025.01.26.02", font=("Times New Roman", 12), fg="#999")
version_label.pack()

url_label = tk.Label(root, text="Enter Google Form URL:")
url_label.pack(pady=5)
url_entry = tk.Entry(root, width=70)
url_entry.pack(pady=5)

btn_frame = tk.Frame(root)
btn_frame.pack(pady=10)
start_button = tk.Button(btn_frame, text="Start Bot", command=start_bot, bg="#4CAF50", fg="white", width=15)
start_button.grid(row=0, column=0, padx=5)
stop_button = tk.Button(btn_frame, text="Stop Bot", command=stop_bot, bg="#f44336", fg="white", width=15)
stop_button.grid(row=0, column=1, padx=5)

console_output = scrolledtext.ScrolledText(root, height=15, width=90, bg="#000", fg="#0f0")
console_output.pack(pady=10)

restart_button = tk.Button(root, text="↻", command=restart_app, bg="#ffeb3b", fg="#333", font=("Arial", 10, "bold"))
restart_button.place(relx=0.97, rely=0.85, anchor="center")

root.protocol("WM_DELETE_WINDOW", on_closing)
root.mainloop()
