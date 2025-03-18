#include <stdio.h>
#include <stdlib.h>
#include <math.h>
#include <time.h>
#include <string.h>
#include <unistd.h> // For usleep function

#define WIDTH 40
#define HEIGHT 20
#define MAX_OBJECTS 10

typedef struct {
    char symbol;         // ASCII representation
    float x, y;         // Position
    float vx, vy;       // Velocity
    float mass;         // Mass
} Object;

Object objects[MAX_OBJECTS];
int object_count = 0;

// Function prototypes
void init_objects();
void apply_gravity();
void update_positions();
void check_collisions();
void transform_shape(Object *obj);
void draw_scene();
float inverse_gravity(float distance);
void spontaneous_force(Object *obj);

int main() {
    srand(time(NULL));
    init_objects();
    
    while (1) {
        apply_gravity();
        update_positions();
        check_collisions();
        draw_scene();

        // Apply spontaneous forces automatically at each frame
        for (int i = 0; i < object_count; i++) {
            spontaneous_force(&objects[i]);
        }

        usleep(100000); // Sleep for 0.1 seconds for a smoother display
    }
    
    return 0;
}

// Initialize objects in the simulation
void init_objects() {
    objects[0] = (Object){'A', 10, 10, 0, 0, 1.0}; // Object 1
    objects[1] = (Object){'B', 20, 5, 0, 0, 2.0};  // Object 2
    object_count = 2;
}

// Simulate inverse gravity based on proximity to objects
void apply_gravity() {
    for (int i = 0; i < object_count; i++) {
        for (int j = 0; j < object_count; j++) {
            if (i != j) {
                float dx = objects[j].x - objects[i].x;
                float dy = objects[j].y - objects[i].y;
                float distance = sqrt(dx * dx + dy * dy);
                float force = inverse_gravity(distance);
                
                // Apply forces
                objects[i].vy += force * (dy / distance);
                objects[i].vx += force * (dx / distance);
            }
        }
    }
}

// Update the positions of objects
void update_positions() {
    for (int i = 0; i < object_count; i++) {
        objects[i].x += objects[i].vx;
        objects[i].y += objects[i].vy;
        // Apply some damping
        objects[i].vx *= 0.99;
        objects[i].vy *= 0.99;

        // Ensure objects stay within bounds
        if (objects[i].x < 0) objects[i].x = 0;
        if (objects[i].x >= WIDTH) objects[i].x = WIDTH - 1;
        if (objects[i].y < 0) objects[i].y = 0;
        if (objects[i].y >= HEIGHT) objects[i].y = HEIGHT - 1;
    }
}

// Check for collisions between objects
void check_collisions() {
    for (int i = 0; i < object_count; i++) {
        for (int j = 0; j < object_count; j++) {
            if (i != j && (int)objects[i].x == (int)objects[j].x && (int)objects[i].y == (int)objects[j].y) {
                transform_shape(&objects[i]);
                transform_shape(&objects[j]);
            }
        }
    }
}

// Function to transform the shape of an object on collision
void transform_shape(Object *obj) {
    if (obj->symbol == 'A') {
        obj->symbol = 'C'; // Change shape on collision
    } else {
        obj->symbol = 'A'; // Change back
    }
}

// Draw the current state of the scene
void draw_scene() {
    system("clear"); // Clear the console
    char screen[HEIGHT][WIDTH + 1];

    // Initialize the screen
    for (int i = 0; i < HEIGHT; i++) {
        for (int j = 0; j < WIDTH; j++) {
            screen[i][j] = ' ';
        }
        screen[i][WIDTH] = '\0'; // Null terminate
    }

    // Place objects on the screen
    for (int i = 0; i < object_count; i++) {
        if ((int)objects[i].y < HEIGHT && (int)objects[i].x < WIDTH) {
            screen[(int)objects[i].y][(int)objects[i].x] = objects[i].symbol;
        }
    }

    // Display the screen
    for (int i = 0; i < HEIGHT; i++) {
        printf("%s\n", screen[i]);
    }
}

// Get inverse gravity based on distance
float inverse_gravity(float distance) {
    return (distance > 0) ? 1.0 / distance : 0;
}

// Apply spontaneous force based on a random chance
void spontaneous_force(Object *obj) {
    if (rand() % 10 < 3) { // 30% chance to apply spontaneous force
        obj->vy += (rand() % 3 + 1) * (rand() % 2 == 0 ? 1 : -1); // Random upward or downward force
        obj->vx += (rand() % 3 + 1) * (rand() % 2 == 0 ? 1 : -1); // Random horizontal force
    }
}
