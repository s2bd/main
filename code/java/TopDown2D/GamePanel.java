import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import javax.imageio.ImageIO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener, KeyListener {
    private BufferedImage spritesheet;
    private Player player;
    private List<NPC> npcs;
    private Timer timer;

    public GamePanel() {
        setPreferredSize(new Dimension(800, 600));
        setBackground(Color.BLACK);
        setFocusable(true);
        addKeyListener(this);

        try {
            spritesheet = ImageIO.read(new File("spritesheet.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        player = new Player(400, 300, spritesheet);
        npcs = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
            npcs.add(new NPC(new Random().nextInt(800), new Random().nextInt(600), spritesheet));
        }

        timer = new Timer(20, this);
        timer.start();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        player.draw(g);
        for (NPC npc : npcs) {
            npc.draw(g);
        }
        g.setColor(Color.WHITE);
        g.drawString("Use WASD to move", 10, 20);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        player.update();
        for (NPC npc : npcs) {
            npc.update();
        }
        repaint();
    }

    @Override
    public void keyPressed(KeyEvent e) {
        player.keyPressed(e);
    }

    @Override
    public void keyReleased(KeyEvent e) {
        player.keyReleased(e);
    }

    @Override
    public void keyTyped(KeyEvent e) {}
}
