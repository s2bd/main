import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

public class NPC {
    private int x, y;
    private BufferedImage spritesheet;
    private int frame = 0;
    private long lastFrameChangeTime;
    private String direction;
    private Random random = new Random();

    public NPC(int x, int y, BufferedImage spritesheet) {
        this.x = x;
        this.y = y;
        this.spritesheet = spritesheet;
        changeDirection();
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

        // Ensure that we don't go out of bounds of the spritesheet
        if (spriteX + 78 <= spritesheet.getWidth() && spriteY + 108 <= spritesheet.getHeight()) {
            g.drawImage(spritesheet.getSubimage(spriteX, spriteY, 78, 108), x, y, null);
        }
    }

    public void update() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastFrameChangeTime > 100) {
            frame = (frame + 1) % 3; // Cycle through 0-2 frames
            lastFrameChangeTime = currentTime;
        }
        
        // Move NPC
        switch (direction) {
            case "UP": y -= 2; break;
            case "DOWN": y += 2; break;
            case "LEFT": x -= 2; break;
            case "RIGHT": x += 2; break;
        }
        
        // Randomly change direction every few frames
        if (random.nextInt(100) < 5) {
            changeDirection();
        }

        // Keep NPC within the bounds of the window
        if (x < 0) x = 0;
        if (x > 800 - 78) x = 800 - 78; // 78 is the width of the sprite
        if (y < 0) y = 0;
        if (y > 600 - 108) y = 600 - 108; // 108 is the height of the sprite
    }

    private void changeDirection() {
        int dir = random.nextInt(4); // Now only 4 directions
        switch (dir) {
            case 0: direction = "UP"; break;
            case 1: direction = "DOWN"; break;
            case 2: direction = "LEFT"; break;
            case 3: direction = "RIGHT"; break;
        }
    }
}
