// Step 1 Install: sudo apt install libsfml-dev
// Step 2 Compile: g++ mandelbrot.cpp -o mandelbrot -lsfml-graphics -lsfml-window -lsfml-system
// Step 3 Run: ./mandelbrot
// Controls: W = Zoom In, S = Zoom Out, Arrow keys = Pan/Move camera
#include <SFML/Graphics.hpp>
#include <complex>
#include <vector>

// Mandelbrot function with iteration limit
int mandelbrot(std::complex<float> c, int maxIterations) {
    std::complex<float> z = 0;
    int n = 0;
    while (abs(z) <= 2 && n < maxIterations) {
        z = z * z + c;
        ++n;
    }
    return n;
}

// Generate a color based on the number of iterations
sf::Color getColor(int iterations, int maxIterations) {
    if (iterations == maxIterations) return sf::Color::Black;

    // Color gradient: map iterations to a color spectrum
    float t = static_cast<float>(iterations) / maxIterations;
    sf::Uint8 r = static_cast<sf::Uint8>(9 * (1 - t) * t * t * t * 255);
    sf::Uint8 g = static_cast<sf::Uint8>(15 * (1 - t) * (1 - t) * t * t * 255);
    sf::Uint8 b = static_cast<sf::Uint8>(8.5 * (1 - t) * (1 - t) * (1 - t) * t * 255);

    return sf::Color(r, g, b);
}

// Progressive Mandelbrot rendering
void progressiveRender(sf::Image& image, sf::Vector2f center, float zoom, int maxIterations, int passes) {
    int width = image.getSize().x;
    int height = image.getSize().y;

    // Iterate over pixels with different passes for progressive detail
    for (int pass = 0; pass < passes; ++pass) {
        for (int x = pass; x < width; x += passes) {
            for (int y = pass; y < height; y += passes) {
                // Map pixel position to the complex plane
                float real = (x - width / 2.0f) / zoom + center.x;
                float imag = (y - height / 2.0f) / zoom + center.y;
                std::complex<float> c(real, imag);

                // Calculate the Mandelbrot value
                int iterations = mandelbrot(c, maxIterations);
                sf::Color color = getColor(iterations, maxIterations);

                // Set pixel color
                image.setPixel(x, y, color);
            }
        }
    }
}

int main() {
    const int WINDOW_WIDTH = 800;
    const int WINDOW_HEIGHT = 600;
    sf::RenderWindow window(sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), "Mandelbrot Explorer");

    // Mandelbrot view settings
    sf::Vector2f center(-0.5f, 0.0f);  // Center of the Mandelbrot set
    float zoom = 200.0f;               // Initial zoom level
    int maxIterations = 100;           // Number of iterations for detail
    int passes = 10;                   // Progressive rendering passes for each frame
    float panSpeed = 1.01f;            // Panning speed

    // Create an image and texture to store and display the fractal
    sf::Image mandelbrotImage;
    mandelbrotImage.create(WINDOW_WIDTH, WINDOW_HEIGHT);
    sf::Texture mandelbrotTexture;
    sf::Sprite mandelbrotSprite;

    bool needsRedraw = true;  // Flag for when we need to recalculate the fractal
    bool progressiveMode = true;  // Start with progressive rendering

    // Main loop
    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                window.close();
            }

            // Zoom in/out using W and S
            if (sf::Keyboard::isKeyPressed(sf::Keyboard::W)) {
                zoom *= 1.1f;
                needsRedraw = true;
                progressiveMode = true;  // Reset progressive mode after zoom
            }
            if (sf::Keyboard::isKeyPressed(sf::Keyboard::S)) {
                zoom *= 0.9f;
                needsRedraw = true;
                progressiveMode = true;
            }

            // Panning using arrow keys
            if (sf::Keyboard::isKeyPressed(sf::Keyboard::Left)) {
                center.x -= panSpeed / zoom;
                needsRedraw = true;
                progressiveMode = true;
            }
            if (sf::Keyboard::isKeyPressed(sf::Keyboard::Right)) {
                center.x += panSpeed / zoom;
                needsRedraw = true;
                progressiveMode = true;
            }
            if (sf::Keyboard::isKeyPressed(sf::Keyboard::Up)) {
                center.y -= panSpeed / zoom;
                needsRedraw = true;
                progressiveMode = true;
            }
            if (sf::Keyboard::isKeyPressed(sf::Keyboard::Down)) {
                center.y += panSpeed / zoom;
                needsRedraw = true;
                progressiveMode = true;
            }
        }

        // Recalculate and draw the fractal only if there was a change
        if (needsRedraw) {
            progressiveRender(mandelbrotImage, center, zoom, maxIterations, passes);
            mandelbrotTexture.loadFromImage(mandelbrotImage);
            mandelbrotSprite.setTexture(mandelbrotTexture);
            needsRedraw = false;  // Reset redraw flag
        }

        // Render the fractal
        window.clear();
        window.draw(mandelbrotSprite);
        window.display();
    }

    return 0;
}
