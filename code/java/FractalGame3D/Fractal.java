import java.awt.image.BufferedImage;

public class Fractal {
    public BufferedImage generateMandelbrot(int width, int height, double xMin, double xMax, double yMin, double yMax, int maxIterations) {
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                double zx = 0;
                double zy = 0;
                double cX = xMin + (x / (double) width) * (xMax - xMin);
                double cY = yMin + (y / (double) height) * (yMax - yMin);
                int iter = 0;

                while (zx * zx + zy * zy < 4 && iter < maxIterations) {
                    double tmp = zx * zx - zy * zy + cX;
                    zy = 2.0 * zx * zy + cY;
                    zx = tmp;
                    iter++;
                }

                int color = iter | (iter << 8);
                image.setRGB(x, y, iter < maxIterations ? color : 0);
            }
        }
        return image;
    }
}
