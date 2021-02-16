package student.adventure;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Input {
    private GameEngine game;
    private Scanner scanner;
    private String action;
    private String secondWord;

    public Input(String file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        game = new GameEngine(file);
        scanner = new Scanner(System.in);
        action = "";
        secondWord = "";
    }
    public void runGame() {
        System.out.println(game.getIntro());
        while (true) {
            System.out.println(game.examine());
            if (isNextCommandTerminal()) {
                break;
            }
            if (isActionTerminal()) {
                break;
            }
        }
    }
    private boolean isNextCommandTerminal() {
        System.out.print(">");
        action = scanner.next().trim().toLowerCase();
        if (action.equals("quit") || action.equals("exit")) {
            return true;
        } else if (action.equals("examine")) {
            secondWord = "";
        } else {
            secondWord = scanner.next().trim().toLowerCase();
        }
        return false;
    }
    private boolean isActionTerminal() {
        String result = game.checkCommand(action, secondWord);
        while(!result.equals("Went") && !result.equals("Took") && !result.equals("Dropped")) {
            if (result.equals("quit") || result.equals("exit")) {
                return true;
            }
            System.out.println(result);
            isNextCommandTerminal();
            result = game.checkCommand(action, secondWord);
        }
        if (game.isComplete(result)) {
            System.out.println("Congratulations! You have reached the Jedi Temple, and you can now begin your training.");
        }
        return game.isComplete(result);
    }



}
