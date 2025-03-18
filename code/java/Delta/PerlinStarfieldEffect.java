import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class PerlinStarfieldEffect extends JPanel {
    private final int width = 800;
    private final int height = 600;
    private final int starCount = 200;
    private final Star[] stars;

    public PerlinStarfieldEffect() {
        stars = new Star[starCount];
        Random rand = new Random();
        for (int i = 0; i < starCount; i++) {
            stars[i] = new Star(rand.nextInt(width), rand.nextInt(height), rand.nextFloat() * 2);
        }
        Timer timer = new Timer(30, e -> {
            for (Star star : stars) {
                star.update();
            }
            repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Perlin Noise Starfield Effect");
        PerlinStarfieldEffect effect = new PerlinStarfieldEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, width, height);
        
        for (Star star : stars) {
            star.draw(g);
        }
    }

    class Star {
        int x, y;
        float size;

        public Star(int x, int y, float size) {
            this.x = x;
            this.y = y;
            this.size = size;
        }

        public void update() {
            // Simulate twinkling effect using Perlin noise
            float noiseValue = (float) (Math.sin(x * 0.01) * Math.cos(y * 0.01));
            size += noiseValue * 0.1; // Change size based on noise
            size = Math.max(1, Math.min(size, 5)); // Clamp size
        }

        public void draw(Graphics g) {
            g.setColor(Color.WHITE);
            g.fillOval(x, y, (int) size, (int) size);
        }
    }
}
