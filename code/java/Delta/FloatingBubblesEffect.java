import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class FloatingBubblesEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private ArrayList<Bubble> bubbles;

    public FloatingBubblesEffect() {
        bubbles = new ArrayList<>();
        
        // Add a timer to periodically add new bubbles
        Timer timer = new Timer(500, e -> {
            int x = (int) (Math.random() * width);
            bubbles.add(new Bubble(x, height)); // Start bubbles at the bottom
            repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Floating Bubbles Effect");
        FloatingBubblesEffect effect = new FloatingBubblesEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fill background
        g2d.setColor(new Color(0, 0, 50));
        g2d.fillRect(0, 0, width, height);

        // Draw bubbles
        for (int i = 0; i < bubbles.size(); i++) {
            Bubble bubble = bubbles.get(i);
            bubble.draw(g2d);
            if (bubble.y < 0) {
                bubbles.remove(i); // Remove bubble when it goes off screen
            }
        }
    }

    // Inner class for managing bubble effects
    class Bubble {
        int x, y;
        int radius;

        public Bubble(int x, int y) {
            this.x = x;
            this.y = y;
            this.radius = 10 + (int) (Math.random() * 10); // Random radius
        }

        public void draw(Graphics2D g2d) {
            y -= 2; // Move bubble upwards
            g2d.setColor(new Color(255, 255, 255, 150)); // White with some transparency
            g2d.fillOval(x - radius / 2, y - radius / 2, radius, radius);
        }
    }
}
