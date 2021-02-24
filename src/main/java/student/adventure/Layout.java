package student.adventure;

public class Layout {
    private String startingRoom;
    private String endingRoom;
    private String intro;
    private Room[] rooms;
    private String videoURL;

    public Layout(String intro, String startingRoom, String endingRoom, Room[] rooms, String videoURL) {
        this.intro = intro;
        this.startingRoom = startingRoom;
        this.endingRoom = endingRoom;
        this.rooms = rooms;
        this.videoURL = videoURL;
    }

    public String getStartingRoom() {
        return startingRoom;
    }

    public String getEndingRoom() {
        return endingRoom;
    }

    public Room[] getRooms() {
        return rooms;
    }

    public String getIntro() {
        return intro;
    }

    public String getVideoURL() {
        return videoURL;
    }

}
