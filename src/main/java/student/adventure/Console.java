package student.adventure;

import java.io.FileNotFoundException;
import java.util.Scanner;

public class Console {
    private GameEngine game;
    private Scanner scanner;
    private String action;
    private String secondWord;

    public Console(String file) throws FileNotFoundException {
        if (file == null) {
            throw new IllegalArgumentException();
        }
        game = new GameEngine(file);
        scanner = new Scanner(System.in);
        action = "";
        secondWord = "";
    }
    private boolean getNextCommand() {
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

}
