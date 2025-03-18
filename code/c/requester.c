#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <pthread.h>
#include <curl/curl.h>

#define MAX_URL_LENGTH 1000
#define MAX_CONCURRENT_REQUESTS 1000

// Global variables
int running = 1;
int total_counter = 0;

// Function to perform HTTP GET request and display response
void* do_request(void* arg) {
    char* url = (char*)arg;
    CURL *curl = curl_easy_init();
    if(curl) {
        CURLM *multi_handle = curl_multi_init();
        curl_multi_add_handle(multi_handle, curl);

        while (running) {
            int still_running = 0;
            curl_multi_perform(multi_handle, &still_running);
            if (!still_running) {
                total_counter++;
                printf("Total requests: %d\n", total_counter);
                curl_easy_setopt(curl, CURLOPT_URL, url);
            }
        }

        curl_multi_remove_handle(multi_handle, curl);
        curl_multi_cleanup(multi_handle);
        curl_easy_cleanup(curl);
    }
    pthread_exit(NULL);
}

int main() {
    char url[MAX_URL_LENGTH];
    printf("Enter URL: ");
    fgets(url, MAX_URL_LENGTH, stdin);
    url[strcspn(url, "\n")] = 0;  // Remove newline character

    pthread_t threads[MAX_CONCURRENT_REQUESTS];
    for (int i = 0; i < MAX_CONCURRENT_REQUESTS; i++) {
        pthread_create(&threads[i], NULL, do_request, url);
    }

    printf("Press 'Enter' to stop...\n");
    getchar(); // Wait for user to press Enter
    running = 0;

    for (int i = 0; i < MAX_CONCURRENT_REQUESTS; i++) {
        pthread_join(threads[i], NULL);
    }

    return 0;
}

