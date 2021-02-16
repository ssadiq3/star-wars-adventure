package student.adventure;

import java.util.Map;

public class Direction {
    private String directionName;
    private String room;

    public Direction(String name, String room) {
        directionName = name;
        this.room = room;
    }

    public String getDirectionName() {
        return directionName;
    }

    public String getRoom() {
        return room;
    }
}
