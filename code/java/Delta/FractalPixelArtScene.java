import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class FractalPixelArtScene extends JPanel {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final Random random;

    public FractalPixelArtScene() {
        random = new Random();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fractal Pixel Art Scene");
        FractalPixelArtScene scene = new FractalPixelArtScene();
        frame.add(scene);
        frame.setSize(scene.WIDTH, scene.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawBackground(g);
        drawBrickWall(g);
        drawWoodenFloor(g);
        drawCarpet(g);
        drawWindows(g);
        drawFurniture(g);
        drawSunlightGlow(g);
        drawSunRays(g);
    }

    // Draws the background gradient
    private void drawBackground(Graphics g) {
        g.setColor(new Color(135, 206, 235)); // Sky color
        g.fillRect(0, 0, WIDTH, HEIGHT);
    }

    // Draws a brick wall
    private void drawBrickWall(Graphics g) {
        g.setColor(new Color(210, 105, 30)); // Brick color
        for (int y = 50; y < 300; y += 20) {
            for (int x = 0; x < WIDTH; x += 40) {
                g.fillRect(x + (y / 20 % 2) * 20, y, 40, 20);
            }
        }
    }

    // Draws a wooden floor with fractal wood grain texture
    private void drawWoodenFloor(Graphics g) {
        for (int y = 300; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                g.setColor(getWoodColor(x, y));
                g.drawLine(x, y, x, y); // Draw a single pixel for more control
            }
        }
    }

    // Get wood color based on fractal patterns
    private Color getWoodColor(int x, int y) {
        double grain = fractalNoise(x * 0.05, y * 0.05, 5); // Fractal noise for wood grain
        int red = (int) Math.min(139 + (grain * 50), 255); // Lighten wood color
        int green = (int) Math.min(69 + (grain * 20), 255);
        int blue = (int) Math.min(19 + (grain * 10), 255);
        return new Color(red, green, blue);
    }

    // Generates fractal noise
    private double fractalNoise(double x, double y, int octaves) {
        double total = 0;
        double frequency = 1;
        double amplitude = 1;
        double maxValue = 0; // Used for normalizing result

        for (int i = 0; i < octaves; i++) {
            total += noise(x * frequency, y * frequency) * amplitude;
            maxValue += amplitude;
            amplitude *= 0.5;
            frequency *= 2;
        }
        return total / maxValue; // Normalize to [0, 1]
    }

    // Simple noise function (placeholder for Perlin noise)
    private double noise(double x, double y) {
        return (random.nextDouble() - 0.5) * 2; // Simple noise
    }

    // Draws a carpet
    private void drawCarpet(Graphics g) {
        g.setColor(new Color(255, 99, 71)); // Carpet color
        g.fillRect(200, 350, 400, 100); // Carpet placement
    }

    // Draws windows
    private void drawWindows(Graphics g) {
        g.setColor(Color.WHITE);
        g.fillRect(100, 100, 100, 100); // Window 1
        g.fillRect(600, 100, 100, 100); // Window 2

        g.setColor(Color.BLUE);
        g.fillRect(105, 105, 90, 90); // Window glass 1
        g.fillRect(605, 105, 90, 90); // Window glass 2
    }

    // Draws furniture
    private void drawFurniture(Graphics g) {
        g.setColor(new Color(210, 180, 140)); // Light brown color for furniture

        // Draw a table
        g.fillRect(300, 400, 200, 15); // Table top
        g.fillRect(370, 415, 15, 50); // Table leg
        g.fillRect(415, 415, 15, 50); // Table leg

        // Draw a chair
        g.fillRect(350, 460, 40, 10); // Chair seat
        g.fillRect(350, 470, 10, 30); // Chair leg
        g.fillRect(380, 470, 10, 30); // Chair leg
    }

    // Draws a sunlight glow effect
    private void drawSunlightGlow(Graphics g) {
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                int alpha = (int) (255 * Math.max(0, 1 - Math.abs((x - (WIDTH - 150)) / 100.0)));
                g.setColor(new Color(255, 255, 0, alpha)); // Yellow glow with fading alpha
                g.drawLine(x, y, x, y); // Draw a single pixel
            }
        }
    }

    // Draws sun rays
    private void drawSunRays(Graphics g) {
        g.setColor(Color.YELLOW);
        for (int i = 0; i < 12; i++) {
            double angle = Math.toRadians(i * 30);
            int x1 = (int) (WIDTH - 100 + 50 * Math.cos(angle));
            int y1 = (int) (50 + 50 * Math.sin(angle));
            int x2 = (int) (WIDTH - 100 + 80 * Math.cos(angle));
            int y2 = (int) (50 + 80 * Math.sin(angle));
            g.drawLine(x1, y1, x2, y2);
        }

        g.setColor(Color.ORANGE);
        g.fillOval(WIDTH - 150, 20, 100, 100); // Sun
    }
}
