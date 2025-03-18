#include <stdio.h>
#include <complex.h>

#define WIDTH 80
#define HEIGHT 40
#define MAX_ITER 100

// Function to print the Mandelbrot set
void draw_mandelbrot() {
    for (int y = 0; y < HEIGHT; y++) {
        for (int x = 0; x < WIDTH; x++) {
            double complex c = (x - WIDTH / 2.0) * (4.0 / WIDTH) + (y - HEIGHT / 2.0) * (4.0 / HEIGHT) * I;
            double complex z = 0;
            int iter;
            for (iter = 0; iter < MAX_ITER && cabs(z) < 2; iter++) {
                z = z * z + c;
            }
            // Print character based on iterations
            putchar(iter == MAX_ITER ? '#' : ' ');
        }
        putchar('\n');
    }
}

int main() {
    draw_mandelbrot(); // Generate and print the Mandelbrot set
    return 0;
}
