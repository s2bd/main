import java.awt.*;
import java.util.Random;

public class Minds {
    private String[] phrases = {"Hello!", "How are you?", "What a nice day!", "I'm here!"};
    private boolean speaking = false;
    private long lastSpeakTime;
    private Random random = new Random();

    public void drawSpeechBubble(Graphics g, int x, int y) {
        g.setColor(Color.WHITE);
        g.fillRect(x, y - 40, 100, 30);
        g.setColor(Color.BLACK);
        g.drawRect(x, y - 40, 100, 30);
        g.drawString(getRandomPhrase(), x + 5, y - 20);
    }

    public boolean shouldSpeak() {
        return speaking;
    }

    private String getRandomPhrase() {
        return phrases[random.nextInt(phrases.length)];
    }

    public void update(NPC npc) {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastSpeakTime > 3000) { // Speaks every 3 seconds
            speaking = true;
            lastSpeakTime = currentTime;
        } else {
            speaking = false;
        }
    }
}
