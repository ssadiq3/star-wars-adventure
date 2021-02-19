package student.adventure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GameEngine {
    protected Layout layout;
    public static Map<String, Room> roomMap;
    public static String currentRoom;
    protected List<Item> inventory;

    public GameEngine(String file) throws FileNotFoundException {
        //must be right file, otherwise need to throw exception
        if (file == null || !(file.equals("src/main/resources/starwars.json"))) {
            throw new IllegalArgumentException();
        }
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        //deserialize into layout class
        layout = gson.fromJson(reader, Layout.class);
        roomMap = new HashMap<>();
        currentRoom = layout.getStartingRoom(); //first room
        inventory = new ArrayList<>(); //starts empty
        //fill roomMap with room name and room object pairs
        for (Room room : layout.getRooms()) {
            roomMap.put(room.getName(), room);
        }
    }

    /**
     * Takes in command keyword and runs appropriate method
     * @param action First input (go, take etc.)
     * @param second Second input if applicable (direction, item)
     * @return String result of appropriate method to send back to console
     */
    public String checkCommand(String action, String second) {
        //check if action is one of following commands
        if (action.equals("go")) {
            //run appropriate method on second input
            return goDirection(second.toLowerCase());
        } if (action.equals("take")) {
            return takeItem(second.toLowerCase());
        } if (action.equals("drop")) {
            return dropItem(second.toLowerCase());
        } if (action.equals("examine")) {
            return examine();
        } if (action.equals("quit") || action.equals("exit")) {
            return action;
        }
        return "I can't complete that action!";
    }

    /**
     * Checks if attempted direction is valid
     * @param direction String name of direction
     * @return true if valid, false if not
     */
    private boolean isValidDirection(String direction) {
        //if direction can be found in directions list of current room
        for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
            if (direction.equals(posDirection.getDirectionName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    /**
     * updates current room if direction was valid
     * @param direction String name of direction
     * @return keyword "went" if successful and console message if not
     */
    private String goDirection(String direction) {
        if (isValidDirection(direction)) {
            //find direction in list of possible directions, then update current room
            for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
                if (direction.equalsIgnoreCase(posDirection.getDirectionName())) {
                    currentRoom = posDirection.getRoom();
                    return "Went";
                }
            }
        }
        return "I can't go " + direction +"!";
    }

    /**
     * Adds to inventory if item is valid
     * @param item String name of item being taken
     * @return key word "Took" if successful or error message if not for console
     */
    private String takeItem(String item) {
        //if room's items list is null or empty return error message for console to print
        if (roomMap.get(currentRoom).getItems() == null || roomMap.get(currentRoom).getItems().size() == 0) {
            return "This room does not have any items!";
        }
        //find and remove item in room's list of items and add to inventory, return succesful keyword
        for (Item posItem : roomMap.get(currentRoom).getItems()) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                inventory.add(posItem);
                roomMap.get(currentRoom).getItems().remove(posItem);
                return "Took";
            }
        }
        //item wasn't found, so return error message for console
        return item + " isn't in the room!";
    }

    /**
     * Removes item from inventory
     * @param item name of item to drop
     * @return keyword "dropped" if successful or error message to console if not
     */
    private String dropItem(String item) {
        // check if inventory is empty, then send error message to console
        if (inventory.size() == 0) {
            return "You have no items to drop!";
        }
        //iterate through inventory to find match with passed item
        for (Item posItem : inventory) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                inventory.remove(posItem);
                //if room did not have items list, need to create list and add this item
                if (roomMap.get(currentRoom).getItems() == null) {
                    ArrayList<Item> items = new ArrayList<>();
                    items.add(posItem);
                    roomMap.get(currentRoom).setItems(items);
                }
                return "Dropped";
            }
        }
        //error message if player has items, but does not have passed item
        return "You don't have " + item + "!";
    }

    /**
     * Returns current state of game that console uses to print on terminal
     * @return String that is printed after every move, and after examine command
     */
    public String examine() {
        //use below print methods to return status of game that console can print
        return "You are in " + currentRoom + "\n" +
                "Description: " + roomMap.get(currentRoom).getDescription() + "\n" +
                "Your current items: " + printItems(inventory) + "\n" +
                "Items available in the room: " + printItemsDescription(roomMap.get(currentRoom).getItems()) + "\n" +
                "You can go in these directions: " + printDirections(roomMap.get(currentRoom).getDirections());
    }

    /**
     * Checks if player has reached final room after "go" command
     * @param result passed result must be "went" keyword
     * @return true if game is over, false otherwise
     */
    public boolean isComplete(String result) {
        //must have "went" keyword if a successful go to other room
        if (result.equals("Went")) {
            //check if current room is equal to the last room
            return currentRoom.equals(layout.getEndingRoom());
        }
        return false;
    }

    /**
     * Returns printable items' names in readable fashion
     * @param list inventory of items
     * @return String item names in inventory
     */
    public String printItems(List<Item> list) {
        String toReturn = "";
        if (list != null) {
            for (Item item : list) {
                toReturn += item.getItemName() + ", ";
            }
        }
        return toReturn.trim();
    }

    /**
     * Returns printable items' names with descriptions for room's items in readable fashion
     * @param list list of items in room
     * @return String item names with description for room's items
     */
    public String printItemsDescription(List<Item> list) {
        String toReturn = "";
        if (list != null) {
            for (Item item : list) {
                toReturn += item.getItemName() + "-" + item.getItemDescription() + ", ";
            }
        }
        return toReturn.trim();
    }

    /**
     * Returns readable direction list using direction names
     * @param list list of directions that player can go in
     * @return String list of direction names allowed for player
     */
    public String printDirections(List<Direction> list) {
        String toReturn = "";
        if (list != null) {
            for (Direction direction : list) {
                toReturn += direction.getDirectionName() + ", ";
            }
        }
        return toReturn.trim();
    }

    /**
     * gets introduction of game
     * @return intro
     */
    public String getIntro() {
        return layout.getIntro();
    }

}
