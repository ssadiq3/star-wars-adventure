
package student.server;

import student.adventure.GameEngine;

import java.io.FileNotFoundException;
import java.util.*;

public class StarWarsAdventure implements AdventureService {
    private Map<Integer, GameEngine> games;
    private Integer id;

    public StarWarsAdventure() {
        games = new HashMap<>();
        id = 0;
    }
    /**
     * Resets the service to its initial state.
     */

    public void reset() {
        for (Integer posID : games.keySet()) {
            games.remove(posID);
        }
        id = 0;
    }

    /**
     * Creates a new Adventure game and stores it.
     * @return the id of the game.
     */
    public int newGame() throws AdventureException, FileNotFoundException {
        int currentID = id;
        GameEngine newGame = new GameEngine();
        games.put(id, newGame);
        id++;
        return currentID;
    }

    /**
     * Returns the state of the game instance associated with the given ID.
     * @param id the instance id
     * @return the current state of the game
     */
    public GameStatus getGame(int id) throws FileNotFoundException, AdventureException {
        GameEngine game = null;
        boolean error = true;
        String message = "";
        String imageURL = "";
        String videoURL = "";
        for (Integer posID : games.keySet()) {
            if (posID == id) {
                game = games.get(posID);
            } else {
                game = null;
                throw new AdventureException("Game with this id does not exist");
            }
        }
        try {
            message = game.getIntro() + "\n" + game.examine();
            imageURL = game.getCurrentRoom().getUrl();
            videoURL = game.getVideoURL();
            error = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        Map<String, List<String>> commandOptions = new HashMap<>();
        commandOptions.put("go", game.getCurrentRoom().getDirectionNames());
        commandOptions.put("take", game.getCurrentRoom().getItemNames());
        commandOptions.put("drop", game.getInventoryNames());
        commandOptions.put("history", game.getTraversed());
        AdventureState state = new AdventureState();
        GameStatus status = new GameStatus(error, id, message, imageURL, videoURL, state, commandOptions);
        return status;
    }

    /**
     * Removes & destroys a game instance with the given ID.
     * @param id the instance id
     * @return false if the instance could not be found and/or was not deleted
     */
    public boolean destroyGame(int id) {
        for (Integer posID : games.keySet()) {
            if (posID == id) {
                games.remove(posID);
                return true;
            }
        }
        return false;
    }

    /**
     * Executes a command on the game instance with the given id, changing the game state if applicable.
     * @param id the instance id
     * @param command the issued command
     */
    public void executeCommand(int id, Command command) throws FileNotFoundException {
        GameEngine game = new GameEngine();
        for (Integer posID : games.keySet()) {
            if (posID == id) {
                game = games.get(posID);
            }
        }
        game.checkCommand(command.getCommandName(), command.getCommandValue());
    }

    /**
     * Returns a sorted leaderboard of player "high" scores.
     * @return a sorted map of player names to scores
     */
    public SortedMap<String, Integer> fetchLeaderboard() { return null; }
}

