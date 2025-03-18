import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class PixelStarryNightEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private int numStars = 100;
    private Random random = new Random();
    private int[][] stars;

    public PixelStarryNightEffect() {
        stars = new int[numStars][3]; // [x, y, brightness]
        for (int i = 0; i < numStars; i++) {
            stars[i][0] = random.nextInt(width);
            stars[i][1] = random.nextInt(height);
            stars[i][2] = random.nextInt(100) + 150; // Initial brightness
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Starry Night Effect");
        PixelStarryNightEffect effect = new PixelStarryNightEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw dark sky background
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        // Draw pixel stars
        for (int i = 0; i < numStars; i++) {
            g2d.setColor(getStarColor(stars[i][2]));
            g2d.fillRect(stars[i][0], stars[i][1], 3, 3); // Pixel size of the stars

            // Random twinkling effect
            stars[i][2] += random.nextInt(3) - 1; // Brightness change
            stars[i][2] = Math.max(50, Math.min(255, stars[i][2])); // Keep brightness in bounds
        }

        repaint();
    }

    // Generate the star color based on brightness
    private Color getStarColor(int brightness) {
        return new Color(brightness, brightness, brightness);
    }
}
