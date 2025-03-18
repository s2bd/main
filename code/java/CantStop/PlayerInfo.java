/**
 * Abstract class for characters in-game
 * 
 * @author Dewan Mukto
 * @version 2024-10-13
 */
public class PlayerInfo {
    String name; // player's display name
    String type; // player type [human, easy a.i., hard a.i.]
    int character; // player's sprite
    public PlayerInfo(String name, String type, int character) {
        this.name = name;
        this.type = type;
        this.character = character;
    }
}