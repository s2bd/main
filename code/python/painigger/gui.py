import cv2
import numpy as np
import pyautogui
import tkinter as tk
from tkinter import filedialog, messagebox
from PIL import Image, ImageTk
import threading
import time
from automation import process_image, start_drawing, update_preview

# GUI Setup
root = tk.Tk()
root.title("🖼️ Image Processing & AutoDraw ✏️")
root.geometry("1200x700")  # Adjusted for larger space

# Create a frame for the left panel (image preview) and the right panel (buttons and controls)
frame_left = tk.Frame(root)
frame_left.grid(row=0, column=0, padx=10, pady=10, sticky="nsew")

frame_right = tk.Frame(root)
frame_right.grid(row=0, column=1, padx=10, pady=10, sticky="ns")

# Canvas for Image Preview (Left Panel)
canvas = tk.Label(frame_left)
canvas.pack(fill="both", expand=True)

# Global Variables
image_path = None
original_image = None
processed_image = None
contours = []
processing_thread = None
drawing_thread = None

# Load Image Function (Remains in the GUI file)
def load_image():
    global image_path, original_image
    image_path = filedialog.askopenfilename(filetypes=[("Image Files", "*.jpg;*.png;*.bmp;*.jpeg")])
    if not image_path:
        return
    original_image = cv2.imread(image_path)
    original_image = resize_image_to_max_size(original_image)  # Resize the image if necessary
    update_preview(original_image, canvas)

# Resize Image to Fit 2/3 of Screen Size (From the automation.py module)
def resize_image_to_max_size(original_image):
    screen_width, screen_height = pyautogui.size()

    # Calculate maximum allowable image width and height (2/3 of screen size)
    max_width = int(screen_width * 2 / 3)
    max_height = int(screen_height * 2 / 3)

    # Get current image dimensions
    image_height, image_width = original_image.shape[:2]

    # Calculate scale factor
    scale_factor = min(max_width / image_width, max_height / image_height)

    # Resize the image with the scale factor while preserving aspect ratio
    new_width = int(image_width * scale_factor)
    new_height = int(image_height * scale_factor)
    original_image = cv2.resize(original_image, (new_width, new_height))

    return original_image

# Create Buttons and Controls in the Sidebar (Right Panel)
btn_load = tk.Button(frame_right, text="📂 Load Image", command=load_image)
btn_load.pack(fill="x", pady=10)

btn_process = tk.Button(frame_right, text="🔍 Process Image", command=lambda: threading.Thread(target=process_image, args=(original_image,)).start())
btn_process.pack(fill="x", pady=10)

btn_draw = tk.Button(frame_right, text="🎨 Start AutoDraw", command=lambda: threading.Thread(target=start_drawing, args=(contours, original_image)).start())
btn_draw.pack(fill="x", pady=10)

# Configure Grid Weight for Responsive Layout
root.grid_rowconfigure(0, weight=1)
root.grid_columnconfigure(0, weight=3)  # Left panel takes up more space
root.grid_columnconfigure(1, weight=1)  # Right panel takes less space

root.mainloop()
