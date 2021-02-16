package student.adventure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.*;

public class GameEngine {
    private Layout layout;
    private static Map<String, Room> roomMap;
    private static String currentRoom;
    private List<Item> inventory;

    public GameEngine(String file) throws FileNotFoundException {
        if (!(file.equals("src/main/resources/starwars.json"))) {
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

    private boolean isValidAction(String action, String second) {
        if (action.equals("go") || action.equals("examine") || action.equals("take") || action.equals("drop")
        || action.equals("quit") || action.equals("exit")) {
            return true;
        }
        return false;
    }

    private boolean isValidDirection(String direction) {
        for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
            if (direction.equals(posDirection.getDirectionName().toLowerCase())) {
                return true;
            }
        }
        return false;
    }

    private Enum goDirection(String direction) {
        if (isValidDirection(direction)) {
            for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
                if (direction.equalsIgnoreCase(posDirection.getDirectionName())) {
                    currentRoom = posDirection.getRoom();
                    return CheckGame.VALID_GO;
                }
            }
        }
        return CheckGame.INVALID_GO;
    }
    private boolean isValidTake(String item) {
        for (Item posItem : roomMap.get(currentRoom).getItems()) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                return true;
            }
        }
        return false;
    }
    private Enum takeItem(String item) {
        for (Item posItem : roomMap.get(currentRoom).getItems()) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                inventory.add(posItem);
                roomMap.get(currentRoom).getItems().remove(posItem);
                return CheckGame.VALID_TAKE;
            }
        }
        return CheckGame.INVALID_TAKE;
    }
    private boolean isValidDrop(String item) {
        for (Item posItem : inventory) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                return true;
            }
        }
        return false;
    }
    private Enum dropItem(String item) {
        for (Item posItem : inventory) {
            if (item.equalsIgnoreCase(posItem.getItemName())) {
                inventory.remove(posItem);
                roomMap.get(currentRoom).getItems().add(posItem);
                return CheckGame.VALID_DROP;
            }
        }
        return CheckGame.INVALID_DROP;
    }

}
