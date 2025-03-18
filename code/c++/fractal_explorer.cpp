#include <SFML/Graphics.hpp>
#include <complex>
#include <iostream>
#include <regex>

// Function to validate the custom equation
bool validateEquation(const std::string& equation) {
    // Regex to match valid characters for the equation
    std::regex pattern("^[z\\+\\-\\*/c0-9\\.]*$");
    return std::regex_match(equation, pattern);
}

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

// Julia function with iteration limit
int julia(std::complex<float> z, std::complex<float> c, int maxIterations) {
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
void progressiveRenderMandelbrot(sf::Image& image, sf::Vector2f center, float zoom, int maxIterations, int passes) {
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

// Progressive Julia rendering
void progressiveRenderJulia(sf::Image& image, sf::Vector2f center, float zoom, std::complex<float> c, int maxIterations, int passes) {
    int width = image.getSize().x;
    int height = image.getSize().y;

    // Iterate over pixels with different passes for progressive detail
    for (int pass = 0; pass < passes; ++pass) {
        for (int x = pass; x < width; x += passes) {
            for (int y = pass; y < height; y += passes) {
                // Map pixel position to the complex plane
                float real = (x - width / 2.0f) / zoom + center.x;
                float imag = (y - height / 2.0f) / zoom + center.y;
                std::complex<float> z(real, imag);

                // Calculate the Julia value
                int iterations = julia(z, c, maxIterations);
                sf::Color color = getColor(iterations, maxIterations);

                // Set pixel color
                image.setPixel(x, y, color);
            }
        }
    }
}

// Placeholder function for custom fractal rendering
void customFractalRender(sf::Image& image, std::string equation, sf::Vector2f center, float zoom, int maxIterations) {
    int width = image.getSize().x;
    int height = image.getSize().y;

    // Logic for custom fractal rendering using the custom equation would go here
    // This part needs to be implemented based on the custom equation
}

int main() {
    const int WINDOW_WIDTH = 800;
    const int WINDOW_HEIGHT = 600;
    sf::RenderWindow window(sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), "Fractal Explorer");

    // Prompt user for fractal choice
    char fractalChoice;
    std::cout << "Would you like to explore the Mandelbrot fractal? (y/n): ";
    std::cin >> fractalChoice;

    if (fractalChoice == 'y' || fractalChoice == 'Y') {
        // Set settings for Mandelbrot fractal
        sf::Vector2f center(-0.5f, 0.0f);
        float zoom = 200.0f;
        int maxIterations = 100;
        int passes = 10;

        // Create an image and texture for the Mandelbrot fractal
        sf::Image mandelbrotImage;
        mandelbrotImage.create(WINDOW_WIDTH, WINDOW_HEIGHT);
        sf::Texture mandelbrotTexture;
        sf::Sprite mandelbrotSprite;

        bool needsRedraw = true;

        // Main loop for Mandelbrot fractal
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
                }
                if (sf::Keyboard::isKeyPressed(sf::Keyboard::S)) {
                    zoom *= 0.9f;
                    needsRedraw = true;
                }

                // Panning using arrow keys
                if (sf::Keyboard::isKeyPressed(sf::Keyboard::Left)) {
                    center.x -= 0.01f / zoom;
                    needsRedraw = true;
                }
                if (sf::Keyboard::isKeyPressed(sf::Keyboard::Right)) {
                    center.x += 0.01f / zoom;
                    needsRedraw = true;
                }
                if (sf::Keyboard::isKeyPressed(sf::Keyboard::Up)) {
                    center.y -= 0.01f / zoom;
                    needsRedraw = true;
                }
                if (sf::Keyboard::isKeyPressed(sf::Keyboard::Down)) {
                    center.y += 0.01f / zoom;
                    needsRedraw = true;
                }
            }

            // Recalculate and draw the Mandelbrot fractal
            if (needsRedraw) {
                progressiveRenderMandelbrot(mandelbrotImage, center, zoom, maxIterations, passes);
                mandelbrotTexture.loadFromImage(mandelbrotImage);
                mandelbrotSprite.setTexture(mandelbrotTexture);
                needsRedraw = false;
            }

            // Render the fractal
            window.clear();
            window.draw(mandelbrotSprite);
            window.display();
        }
    } else {
        // Check for Julia fractal
        char juliaChoice;
        std::cout << "Would you like to explore the Julia fractal? (y/n): ";
        std::cin >> juliaChoice;

        if (juliaChoice == 'y' || juliaChoice == 'Y') {
            // Get Julia constant
            std::complex<float> c(-0.7f, 0.27015f); // Example Julia constant
            sf::Vector2f center(0.0f, 0.0f);
            float zoom = 200.0f;
            int maxIterations = 100;
            int passes = 10;

            // Create an image and texture for the Julia fractal
            sf::Image juliaImage;
            juliaImage.create(WINDOW_WIDTH, WINDOW_HEIGHT);
            sf::Texture juliaTexture;
            sf::Sprite juliaSprite;

            bool needsRedraw = true;

            // Main loop for Julia fractal
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
                    }
                    if (sf::Keyboard::isKeyPressed(sf::Keyboard::S)) {
                        zoom *= 0.9f;
                        needsRedraw = true;
                    }

                    // Panning using arrow keys
                    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Left)) {
                        center.x -= 2.01f / zoom;
                        needsRedraw = true;
                    }
                    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Right)) {
                        center.x += 2.01f / zoom;
                        needsRedraw = true;
                    }
                    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Up)) {
                        center.y -= 2.01f / zoom;
                        needsRedraw = true;
                    }
                    if (sf::Keyboard::isKeyPressed(sf::Keyboard::Down)) {
                        center.y += 2.01f / zoom;
                        needsRedraw = true;
                    }
                }

                // Recalculate and draw the Julia fractal
                if (needsRedraw) {
                    progressiveRenderJulia(juliaImage, center, zoom, c, maxIterations, passes);
                    juliaTexture.loadFromImage(juliaImage);
                    juliaSprite.setTexture(juliaTexture);
                    needsRedraw = false;
                }

                // Render the fractal
                window.clear();
                window.draw(juliaSprite);
                window.display();
            }
        } else {
            // Custom fractal logic here
            std::string customEquation;
            std::cout << "Enter a custom equation (in terms of z, c, +, -, *, /): ";
            std::cin >> customEquation;

            if (validateEquation(customEquation)) {
                sf::Vector2f center(0.0f, 0.0f);
                float zoom = 200.0f;
                int maxIterations = 100;

                // Create an image and texture for the custom fractal
                sf::Image customImage;
                customImage.create(WINDOW_WIDTH, WINDOW_HEIGHT);
                sf::Texture customTexture;
                sf::Sprite customSprite;

                bool needsRedraw = true;

                // Main loop for custom fractal
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
                        }
                        if (sf::Keyboard::isKeyPressed(sf::Keyboard::S)) {
                            zoom *= 0.9f;
                            needsRedraw = true;
                        }

                        // Panning using arrow keys
                        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Left)) {
                            center.x -= 0.01f / zoom;
                            needsRedraw = true;
                        }
                        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Right)) {
                            center.x += 0.01f / zoom;
                            needsRedraw = true;
                        }
                        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Up)) {
                            center.y -= 0.01f / zoom;
                            needsRedraw = true;
                        }
                        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Down)) {
                            center.y += 0.01f / zoom;
                            needsRedraw = true;
                        }
                    }

                    // Recalculate and draw the custom fractal
                    if (needsRedraw) {
                        customFractalRender(customImage, customEquation, center, zoom, maxIterations);
                        customTexture.loadFromImage(customImage);
                        customSprite.setTexture(customTexture);
                        needsRedraw = false;
                    }

                    // Render the fractal
                    window.clear();
                    window.draw(customSprite);
                    window.display();
                }
            } else {
                std::cout << "Invalid equation. Exiting." << std::endl;
            }
        }
    }

    return 0;
}
