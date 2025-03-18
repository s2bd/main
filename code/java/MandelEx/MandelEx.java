import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import javax.swing.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MandelEx extends JPanel implements KeyListener {
    // Display settings
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    
    // Mandelbrot settings
    private double xOffset = -0.5;  // Camera position (real axis)
    private double yOffset = 0;     // Camera position (imaginary axis)
    private double zoom = 300;      // Zoom level (starts with 300x magnification)
    
    // Movement speed
    private double panSpeed = 20 / zoom;
    
    // Mandelbrot image buffer (for faster rendering)
    private BufferedImage mandelbrotImage;
    
    // Multi-threading
    private final int numThreads = Runtime.getRuntime().availableProcessors();  // Use all available CPU cores
    private ExecutorService executor;
    
    // Constructor to set up JFrame and add KeyListener
    public MandelEx() {
        JFrame frame = new JFrame("Mandelbrot Explorer");
        frame.setSize(WIDTH, HEIGHT);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(this);
        frame.setVisible(true);
        
        // Initialize image buffer
        mandelbrotImage = new BufferedImage(WIDTH, HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        // Add KeyListener to capture key presses
        frame.addKeyListener(this);
        
        // Multi-threading setup
        executor = Executors.newFixedThreadPool(numThreads);
        
        // Initial rendering
        renderMandelbrot();
    }
    
    // Render the Mandelbrot set in parallel using multiple threads
    private void renderMandelbrot() {
        long startTime = System.nanoTime();
        
        // Divide the image into rows for each thread to process
        for (int y = 0; y < HEIGHT; y++) {
            final int yFinal = y;  // Effectively final for lambda usage
            executor.submit(() -> {
                for (int x = 0; x < WIDTH; x++) {
                    // Convert pixel coordinates to complex plane coordinates
                    double c_real = (x - WIDTH / 2) / zoom + xOffset;
                    double c_imag = (yFinal - HEIGHT / 2) / zoom + yOffset;
                    
                    // Calculate whether this point is in the Mandelbrot set
                    int color = mandelbrot(c_real, c_imag);
                    
                    // Set pixel color in the buffer
                    mandelbrotImage.setRGB(x, yFinal, new Color(color, color, color).getRGB());
                }
            });
        }
        
        // Wait for all tasks to finish and repaint the panel
        executor.submit(() -> {
            try {
                Thread.sleep(50);  // Short delay to ensure all threads complete
                repaint();
                long endTime = System.nanoTime();
                System.out.println("Render time: " + (endTime - startTime) / 1_000_000 + " ms");
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        });
    }
    
    // Mandelbrot iteration function
    private int mandelbrot(double c_real, double c_imag) {
        double z_real = 0;
        double z_imag = 0;
        int iterations = 0;
        int maxIterations = (int)(500 + Math.log(zoom) * 10);  // Increase iterations as zoom level increases
        
        while (z_real * z_real + z_imag * z_imag < 4 && iterations < maxIterations) {
            double temp_real = z_real * z_real - z_imag * z_imag + c_real;
            z_imag = 2 * z_real * z_imag + c_imag;
            z_real = temp_real;
            iterations++;
        }
        
        // Return grayscale value based on iteration count
        if (iterations == maxIterations) {
            return 0;  // Black (in the Mandelbrot set)
        } else {
            return (int) (255 * (iterations / (double) maxIterations));  // Shades of gray
        }
    }
    
    // Key press event handling
    @Override
    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        // Move the camera using arrow keys
        if (key == KeyEvent.VK_UP) {
            yOffset -= panSpeed;
        } else if (key == KeyEvent.VK_DOWN) {
            yOffset += panSpeed;
        } else if (key == KeyEvent.VK_LEFT) {
            xOffset -= panSpeed;
        } else if (key == KeyEvent.VK_RIGHT) {
            xOffset += panSpeed;
        }
        
        // Zoom in with 'W' and zoom out with 'S'
        if (key == KeyEvent.VK_W) {
            zoom *= 1.1;  // Zoom in
            panSpeed = 20 / zoom;  // Adjust pan speed to match zoom
        } else if (key == KeyEvent.VK_S) {
            zoom /= 1.1;  // Zoom out
            panSpeed = 20 / zoom;  // Adjust pan speed to match zoom
        }
        
        // Render only when camera or zoom changes
        renderMandelbrot();
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // Draw the Mandelbrot image buffer to the screen
        g.drawImage(mandelbrotImage, 0, 0, null);
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
        // No action needed on key release
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // No action needed on key typed
    }

    // Main method to start the program
    public static void main(String[] args) {
        new MandelbrotExplorer();
    }
}