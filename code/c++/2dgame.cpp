#include <SFML/Graphics.hpp>
#include <complex>
#include <vector>
#include <random>
#include <iostream>

// Global constants
const int WINDOW_WIDTH = 800;
const int WINDOW_HEIGHT = 600;
const float PLAYER_SPEED = 200.0f;
const float ATTACK_RADIUS = 50.0f;

// Utility function for distance
float distance(sf::Vector2f a, sf::Vector2f b) {
    return std::sqrt((a.x - b.x) * (a.x - b.x) + (a.y - b.y) * (a.y - b.y));
}

// Mandelbrot Set function
float mandelbrot(float real, float imag) {
    std::complex<float> c(real, imag);
    std::complex<float> z(0, 0);
    int iterations = 0;
    int maxIterations = 100;

    while (abs(z) <= 2 && iterations < maxIterations) {
        z = z * z + c;
        ++iterations;
    }

    return static_cast<float>(iterations) / maxIterations;
}

// Julia Set function
float julia(float real, float imag, std::complex<float> c) {
    std::complex<float> z(real, imag);
    int iterations = 0;
    int maxIterations = 100;

    while (abs(z) <= 2 && iterations < maxIterations) {
        z = z * z + c;
        ++iterations;
    }

    return static_cast<float>(iterations) / maxIterations;
}

// Random number generator
std::mt19937 rng{std::random_device{}()};

// Procedural enemy class
class Enemy {
public:
    sf::CircleShape shape;
    float health;
    sf::Vector2f velocity;

    Enemy(sf::Vector2f position, float size, sf::Vector2f vel) {
        shape.setPosition(position);
        shape.setRadius(size);
        shape.setFillColor(sf::Color::Red);
        health = 100.0f;
        velocity = vel;
    }

    void takeDamage(float damage) {
        health -= damage;
        shape.setFillColor(health <= 0 ? sf::Color::Black : sf::Color::Red);
    }

    bool isAlive() {
        return health > 0;
    }

    void update(float deltaTime) {
        shape.move(velocity * deltaTime);  // Fractal movement
    }
};

// Player class
class Player {
public:
    sf::CircleShape shape;
    float attackDamage;

    Player() {
        shape.setRadius(20.0f);
        shape.setFillColor(sf::Color::Green);
        attackDamage = 20.0f;
    }

    void move(float deltaTime, sf::Vector2f direction, float accelerationFactor) {
        shape.move(direction * PLAYER_SPEED * accelerationFactor * deltaTime);
    }

    void attack(std::vector<Enemy>& enemies) {
        for (auto& enemy : enemies) {
            if (distance(shape.getPosition(), enemy.shape.getPosition()) < ATTACK_RADIUS && enemy.isAlive()) {
                enemy.takeDamage(attackDamage);
            }
        }
    }
};

// Function to generate fractal textures for entities
sf::Image generateFractalTexture(int width, int height, std::complex<float> fractalConst) {
    sf::Image img;
    img.create(width, height);
    for (int x = 0; x < width; ++x) {
        for (int y = 0; y < height; ++y) {
            float real = (x - width / 2.0f) * 4.0f / width;
            float imag = (y - height / 2.0f) * 4.0f / height;
            float value = julia(real, imag, fractalConst);  // Julia set for texture
            sf::Uint8 colorValue = static_cast<sf::Uint8>(value * 255);
            img.setPixel(x, y, sf::Color(colorValue, colorValue / 2, 255 - colorValue));
        }
    }
    return img;
}

// Game setup function
void setupGame(std::vector<Enemy>& enemies, Player& player) {
    std::uniform_real_distribution<float> distX(50.0f, WINDOW_WIDTH - 50.0f);
    std::uniform_real_distribution<float> distY(50.0f, WINDOW_HEIGHT - 50.0f);
    std::uniform_real_distribution<float> velDist(-50.0f, 50.0f);

    // Create 10 fractal-based enemies using Mandelbrot for size and Julia for velocity
    for (int i = 0; i < 10; ++i) {
        float real = static_cast<float>(i) / 10.0f * 4.0f - 2.0f;
        float imag = static_cast<float>(i) / 10.0f * 4.0f - 2.0f;

        float sizeFactor = mandelbrot(real, imag) * 30.0f + 10.0f;  // Size based on Mandelbrot
        sf::Vector2f velocity(julia(real, imag, {0.355f, 0.355f}) * velDist(rng), 
                              julia(real, imag, {0.355f, 0.355f}) * velDist(rng));

        enemies.emplace_back(sf::Vector2f(distX(rng), distY(rng)), sizeFactor, velocity);
    }

    // Set player position in the center
    player.shape.setPosition(WINDOW_WIDTH / 2, WINDOW_HEIGHT / 2);
}

int main() {
    // Initialize SFML window
    sf::RenderWindow window(sf::VideoMode(WINDOW_WIDTH, WINDOW_HEIGHT), "Fractal Adventure Game");
    window.setFramerateLimit(60);

    // Player and enemies
    Player player;
    std::vector<Enemy> enemies;
    setupGame(enemies, player);

    // Generate fractal background texture using Julia set
    std::complex<float> fractalConst(0.355, 0.355);
    sf::Image fractalImage = generateFractalTexture(WINDOW_WIDTH, WINDOW_HEIGHT, fractalConst);
    sf::Texture fractalTexture;
    fractalTexture.loadFromImage(fractalImage);
    sf::Sprite fractalSprite(fractalTexture);

    // Main game loop
    sf::Clock clock;
    while (window.isOpen()) {
        float deltaTime = clock.restart().asSeconds();

        // Handle events
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed) {
                window.close();
            }
        }

        // Player movement
        sf::Vector2f direction(0.0f, 0.0f);
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::W)) direction.y -= 1.0f;
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::S)) direction.y += 1.0f;
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::A)) direction.x -= 1.0f;
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::D)) direction.x += 1.0f;

        float accelFactor = julia(player.shape.getPosition().x * 0.001f, player.shape.getPosition().y * 0.001f, fractalConst) * 2.0f + 0.5f;
        player.move(deltaTime, direction, accelFactor);  // Movement affected by fractal data

        // Player attack (on space key press)
        if (sf::Keyboard::isKeyPressed(sf::Keyboard::Space)) {
            player.attack(enemies);
        }

        // Update enemies
        for (auto& enemy : enemies) {
            if (enemy.isAlive()) {
                enemy.update(deltaTime);
            }
        }

        // Rendering
        window.clear();
        window.draw(fractalSprite);  // Background fractal
        window.draw(player.shape);   // Player

        // Draw enemies
        for (auto& enemy : enemies) {
            if (enemy.isAlive()) {
                window.draw(enemy.shape);
            }
        }

        window.display();
    }

    return 0;
}
