import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class ElectricStormEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Electric Storm Effect");
        ElectricStormEffect effect = new ElectricStormEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the storm effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render lightning strikes
        if (random.nextInt(100) < 5) { // Randomly trigger lightning
            drawLightning(g2d, random.nextInt(width), random.nextInt(height / 2));
        }

        // Repaint to animate the effect
        repaint();
    }

    // Draws the storm background
    private void drawBackground(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.DARK_GRAY, 0, height, Color.BLACK);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    // Draws a lightning strike
    private void drawLightning(Graphics2D g2d, int startX, int startY) {
        int x = startX;
        int y = startY;

        g2d.setColor(Color.YELLOW);
        g2d.setStroke(new BasicStroke(3)); // Stroke width for lightning

        for (int i = 0; i < 5; i++) {
            int deltaX = random.nextInt(20) - 10; // Randomize x direction
            int deltaY = random.nextInt(20) + 5; // Move downwards

            g2d.drawLine(x, y, x + deltaX, y + deltaY);
            x += deltaX; // Update x position
            y += deltaY; // Update y position
        }

        // Draw glow effect around lightning
        drawGlow(g2d, x, y);
    }

    // Draws the glow effect around the lightning
    private void drawGlow(Graphics2D g2d, int x, int y) {
        for (int i = 1; i <= 5; i++) {
            g2d.setColor(new Color(255, 255, 0, 255 / (i + 1))); // Fade out glow
            g2d.fillOval(x - i * 10, y - i * 10, i * 20, i * 20);
        }
    }
}
