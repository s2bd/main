import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JButton;
import javax.swing.JTextArea;
import java.awt.Color;
import java.awt.Container;
import java.awt.Font;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

/**
 * Main class for the game, generates the GUI window
 *
 * @author Dewan Mukto (dmimukto)
 * @attribution Ryi Snow - How to Make a Text Adventure Game with GUI in Java
 * @version 21 Sept 2022
 */
public class Game
{
    JFrame gameWindow; // declaring the window
    static JPanel gameTitle; // panel for the window
    static JPanel startButton; // panel for start button
    JLabel gameTitleText; // the text body of the title
    JButton startButtonTrigger; // event listener (button)
    JPanel storyText;
    JTextArea storyTextArea;
    Container storyCont;
    Font gameTitleTextFont = new Font("URW Bookman", Font.PLAIN, 38); // font of the title
    Font bodyTextFont = new Font("Ubuntu", Font.PLAIN, 28); // font for body text
    Container windowCont;
    TitleScreenHandler titleTrigger = new TitleScreenHandler(); // event listener
    /**
     * Launcher for the game
     */
    public static void main(){
        new Game();
    }
    /**
     * Constructor for the game's window
     */
    public Game()
    {
        gameWindow = new JFrame(); // create a window for the game
        gameWindow.setSize(800,600); // default size for the window
        gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        gameWindow.getContentPane().setBackground(Color.darkGray);
        gameWindow.setLayout(null); // getting rid of the default GUI layout
        gameWindow.setVisible(true); // making the window actually appear
        // getting the container frame
        windowCont = gameWindow.getContentPane();
        // setting the game title
        gameTitle = new JPanel();
        gameTitle.setBounds(100,100,600,150); // (starting_x, starting_y, width, height)
        gameTitle.setBackground(Color.darkGray); // visualizing the position with a different color
        gameTitleText = new JLabel("GAME TITLE"); // game title goes here
        gameTitleText.setForeground(Color.white); // title text color
        gameTitleText.setFont(gameTitleTextFont); // title text font
        // creating the start button
        startButton = new JPanel();
        startButton.setBounds(300, 400, 200, 100);
        startButton.setBackground(Color.black);
        startButtonTrigger = new JButton("Begin"); // start button text
        startButtonTrigger.setFont(bodyTextFont); // body text,e.g. buttons
        startButtonTrigger.setBackground(Color.black);
        startButtonTrigger.setForeground(Color.white);
        startButtonTrigger.addActionListener(titleTrigger); // adding functionality to the button
        // adding the elements to the window
        gameTitle.add(gameTitleText);
        startButton.add(startButtonTrigger);
        windowCont.add(gameTitle); // adding the game title to the window
        windowCont.add(startButton);
    }
    
}
class TitleScreenHandler implements ActionListener{
    JPanel gameTitle;
    JPanel startButton;
    JPanel storyText;
    Container storyCont;
    JTextArea storyTextArea;
    Font bodyTextFont = new Font("Ubuntu", Font.PLAIN, 28);
    /**
     * Creates a game session
     */
    public void initializeGame(){
        gameTitle.setVisible(false);
        startButton.setVisible(false);
        
        storyText = new JPanel();
        storyText.setBounds(100,100,600,250);
        storyText.setBackground(Color.black);
        storyCont.add(storyText);
        storyTextArea = new JTextArea();
        storyTextArea.setBounds(100,100,600,250);
        storyTextArea.setBackground(Color.black);
        storyTextArea.setForeground(Color.white);
        storyTextArea.setFont(bodyTextFont);
        storyTextArea.setLineWrap(true);
        storyText.add(storyTextArea);
        
    }
    public void actionPerformed(ActionEvent event){
        initializeGame();
    }
}    