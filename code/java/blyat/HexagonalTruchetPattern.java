import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class HexagonalTruchetPattern extends Application {

    // Constants
    private static final int WIDTH = 800;
    private static final int HEIGHT = 800;
    private static final int HEX_SIZE = 30; // Size of the hexagons

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Hexagonal Truchet Pattern");

        // Create a canvas
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        GraphicsContext gc = canvas.getGraphicsContext2D();

        // Draw the pattern
        drawHexagonalTruchet(gc);

        // Set up the scene
        Group root = new Group();
        root.getChildren().add(canvas);
        Scene scene = new Scene(root, WIDTH, HEIGHT);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void drawHexagonalTruchet(GraphicsContext gc) {
        for (int y = 0; y < HEIGHT; y += (int) (HEX_SIZE * 1.5)) {
            for (int x = 0; x < WIDTH; x += (int) (HEX_SIZE * Math.sqrt(3))) {
                drawHexagon(gc, x, y);
            }
        }
    }

    private void drawHexagon(GraphicsContext gc, int x, int y) {
        double[] xPoints = {
            x + HEX_SIZE * Math.cos(0),
            x + HEX_SIZE * Math.cos(Math.PI / 3),
            x + HEX_SIZE * Math.cos(2 * Math.PI / 3),
            x + HEX_SIZE * Math.cos(Math.PI),
            x + HEX_SIZE * Math.cos(4 * Math.PI / 3),
            x + HEX_SIZE * Math.cos(5 * Math.PI / 3)
        };
        double[] yPoints = {
            y + HEX_SIZE * Math.sin(0),
            y + HEX_SIZE * Math.sin(Math.PI / 3),
            y + HEX_SIZE * Math.sin(2 * Math.PI / 3),
            y + HEX_SIZE * Math.sin(Math.PI),
            y + HEX_SIZE * Math.sin(4 * Math.PI / 3),
            y + HEX_SIZE * Math.sin(5 * Math.PI / 3)
        };

        // Randomly choose to flip the hexagon about the x-axis
        if (Math.random() < 0.5) {
            for (int i = 0; i < yPoints.length; i++) {
                yPoints[i] = -yPoints[i] + (2 * y); // Flip
            }
        }

        // Set a color based on the distance from the center
        double distance = Math.sqrt((x - WIDTH / 2) * (x - WIDTH / 2) + (y - HEIGHT / 2) * (y - HEIGHT / 2));
        Color color = Color.hsb(distance / Math.max(WIDTH, HEIGHT) * 360, 0.8, 0.8);

        gc.setFill(color);
        gc.fillPolygon(xPoints, yPoints, xPoints.length);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
