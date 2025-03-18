#include <SFML/Graphics.hpp>
#include <vector>
#include <string>
#include <sstream>
#include <iostream>

const int GRID_WIDTH = 80;
const int GRID_HEIGHT = 60;
const int CELL_SIZE = 10;

class CellularAutomata {
public:
    CellularAutomata() {
        grid.resize(GRID_WIDTH, std::vector<int>(GRID_HEIGHT, 0));
    }

    void toggleCell(int x, int y) {
        if (x >= 0 && x < GRID_WIDTH && y >= 0 && y < GRID_HEIGHT) {
            grid[x][y] = grid[x][y] == 0 ? 1 : 0; // Toggle cell state
        }
    }

    void update() {
        // Implement your automata rules here
        std::vector<std::vector<int>> newGrid = grid;

        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                // Simple rule example: survive or die based on neighbors
                int aliveNeighbors = countAliveNeighbors(x, y);
                if (grid[x][y] == 1) {
                    newGrid[x][y] = (aliveNeighbors < 2 || aliveNeighbors > 3) ? 0 : 1;
                } else {
                    newGrid[x][y] = (aliveNeighbors == 3) ? 1 : 0;
                }
            }
        }
        grid = newGrid;
    }

    void draw(sf::RenderWindow& window) {
        for (int x = 0; x < GRID_WIDTH; x++) {
            for (int y = 0; y < GRID_HEIGHT; y++) {
                if (grid[x][y] == 1) {
                    sf::RectangleShape cell(sf::Vector2f(CELL_SIZE, CELL_SIZE));
                    cell.setPosition(x * CELL_SIZE, y * CELL_SIZE);
                    cell.setFillColor(sf::Color::White);
                    window.draw(cell);
                }
            }
        }
    }

private:
    std::vector<std::vector<int>> grid;

    int countAliveNeighbors(int x, int y) {
        int count = 0;
        for (int dx = -1; dx <= 1; dx++) {
            for (int dy = -1; dy <= 1; dy++) {
                if (dx == 0 && dy == 0) continue; // Skip self
                int nx = x + dx;
                int ny = y + dy;
                if (nx >= 0 && nx < GRID_WIDTH && ny >= 0 && ny < GRID_HEIGHT) {
                    count += grid[nx][ny];
                }
            }
        }
        return count;
    }
};

int main() {
    sf::RenderWindow window(sf::VideoMode(800, 600), "2CAS Simulator");
    CellularAutomata automata;
    bool isPlaying = false;

    while (window.isOpen()) {
        sf::Event event;
        while (window.pollEvent(event)) {
            if (event.type == sf::Event::Closed)
                window.close();

            if (event.type == sf::Event::MouseButtonPressed) {
                if (event.mouseButton.button == sf::Mouse::Left) {
                    // Draw cells
                    int x = event.mouseButton.x / CELL_SIZE;
                    int y = event.mouseButton.y / CELL_SIZE;
                    automata.toggleCell(x, y);
                }
            }

            // You can handle other input events like play/pause here
            // For example, if the user presses a key to toggle play
            if (event.type == sf::Event::KeyPressed) {
                if (event.key.code == sf::Keyboard::Space) {
                    isPlaying = !isPlaying; // Toggle play/pause
                }
                if (event.key.code == sf::Keyboard::R) {
                    // Reset the simulation
                    automata = CellularAutomata();
                }
            }
        }

        if (isPlaying) {
            automata.update(); // Update simulation if playing
        }

        window.clear();
        automata.draw(window); // Draw cells
        window.display();
    }

    return 0;
}
