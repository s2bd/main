import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class MandelbrotExplorer extends JPanel implements KeyListener {
    private MandelbrotRenderer renderer;
    private ZoomController zoomController;

    public MandelbrotExplorer() {
        zoomController = new ZoomController(300); // Initial zoom level
        renderer = new MandelbrotRenderer(zoomController.getXOffset(), zoomController.getYOffset(), zoomController.getZoom());

        JFrame frame = new JFrame("Mandelbrot Explorer");
        frame.setSize(Settings.WIDTH, Settings.HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        
        frame.addKeyListener(this);
        
        // Create a timer that triggers an update every 100 milliseconds
        Timer timer = new Timer(100, e -> {
            renderMandelbrot();
        });
        timer.start(); // Start the timer

        renderMandelbrot(); // Initial render
    }

    private void renderMandelbrot() {
        long startTime = System.nanoTime();
        renderer.setOffsets(zoomController.getXOffset(), zoomController.getYOffset());
        renderer.setZoom(zoomController.getZoom());
        //renderer.render();
        repaint();
        long endTime = System.nanoTime();
        System.out.println("Render time: " + (endTime - startTime) / 1_000_000 + " ms");
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(renderer.getMandelbrotImage(), 0, 0, null);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        // Handle movement and zoom actions
        switch (e.getKeyCode()) {
            case KeyEvent.VK_UP: 
                zoomController.pan(0, -zoomController.getPanSpeed());
                break;
            case KeyEvent.VK_DOWN: 
                zoomController.pan(0, zoomController.getPanSpeed());
                break;
            case KeyEvent.VK_LEFT: 
                zoomController.pan(-zoomController.getPanSpeed(), 0);
                break;
            case KeyEvent.VK_RIGHT: 
                zoomController.pan(zoomController.getPanSpeed(), 0);
                break;
            case KeyEvent.VK_W: 
                zoomController.zoomIn();
                break;
            case KeyEvent.VK_S: 
                zoomController.zoomOut();
                break;
        }
        renderMandelbrot(); // Render after user input
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        new MandelbrotExplorer();
    }
}
