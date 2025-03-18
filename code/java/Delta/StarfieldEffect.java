import javax.swing.*;
import java.awt.*;
import java.util.Random;

public class StarfieldEffect extends JPanel {
    private final int WIDTH = 800;
    private final int HEIGHT = 600;
    private final Star[] stars;
    private final Random random;

    public StarfieldEffect() {
        stars = new Star[100];
        random = new Random();
        for (int i = 0; i < stars.length; i++) {
            stars[i] = new Star(random.nextInt(WIDTH), random.nextInt(HEIGHT), random.nextInt(5) + 1);
        }
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Starfield Effect");
        StarfieldEffect starfield = new StarfieldEffect();
        frame.add(starfield);
        frame.setSize(starfield.WIDTH, starfield.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        starfield.animate();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, WIDTH, HEIGHT);
        
        g.setColor(Color.WHITE);
        for (Star star : stars) {
            star.move();
            g.fillOval(star.x, star.y, star.size, star.size);
        }
    }

    private void animate() {
        Timer timer = new Timer(20, e -> {
            repaint();
        });
        timer.start();
    }

    private class Star {
        int x, y, size, speed;

        Star(int x, int y, int size) {
            this.x = x;
            this.y = y;
            this.size = size;
            this.speed = random.nextInt(3) + 1; // Random speed
        }

        void move() {
            y += speed; // Move downward
            if (y > HEIGHT) {
                y = 0; // Reset position to the top
                x = random.nextInt(WIDTH); // Random horizontal position
            }
        }
    }
}
