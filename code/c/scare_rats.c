#include <SDL2/SDL.h>
#include <stdlib.h>
#include <stdio.h>
#include <math.h>
#include <time.h>

#define WIDTH 800
#define HEIGHT 600
#define NUM_RATS 30
#define MAX_SPEED 2
#define FOOD_LIFETIME 10000 // 10 seconds in milliseconds
#define DEVICE_RADIUS 500

// Struct for Rats
typedef struct {
    float x, y;
    float vx, vy;
    int isScared;
} Rat;

// Struct for Food
typedef struct {
    float x, y;
    int active;
} Food;

// Struct for Devices (Sound Wave)
typedef struct {
    float x, y;
    int active;
    Uint32 startTime; // When it was placed
} Device;

Rat rats[NUM_RATS];
Food food;
Device devices[10];
int deviceCount = 0;
Uint32 foodSpawnTime = 0;

// Initialize rats at random positions
void initialize_rats() {
    srand(time(NULL));
    for (int i = 0; i < NUM_RATS; i++) {
        rats[i].x = rand() % WIDTH;
        rats[i].y = rand() % HEIGHT;
        rats[i].vx = (rand() % MAX_SPEED) - MAX_SPEED / 2;
        rats[i].vy = (rand() % MAX_SPEED) - MAX_SPEED / 2;
        rats[i].isScared = 0;
    }
}

// Spawn food at a random position
void spawn_food() {
    food.x = rand() % WIDTH;
    food.y = rand() % HEIGHT;
    food.active = 1;
}

// Simulate simple electronic sound wave circuit (device scare)
void play_sound_wave() {
    // In reality, we would simulate electronic IC behavior, 
    // here we simply play a sound and display the range.
    printf("Sound wave device activated!\n");
    // SDL could play a real sound here.
}

// Update rat positions
void update_rats() {
    for (int i = 0; i < NUM_RATS; i++) {
        // Attraction to food if it's active
        if (food.active && !rats[i].isScared) {
            float dx = food.x - rats[i].x;
            float dy = food.y - rats[i].y;
            float distance = sqrt(dx * dx + dy * dy);

            // Move towards food
            rats[i].vx += (dx / distance) * 0.05;
            rats[i].vy += (dy / distance) * 0.05;
        }

        // Scared away by active sound devices
        for (int j = 0; j < deviceCount; j++) {
            if (devices[j].active) {
                float dx = devices[j].x - rats[i].x;
                float dy = devices[j].y - rats[i].y;
                float distance = sqrt(dx * dx + dy * dy);

                if (distance < DEVICE_RADIUS) {
                    rats[i].isScared = 1; // Mark rat as scared
                    rats[i].vx -= (dx / distance) * 0.5;
                    rats[i].vy -= (dy / distance) * 0.5;
                }
            }
        }

        // Move rat and ensure it stays in bounds
        rats[i].x += rats[i].vx;
        rats[i].y += rats[i].vy;

        if (rats[i].x < 0) rats[i].x = WIDTH;
        if (rats[i].x > WIDTH) rats[i].x = 0;
        if (rats[i].y < 0) rats[i].y = HEIGHT;
        if (rats[i].y > HEIGHT) rats[i].y = 0;

        // Reduce fear if far from device
        rats[i].isScared = 0;
    }
}

// Handle mouse click to drop sound wave device
void place_device(int mouseX, int mouseY) {
    if (deviceCount < 10) {
        devices[deviceCount].x = mouseX;
        devices[deviceCount].y = mouseY;
        devices[deviceCount].active = 1;
        devices[deviceCount].startTime = SDL_GetTicks();
        play_sound_wave();
        deviceCount++;
    }
}

// Main game loop
int main(int argc, char* argv[]) {
    SDL_Init(SDL_INIT_VIDEO);
    SDL_Window* window = SDL_CreateWindow("Rat Simulation", SDL_WINDOWPOS_UNDEFINED, SDL_WINDOWPOS_UNDEFINED, WIDTH, HEIGHT, SDL_WINDOW_SHOWN);
    SDL_Renderer* renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED);

    initialize_rats();
    spawn_food();
    foodSpawnTime = SDL_GetTicks();

    int running = 1;
    while (running) {
        SDL_Event event;
        while (SDL_PollEvent(&event)) {
            if (event.type == SDL_QUIT) {
                running = 0;
            } else if (event.type == SDL_MOUSEBUTTONDOWN) {
                if (event.button.button == SDL_BUTTON_LEFT) {
                    place_device(event.button.x, event.button.y);
                }
            }
        }

        // Spawn new food every 10 seconds
        if (SDL_GetTicks() - foodSpawnTime > FOOD_LIFETIME) {
            spawn_food();
            foodSpawnTime = SDL_GetTicks();
        }

        // Update rats and their behavior
        update_rats();

        // Rendering (clear and draw)
        SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255);
        SDL_RenderClear(renderer);

        // Draw rats
        SDL_SetRenderDrawColor(renderer, 255, 255, 255, 255);
        for (int i = 0; i < NUM_RATS; i++) {
            SDL_Rect ratRect = { (int)rats[i].x, (int)rats[i].y, 10, 10 };
            SDL_RenderFillRect(renderer, &ratRect);
        }

        // Draw food
        if (food.active) {
            SDL_SetRenderDrawColor(renderer, 0, 255, 0, 255);
            SDL_Rect foodRect = { (int)food.x, (int)food.y, 10, 10 };
            SDL_RenderFillRect(renderer, &foodRect);
        }

        // Draw sound wave devices
        SDL_SetRenderDrawColor(renderer, 255, 0, 0, 255);
        for (int i = 0; i < deviceCount; i++) {
            if (devices[i].active) {
                SDL_Rect deviceRect = { (int)devices[i].x - 5, (int)devices[i].y - 5, 10, 10 };
                SDL_RenderFillRect(renderer, &deviceRect);
            }
        }

        SDL_RenderPresent(renderer);
        SDL_Delay(90); // Roughly 60 FPS
    }

    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    SDL_Quit();

    return 0;
}
