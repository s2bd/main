import java.awt.*;

public class GameLoop {
    private Game game;
    private Player player;
    private Room room;

    public GameLoop(Game game) {
        this.game = game;
        player = new Player();
        room = new Room();
    }

    public void start() {
        while (true) {
            game.repaint();
            update();
            try {
                Thread.sleep(16); // Approximately 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public void update() {
        //player.update();
        room.update(player);
    }

    public void render(Graphics g) {
        room.render(g);
        player.render(g);
    }
}
