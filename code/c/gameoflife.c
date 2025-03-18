#include <stdio.h>
#include <string.h>
#include <unistd.h> // For sleep function

#define SIZE 10

// Function to print the grid
void print_grid(int grid[SIZE][SIZE]) {
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            printf("%c ", grid[i][j] ? 'O' : '.');
        }
        printf("\n");
    }
}

// Function to compute the next generation
void next_generation(int grid[SIZE][SIZE]) {
    int new_grid[SIZE][SIZE] = {0};
    for (int i = 0; i < SIZE; i++) {
        for (int j = 0; j < SIZE; j++) {
            int alive_neighbors = 0;
            for (int x = -1; x <= 1; x++) {
                for (int y = -1; y <= 1; y++) {
                    if (x || y) {
                        int ni = i + x, nj = j + y;
                        if (ni >= 0 && ni < SIZE && nj >= 0 && nj < SIZE) {
                            alive_neighbors += grid[ni][nj];
                        }
                    }
                }
            }
            // Rules of Life
            if (grid[i][j] && (alive_neighbors == 2 || alive_neighbors == 3)) {
                new_grid[i][j] = 1; // Stay alive
            } else if (!grid[i][j] && alive_neighbors == 3) {
                new_grid[i][j] = 1; // Born
            }
        }
    }
    memcpy(grid, new_grid, sizeof(new_grid)); // Update grid
}

int main() {
    // Initialize a simple glider pattern
    int grid[SIZE][SIZE] = {0};
    grid[1][2] = grid[2][3] = grid[3][1] = grid[3][2] = grid[3][3] = 1;

    while (1) {
        print_grid(grid);
        next_generation(grid);
        usleep(500000); // Sleep for half a second
        printf("\033[H\033[J"); // Clear screen
    }
    return 0;
}
