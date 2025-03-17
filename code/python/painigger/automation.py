import cv2
import numpy as np
import pyautogui
import time
from PIL import Image, ImageTk
from scipy import interpolate

# Global Variables
processed_image = None
contours = []

# Resize Image to Fit 2/3 of Screen Size
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

# Update Preview
def update_preview(image, canvas):
    image_rgb = cv2.cvtColor(image, cv2.COLOR_BGR2RGB)
    image_pil = Image.fromarray(image_rgb)
    image_tk = ImageTk.PhotoImage(image_pil)
    canvas.config(image=image_tk)
    canvas.image = image_tk

# Process Image with Custom Parameters
def process_image(original_image):
    global contours, processed_image
    gray = cv2.cvtColor(original_image, cv2.COLOR_BGR2GRAY)
    clahe = cv2.createCLAHE(clipLimit=3.0, tileGridSize=(8, 8))
    contrast_enhanced = clahe.apply(gray)
    bright_contrast_enhanced = cv2.convertScaleAbs(contrast_enhanced, alpha=1.5, beta=50)
    
    # Gaussian Blur & Canny Edge Detection
    blurred = cv2.GaussianBlur(bright_contrast_enhanced, (5, 5), 0)
    edges = cv2.Canny(blurred, 50, 150)

    contours, _ = cv2.findContours(edges, cv2.RETR_EXTERNAL, cv2.CHAIN_APPROX_SIMPLE)
    processed_image = cv2.cvtColor(edges, cv2.COLOR_GRAY2BGR)
    cv2.drawContours(processed_image, contours, -1, (0, 255, 0), 2)
    
    return contours, processed_image

# Bezier curve interpolation for smooth drawing
def bezier_curve(points, num_points=100):
    """
    Calculates a smooth curve using Bezier interpolation.
    """
    x_points, y_points = zip(*points)

    # Using cubic Bezier curve interpolation (order 3)
    t = np.linspace(0, 1, num_points)
    spline_x = interpolate.CubicSpline(range(len(x_points)), x_points, bc_type='clamped')
    spline_y = interpolate.CubicSpline(range(len(y_points)), y_points, bc_type='clamped')

    return list(zip(spline_x(t), spline_y(t)))

# Add space buffer for safety margin
def add_buffer_space(image, top_margin=100, bottom_margin=100):
    screen_width, screen_height = pyautogui.size()
    image_height, image_width = image.shape[:2]

    # Create a new blank image with extra buffer space
    new_image = np.ones((image_height + top_margin + bottom_margin, image_width, 3), dtype=np.uint8) * 255
    new_image[top_margin:top_margin + image_height, :] = image

    return new_image

# Start Auto-Drawing with Smooth Movements and Customization
def start_drawing(contours, original_image):
    if not contours:
        print("No contours found!")
        return

    screen_width, screen_height = pyautogui.size()
    image_height, image_width = original_image.shape[:2]
    margin_top = 50  # Taskbar margin
    margin_bottom = 50  # Browser menu margin
    
    # Adding buffer space above and below the image to avoid taskbar/browser menu
    original_image_with_buffer = add_buffer_space(original_image, top_margin=margin_top, bottom_margin=margin_bottom)

    scale_factor = min(screen_width / image_width, (screen_height - margin_top - margin_bottom) / image_height)
    x_offset = (screen_width - (image_width * scale_factor)) // 2
    y_offset = margin_top + (screen_height - margin_top - margin_bottom - (image_height * scale_factor)) // 2

    def remap_point(x, y):
        return int(x * scale_factor) + x_offset, int(y * scale_factor) + y_offset

    time.sleep(1)  # Brief delay for smoother transition

    last_x, last_y = None, None
    for contour in contours:
        if len(contour) < 5:
            continue
        
        # Remap points to fit the screen and add buffer space
        remapped_contour = [remap_point(pt[0][0], pt[0][1]) for pt in contour]

        # Smooth the path using Bezier interpolation
        smooth_path = bezier_curve(remapped_contour, num_points=200)

        # Start the drawing from the first point
        start_x, start_y = smooth_path[0]
        pyautogui.moveTo(start_x, start_y, duration=0.1)
        pyautogui.mouseDown()

        # Draw the rest of the points with smooth movements
        for x, y in smooth_path[1:]:
            distance = np.sqrt((x - last_x) ** 2 + (y - last_y) ** 2) if last_x is not None else 0
            duration = 0.01 if distance < 10 else 0.2
            pyautogui.moveTo(x, y, duration=duration)
            last_x, last_y = x, y
        
        pyautogui.mouseUp()
    
    print("Drawing complete!")
