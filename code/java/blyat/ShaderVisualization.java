import java.awt.*;
import javax.swing.*;

public class ShaderVisualization extends JPanel {
    private float time;
    private final int width = 800;
    private final int height = 600;

    public ShaderVisualization() {
        this.setPreferredSize(new Dimension(width, height));
        Timer timer = new Timer(16, e -> {
            time += 0.016; // time increment
            repaint();
        });
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                float nx = (float)x / width;
                float ny = (float)y / height;
                Color color = calculateColor(nx, ny, time);
                g2d.setColor(color);
                g2d.drawLine(x, y, x, y);
            }
        }
    }

    private Color calculateColor(float nx, float ny, float time) {
        float r = (float) (0.5 + 0.5 * Math.sin(3.14 * (nx + time)));
        float g = (float) (0.6 + 0.4 * Math.cos(3.14 * (ny + time)));
        float b = (float) (0.65 + 0.35 * Math.sin(3.14 * (nx * ny + time)));
        return new Color(clamp(r), clamp(g), clamp(b));
    }

    private float clamp(float value) {
        return Math.max(0, Math.min(1, value));
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Shader Visualization");
        ShaderVisualization shaderVisualization = new ShaderVisualization();
        frame.add(shaderVisualization);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
