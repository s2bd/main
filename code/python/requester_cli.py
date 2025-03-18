import requests
import threading
url = input("Enter URL:")
limit = int(input("How many? "))
def do_request():
    while True:
        response = requests.post(url, data=data).text
        print(response)
data = {
    'log': 'jake',
    'pwd': 'AlanTuring'
    }
threads = []
for i in range(limit):
    t = threading.Thread(target=do_request)
    t.daemon = True
    threads.append(t)
for i in range(limit):
    threads[i].start()
for i in range(limit):
    threads[i].join()
