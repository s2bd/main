import java.awt.*;
import java.util.ArrayList;
import java.awt.image.BufferedImage;  // For BufferedImage
import java.awt.Graphics;              // For Graphics
import javax.swing.JPanel;             // For JPanel
import javax.swing.JFrame;             // For JFrame

public class Room {
    private ArrayList<Wall> walls;

    public Room() {
        walls = new ArrayList<>();
        Fractal fractal = new Fractal();
        BufferedImage texture = fractal.generateMandelbrot(800, 600, -2, 1, -1, 1, 100);
        walls.add(new Wall(0, 0, 800, 10, texture)); // Top wall
        walls.add(new Wall(0, 590, 800, 10, texture)); // Bottom wall
        walls.add(new Wall(0, 0, 10, 600, texture)); // Left wall
        walls.add(new Wall(790, 0, 10, 600, texture)); // Right wall
    }

    public void update(Player player) {
        // Room update logic (if any)
    }

    public void render(Graphics g) {
        for (Wall wall : walls) {
            wall.render(g);
        }
    }
}
