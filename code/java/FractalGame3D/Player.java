import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.BufferedImage;  // For BufferedImage
import java.awt.Graphics;              // For Graphics
import javax.swing.JPanel;             // For JPanel
import javax.swing.JFrame;             // For JFrame

public class Player implements KeyListener {
    private Vector position;

    public Player() {
        position = new Vector(400, 300, 0);
    }

    // public void update() {
        // if (Game.keyPressed(KeyEvent.VK_W)) position.y -= 5; // Move up
        // if (Game.keyPressed(KeyEvent.VK_S)) position.y += 5; // Move down
        // if (Game.keyPressed(KeyEvent.VK_A)) position.x -= 5; // Move left
        // if (Game.keyPressed(KeyEvent.VK_D)) position.x += 5; // Move right
    // }

    public void render(Graphics g) {
        g.setColor(Color.BLUE);
        g.fillRect((int) position.x, (int) position.y, 20, 20); // Simple player representation
    }

    public Vector getPosition() {
        return position;
    }

    @Override
    public void keyPressed(KeyEvent e) {}

    @Override
    public void keyReleased(KeyEvent e) {}

    @Override
    public void keyTyped(KeyEvent e) {}
}
