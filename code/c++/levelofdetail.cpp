#include <SFML/Graphics.hpp>
#include <cmath>
#include <iostream>
#include <vector>

const int WIDTH = 800;
const int HEIGHT = 600;
const int MAP_WIDTH = 10;
const int MAP_HEIGHT = 10;
const float FOV = 3.14159 / 4; // Field of view of 90 degrees

// Simple map representation (1 = wall, 0 = empty space)
int map[MAP_WIDTH][MAP_HEIGHT] = {
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 0, 1, 1, 1, 1, 0, 1, 0, 1},
    {1, 0, 0, 0, 0, 1, 0, 1, 0, 1},
    {1, 1, 1, 1, 0, 1, 0, 1, 0, 1},
    {1, 0, 0, 0, 0, 0, 0, 0, 0, 1},
    {1, 1, 1, 1, 1, 1, 1, 1, 1, 1},
};

// Player struct to hold player information
struct Player {
    float x, y; // Player position
    float angle; // Player angle
};

// Function to cast a ray
float castRay(float angle, Player& player) {
    float rayX = player.x;
    float rayY = player.y;

    float stepSize = 0.01; // Increment to move along the ray
    float distance = 0.0f;

    while (true) {
        distance += stepSize;
        rayX = player.x + cos(angle) * distance;
        rayY = player.y + sin(angle) * distance;

        // Check for wall collision
        int mapX = static_cast<int>(rayX);
        int mapY = static_cast<int>(rayY);
        if (mapX < 0 || mapX >= MAP_WIDTH || mapY < 0 || mapY >= MAP_HEIGHT || map[mapX][mapY] == 1) {
            return distance;
        }
    }
}

int main() {
    sf::RenderWindow window(sf::VideoMode(WIDTH, HEIGHT), "Simple Raycasting Game");

    Player player = {5.0f, 5.0f, 0.0f}; // Initialize player position and angle

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                window.close();
            }
        }

        // Player controls
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Left)) {
            player.angle -= 0.05f; // Rotate left
        }
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Right)) {
            player.angle += 0.05f; // Rotate right
        }
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Up)) {
            player.x += cos(player.angle) * 0.1f; // Move forward
            player.y += sin(player.angle) * 0.1f;
        }
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Down)) {
            player.x -= cos(player.angle) * 0.1f; // Move backward
            player.y -= sin(player.angle) * 0.1f;
        }

        window.clear();

        // Render rays
        for (int x = 0; x < WIDTH; x++) {
            float angle = player.angle - FOV / 2 + (static_cast<float>(x) / WIDTH) * FOV;
            float distance = castRay(angle, player);

            // Map the distance to screen height
            int lineHeight = static_cast<int>(HEIGHT / (distance + 0.0001)); // Prevent division by zero
            sf::Vertex line[] = {
                sf::Vertex(sf::Vector2f(x, (HEIGHT / 2) - (lineHeight / 2)), sf::Color::White),
                sf::Vertex(sf::Vector2f(x, (HEIGHT / 2) + (lineHeight / 2)), sf::Color::White),
            };
            window.draw(line, 2, sf::PrimitiveType::Lines);
        }

        window.display();
    }

    return 0;
}
