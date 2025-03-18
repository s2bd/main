import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class RadiantLightningEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Radiant Lightning Effect");
        RadiantLightningEffect effect = new RadiantLightningEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the lightning effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render lightning effect
        for (int i = 0; i < 5; i++) {
            drawLightning(g2d, random.nextInt(width), random.nextInt(height / 2), random.nextInt(3, 8));
        }

        // Repaint to animate the effect
        repaint();
    }

    // Draws the background gradient
    private void drawBackground(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, 0, height, Color.DARK_GRAY);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    // Draws lightning strikes
    private void drawLightning(Graphics2D g2d, int startX, int startY, int branches) {
        int x = startX;
        int y = startY;

        for (int i = 0; i < branches; i++) {
            x += random.nextInt(20) - 10; // Randomize x direction
            y += random.nextInt(15); // Move downwards

            g2d.setColor(Color.WHITE);
            g2d.setStroke(new BasicStroke(random.nextFloat() * 2 + 1)); // Random stroke width
            g2d.drawLine(x, y - 5, x, y + 5); // Draw lightning line

            // Add glow effect
            drawGlow(g2d, x, y);
        }
    }

    // Draws the glow effect around the lightning
    private void drawGlow(Graphics2D g2d, int x, int y) {
        for (int i = 1; i <= 5; i++) {
            g2d.setColor(new Color(255, 255, 255, 255 / (i + 1))); // Fade out glow
            g2d.fillOval(x - i * 5, y - i * 5, i * 10, i * 10);
        }
    }
}
