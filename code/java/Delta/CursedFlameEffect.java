import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class CursedFlameEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private double noiseScale = 0.02;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Cursed Flame Effect");
        CursedFlameEffect effect = new CursedFlameEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the flame effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render flame effect
        for (int y = 0; y < height; y += 5) {
            for (int x = 0; x < width; x += 5) {
                // Generate Perlin noise
                double noise = generatePerlinNoise(x * noiseScale, y * noiseScale);

                // Displace noise with itself (feedback loop)
                double displacement = getDisplacement(noise);

                // Set gradient scale and curve
                Color flameColor = getFlameColor(noise, displacement, x, y);

                // Draw flame leaf shape
                drawLeafShape(g2d, x, y, flameColor, displacement);
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

    // Displacement map based on noise
    private double getDisplacement(double noise) {
        return (noise + 1) * 1.5; // Map noise to displacement value
    }

    // Get flame color based on noise and displacement
    private Color getFlameColor(double noise, double displacement, int x, int y) {
        float brightness = (float) Math.max(0, Math.min(1, displacement)); // Map displacement to brightness

        // Adjust flame color (inner radius to outer)
        return new Color(brightness, 0.2f, 0.5f); // Purple-like cursed flame
    }

    // Draw the leaf shape that forms the flame
    private void drawLeafShape(Graphics2D g2d, int x, int y, Color color, double displacement) {
        int radius = (int) (displacement * 10); // Scale radius based on displacement
        g2d.setColor(color);
        g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
    }
}
