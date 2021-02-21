package student.adventure;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Input {
    private GameEngine game;
    private Scanner scanner;
    private String action;
    private String secondWord;

    public Input(String file) throws FileNotFoundException {
        if (file == null || !file.equals("src/main/resources/starwars.json")) {
            throw new IllegalArgumentException();
        }
        game = new GameEngine(file); //creates game
        scanner = new Scanner(System.in);
        action = "";
        secondWord = "";
    }

    /**
     * Main method of input, runs next command and action methods until one returns true and game should stop running
     */
    public void runGame() {
        System.out.println(game.getIntro()); //print intro at start
        //keep looping until one method returns true and break;
        while (true) {
            //prints status at every turn
            System.out.println(game.examine());
            //get next command
            if (isNextCommandTerminal()) {
                break;
            }
            //do action in command
            if (isActionTerminal()) {
                break;
            }
        }
    }

    /**
     * Gets next command from user
     * @return true if command should stop game, false if game should continue
     */
    private boolean isNextCommandTerminal() {
        System.out.print(">"); //prints prompt
        //action gets first word from scanner
        action = scanner.next().trim().toLowerCase();
        if (action.equals("quit") || action.equals("exit")) {
            //return true to stop runGame() loop if quit or exit
            return true;
        } else if (action.equals("examine") || action.equals("history")) {
            //empty second word if examine is command, continue loop
            secondWord = "";
        } else {
            //must be second word (item, direction)
            secondWord = scanner.next().trim().toLowerCase();
        }
        return false;
    }

    /**
     * Uses game engine check command method to do command from user
     * @return true if the action should end the game, false if it should continue
     */
    private boolean isActionTerminal() {
        //get output from gameEngine checkCommand method for given input
        String result = game.checkCommand(action, secondWord);
        //while one of the keywords indicating success is the result
        while(!result.equals("Went") && !result.equals("Took") && !result.equals("Dropped")) {
            if (result.equals("quit") || result.equals("exit")) {
                //stop if quit is command
                return true;
            }
            //print the output-either error message or status of game, get the next command, and update the result and restart
            System.out.println(result);
            isNextCommandTerminal();
            result = game.checkCommand(action, secondWord);
        }
        //check if game engine finds player has won and print end message if so
        if (game.isComplete(result)) {
            System.out.println(game.winMessage());
            return true;
        }
        return false;
    }



}
