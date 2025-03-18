import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.concurrent.*;

public class MandelbrotExplorer extends JPanel implements KeyListener {
    private double minRe = -2.0, maxRe = 1.0;
    private double minIm = -1.5, maxIm = 1.5;
    private int width = 800, height = 800;
    private double zoomFactor = 1.1;
    private BufferedImage image;
    private boolean needsRedraw = true;
    private ExecutorService executor; // Thread pool for rendering

    public MandelbrotExplorer() {
        this.image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        addKeyListener(this);
        setFocusable(true);
        setFocusTraversalKeysEnabled(false);
        executor = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors()); // Create a thread pool
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (needsRedraw) {
            renderMandelbrot();
            needsRedraw = false;
        }
        g.drawImage(image, 0, 0, null);
    }

    private void renderMandelbrot() {
        // Create a list of futures to hold the results of each row computation
        Future<?>[] futures = new Future[height];
        for (int y = 0; y < height; y++) {
            final int row = y; // Final variable for lambda expression
            futures[y] = executor.submit(() -> {
                for (int x = 0; x < width; x++) {
                    double cRe = minRe + (maxRe - minRe) * x / width;
                    double cIm = minIm + (maxIm - minIm) * row / height;
                    double zRe = 0, zIm = 0;
                    int iteration = 0;
                    double threshold = 4;  // Escape radius

                    while (zRe * zRe + zIm * zIm <= threshold && iteration < 1000) {
                        double zRe2 = zRe * zRe;
                        double zIm2 = zIm * zIm;

                        zIm = 2 * zRe * zIm + cIm;
                        zRe = zRe2 - zIm2 + cRe;

                        iteration++;
                    }

                    // Color based on number of iterations
                    int color = iteration == 1000 ? 0 : Color.HSBtoRGB(iteration / 256f, 1, iteration / (iteration + 8f));
                    image.setRGB(x, row, color);
                }
            });
        }

        // Wait for all tasks to complete
        for (Future<?> future : futures) {
            try {
                future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

        repaint();
    }

    private void zoomOut() {
        adjustZoom(zoomFactor);
    }

    private void zoomIn() {
        adjustZoom(1 / zoomFactor);
    }

    private void adjustZoom(double factor) {
        double reCenter = (minRe + maxRe) / 2;
        double imCenter = (minIm + maxIm) / 2;

        double reRange = (maxRe - minRe) * factor;
        double imRange = (maxIm - minIm) * factor;

        minRe = reCenter - reRange / 2;
        maxRe = reCenter + reRange / 2;
        minIm = imCenter - imRange / 2;
        maxIm = imCenter + imRange / 2;

        needsRedraw = true;
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W -> zoomIn();
            case KeyEvent.VK_S -> zoomOut();
            case KeyEvent.VK_LEFT -> panLeft();
            case KeyEvent.VK_RIGHT -> panRight();
            case KeyEvent.VK_UP -> panUp();
            case KeyEvent.VK_DOWN -> panDown();
        }
    }

    private void panLeft() {
        double shift = (maxRe - minRe) * 0.1;
        minRe -= shift;
        maxRe -= shift;
        needsRedraw = true;
        repaint();
    }

    private void panRight() {
        double shift = (maxRe - minRe) * 0.1;
        minRe += shift;
        maxRe += shift;
        needsRedraw = true;
        repaint();
    }

    private void panDown() {
        double shift = (maxIm - minIm) * 0.1;
        minIm += shift;
        maxIm += shift;
        needsRedraw = true;
        repaint();
    }

    private void panUp() {
        double shift = (maxIm - minIm) * 0.1;
        minIm -= shift;
        maxIm -= shift;
        needsRedraw = true;
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Mandelbrot Explorer");
        MandelbrotExplorer explorer = new MandelbrotExplorer();
        frame.add(explorer);
        frame.setSize(800, 800);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
