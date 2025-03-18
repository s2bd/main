import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

public class CircleSimulator extends JPanel implements ActionListener {
    private final ArrayList<Circle> circles;
    private final Timer timer;
    private Entity entity;
    private boolean entityMoving = false;
    private double totalDistanceMoved = 0.0;
    private long startTime;

    public CircleSimulator() {
        circles = new ArrayList<>();
        timer = new Timer(20, this); // Timer for regular updates
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.WHITE);

        // Mouse click listener for adding circles
        addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                circles.add(new Circle(e.getX(), e.getY()));
                repaint();
            }
        });

        // Button to spawn the entity
        JButton spawnButton = new JButton("Spawn Entity");
        spawnButton.addActionListener(e -> spawnEntity());
        add(spawnButton, BorderLayout.SOUTH);
    }

    private void spawnEntity() {
        if (entityMoving) {
            return; // If already moving, do nothing
        }
        if (circles.size() < 2) return; // Need at least two circles

        entity = new Entity(circles);
        totalDistanceMoved = 0; // Reset total distance
        startTime = System.currentTimeMillis(); // Start time for total duration
        entityMoving = true; // Start moving the entity
        timer.start(); // Start the timer for updates
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        repaint(); // Repaint the panel to update the display
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        for (Circle circle : circles) {
            circle.draw(g);
        }
        drawLinesAndDistances(g);
        if (entityMoving && entity != null) {
            entity.move();
            entity.draw(g);
            totalDistanceMoved += entity.getDistanceMoved(); // Update total distance
            if (entity.isFinished()) {
                entityMoving = false; // Stop moving when finished
                timer.stop(); // Stop the timer
                long totalTime = System.currentTimeMillis() - startTime; // Calculate total time
                showTotalTime(g, totalTime); // Display total time
            }
        }
        showHUD(g); // Display HUD information
    }

    private void drawLinesAndDistances(Graphics g) {
        for (int i = 0; i < circles.size(); i++) {
            for (int j = i + 1; j < circles.size(); j++) {
                Circle circleA = circles.get(i);
                Circle circleB = circles.get(j);
                g.setColor(Color.GRAY);
                g.drawLine(circleA.x, circleA.y, circleB.x, circleB.y);
                double distance = circleA.distanceTo(circleB);
                g.drawString(String.format("%.2f", distance), (circleA.x + circleB.x) / 2, (circleA.y + circleB.y) / 2);
            }
        }
    }

    private void showHUD(Graphics g) {
        g.setColor(Color.BLACK);
        g.drawString("Current Nodes: " + circles.size(), 10, 20);
        g.drawString("Distance Moved: " + String.format("%.2f", totalDistanceMoved), 10, 40);
    }

    private void showTotalTime(Graphics g, long totalTime) {
        g.setColor(Color.BLACK);
        g.drawString("Total Time: " + (totalTime / 1000.0) + " seconds", 10, 60);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Circle Simulator");
        CircleSimulator simulator = new CircleSimulator();
        frame.add(simulator);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    // Circle class to represent a circle with position and color
    class Circle {
        int x, y;
        Color color;
        boolean isCrossed;

        Circle(int x, int y) {
            this.x = x;
            this.y = y;
            this.color = Color.BLUE;
            this.isCrossed = false; // Initially not crossed
        }

        void draw(Graphics g) {
            g.setColor(isCrossed ? Color.GREEN : color); // Change color if crossed
            g.fillOval(x - 15, y - 15, 30, 30);
            g.setColor(Color.BLACK);
            g.drawString(String.valueOf(circles.indexOf(this) + 1), x, y - 20); // Draw number above circle
        }

        double distanceTo(Circle other) {
            return Math.sqrt(Math.pow(other.x - this.x, 2) + Math.pow(other.y - this.y, 2));
        }
    }

    // Entity class that finds a path visiting all circles using Nearest Neighbor algorithm
    class Entity {
        ArrayList<Circle> path;
        int currentCircleIndex = 0;
        double distanceMoved = 0.0;
        boolean[] visited;

        Entity(ArrayList<Circle> circles) {
            path = new ArrayList<>();
            visited = new boolean[circles.size()]; // Track visited circles
            int startCircleIndex = (int) (Math.random() * circles.size()); // Random start
            path.add(circles.get(startCircleIndex));
            visited[startCircleIndex] = true; // Mark as visited

            // Build the path using the Nearest Neighbor algorithm
            for (int i = 1; i < circles.size(); i++) {
                Circle currentCircle = path.get(i - 1);
                Circle nextCircle = findNearestNeighbor(currentCircle, circles);
                path.add(nextCircle);
                visited[circles.indexOf(nextCircle)] = true; // Mark as visited
            }
        }

        private Circle findNearestNeighbor(Circle currentCircle, ArrayList<Circle> circles) {
            Circle nearest = null;
            double minDistance = Double.MAX_VALUE;

            for (Circle circle : circles) {
                if (!visited[circles.indexOf(circle)]) { // If not visited
                    double distance = currentCircle.distanceTo(circle);
                    if (distance < minDistance) {
                        minDistance = distance;
                        nearest = circle;
                    }
                }
            }
            return nearest; // Return the nearest unvisited circle
        }

        void move() {
            if (currentCircleIndex < path.size()) {
                Circle current = path.get(currentCircleIndex);
                current.isCrossed = true; // Mark current circle as crossed
                distanceMoved += 1.0; // Increment distance moved
                currentCircleIndex++;
            }
        }

        void draw(Graphics g) {
            g.setColor(Color.RED);
            if (currentCircleIndex < path.size()) {
                Circle currentCircle = path.get(currentCircleIndex);
                g.fillOval(currentCircle.x - 5, currentCircle.y - 5, 10, 10);
            }
        }

        boolean isFinished() {
            return currentCircleIndex >= path.size(); // Return true if finished
        }

        double getDistanceMoved() {
            return distanceMoved; // Get total distance moved
        }
    }
}
