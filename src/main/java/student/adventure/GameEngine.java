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
        if (file == null || !(file.equals("src/main/resources/starwars.json"))) {
            throw new IllegalArgumentException();
        }
        Gson gson = new Gson();
        JsonReader reader = new JsonReader(new FileReader(file));
        layout = gson.fromJson(reader, Layout.class);
        roomMap = new HashMap<>();
        currentRoom = "Tatooine";
        inventory = new ArrayList<>();
        for (Room room : layout.getRooms()) {
            roomMap.put(room.getName(), room);
        }
    }

    public String checkCommand(String action, String second) {
        if (action.equals("go")) {
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

    private boolean isValidDirection(String direction) {
        for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
            if (direction.equals(posDirection.getDirectionName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private String goDirection(String direction) {
        if (isValidDirection(direction)) {
            for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
                if (direction.equalsIgnoreCase(posDirection.getDirectionName())) {
                    currentRoom = posDirection.getRoom();
                    return "Went";
                }
            }
        }
        return "I can't go " + direction +"!";
    }
    private String takeItem(String item) {
        if (roomMap.get(currentRoom).getItems() == null || roomMap.get(currentRoom).getItems().size() == 0) {
            return "This room does not have any items!";
        }
        for (Item posItem : roomMap.get(currentRoom).getItems()) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                inventory.add(posItem);
                roomMap.get(currentRoom).getItems().remove(posItem);
                return "Took";
            }
        }
        return item + " isn't in the room!";
    }
    private String dropItem(String item) {
        if (inventory.size() == 0) {
            return "You have no items to drop!";
        }
        for (Item posItem : inventory) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                inventory.remove(posItem);
                roomMap.get(currentRoom).getItems().add(posItem);
                return "Dropped";
            }
        }
        return "You don't have " + item + "!";
    }
    public String examine() {
        return "You are in " + roomMap.get(currentRoom).getName() + "\n" +
                "Description: " + roomMap.get(currentRoom).getDescription() + "\n" +
                "Your current items: " + printItems(inventory) + "\n" +
                "Items available in the room: " + printItemsDescription(roomMap.get(currentRoom).getItems()) + "\n" +
                "You can go in these directions: " + printDirections(roomMap.get(currentRoom).getDirections());
    }
    public boolean isComplete(String result) {
        if (result.equals("Went")) {
            if (roomMap.get(currentRoom).getName().equals("Jedi Temple")) {
                return true;
            }
        }
        return false;
    }
    public String printItems(List<Item> list) {
        String toReturn = "";
        if (list != null) {
            for (Item item : list) {
                toReturn += item.getItemName() + ", ";
            }
        }
        return toReturn.trim();
    }
    public String printItemsDescription(List<Item> list) {
        String toReturn = "";
        if (list != null) {
            for (Item item : list) {
                toReturn += item.getItemName() + "-" + item.getItemDescription() + ", ";
            }
        }
        return toReturn.trim();
    }
    public String printDirections(List<Direction> list) {
        String toReturn = "";
        if (list != null) {
            for (Direction direction : list) {
                toReturn += direction.getDirectionName() + ", ";
            }
        }
        return toReturn.trim();
    }
    public String getIntro() {
        return layout.getIntro();
    }

}
