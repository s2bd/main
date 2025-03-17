import time
import random
import pandas as pd
from selenium import webdriver
from selenium.webdriver.common.by import By
from selenium.webdriver.common.keys import Keys
from webdriver_manager.chrome import ChromeDriverManager
from selenium.webdriver.chrome.service import Service
from sklearn.ensemble import RandomForestClassifier
from sklearn.preprocessing import LabelEncoder

# Load the dataset
data_file = "Survey Sheet.xlsx"
form_url = "https://docs.google.com/forms/d/e/1FAIpQLSf6GcrxsrYiT4M3qtbVYqEVVvEUdcErm8bdhqigOnMNecrp0w/viewform"
df = pd.read_excel(data_file)

# Encode categorical values for machine learning
encoders = {col: LabelEncoder().fit(df[col]) for col in df.columns if df[col].dtype == 'object'}
encoded_df = df.apply(lambda col: encoders[col.name].transform(col) if col.name in encoders else col)

# Train a basic machine learning model (RandomForest)
X = encoded_df.drop(columns=df.columns[-1])  # Features
y = encoded_df[df.columns[-1]]  # Target (last column as prediction target)

model = RandomForestClassifier(n_estimators=50, random_state=42)
model.fit(X, y)

# Set up the WebDriver
service = Service(ChromeDriverManager().install())
driver = webdriver.Chrome(service=service)

driver.get(form_url)
time.sleep(5)

def predict_answers(row):
    """Use trained model to predict responses based on the provided row data."""
    encoded_input = []
    for col_name in df.columns[:-1]:  # Exclude the target column
        if col_name in encoders:
            encoded_input.append(encoders[col_name].transform([row[col_name]])[0])
        else:
            encoded_input.append(row[col_name])
    
    prediction = model.predict([encoded_input])
    predicted_value = encoders[df.columns[-1]].inverse_transform(prediction)[0]
    return predicted_value


def fill_form(row):
    # Dynamically find all input fields
    text_fields = driver.find_elements(By.CSS_SELECTOR, "input[type='text']")
    dropdown_fields = driver.find_elements(By.CSS_SELECTOR, "div[role='listbox']")
    radio_buttons = driver.find_elements(By.CSS_SELECTOR, "div[role='radiogroup']")
    buttons = driver.find_elements(By.TAG_NAME, "button")

    # Fill text fields
    for index, field in enumerate(text_fields):
        field_name = field.get_attribute("aria-label")  # Extract question text
        matched_col = next((col for col in df.columns if field_name and field_name in col), None)
        if matched_col:
            field.send_keys(str(row[matched_col]))
        else:
            field.send_keys(predict_answers(row))
        time.sleep(1)

    # Fill dropdown fields
    for dropdown in dropdown_fields:
        dropdown.click()
        time.sleep(1)
        options = driver.find_elements(By.CSS_SELECTOR, "div[role='option']")
        random.choice(options).click()
        time.sleep(1)

    # Fill radio button selections
    for group in radio_buttons:
        options = group.find_elements(By.TAG_NAME, "span")
        random.choice(options).click()
        time.sleep(1)

    # Handle Next button if available
    for btn in buttons:
        if btn.text.lower() in ["next", "পরবর্তী"]:  # Add translations if needed
            btn.click()
            time.sleep(3)

    # Submit form if no Next button found
    submit_button = driver.find_element(By.XPATH, "//span[text()='Submit']")
    submit_button.click()
    print("Form submitted successfully.")

# Process each row in the dataset
for _, row in df.iterrows():
    fill_form(row)

driver.quit()
