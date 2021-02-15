package student.adventure;

public class Direction {
    private String name;
    private String room;

    public Direction(String name, String room) {
        this.name = name;
        this.room = room;
    }

    public String getName() {
        return name;
    }

    public String getRoom() {
        return room;
    }
}
