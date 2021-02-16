import student.adventure.Input;

import java.io.FileNotFoundException;

public class Main {
    public static void main(String[] args) throws FileNotFoundException {
        Input runGame = new Input("src/main/resources/starwars.json");
        runGame.runGame();
    }
}
