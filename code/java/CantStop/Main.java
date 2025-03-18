import javax.swing.JFrame;

/**
 * Launcher for the Can't Stop game
 *
 * @author Dewan Mukto
 * @version 2024-10-13
 */
public class Main
{
    public static void main(String args[]){
        JFrame frame = new JFrame("Can't Stop - Virtual Edition");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(new Menu());
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
