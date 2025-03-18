import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;

public class Player {
    private int x, y;
    private int speed = 2;
    private BufferedImage spritesheet;
    private int frame = 0;
    private long lastFrameChangeTime;
    private String direction = "DOWN";
    private boolean up, down, left, right;

    public Player(int x, int y, BufferedImage spritesheet) {
        this.x = x;
        this.y = y;
        this.spritesheet = spritesheet;
    }

    public void draw(Graphics g) {
        int spriteX = (frame % 3) * 78; // Each frame is 78 pixels wide
        int spriteY = 0; // Default to south

        switch (direction) {
            case "DOWN": spriteY = 0; break; // Row 0
            case "LEFT": spriteY = 1 * 108; break; // Row 1
            case "RIGHT": spriteY = 2 * 108; break; // Row 2
            case "UP": spriteY = 3 * 108; break; // Row 3
        }

        // If not moving, use the stationary frame (middle frame)
        if (!up && !down && !left && !right) {
            spriteX = 78; // Stationary frame
        }

        // Ensure that we don't go out of bounds of the spritesheet
        if (spriteX + 78 <= spritesheet.getWidth() && spriteY + 108 <= spritesheet.getHeight()) {
            g.drawImage(spritesheet.getSubimage(spriteX, spriteY, 78, 108), x, y, null);
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime > 100) {
            // Change frame only if moving
            if (up || down || left || right) {
                frame = (frame + 1) % 3; // Cycle through 0-2 frames
            }
            lastFrameChangeTime = currentTime;
        }

        // Move Player instantly
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        // Keep player within bounds
        if (x < 0) x = 0;
        if (x > 800 - 78) x = 800 - 78; // 78 is the width of the sprite
        if (y < 0) y = 0;
        if (y > 600 - 108) y = 600 - 108; // 108 is the height of the sprite
    }

    public void keyPressed(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: up = true; direction = "UP"; break;
            case KeyEvent.VK_A: left = true; direction = "LEFT"; break;
            case KeyEvent.VK_S: down = true; direction = "DOWN"; break;
            case KeyEvent.VK_D: right = true; direction = "RIGHT"; break;
        }
    }

    public void keyReleased(KeyEvent e) {
        switch (e.getKeyCode()) {
            case KeyEvent.VK_W: up = false; break;
            case KeyEvent.VK_A: left = false; break;
            case KeyEvent.VK_S: down = false; break;
            case KeyEvent.VK_D: right = false; break;
        }
    }
}
