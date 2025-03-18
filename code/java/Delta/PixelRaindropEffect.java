import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class PixelRaindropEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private int numRaindrops = 100;
    private Random random = new Random();
    private int[][] raindrops;

    public PixelRaindropEffect() {
        raindrops = new int[numRaindrops][3]; // [x, y, speed]
        for (int i = 0; i < numRaindrops; i++) {
            raindrops[i][0] = random.nextInt(width);
            raindrops[i][1] = random.nextInt(height);
            raindrops[i][2] = random.nextInt(5) + 2; // Random fall speed
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Raindrop Effect");
        PixelRaindropEffect effect = new PixelRaindropEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Clear the screen
        g2d.setColor(Color.DARK_GRAY); // Rainy sky background
        g2d.fillRect(0, 0, width, height);

        // Draw pixelated raindrops
        for (int i = 0; i < numRaindrops; i++) {
            Color dropColor = getRaindropColor();
            g2d.setColor(dropColor);

            // Update raindrop position
            raindrops[i][1] += raindrops[i][2];
            if (raindrops[i][1] > height) {
                raindrops[i][1] = 0; // Reset position when off-screen
                raindrops[i][0] = random.nextInt(width); // Random horizontal position
            }

            // Draw pixelated raindrop
            g2d.fillRect(raindrops[i][0], raindrops[i][1], 2, 10); // Pixelated raindrop shape
        }

        repaint();
    }

    private Color getRaindropColor() {
        return new Color(173, 216, 230); // Light blue raindrop
    }
}
