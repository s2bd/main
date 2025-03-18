import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class RadiantMagicalAuraEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private Random random = new Random();
    private int pulse = 0;

    public static void main(String[] args) {
        JFrame frame = new JFrame("Radiant Magical Aura Effect");
        RadiantMagicalAuraEffect effect = new RadiantMagicalAuraEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the magical aura effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render magical aura
        drawMagicalAura(g2d, width / 2, height / 2, 50 + pulse);

        // Update pulse size for animation
        pulse = (pulse + 1) % 20;

        // Repaint to animate the effect
        repaint();
    }

    // Draws the background gradient
    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }

    // Draws the magical aura
    private void drawMagicalAura(Graphics2D g2d, int centerX, int centerY, int radius) {
        for (int i = 0; i < 10; i++) {
            float alpha = (float) Math.max(0, Math.min(1, 1 - (i / 10f))); // Fade out effect
            Color auraColor = new Color(0, 255, 255, (int) (255 * alpha)); // Cyan color
            g2d.setColor(auraColor);
            g2d.fillOval(centerX - radius + i * 5, centerY - radius + i * 5, radius * 2 - i * 10, radius * 2 - i * 10);
        }
    }
}
