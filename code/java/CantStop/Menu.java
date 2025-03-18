import javax.swing.JPanel;
import java.awt.Dimension;
import java.awt.Color;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Component;
import javax.swing.Box;
import javax.swing.JButton;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.BorderLayout;
import javax.swing.JEditorPane;
import javax.swing.BorderFactory;
import javax.swing.JScrollPane;
import java.io.File;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import javax.swing.JTextField;
import javax.swing.ImageIcon;
import javax.swing.JComboBox;
import javax.swing.JSlider;
import java.util.ArrayList;
import java.util.HashSet;
import java.awt.GridLayout;
import java.awt.FlowLayout;
import java.util.Random;
import java.util.Timer;

/**
 * Main menu for the Can't Stop game
 *
 * @author Dewan Mukto
 * @version 2024-10-13
 * 
 * 
 */
public class Menu extends JPanel
{
    /** 
     * Using this color palette
     * https://coolors.co/ffbe0b-fb5607-ff006e-8338ec-3a86ff
     */
    private BufferedImage char1, char2, char3, char4, char5, char6, char7, char8;
    private Color azure = new Color(58,134,255);
    private Color blueViolet = new Color(131,56,236);
    private Color rose = new Color(255,0,110);
    private Color orangePantone = new Color(251,86,7);
    private Color amber = new Color(255,190,11);
    private int NUM_OF_PLAYERS = 2; // default number of players
    private ArrayList<PlayerColumn> playerColumns = new ArrayList<>();
    private HashSet<Integer> selectedCharacters = new HashSet<>(); // tracks selected characters
    private JLabel warningLabel = new JLabel("", SwingConstants.CENTER);
    private Timer timer;
    
    
    public Menu()
    {
       setPreferredSize(new Dimension(800,600));
       setBackground(Color.BLACK);
       setFocusable(true);
       setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
       // load up the assets
       try {
           char1 = ImageIO.read(new File("Assets/1.png"));
           char2 = ImageIO.read(new File("Assets/2.png"));
           char3 = ImageIO.read(new File("Assets/3.png"));
           char4 = ImageIO.read(new File("Assets/4.png"));
           char5 = ImageIO.read(new File("Assets/5.png"));
           char6 = ImageIO.read(new File("Assets/6.png"));
           char7 = ImageIO.read(new File("Assets/7.png"));
           char8 = ImageIO.read(new File("Assets/8.png"));
       } catch(Exception error){
           error.printStackTrace();
       }
       // by default, display menu screen
       showMainMenu();
    }
    
    private void showStartScreen() {
        removeAll();
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setBackground(Color.BLACK);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Slider to choose number of players (2 to 4)
        JPanel sliderPanel = new JPanel();
        sliderPanel.setOpaque(false);
        JLabel sliderLabel = new JLabel("Number of Players:");
        sliderLabel.setForeground(Color.WHITE);
        JSlider playerSlider = new JSlider(2, 4, NUM_OF_PLAYERS);
        playerSlider.setMajorTickSpacing(1);
        playerSlider.setPaintTicks(true);
        playerSlider.setPaintLabels(true);
        playerSlider.addChangeListener(e -> updatePlayerColumns(playerSlider.getValue()));

        sliderPanel.add(sliderLabel);
        sliderPanel.add(playerSlider);
        contentPanel.add(sliderPanel);

        // Panel for player columns (character selection, name, type)
        JPanel playerSelectionPanel = new JPanel();
        playerSelectionPanel.setLayout(new GridLayout(1, 4, 20, 0));
        playerSelectionPanel.setOpaque(false);

        // Create columns for each player (max 4 players)
        for (int i = 0; i < 4; i++) {
            PlayerColumn column = new PlayerColumn(i + 1);
            playerColumns.add(column);
            playerSelectionPanel.add(column);
        }

        contentPanel.add(playerSelectionPanel);

        // Warning label for duplicate character selection
        warningLabel.setForeground(Color.RED);
        contentPanel.add(warningLabel);

        // Bottom panel with buttons
        JPanel bottomPanel = new JPanel();
        bottomPanel.setOpaque(false);
        bottomPanel.setLayout(new FlowLayout());

        // Back button
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> {
            removeAll();
            showMainMenu();
            revalidate();
            repaint();
        });
        bottomPanel.add(backButton);

        // Start button
        JButton startButton = createButton("Start Game");
        startButton.addActionListener(e -> startGame());
        bottomPanel.add(startButton);

        contentPanel.add(bottomPanel);
        add(contentPanel, BorderLayout.CENTER);

        updatePlayerColumns(NUM_OF_PLAYERS); // Initialize with default player count
        revalidate();
        repaint();
    }

    private void updatePlayerColumns(int numPlayers) {
        NUM_OF_PLAYERS = numPlayers;
        selectedCharacters.clear();
        warningLabel.setText("");

        Random random = new Random();

        for (int i = 0; i < playerColumns.size(); i++) {
            PlayerColumn column = playerColumns.get(i);
            if (i < NUM_OF_PLAYERS) {
                int randomChar;
                do {
                    randomChar = random.nextInt(8) + 1;
                } while (selectedCharacters.contains(randomChar)); // Ensure no duplicate characters
                column.setCharacter(randomChar); // Initialize with random character
                selectedCharacters.add(randomChar);
                column.activate();
            } else {
                column.deactivate();
            }
        }
    }

    private void startGame() {
        // Collect player details (name, character, type) and pass to Game()
        ArrayList<PlayerInfo> players = new ArrayList<>();
        for (int i = 0; i < NUM_OF_PLAYERS; i++) {
            PlayerColumn column = playerColumns.get(i);
            players.add(column.getPlayerInfo());
        }
        // Initialize and start the game
        new Game(players);
    }

    
    // Inner class for each player column
    private class PlayerColumn extends JPanel {
        private Character character;
        private BufferedImage nextChar;
        private int playerIndex;
        private JComboBox<String> playerType;
        private JTextField playerName;
        private int selectedCharacter;

        public PlayerColumn(int playerIndex) {
            this.playerIndex = playerIndex;
            setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
            setOpaque(false);
            setAlignmentX(Component.CENTER_ALIGNMENT);

            // Character selection
            JPanel charPanel = new JPanel();
            charPanel.setOpaque(false);
            charPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

            JButton prevCharButton = createButton("<");
            prevCharButton.addActionListener(e -> changeCharacter(-1));
            JButton nextCharButton = createButton(">");
            nextCharButton.addActionListener(e -> changeCharacter(1));

            switch (playerIndex) {
                case 1: 
                    nextChar = char1;
                    break;
                case 2: 
                    nextChar = char2;
                    break;
                case 3: 
                    nextChar = char3;
                    break;
                case 4: 
                    nextChar = char4;
                    break;
                case 5: 
                    nextChar = char5;
                    break;
                case 6: 
                    nextChar = char6;
                    break;
                case 7: 
                    nextChar = char7;
                    break;
                case 8: 
                    nextChar = char8;
                    break;
                default:
                    break;
            }
            character = new Character(0,0,nextChar);

            charPanel.add(prevCharButton);
            charPanel.add(characterL);
            charPanel.add(nextCharButton);

            // Player name and type
            playerName = new JTextField("Player " + playerIndex, 10);
            playerName.setAlignmentX(Component.CENTER_ALIGNMENT);
            playerType = new JComboBox<>(new String[]{"Human", "Easy A.I.", "Hard A.I."});
            playerType.setAlignmentX(Component.CENTER_ALIGNMENT);

            add(charPanel);
            add(new JLabel("Name:"));
            add(playerName);
            add(new JLabel("Type:"));
            add(playerType);
        }

        public void activate() {
            setVisible(true);
        }

        public void deactivate() {
            setVisible(false);
        }

        private void changeCharacter(int delta) {
            int oldCharacter = selectedCharacter;
            selectedCharacter = (selectedCharacter + delta - 1 + 8) % 8 + 1; // Wrap-around logic

            if (selectedCharacters.contains(selectedCharacter)) {
                warningLabel.setText("Character already selected by another player!");
                selectedCharacter = oldCharacter;
            } else {
                selectedCharacters.remove(oldCharacter);
                selectedCharacters.add(selectedCharacter);
                characterLabel.setText("Character " + selectedCharacter);
                warningLabel.setText(""); // Clear warning
            }
        }

        public void setCharacter(int character) {
            selectedCharacter = character;
            characterLabel.setText("Character " + character);
        }

        public PlayerInfo getPlayerInfo() {
            return new PlayerInfo(playerName.getText(), (String) playerType.getSelectedItem(), selectedCharacter);
        }
    }

    private void showMainMenu(){
        // Title
       JLabel title = new JLabel("CAN'T STOP", SwingConstants.CENTER);
       title.setFont(new Font("Serif", Font.BOLD, 52));
       title.setForeground(Color.WHITE);
       title.setAlignmentX(Component.CENTER_ALIGNMENT);
       add(title);
       
       // Subtitle
       JLabel subtitle = new JLabel("Virtual Edition", SwingConstants.CENTER);
       subtitle.setFont(new Font("SansSerif", Font.PLAIN, 32));
       subtitle.setForeground(Color.GRAY);
       subtitle.setAlignmentX(Component.CENTER_ALIGNMENT);
       add(subtitle);
       
       add(Box.createRigidArea(new Dimension(0, 100)));
       
       /**
        *  Generating buttons: Start Game, Help, Exit
        */
       // Start Game
       JButton playButton = createButton("Start Game");
       playButton.addActionListener(e -> {
           removeAll();
           revalidate();
           repaint();
           showStartScreen();
           // add()
           revalidate();
           repaint();
       });
       add(playButton);
       // Help
       JButton helpButton = createButton("Help");
       helpButton.addActionListener(e -> {
           removeAll();
           showHelpScreen();
           revalidate();
           repaint();
       });
       add(helpButton);
       // Exit
       JButton exitButton = createButton("Exit");
       exitButton.addActionListener(e -> System.exit(0));
       add(exitButton);
    }
    
    private void showHelpScreen(){
        setLayout(new BorderLayout());
        // JEditorPane for rendering HTML formatting
        JEditorPane textArea = new JEditorPane();
        textArea.setEditable(false);
        textArea.setBackground(Color.BLACK);
        textArea.setForeground(Color.WHITE);
        textArea.setContentType("text/html");
        textArea.setBorder(BorderFactory.createEmptyBorder(15,15,25,25));
        textArea.setText("<html>"
        + "<center><font color='#cfcfcf'>v0.0.1</font></center>"
        + "<style>a { text-decoration: none; color: #aaabbd }</style>"
        + "<div style='padding: 10px; color: white;'>HOW TO PLAY</div>"
        + "<div style='padding-left: 10px; color: #aaaaaa;'>"
        + "This is a computerized version of the board game named Can't Stop.<br>"
        + "The same rules apply."
        + "</div><br><br>"
        + "<div style='padding: 10px; color: white;'>ABOUT</div>"
        + "<div style='padding-left: 10px; color: #aaaaaa;'>"
        + "Inspired by a group project for COMP2005 at Memorial University of Newfoundland<br>"
        + "Developed by <font color='#ff0a45'>Dewan Mukto</font> (<a href='https://x.com/dewan_mukto'>@dewan_mukto</a>)<br>"
        + "Character sprites by <font color='#ff0a45'>Glitched Velocity</font> (<a href='https://x.com/VazahatJordan'>@VazahatJordan</a>)<br>"
        + "All other images are generated using fractals and math!<br>"
        + "Published by <font color='#ff0a45'>Muxday</font>"
        + "</div>"
        + "</html>");
        // Custom scroll bar
        JScrollPane scrollpane = new JScrollPane(textArea);
        scrollpane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);
        scrollpane.getVerticalScrollBar().setUI(new scrollBarUI());
        add(scrollpane, BorderLayout.CENTER);
        
        JButton backButton = createButton("Back");
        backButton.addActionListener(e -> {
            removeAll();
            setLayout(new BoxLayout(Menu.this, BoxLayout.Y_AXIS));
            showMainMenu();
            revalidate();
            repaint();
        });
        add(backButton, BorderLayout.SOUTH);
    }

    /**
     *  Quick way of generating buttons with a preset theme
     */
    private JButton createButton(String text){
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setMaximumSize(new Dimension(200,40));
        button.setBackground(azure);
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e){
                button.setBackground(blueViolet);
                button.setForeground(Color.WHITE);
            }
            public void mouseExited(MouseEvent e){
                button.setBackground(azure);
                button.setForeground(Color.BLACK);
            }
        });
        return button;
    }
    
    private class scrollBarUI extends javax.swing.plaf.basic.BasicScrollBarUI {
        @Override
        protected void configureScrollBarColors(){
            thumbColor = Color.GRAY;
        }
        @Override
        protected JButton createDecreaseButton(int orientation){
            return createInvisibleButton();
        }
        @Override
        protected JButton createIncreaseButton(int orientation) {
            return createInvisibleButton();
        }

        private JButton createInvisibleButton(){
            JButton button = new JButton();
            button.setPreferredSize(new Dimension(0,0));
            button.setVisible(false);
            return button;
        }
    }
}
