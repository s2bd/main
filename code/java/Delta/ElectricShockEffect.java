import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class ElectricShockEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private double noiseScale = 0.025;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Electric Shock Effect");
        ElectricShockEffect effect = new ElectricShockEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw electric background
        drawElectricBackground(g2d);

        // Generate and render electric shock effect
        for (int y = 0; y < height; y += 5) {
            for (int x = 0; x < width; x += 5) {
                double noise = generatePerlinNoise(x * noiseScale, y * noiseScale);
                double displacement = getDisplacement(noise);

                Color electricColor = getElectricColor(noise, displacement, x, y);
                drawLightningShape(g2d, x, y, electricColor, displacement);
            }
        }
        repaint();
    }

    private void drawElectricBackground(Graphics2D g2d) {
        GradientPaint gradient = new GradientPaint(0, 0, Color.BLACK, 0, height, Color.DARK_GRAY);
        g2d.setPaint(gradient);
        g2d.fillRect(0, 0, width, height);
    }

    private double generatePerlinNoise(double x, double y) {
        double n = random.nextDouble();
        return Math.sin(x + n) * Math.cos(y + n);
    }

    private double getDisplacement(double noise) {
        return (noise + 1) * 1.8;
    }

    private Color getElectricColor(double noise, double displacement, int x, int y) {
        float brightness = (float) Math.max(0, Math.min(1, displacement));
        return new Color(brightness, brightness, 1.0f); // Electric blue
    }

    private void drawLightningShape(Graphics2D g2d, int x, int y, Color color, double displacement) {
        int radius = (int) (displacement * 10);
        g2d.setColor(color);

        // Draw jagged lightning-like line
        int x2 = x + (int) (random.nextDouble() * radius);
        int y2 = y + (int) (random.nextDouble() * radius);
        g2d.setStroke(new BasicStroke(2));
        g2d.drawLine(x, y, x2, y2);

        // Add some randomness to create electric effect
        for (int i = 0; i < 3; i++) {
            int x3 = x2 + (int) (random.nextDouble() * radius);
            int y3 = y2 + (int) (random.nextDouble() * radius);
            g2d.drawLine(x2, y2, x3, y3);
        }
    }
}
