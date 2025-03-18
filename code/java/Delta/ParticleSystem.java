import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;

public class ParticleSystem extends JPanel implements ActionListener {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final ArrayList<Particle> particles;
    private final Timer timer;
    private final Random random;

    public ParticleSystem() {
        particles = new ArrayList<>();
        random = new Random();
        timer = new Timer(30, this);
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Particle System");
        ParticleSystem ps = new ParticleSystem();
        frame.add(ps);
        frame.setSize(ps.WIDTH, ps.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);

        for (Particle p : particles) {
            p.move();
            g.setColor(p.color);
            g.fillOval(p.x, p.y, p.size, p.size);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (particles.size() < 200) {
            particles.add(new Particle(random.nextInt(WIDTH), random.nextInt(HEIGHT)));
        }
        repaint();
    }

    private class Particle {
        int x, y, size;
        Color color;

        Particle(int x, int y) {
            this.x = x;
            this.y = y;
            this.size = random.nextInt(5) + 5;
            this.color = new Color(random.nextInt(256), random.nextInt(256), random.nextInt(256));
        }

        void move() {
            x += random.nextInt(3) - 1; // Move left, right, or stay
            y += random.nextInt(3) - 1; // Move up, down, or stay
            // Keep particles within bounds
            x = Math.max(0, Math.min(WIDTH - size, x));
            y = Math.max(0, Math.min(HEIGHT - size, y));
        }
    }
}
