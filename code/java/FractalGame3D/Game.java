import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Game extends JPanel implements KeyListener {
    private GameLoop gameLoop;
    private boolean[] keys; // Array to track key states

    public Game() {
        gameLoop = new GameLoop(this);
        keys = new boolean[256]; // Initialize array for key states
        setPreferredSize(new Dimension(800, 600));
        JFrame frame = new JFrame("Fractal Doom");
        frame.add(this);
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
        addKeyListener(this); // Add KeyListener to the JPanel
        setFocusable(true); // Ensure the JPanel can gain focus
        gameLoop.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        gameLoop.update();
        gameLoop.render(g);
    }

    // Main method to run the game
    public static void main(String[] args) {
        new Game();
    }

    // Implement KeyListener methods
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true; // Set the key pressed state to true
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false; // Set the key pressed state to false
    }

    @Override
    public void keyTyped(KeyEvent e) {
        // Not used, but required to implement the interface
    }
}
