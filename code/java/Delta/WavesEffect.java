import java.awt.*;
import javax.swing.*;

public class WavesEffect extends JPanel {
    private float waveHeight = 10.0f;
    private float waveFrequency = 0.1f;

    public WavesEffect() {
        Timer timer = new Timer(50, e -> {
            waveHeight += 0.1f; // Control wave height change
            repaint();
        });
        timer.start();
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Waves Effect");
        WavesEffect effect = new WavesEffect();
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

        // Draw waves
        g2d.setColor(new Color(100, 100, 255, 150)); // Water color with some transparency
        for (int x = 0; x < getWidth(); x++) {
            int y = (int) (getHeight() / 2 + Math.sin((x + waveHeight) * waveFrequency) * waveHeight);
            g2d.drawLine(x, y, x, getHeight()); // Draw vertical lines to create a wave effect
        }
    }
}
