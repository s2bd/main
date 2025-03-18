import java.awt.*;
import javax.swing.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.Random;

public class PixelWaterRippleEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private ArrayList<Ripple> ripples;
    private Random random = new Random();

    public PixelWaterRippleEffect() {
        ripples = new ArrayList<>();

        // Add mouse listener for clicks to create ripples
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ripples.add(new Ripple(e.getX(), e.getY())); // Create ripple at click location
            }
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Water Ripple Effect");
        PixelWaterRippleEffect effect = new PixelWaterRippleEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fill background with a deep water-like color
        g2d.setColor(new Color(0, 0, 50));
        g2d.fillRect(0, 0, width, height);

        // Draw ripples
        for (int i = 0; i < ripples.size(); i++) {
            Ripple ripple = ripples.get(i);
            ripple.draw(g2d);

            if (ripple.radius > 300) {
                ripples.remove(i); // Remove ripple once it's too large
            }
        }

        repaint();
    }

    // Inner class for managing ripple effects
    class Ripple {
        int x, y, radius;
        double noiseOffset;

        public Ripple(int x, int y) {
            this.x = x;
            this.y = y;
            this.radius = 0;
            this.noiseOffset = random.nextDouble() * 1000; // Random noise offset for each ripple
        }

        public void draw(Graphics2D g2d) {
            int alpha = Math.max(0, 200 - radius); // Ensure alpha stays in range [0, 255]
            double noise = generatePerlinNoise(radius * 0.02, noiseOffset); // Simulate wave deformation

            int noiseRadius = (int) (radius + noise * 5); // Adjust radius with noise
            g2d.setColor(new Color(100, 100, 255, alpha)); // Fading blue color
            g2d.drawOval(x - noiseRadius / 2, y - noiseRadius / 2, noiseRadius, noiseRadius);

            // Draw concentric circles
            for (int i = 1; i < 3; i++) {
                int innerRadius = noiseRadius - i * 10;
                if (innerRadius > 0) {
                    int innerAlpha = Math.max(0, 150 - i * 50); // Fade inner circles
                    g2d.setColor(new Color(100, 100, 255, innerAlpha));
                    g2d.drawOval(x - innerRadius / 2, y - innerRadius / 2, innerRadius, innerRadius);
                }
            }

            radius += 2; // Control ripple expansion speed
        }
    }

    // Simple Perlin noise approximation (could be improved with a real Perlin noise algorithm)
    private double generatePerlinNoise(double x, double offset) {
        return Math.sin(x + offset) * Math.cos(x * 2 + offset); // Wave-like noise pattern
    }
}
