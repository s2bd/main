import java.awt.*;
import javax.swing.*;
import java.util.Random;

public class PixelTorchlightEffect extends JPanel {
    private int width = 800;
    private int height = 600;
    private Random random = new Random();

    public static void main(String[] args) {
        JFrame frame = new JFrame("Pixel Torchlight Effect");
        PixelTorchlightEffect effect = new PixelTorchlightEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Fill background with darkness
        g2d.setColor(Color.BLACK);
        g2d.fillRect(0, 0, width, height);

        // Create flickering torchlight
        int lightRadius = random.nextInt(100) + 120; // Random flicker effect

        // Draw pixelated light circle
        for (int y = 0; y < height; y += 4) {
            for (int x = 0; x < width; x += 4) {
                int dist = (int) Math.sqrt(Math.pow(x - width / 2, 2) + Math.pow(y - height / 2, 2));
                if (dist < lightRadius) {
                    int brightness = Math.max(0, 255 - dist * 2); // Pixelated gradient effect
                    g2d.setColor(new Color(brightness, brightness, brightness));
                    g2d.fillRect(x, y, 4, 4); // Pixel size 4x4
                }
            }
        }
        repaint();
    }
}
