#include <stdio.h>

// Function to generate prime numbers up to nmax
void generate_primes(int nmax) {
    int x[5] = {2, 3, 5, 7, 11}; // Initial values
    int n = 1; // Counter of the current nth prime

    // Just print the preset values for n < 5
    if (nmax <= 5) {
        while (n <= nmax) {
            printf("%d, ", x[n-1]);
            n++;
        }
        printf("\n");
    } else {
        // Print initial values
        while (n <= 5) {
            printf("%d, ", x[n-1]);
            n++;
        }
        // Apply the recurrence relationship otherwise
        while (n <= nmax) {
            // Apply the formula
            int xn = x[4] + x[3] - x[2];
            int is_prime = 1; // Flag to check if xn is prime

            // Check if xn is prime
            for (int i = 0; i < 5; i++) {
                if (xn % x[i] == 0) {
                    is_prime = 0;
                    break;
                }
            }

            if (is_prime) {
                printf("%d, ", xn);
                // Update values
                for (int i = 0; i < 4; i++) {
                    x[i] = x[i+1];
                }
                x[4] = xn;
                n++;
            } else {
                // Update values
                for (int i = 0; i < 4; i++) {
                    x[i] = x[i+1];
                }
                x[4] = xn;
                n++;
            }
        }
        printf("\n");
    }
}

int main() {
    printf("Welcome to Dewan Mukto's Prime Number Generator!\n");
    int nmax;
    printf("Upto which prime to generate?: ");
    scanf("%d", &nmax);
    generate_primes(nmax);
    return 0;
}

