#include <SFML/Graphics.hpp>
#include <vector>
#include <cstdlib>
#include <cmath>

enum Mood { HAPPY, IMPATIENT, ANGRY, SAD };

struct Person {
    sf::CircleShape shape;
    Mood mood;

    Person(float x, float y) {
        shape.setRadius(10);
        shape.setPosition(x, y);
        mood = static_cast<Mood>(rand() % 4);
        updateColor();
    }

    void updateColor() {
        switch (mood) {
            case HAPPY: shape.setFillColor(sf::Color::Green); break;
            case IMPATIENT: shape.setFillColor(sf::Color::Yellow); break;
            case ANGRY: shape.setFillColor(sf::Color::Red); break;
            case SAD: shape.setFillColor(sf::Color::Blue); break;
        }
    }

    void move(float width, float height) {
        float dx = rand() % 5 - 2; // Random x movement
        float dy = rand() % 5 - 2; // Random y movement

        // Update position
        shape.move(dx, dy);

        // Prevent moving outside the window
        if (shape.getPosition().x < 0) shape.setPosition(0, shape.getPosition().y);
        if (shape.getPosition().y < 0) shape.setPosition(shape.getPosition().x, 0);
        if (shape.getPosition().x > width - shape.getRadius() * 2) 
            shape.setPosition(width - shape.getRadius() * 2, shape.getPosition().y);
        if (shape.getPosition().y > height - shape.getRadius() * 2) 
            shape.setPosition(shape.getPosition().x, height - shape.getRadius() * 2);
    }

    void push(std::vector<Person>& crowd) {
        int nearbyCount = 0;
        for (auto& other : crowd) {
            if (&other != this && shape.getGlobalBounds().intersects(other.shape.getGlobalBounds())) {
                nearbyCount++;
            }
        }

        // Check nearby people and update mood accordingly
        if (nearbyCount > 5) { // Threshold for crowding
            mood = ANGRY;
        } else if (nearbyCount > 2) {
            mood = IMPATIENT;
        } else {
            mood = HAPPY; // Reset to happy if there's space
        }

        updateColor();
    }

    void scatter(float explosionX, float explosionY) {
        // Calculate distance from the explosion center
        sf::Vector2f position = shape.getPosition();
        float distance = std::sqrt(std::pow(position.x - explosionX, 2) + std::pow(position.y - explosionY, 2));

        // If within a certain range, push the person away
        if (distance < 100.0f) { // Explosion radius
            float pushStrength = (100.0f - distance) / 100.0f; // Strength decreases with distance
            float angle = std::atan2(position.y - explosionY, position.x - explosionX); // Direction away from the explosion

            // Calculate new movement vector
            float dx = pushStrength * std::cos(angle) * 5; // Scale push strength
            float dy = pushStrength * std::sin(angle) * 5;

            // Move the person away from the explosion
            shape.move(dx, dy);
        }
    }
};

int main() {
    sf::RenderWindow window(sf::VideoMode(800, 600), "Crowd Simulator");
    std::vector<Person> crowd;
    bool isLeftMousePressed = false;
    bool isRightMousePressed = false;
    sf::Vector2i lastRightClickPosition; // To store the last right-click position

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                window.close();
            if (event.type == sf::Event::MouseButtonPressed) {
                if (event.mouseButton.button == sf::Mouse::Left) {
                    isLeftMousePressed = true; // Start adding people
                } else if (event.mouseButton.button == sf::Mouse::Right) {
                    isRightMousePressed = true; // Start explosion effect
                    lastRightClickPosition = sf::Mouse::getPosition(window); // Store position
                }
            }
            if (event.type == sf::Event::MouseButtonReleased) {
                if (event.mouseButton.button == sf::Mouse::Left) {
                    isLeftMousePressed = false; // Stop adding people
                } else if (event.mouseButton.button == sf::Mouse::Right) {
                    isRightMousePressed = false; // Stop explosion effect
                }
            }
        }

        // Add people continuously while left mouse button is pressed
        if (isLeftMousePressed) {
            // Get the mouse position
            sf::Vector2i mousePosition = sf::Mouse::getPosition(window);
            crowd.emplace_back(mousePosition.x, mousePosition.y);
        }

        // Create an explosion effect continuously while right mouse button is pressed
        if (isRightMousePressed) {
            float explosionX = lastRightClickPosition.x;
            float explosionY = lastRightClickPosition.y;

            // Scatter nearby people
            for (auto& person : crowd) {
                person.scatter(explosionX, explosionY);
            }
        }

        // Update and draw the crowd
        window.clear();
        for (auto& person : crowd) {
            person.move(window.getSize().x, window.getSize().y);
        }

        for (auto& person : crowd) {
            person.push(crowd);
            window.draw(person.shape);
        }

        window.display(); // Show the updated frame
    }

    return 0;
}
