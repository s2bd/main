#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <SDL2/SDL.h>

#define NUM_DRONES 500 // 50
#define WIDTH 800
#define HEIGHT 600
#define MAX_SPEED 5 // 4
#define COHESION_FACTOR 0.01
#define ALIGNMENT_FACTOR 0.05
#define SEPARATION_FACTOR 0.1
#define SEPARATION_DISTANCE 25

// Structure to represent a drone (or Boid)
typedef struct {
    float x, y;
    float vx, vy; // velocity
} Drone;

Drone drones[NUM_DRONES];
SDL_Window *window = NULL;
SDL_Renderer *renderer = NULL;

// Initialize the drones with random positions and velocities
void initialize_drones() {
    srand(time(NULL));
    for (int i = 0; i < NUM_DRONES; i++) {
        drones[i].x = rand() % WIDTH;
        drones[i].y = rand() % HEIGHT;
        drones[i].vx = (float)(rand() % MAX_SPEED) - MAX_SPEED / 2;
        drones[i].vy = (float)(rand() % MAX_SPEED) - MAX_SPEED / 2;
    }
}

// Helper function to limit the velocity of drones
void limit_velocity(Drone *drone) {
    float speed = sqrt(drone->vx * drone->vx + drone->vy * drone->vy);
    if (speed > MAX_SPEED) {
        drone->vx = (drone->vx / speed) * MAX_SPEED;
        drone->vy = (drone->vy / speed) * MAX_SPEED;
    }
}

// Update drone positions based on the rules of Boids algorithm
void update_drones() {
    for (int i = 0; i < NUM_DRONES; i++) {
        float avg_vx = 0, avg_vy = 0;
        float avg_x = 0, avg_y = 0;
        float separation_x = 0, separation_y = 0;
        int neighbors = 0;

        for (int j = 0; j < NUM_DRONES; j++) {
            if (i == j) continue;
            float distance = sqrt(pow(drones[i].x - drones[j].x, 2) + pow(drones[i].y - drones[j].y, 2));

            // Rule 1: Cohesion (move towards the average position of local flockmates)
            avg_x += drones[j].x;
            avg_y += drones[j].y;

            // Rule 2: Alignment (align velocity with the average velocity of local flockmates)
            avg_vx += drones[j].vx;
            avg_vy += drones[j].vy;

            // Rule 3: Separation (avoid crowding local flockmates)
            if (distance < SEPARATION_DISTANCE) {
                separation_x += (drones[i].x - drones[j].x);
                separation_y += (drones[i].y - drones[j].y);
            }

            neighbors++;
        }

        if (neighbors > 0) {
            avg_x /= neighbors;
            avg_y /= neighbors;
            avg_vx /= neighbors;
            avg_vy /= neighbors;

            drones[i].vx += (avg_x - drones[i].x) * COHESION_FACTOR;  // Cohesion
            drones[i].vy += (avg_y - drones[i].y) * COHESION_FACTOR;

            drones[i].vx += avg_vx * ALIGNMENT_FACTOR;  // Alignment
            drones[i].vy += avg_vy * ALIGNMENT_FACTOR;

            drones[i].vx += separation_x * SEPARATION_FACTOR;  // Separation
            drones[i].vy += separation_y * SEPARATION_FACTOR;
        }

        limit_velocity(&drones[i]);

        drones[i].x += drones[i].vx;
        drones[i].y += drones[i].vy;

        // Keep drones within the bounds of the window
        if (drones[i].x < 0) drones[i].x = WIDTH;
        if (drones[i].x > WIDTH) drones[i].x = 0;
        if (drones[i].y < 0) drones[i].y = HEIGHT;
        if (drones[i].y > HEIGHT) drones[i].y = 0;
    }
}

// Render the drones on the screen
void render_drones() {
    // Set background color to black
    SDL_SetRenderDrawColor(renderer, 0, 0, 0, 255);
    SDL_RenderClear(renderer);

    // Set color for the drones
    SDL_SetRenderDrawColor(renderer, 0, 255, 0, 255);

    // Render each drone as a small rectangle or point
    for (int i = 0; i < NUM_DRONES; i++) {
        SDL_Rect drone_rect = { (int)drones[i].x, (int)drones[i].y, 4, 4 };
        SDL_RenderFillRect(renderer, &drone_rect);
    }

    SDL_RenderPresent(renderer);
}

int main(int argc, char* argv[]) {
    // Initialize SDL
    if (SDL_Init(SDL_INIT_VIDEO) != 0) {
        printf("SDL_Init Error: %s\n", SDL_GetError());
        return 1;
    }

    // Create SDL window
    window = SDL_CreateWindow("Drone Simulation", 100, 100, WIDTH, HEIGHT, SDL_WINDOW_SHOWN);
    if (window == NULL) {
        printf("SDL_CreateWindow Error: %s\n", SDL_GetError());
        SDL_Quit();
        return 1;
    }

    // Create SDL renderer
    renderer = SDL_CreateRenderer(window, -1, SDL_RENDERER_ACCELERATED | SDL_RENDERER_PRESENTVSYNC);
    if (renderer == NULL) {
        SDL_DestroyWindow(window);
        printf("SDL_CreateRenderer Error: %s\n", SDL_GetError());
        SDL_Quit();
        return 1;
    }

    initialize_drones();

    int running = 1;
    SDL_Event event;

    // Main simulation loop
    while (running) {
        // Handle events (such as closing the window)
        while (SDL_PollEvent(&event)) {
            if (event.type == SDL_QUIT) {
                running = 0;
            }
        }

        update_drones();
        render_drones();

        SDL_Delay(16); // Delay for ~60 FPS
    }

    // Clean up SDL
    SDL_DestroyRenderer(renderer);
    SDL_DestroyWindow(window);
    SDL_Quit();

    return 0;
}
