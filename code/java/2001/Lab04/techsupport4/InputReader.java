package techsupport4;

import java.util.HashSet;
import java.util.Scanner;

/**
 * InputReader reads typed text input from the standard text terminal. 
 * The text typed by a user is then chopped into words, and a set of words 
 * is provided.
 * 
 * @author     Michael Kölling and David J. Barnes
 * @version    1.0 (2016.02.29)
 */
public class InputReader
{
    private Scanner reader;

    /**
     * Create a new InputReader that reads text from the text terminal.
     */
    public InputReader()
    {
        reader = new Scanner(System.in);
    }

    /**
     * Read a line of text from standard input (the text terminal),
     * and return it as a set of words.
     *
     * @return  A set of Strings, where each String is one of the 
     *          words typed by the user
     */
    public HashSet<String> getInput() 
    {
        System.out.print("> ");                // print prompt
        return wordSet(reader.nextLine());

    }

    /**
     * Convert a string to a set of words.
     *
     * @return  A set of Strings, where each String is one of the
     *          words typed by the user
     */

    public HashSet<String> wordSet(String line)
    {
        String[] wordArray = line.trim().toLowerCase().split(" ");  // split at spaces

        // add words from array into hashset
        HashSet<String> words = new HashSet<>();
        for(String word : wordArray) {
            words.add(word);
        }
        return words;
    }

}
