import java.awt.*;
import javax.swing.*;
import java.util.ArrayList;

public class ParticleEffect extends JPanel {
    private ArrayList<Particle> particles;

    public ParticleEffect() {
        particles = new ArrayList<>();
        Timer timer = new Timer(100, e -> {
            particles.add(new Particle((int) (Math.random() * 800), 600)); // Create new particle
            for (int i = particles.size() - 1; i >= 0; i--) {
                particles.get(i).update(); // Update particles
                if (particles.get(i).y < 0) {
                    particles.remove(i); // Remove off-screen particles
                }
            }
            repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle Effect");
        ParticleEffect effect = new ParticleEffect();
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
        g2d.fillRect(0, 0, getWidth(), getHeight());

        // Draw particles
        for (Particle particle : particles) {
            particle.draw(g2d);
        }
    }

    // Inner class for Particle
    class Particle {
        int x, y;
        float velocityY;
        Color color;

        public Particle(int x, int y) {
            this.x = x;
            this.y = y;
            this.velocityY = (float) (Math.random() * -2 - 1); // Random upward velocity
            this.color = new Color(255, 255, 255, (int) (Math.random() * 255)); // Random color with alpha
        }

        public void update() {
            y += velocityY; // Update y position
        }

        public void draw(Graphics2D g2d) {
            g2d.setColor(color);
            g2d.fillOval(x, y, 5, 5); // Draw particle
        }
    }
}
