import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class FieryEmberEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Fiery Ember Effect");
        FieryEmberEffect effect = new FieryEmberEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    // Paints the ember effect
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw background
        drawBackground(g2d);

        // Generate and render ember effect
        for (int i = 0; i < 100; i++) {
            drawEmber(g2d, random.nextInt(width), random.nextInt(height), random.nextInt(5, 15));
        }

        // Repaint to animate the effect
        repaint();
    }

    // Draws the background gradient
    private void drawBackground(Graphics2D g2d) {
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);
    }

    // Draws an ember particle
    private void drawEmber(Graphics2D g2d, int x, int y, int size) {
        float alpha = (float) Math.random();
        Color emberColor = new Color(255, 100 + (int)(Math.random() * 155), 0, (int)(255 * alpha));
        g2d.setColor(emberColor);
        g2d.fillOval(x, y, size, size);
    }
}
