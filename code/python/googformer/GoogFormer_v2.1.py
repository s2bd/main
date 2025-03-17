import time
import pandas as pd
from selenium import webdriver
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.by import By
import urllib.parse
from tqdm import tqdm  # For progress bars

# Load the dataset
data_file = "Surveysheet.xlsx"
form_url_template = (
    "https://docs.google.com/forms/d/e/1FAIpQLSf6GcrxsrYiT4M3qtbVYqEVVvEUdcErm8bdhqigOnMNecrp0w/viewform?usp=pp_url"
    "&entry.963569756={}&entry.924925125={}&entry.101149094={}&entry.945378859={}&entry.198410586={}"
    "&entry.1924438207={}&entry.198624539={}&entry.1775324208={}&entry.935012407={}&entry.1075272017={}"
    "&entry.1125739753={}&entry.1056899585={}&entry.1671986682={}&entry.954671807={}&entry.1644959082={}"
    "&entry.1605900025={}&entry.1667712064={}&entry.585469187={}"
)

df = pd.read_excel(data_file)

# Function to build the URL with pre-filled values from the row
def build_form_url(row):
    # Generate the query string by extracting the values in order from the row
    values = [str(row[col]) for col in df.columns]
    
    # URL encode the values
    encoded_values = [urllib.parse.quote(value) for value in values]
    
    # Format the URL with the encoded values
    return form_url_template.format(*encoded_values)

# Set up the WebDriver
service = Service(ChromeDriverManager().install())
driver = webdriver.Chrome(service=service)

# Use tqdm to show progress for each row
for idx, row in tqdm(df.iterrows(), total=df.shape[0], desc="Processing forms"):
    # Build the form URL with pre-filled data
    filled_url = build_form_url(row)
    print(f"\nNavigating to form URL: {filled_url}")
    driver.get(filled_url)
    time.sleep(5)  # Allow time for the page to load

    while True:
        try:
            # Prioritize finding the "Next" button
            print("Looking for 'Next' button...")
            next_button = WebDriverWait(driver, 5).until(
                EC.element_to_be_clickable((By.XPATH, "//div[@jsname='OCpkoe']//span[text()='পরবর্তী']"))
            )
            print("Found 'Next' button. Clicking it...")
            next_button.click()
            time.sleep(3)  # Wait for the page transition
        except Exception as e:
            print("No 'Next' button found. Trying to locate the 'Submit' button...")

            try:
                # Try finding the submit button instead
                submit_button = WebDriverWait(driver, 5).until(
                    EC.element_to_be_clickable((By.XPATH, "//div[@jsname='M2UYVd']//span[text()='জমা দিন']"))
                )
                print("Found 'Submit' button. Clicking it to submit the form...")
                submit_button.click()
                print("Form submitted successfully.")
                time.sleep(3)  # Allow submission processing time
                break  # Exit the loop after submission
            except Exception as e:
                print(f"Could not find 'Submit' button. Error: {e}")
                break  # Exit loop if neither button is found

    # Log progress for form submission or navigation completion
    print(f"Form {idx + 1} completed. Moving to next form...\n")

    # Generate a random wait time between 1 and 15 minutes (converted to seconds)
    wait_time = random.randint(60, 900)
    print(f"Waiting for {wait_time} seconds before proceeding to the next form...")
    time.sleep(wait_time)  # Pause execution for the random time

# Close the browser after all forms are processed
print("\nAll forms processed. Closing the browser.")
driver.quit()
