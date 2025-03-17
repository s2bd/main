import json
import random
import threading
from concurrent.futures import ThreadPoolExecutor, as_completed
import urllib.request
import urllib.error
import time
import ssl
import sys

# Disable SSL verification to avoid certificate errors
ssl._create_default_https_context = ssl._create_unverified_context

# User-Agent list
USER_AGENTS = [
    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/58.0.3029.110 Safari/537.3",
    "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_7) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/85.0.4183.121 Safari/537.36",
    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.138 Safari/537.36"
]

# Fetch books data from the provided URL
BOOKS_URL = "https://cdn.mux8.com/books.json"

# Fetch the books data from the URL and load it into a dictionary
def fetch_books_data():
    try:
        with urllib.request.urlopen(BOOKS_URL) as response:
            data = json.load(response)
            return data
    except urllib.error.URLError as e:
        print(f"Failed to fetch books data: {e}")
        return None

# Randomly choose a user-agent from the list
def get_random_user_agent():
    return random.choice(USER_AGENTS)

# Randomly choose a URL from a list of URLs
def get_random_url(book_data):
    book = random.choice(list(book_data.keys()))
    url = random.choice(book_data[book])
    return book, url

# Make a request to a URL and return the status code and headers with timeout handling
def visit_url(url, user_agent, timeout=5):
    headers = {"User-Agent": user_agent}
    try:
        req = urllib.request.Request(url, headers=headers)
        with urllib.request.urlopen(req, timeout=timeout) as response:
            status_code = response.getcode()
            response_header = response.getheaders()
            return status_code, response_header
    except urllib.error.URLError as e:
        print(f"Error visiting {url}: {e}\n")
        return None, None

# Keep track of visit counts for each URL and the URLs that are healthy
url_visit_count = {}
healthy_urls = set()
bad_urls = set()

# Lock for thread-safe printing
print_lock = threading.Lock()

# Function to simulate visiting a URL (for stage 1)
def visit_and_track_status(book_data, status_dict, total_urls, progress_lock, progress_bar):
    book, url = get_random_url(book_data)
    user_agent = get_random_user_agent()
    status_code, response_header = visit_url(url, user_agent)

    # Track healthy URLs, bad URLs, and remove bad URLs
    if status_code:
        if status_code == 200:
            healthy_urls.add(url)
        else:
            if url in healthy_urls:
                healthy_urls.remove(url)

        # Count visits
        if url not in url_visit_count:
            url_visit_count[url] = 0
        url_visit_count[url] += 1

        status_dict[url] = status_code
    else:
        bad_urls.add(url)  # Mark URL as bad if it didn't respond

    # Update progress bar in a thread-safe way
    with progress_lock:
        progress_bar[0] += 1
        print_progress_bar(progress_bar[0], total_urls)

# Function to count the total number of URLs across all books
def count_total_urls(book_data):
    return sum(len(urls) for urls in book_data.values())

# Custom progress bar for live updates
def print_progress_bar(iteration, total, bar_length=40):
    progress = iteration / total
    arrow = '=' * int(round(progress * bar_length) - 1)
    spaces = ' ' * (bar_length - len(arrow))
    sys.stdout.write(f"\r[{arrow}{spaces}] {iteration}/{total} ({progress * 100:.2f}%)\n")
    sys.stdout.flush()

# Function to visit every URL once (Stage 1) with ThreadPoolExecutor
def stage_one(book_data):
    print("Stage 1: Visiting every URL once...\n")
    
    # Calculate the total number of URLs (not just books)
    total_urls = count_total_urls(book_data)
    print(f"Total URLs to visit: {total_urls}")
    
    status_dict = {}

    progress_lock = threading.Lock()  # Lock to update the progress bar in a thread-safe way
    progress_bar = [0]  # Using a list to share state across threads

    with ThreadPoolExecutor(max_workers=50) as executor:  # Use ThreadPoolExecutor for concurrent requests
        futures = []
        for _ in range(total_urls):
            futures.append(executor.submit(visit_and_track_status, book_data, status_dict, total_urls, progress_lock, progress_bar))

        # Wait for all threads to complete
        for future in as_completed(futures):
            pass

    # Stage 1 Summary
    success_count = sum(1 for status in status_dict.values() if status == 200)
    failure_count = len(status_dict) - success_count

    print(f"\nStage 1 Summary:")
    print(f"Total URLs: {total_urls}")
    print(f"Successes (200): {success_count}")
    print(f"Failures (non-200): {failure_count}")
    print(f"Healthy URLs: {len(healthy_urls)}")
    print(f"Bad URLs: {len(bad_urls)}")
    print("-" * 50)

    # Wait for 10 seconds before moving to Stage 2
    print("Waiting 10 seconds before moving to Stage 2...")
    time.sleep(10)

# Function to simulate visiting a URL (for Stage 2) with ThreadPoolExecutor
def visit_book_urls_stage2(book_data):
    book, url = get_random_url(book_data)
    user_agent = get_random_user_agent()
    status_code, response_header = visit_url(url, user_agent)

    if status_code:
        if url not in url_visit_count:
            url_visit_count[url] = 0
        url_visit_count[url] += 1

        with print_lock:  # Ensure only one thread prints at a time
            print(f"Book: {book}\nURL: {url}\nTotal Hits: {url_visit_count[url]}")
            print(f"User-Agent: {user_agent}")
            print(f"Status Code: {status_code} {response_header[0][1]}")
            print("-" * 50)

# Function to display Stage 3 summary
def display_stage3_summary():
    print("Stage 3: Summary of Visits...\n")
    total_visits = sum(url_visit_count.values())
    print(f"Total Visits: {total_visits}")
    print(f"Total Visits by Book:")

    # Count total visits by book
    visits_by_book = {}
    for book, url in url_visit_count.items():
        visits_by_book[book] = visits_by_book.get(book, 0) + 1

    for book, count in visits_by_book.items():
        print(f"{book}: {count} visits")

    # Display top 3 most visited URLs
    sorted_urls = sorted(url_visit_count.items(), key=lambda x: x[1], reverse=True)[:3]
    print("Top 3 Most Visited URLs:")
    for url, count in sorted_urls:
        print(f"{url}: {count} visits")
    
    print("-" * 50)
    time.sleep(10)

# Main function to execute the stages
def start_all_stages(book_data):
    # Stage 1: Visit every URL once
    stage_one(book_data)

    total_visits = 0
    with ThreadPoolExecutor(max_workers=50) as executor:
        while True:
            # Stage 2: Random visits with summary at multiples of 5000
            futures = [executor.submit(visit_book_urls_stage2, book_data) for _ in range(50)]
            for future in as_completed(futures):
                total_visits += 1

                # Every time we reach a multiple of 5000 visits, show Stage 3
                if total_visits % 5000 == 0:
                    display_stage3_summary()

if __name__ == "__main__":
    book_data = fetch_books_data()
    if book_data:
        start_all_stages(book_data)
