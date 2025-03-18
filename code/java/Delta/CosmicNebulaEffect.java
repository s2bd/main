import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class CosmicNebulaEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private double noiseScale = 0.03;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cosmic Nebula Effect");
        CosmicNebulaEffect effect = new CosmicNebulaEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the nebula effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render nebula effect
        for (int y = 0; y < height; y += 5) {
            for (int x = 0; x < width; x += 5) {
                // Generate Perlin noise
                double noise = generatePerlinNoise(x * noiseScale, y * noiseScale);

                // Get nebula color based on noise
                Color nebulaColor = getNebulaColor(noise);

                // Draw nebula shapes
                drawNebulaShape(g2d, x, y, nebulaColor, noise);
            }
        }

        // Repaint to animate the effect
        repaint();
    }

    // Draws the background gradient
    private void drawBackground(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, 0, height, Color.BLUE);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    // Generate Perlin noise
    private double generatePerlinNoise(double x, double y) {
        double n = random.nextDouble();
        return Math.sin(x + n) * Math.cos(y + n); // Simple approximation of Perlin noise
    }

    // Get nebula color based on noise
    private Color getNebulaColor(double noise) {
        float r = (float) Math.abs(noise) * 0.5f;
        float g = (float) Math.abs(noise * 0.8) * 0.5f;
        float b = (float) Math.abs(noise * 0.6) * 0.7f;
        return new Color(r, g, b, 0.5f); // Semi-transparent color
    }

    // Draw the nebula shape
    private void drawNebulaShape(Graphics2D g2d, int x, int y, Color color, double noise) {
        int radius = (int) ((noise + 1) * 20); // Scale radius based on noise
        g2d.setColor(color);
        g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
