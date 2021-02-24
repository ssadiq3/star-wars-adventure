
package student.server;

import student.adventure.GameEngine;

import java.io.FileNotFoundException;
import java.sql.ClientInfoStatus;
import java.util.*;

public class StarWarsAdventure implements AdventureService {
    private Map<Integer, GameEngine> games;
    private Integer id;
    private String commandAction;
    private String commandValue;

    /**
     * Creates games map and sets id to 0
     */
    public StarWarsAdventure() {
        games = new HashMap<>();
        id = 0;
    }
    /**
     * Resets the service to its initial state.
     */
    public void reset() {
        //remove every id's game and reset id
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
        games.put(id, newGame); //put new game in map
        id++;
        return currentID;
    }

    /**
     * Returns the state of the game instance associated with the given ID.
     * @param id the instance id
     * @return the current state of the game
     */
    public GameStatus getGame(int id) throws FileNotFoundException, AdventureException {
        //create variables for new game status variables
        GameEngine game = null;
        boolean error = true;
        String message = "";
        String videoURL = "";
        String imageURL = "";
        for (Integer posID : games.keySet()) {
            if (posID == id) {
                game = games.get(posID); //set game to given id
            }
        }
        //check if id doesn't exist
        if (game == null) {
            throw new AdventureException("id has no game");
        }
        Map<String, List<String>> commandOptions = new HashMap<>();
        //put all commands in commandoptions map
        List<String> historyList = new ArrayList<>();
        historyList.add("");
        //check if game is over
        if (game.isComplete()) {
            message = game.winMessage();
            commandOptions = null;
        } else {
            commandOptions.put("go", game.getCurrentRoom().getDirectionNames());
            commandOptions.put("take", game.getCurrentRoom().getItemNames());
            commandOptions.put("drop", game.getInventoryNames());
            commandOptions.put("history", historyList);
        }
        if (commandAction != null && commandAction.equals("history")) {
            //need to add traversed rooms if command is history
            message = game.getIntro() + "\n" + game.examine() + "\n" + game.getTraversedRooms();
        } else {
            message = game.getIntro() + "\n" + game.examine();
        }
        try  {
            //set all game status variables
            imageURL = game.getCurrentRoom().getImageUrl();
            videoURL = game.getVideoURL();
            error = false;
        } catch (Exception e) {
            e.printStackTrace();
        }
        AdventureState state = new AdventureState();
        //put all variables in new game status and return
        GameStatus status = new GameStatus(error, id, message, imageURL, videoURL, state, commandOptions);
        return status;
    }

    /**
     * Removes & destroys a game instance with the given ID.
     * @param id the instance id
     * @return false if the instance could not be found and/or was not deleted
     */
    public boolean destroyGame(int id) throws AdventureException {
        //find game and remove from map
        if (id < 0) {
            throw new AdventureException("id can not be negative");
        }
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
        commandAction = command.getCommandName();
        commandValue = command.getCommandValue();
        GameEngine game = null;
        for (Integer posID : games.keySet()) {
            if (posID == id) {
                game = games.get(posID); //set game to given id game
            }
        }
        //check if id has no game
        if (game == null) {
            throw new IllegalArgumentException("id has no game");
        }
        //run game engine check command on name and value
        game.checkCommand(command.getCommandName(), command.getCommandValue());
    }

    /**
     *  Not used
     * @return Null
     */
    public SortedMap<String, Integer> fetchLeaderboard() { return null; }

    /**
     * Getter for games map
     * @return games map
     */
    public Map<Integer, GameEngine> getGames() {
        return games;
    }
}

