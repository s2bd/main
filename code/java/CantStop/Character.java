import java.awt.image.BufferedImage;
import java.awt.Graphics;

/**
 * Characters in-game
 * 
 * @author Dewan Mukto
 * @version 2024-10-13
 */
public class Character {
    private int x, y;
    private int speed = 2;
    private BufferedImage spritesheet;
    private int frame = 0;
    private long lastFrameChangeTime;
    private String direction;
    protected static final int FRAME_WIDTH = 78;
    protected static final int FRAME_HEIGHT = 108;
    private boolean up, down, left, right;

    public Character(int x, int y, BufferedImage spritesheet) {
        this.x = x;
        this.y = y;
        this.spritesheet = spritesheet;
        this.direction = "SOUTH";
    }

    /**
     * Renders specific frames from the spritesheet
     *
     * @param g graphical image provided, containing all the directional frames
     */
    public void draw(Graphics g) {
        int spriteX = (frame % 3) * FRAME_WIDTH; // according to frame width
        int spriteY = 0; // sprite facing south by default

        switch (direction) {
            case "SOUTH": spriteY = 0; break; // 1st row
            case "WEST": spriteY = 1 * 108; break; // 2nd row
            case "EAST": spriteY = 2 * 108; break; // 3rd row
            case "NORTH": spriteY = 3 * 108; break; // 4th row
        }

        // Actual rendering of the frame on-screen
        if (spriteX + FRAME_WIDTH <= spritesheet.getWidth() && spriteY + FRAME_HEIGHT <= spritesheet.getHeight()) {
            g.drawImage(spritesheet.getSubimage(spriteX, spriteY, FRAME_WIDTH, FRAME_HEIGHT), x, y, null);
        } else {
            frame = 0;
        }
    }

    /**
     * Updates the character state, should be implemented in derived classes.
     */
    public void update(){
        long currentTime = System.currentTimeMillis();
        if(currentTime - lastFrameChangeTime > 100){
            if (up || down || left || right) { // change frames only when moving
                frame = (frame + 1) % 3; // cycle through frames in current row of spritesheet
            }
        }
        lastFrameChangeTime = currentTime;
        // Moving the player
        if (up) y -= speed;
        if (down) y += speed;
        if (left) x -= speed;
        if (right) x += speed;

        // Keep the player within bounds
        keepWithinBounds(800, 600);
    }

    protected void keepWithinBounds(int width, int height) {
        if (x < 0) x = 0;
        if (x > width - 78) x = width - 78;
        if (y < 0) y = 0;
        if (y > height - 108) y = height - 108;
    }
    
    public int getX(){ 
        return x;
    }
    public int getY(){ 
        return y;
    }
    public int getWidth(){
        return FRAME_WIDTH;
    }
    public int getHeight(){
        return FRAME_HEIGHT;
    }
}
