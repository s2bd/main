import time
import random
import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.chrome.service import Service
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.support.ui import WebDriverWait
from selenium.webdriver.support import expected_conditions as EC
from selenium.webdriver.common.action_chains import ActionChains

# Set up the WebDriver
service = Service(ChromeDriverManager().install())
driver = webdriver.Chrome(service=service)

# Load the SurveySheet.xlsx file using pandas
def load_links_from_excel(excel_file):
    df = pd.read_excel(excel_file)
    
    # Extract links from column S (19th column) and strip any extra whitespace
    links = df.iloc[:, 18].dropna().apply(lambda x: x.strip()).tolist()
    return links

def scroll_into_view(element):
    """Scroll element into view using JavaScript."""
    driver.execute_script("arguments[0].scrollIntoView(true);", element)
    time.sleep(1)

def visit_and_fill_form(link):
    print(f"Visiting link: {link}")  # Debug: Print the link to check its validity
    driver.get(link)
    time.sleep(5)  # Wait for the form to load

    # Dynamically find all input fields
    text_fields = driver.find_elements(By.CSS_SELECTOR, "input[type='text']")
    dropdown_fields = driver.find_elements(By.CSS_SELECTOR, "div[role='listbox']")
    radio_buttons = driver.find_elements(By.CSS_SELECTOR, "div[role='radiogroup']")
    buttons = driver.find_elements(By.TAG_NAME, "button")

    # Fill text fields
    for field in text_fields:
        field.send_keys("Sample Text")  # You can modify this with dynamic data if needed
        time.sleep(1)

    # Fill dropdown fields
    for dropdown in dropdown_fields:
        try:
            # Wait until the dropdown options are visible and clickable
            WebDriverWait(driver, 10).until(
                EC.element_to_be_clickable(dropdown)
            )
            scroll_into_view(dropdown)
            dropdown.click()  # Open the dropdown
            time.sleep(1)
            
            # Ensure the options are clickable before selecting one
            options = WebDriverWait(driver, 10).until(
                EC.presence_of_all_elements_located((By.CSS_SELECTOR, "div[role='option']"))
            )
            # Pick a random option
            option = random.choice(options)
            ActionChains(driver).move_to_element(option).click().perform()  # Using ActionChains for more control
            time.sleep(1)
        except Exception as e:
            print(f"Error while selecting dropdown option: {e}")

    # Fill radio button selections
    for group in radio_buttons:
        try:
            options = group.find_elements(By.TAG_NAME, "span")
            # Scroll into view to make sure it's clickable
            for option in options:
                driver.execute_script("arguments[0].scrollIntoView(true);", option)
            random.choice(options).click()
            time.sleep(1)
        except Exception as e:
            print(f"Error while selecting radio button: {e}")

    # Handle Next button if available
    for btn in buttons:
        if btn.text.lower() in ["next", "পরবর্তী"]:  # Add translations if needed
            btn.click()
            time.sleep(3)

    # Submit form if no Next button found
    try:
        submit_button = WebDriverWait(driver, 10).until(
            EC.element_to_be_clickable((By.XPATH, "//span[text()='Submit']"))
        )
        submit_button.click()
        print("Form submitted successfully.")
    except Exception as e:
        print(f"Error submitting form: {e}")

# Main function
def main():
    excel_file = "Surveysheet.xlsx"  # Replace with your local file path
    links = load_links_from_excel(excel_file)
    
    # Loop through each link and process the form
    for link in links:
        visit_and_fill_form(link)
        time.sleep(3)  # Wait between form submissions

    driver.quit()

if __name__ == "__main__":
    main()
