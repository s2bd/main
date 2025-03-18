import java.util.Random;

/**
 * Virtual dice simulator
 * 
 * @author Dewan Mukto
 * @version 2024-10-13
 */
public class Dice {
    private Random random;

    public Dice() {
        random = new Random();
    }

    public int[] roll() {
        int dice1 = random.nextInt(6) + 1;
        int dice2 = random.nextInt(6) + 1;
        return new int[]{dice1, dice2};
    }
}