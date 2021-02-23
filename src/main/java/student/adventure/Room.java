package student.adventure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private ArrayList<Item> items;
    private ArrayList<Direction> directions;
    private String url;

    public Room(String name, String description, ArrayList<Item> items, ArrayList<Direction> directions) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.directions = directions;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public ArrayList<Item> getItems() {
        return items;
    }

    public void setItems(ArrayList<Item> setItems) {
        items = setItems;
    }

    public ArrayList<Direction> getDirections() {
        return directions;
    }

    public List<String> getDirectionNames() {
        List<String> directionNames = new ArrayList<>();
        for (Direction direction : directions) {
            directionNames.add(direction.getDirectionName());
        }
        return directionNames;
    }

    public List<String> getItemNames() {
        List<String> itemNames = new ArrayList<>();
        for (Item item : items) {
            itemNames.add(item.getItemName());
        }
        return itemNames;
    }

    public String getUrl() { return url;}
}
