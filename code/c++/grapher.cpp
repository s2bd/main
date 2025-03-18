#include <SFML/Graphics.hpp>
#include <cmath>
#include <iostream>
#include <vector>

// Constants
const float PHI = (1 + std::sqrt(5)) / 2;

// Function to calculate the polar equation
float calculateR(float theta, int equation) {
    switch (equation) {
        case 1: // Butterfly
            return -PHI * theta * std::sin(theta * PHI);
        case 2: // Alien Rocks
            return (PHI * std::log(std::exp(theta) * std::log(theta)) + (PHI * theta * std::sin(theta) - std::cos(PHI * theta)) * (PHI * theta * std::sin(theta) - std::cos(PHI * theta)));
        case 3: // Cherry
            return -PHI * std::log(std::exp(theta) * std::log(theta)) - (PHI * theta * std::sin(theta));
        case 4: // Paper Windmill
            return PHI * std::log(std::exp(theta) * std::log(theta)) + std::pow(PHI * theta * std::sin(PHI * theta), 2);
        // Add more cases for other equations
        default:
            return 0;
    }
}

// Function to convert polar coordinates to Cartesian coordinates
sf::Vector2f polarToCartesian(float r, float theta) {
    return sf::Vector2f(r * std::cos(theta), r * std::sin(theta));
}

int main() {
    // Create the window
    sf::RenderWindow window(sf::VideoMode(800, 800), "Graph Visualizer");

    // Set up a vertex array for the graph
    std::vector<sf::Vertex> graph;
    int equation = 4; // Change this to choose different equations

    // Range of theta
    float thetaMin = -4;
    float thetaMax = 4;
    int points = 1000; // Number of points to draw

    // Generate points
    for (int i = 0; i < points; ++i) {
        float theta = thetaMin + (thetaMax - thetaMin) * (i / (float)(points - 1));
        float r = calculateR(theta, equation);
        sf::Vector2f point = polarToCartesian(r * 10, theta); // Scale points for visibility
        point += sf::Vector2f(400, 400); // Center the graph in the window
        graph.emplace_back(point, sf::Color::White);
    }

    // Main loop
    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                window.close();
        }

        // Clear the window
        window.clear(sf::Color::Black);

        // Draw the graph
        window.draw(&graph[0], graph.size(), sf::PrimitiveType::LineStrip);

        // Display the contents
        window.display();
    }

    return 0;
}
