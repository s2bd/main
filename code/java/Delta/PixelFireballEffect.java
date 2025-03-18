import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class PixelFireballEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private int fireballX = 100;
    private int fireballY = 300;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Fireball Effect");
        PixelFireballEffect effect = new PixelFireballEffect();
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
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        // Draw fireball with trail
        drawFireball(g2d, fireballX, fireballY);

        // Update fireball position
        fireballX += 5; // Fireball moves to the right
        if (fireballX > width) {
            fireballX = -50; // Reset to the left once off-screen
        }

        repaint();
    }

    // Draw a glowing fireball with a pixelated trail
    private void drawFireball(Graphics2D g2d, int x, int y) {
        for (int i = 0; i < 20; i++) {
            int trailSize = 10 - i; // Smaller trail as it moves
            int offsetX = random.nextInt(10) - 5; // Random pixel shift
            int offsetY = random.nextInt(10) - 5;
            g2d.setColor(getFireballColor(i));
            g2d.fillRect(x - (i * 4) + offsetX, y + offsetY, trailSize * 4, trailSize * 4);
        }
    }

    // Fireball color, hotter at the center, cooler toward the edges
    private Color getFireballColor(int intensity) {
        float r = Math.max(0, 1 - intensity * 0.05f); // Decrease red component
        float g = Math.max(0, 0.4f - intensity * 0.03f); // Decrease green component
        float b = 0.1f; // Small blue component for the fire
        return new Color(r, g, b);
    }
}
