import java.awt.*;
import javax.swing.*;
import java.awt.image.BufferedImage;

public class UnderwaterDistortionEffect extends JPanel {
    private BufferedImage image;

    public UnderwaterDistortionEffect() {
        image = new BufferedImage(800, 600, BufferedImage.TYPE_INT_ARGB);
        createDistortionEffect();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Underwater Distortion Effect");
        UnderwaterDistortionEffect effect = new UnderwaterDistortionEffect();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(effect);
        frame.setSize(800, 600);
        frame.setVisible(true);
    }

    private void createDistortionEffect() {
        Graphics2D g2d = image.createGraphics();
        for (int y = 0; y < 600; y++) {
            for (int x = 0; x < 800; x++) {
                // Create a distortion effect
                int offsetX = (int) (Math.sin((y + System.currentTimeMillis() * 0.01) * 0.05) * 10);
                int offsetY = (int) (Math.cos((x + System.currentTimeMillis() * 0.01) * 0.05) * 10);
                g2d.setColor(new Color(0, 0, 50));
                g2d.fillRect(x + offsetX, y + offsetY, 1, 1);
            }
        }
        g2d.dispose();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        // Draw the distortion effect
        g2d.drawImage(image, 0, 0, null);
        createDistortionEffect(); // Update distortion
        repaint(); // Continuously repaint to update
    }
}
