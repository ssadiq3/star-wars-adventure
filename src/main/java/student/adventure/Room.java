package student.adventure;

public class Room {
    private String name;
    private String description;
    private Item[] items;
    private Direction[] directions;

    public Room(String name, String description, Item[] items, Direction[] directions) {
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

    public Item[] getItems() {
        return items;
    }

    public Direction[] getDirections() {
        return directions;
    }
}
