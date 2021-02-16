package student.adventure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    public static boolean isValidAction(String action, String second) {
        if (action.equals("go") || action.equals("examine") || action.equals("take") || action.equals("drop")
        || action.equals("quit") || action.equals("exit")) {
            return true;
        }
        return false;
    }

    public static boolean isValidDirection(String direction) {
        for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
            if (direction.equals(posDirection)) {
                return true;
            }
        }
        return false;
    }

    private static Enum goDirection(String direction) {
        if (isValidDirection(direction)) {
            for (Direction posDirection : roomMap.get(currentRoom).getDirections()) {
                if (direction.equals(posDirection.getDirectionName())) {
                    currentRoom = posDirection.getRoom();
                    return CheckGame.VALID_GO;
                }
            }
        }
        return CheckGame.INVALID_GO;
    }
}
