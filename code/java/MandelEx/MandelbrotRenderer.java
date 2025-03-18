import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MandelbrotRenderer {
    private BufferedImage mandelbrotImage;
    private double xOffset;  // Camera position (real axis)
    private double yOffset;  // Camera position (imaginary axis)
    private double zoom;     // Zoom level

    private final int numThreads; // Number of threads for rendering
    private final ExecutorService executor;

    // Constructor accepting offsets and zoom level
    public MandelbrotRenderer(double initialXOffset, double initialYOffset, double initialZoom) {
        this.xOffset = initialXOffset; // Initialize xOffset
        this.yOffset = initialYOffset; // Initialize yOffset
        this.zoom = initialZoom;       // Initialize zoom
        mandelbrotImage = new BufferedImage(Settings.WIDTH, Settings.HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        numThreads = Runtime.getRuntime().availableProcessors(); // Use all available CPU cores
        executor = Executors.newFixedThreadPool(numThreads);

        renderMandelbrot(); // Initial rendering
    }

    // Method to get the Mandelbrot image
    public BufferedImage getMandelbrotImage() {
        return mandelbrotImage;
    }

    // Render the Mandelbrot set in parallel using multiple threads
    public void renderMandelbrot() {
        long startTime = System.nanoTime();

        // Divide the image into rows for each thread to process
        for (int y = 0; y < Settings.HEIGHT; y++) {
            final int yFinal = y; // Effectively final for lambda usage
            executor.submit(() -> {
                for (int x = 0; x < Settings.WIDTH; x++) {
                    // Convert pixel coordinates to complex plane coordinates
                    double c_real = (x - Settings.WIDTH / 2) / zoom + xOffset;
                    double c_imag = (yFinal - Settings.HEIGHT / 2) / zoom + yOffset;

                    // Calculate whether this point is in the Mandelbrot set
                    int color = mandelbrot(c_real, c_imag);

                    // Set pixel color in the buffer
                    mandelbrotImage.setRGB(x, yFinal, color);
                }
            });
        }

        executor.submit(() -> {
            try {
                Thread.sleep(50); // Short delay to ensure all threads complete
                // Call repaint() or notify the GUI to refresh the display
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
        int maxIterations = (int) (500 + Math.log(zoom) * 10); // Increase iterations as zoom level increases

        while (z_real * z_real + z_imag * z_imag < 4 && iterations < maxIterations) {
            double temp_real = z_real * z_real - z_imag * z_imag + c_real;
            z_imag = 2 * z_real * z_imag + c_imag;
            z_real = temp_real;
            iterations++;
        }

        // Return grayscale value based on iteration count
        if (iterations == maxIterations) {
            return 0; // Black (in the Mandelbrot set)
        } else {
            return new Color(iterations * 255 / maxIterations, iterations * 255 / maxIterations, iterations * 255 / maxIterations).getRGB();
        }
    }

    // Method to change offsets and trigger rendering
    public void setOffsets(double xOffset, double yOffset) {
        this.xOffset = xOffset;
        this.yOffset = yOffset;
        renderMandelbrot(); // Render again with new offsets
    }

    // Method to change zoom level and trigger rendering
    public void setZoom(double zoom) {
        this.zoom = zoom;
        renderMandelbrot(); // Render again with new zoom
    }

    public void shutdown() {
        executor.shutdown();
    }
}
