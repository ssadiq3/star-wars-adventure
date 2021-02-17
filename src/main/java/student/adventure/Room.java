package student.adventure;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class Room {
    private String name;
    private String description;
    private ArrayList<Item> items;
    private ArrayList<Direction> directions;

    public Room(String name, String description, ArrayList<Item> items, ArrayList<Direction> directions) {
        this.name = name;
        this.description = description;
        this.items = items;
        this.directions = directions;
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
}
