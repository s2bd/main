import javax.swing.*;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    private ArrayList<PlayerInfo> players;
    private int currentPlayerIndex = 0;
    private Board board;
    private Dice dice;

    public Game(ArrayList<PlayerInfo> players) {
        this.players = players;
        this.board = new Board();
        this.dice = new Dice();
        startGame();
    }

    private void startGame() {
        while (!board.isGameOver()) {
            PlayerInfo currentPlayer = players.get(currentPlayerIndex);
            takeTurn(currentPlayer);
            currentPlayerIndex = (currentPlayerIndex + 1) % players.size(); // Rotate to next player
        }
        announceWinner();
    }

    private void takeTurn(PlayerInfo player) {
        JOptionPane.showMessageDialog(null, player.name + "'s turn!");
        int[] diceRoll = dice.roll();
        board.processDiceRoll(player, diceRoll);
        if (player.type.equals("Easy A.I.")) {
            easyAI(player);
        } else if (player.type.equals("Hard A.I.")) {
            hardAI(player);
        }
    }

    private void easyAI(PlayerInfo player) {
        // Simple AI that makes random but legal moves
        board.makeRandomMove(player);
    }

    private void hardAI(PlayerInfo player) {
        // More advanced AI with a strategy
        board.makeOptimalMove(player);
    }

    private void announceWinner() {
        PlayerInfo winner = board.getWinner();
        JOptionPane.showMessageDialog(null, "The winner is: " + winner.name);
    }
}
