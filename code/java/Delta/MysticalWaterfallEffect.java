import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class MysticalWaterfallEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private double noiseScale = 0.02;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mystical Waterfall Effect");
        MysticalWaterfallEffect effect = new MysticalWaterfallEffect();
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

        // Generate and render waterfall effect
        for (int y = 0; y < height; y += 5) {
            for (int x = 0; x < width; x += 5) {
                // Generate Perlin noise
                double noise = generatePerlinNoise(x * noiseScale, y * noiseScale);

                // Set water color based on noise
                Color waterColor = getWaterColor(noise);

                // Draw flowing water shapes
                drawWaterShape(g2d, x, y, waterColor, noise);
            }
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

    // Generate Perlin noise
    private double generatePerlinNoise(double x, double y) {
        double n = random.nextDouble();
        return Math.sin(x + n) * Math.cos(y + n); // Simple approximation of Perlin noise
    }

    // Get water color based on noise
    private Color getWaterColor(double noise) {
        float brightness = (float) Math.max(0, Math.min(1, noise + 0.5)); // Adjust brightness
        return new Color(0.0f, brightness * 0.5f, 1.0f); // Blue water color
    }

    // Draw the flowing water shape
    private void drawWaterShape(Graphics2D g2d, int x, int y, Color color, double noise) {
        int radius = (int) ((noise + 1) * 10); // Scale radius based on noise
        g2d.setColor(color);
        g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
