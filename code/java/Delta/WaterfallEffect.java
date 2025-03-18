import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class WaterfallEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mystical Waterfall Effect");
        WaterfallEffect effect = new WaterfallEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the waterfall effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render water particles
        for (int i = 0; i < 100; i++) {
            drawWaterParticle(g2d, random.nextInt(width), random.nextInt(height), random.nextInt(5, 15));
        }

        // Repaint to animate the effect
        repaint();
    }

    // Draws the background gradient
    private void drawBackground(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLUE.brighter(), 0, height, Color.DARK_GRAY);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    // Draws a water particle
    private void drawWaterParticle(Graphics2D g2d, int x, int y, int size) {
        float alpha = (float) (Math.random() * 0.5 + 0.5); // Alpha between 0.5 and 1
        Color waterColor = new Color(0, 150, 255, (int) (255 * alpha));
        g2d.setColor(waterColor);
        g2d.fillOval(x, y, size, size);
    }
}
