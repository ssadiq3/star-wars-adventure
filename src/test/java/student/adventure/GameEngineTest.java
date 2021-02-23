package student.adventure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import student.server.GameStatus;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;

public class GameEngineTest {
    private GameEngine game;

    @Before
    public void setUp() throws FileNotFoundException {
        game = new GameEngine();
    }

    @Test
    public void testSpaceInput() {
        assertEquals("I can't complete that action!", game.checkCommand(" ", ""));
    }
    @Test
    public void testValidGoNormal() {
        assertEquals("Went", game.checkCommand("go", "East"));
    }
    @Test
    public void testValidGoWhiteSpace() {
        assertEquals("Went", game.checkCommand("  go", "East  "));
    }
    @Test
    public void testValidGOWithCAPS() {
        assertEquals("Went", game.checkCommand("GO", "eaSt"));
    }
    @Test
    public void testInvalidGoNotDirection() {
        assertEquals("I can't go far!", game.checkCommand("go", "far"));
    }
    @Test
    public void testInvalidGoDirectionNotForRoom() {
        assertEquals("I can't go west!", game.checkCommand("go", "West"));
    }
    @Test
    public void testValidTakeNormal() {
        assertEquals("Took", game.checkCommand("take", "keys"));
    }
    @Test
    public void testValidTakeWhitespace() {
        assertEquals("Took", game.checkCommand("  take ", "keys  "));
    }
    @Test
    public void testValidTakeCAPS() {
        assertEquals("Took", game.checkCommand("TAKE", "keYs"));
    }
    @Test
    public void testValidDropNormal() {
        game.checkCommand("take", "keys");
        assertEquals("Dropped", game.checkCommand("drop", "keys"));
    }
    @Test
    public void testValidDropWhitespace() {
        game.checkCommand("take ", "keys");
        assertEquals("Dropped", game.checkCommand("  drop", "keys "));
    }
    @Test
    public void testValidDropCAPS() {
        game.checkCommand("take ", "keys");
        assertEquals("Dropped", game.checkCommand("dRoP", "KeYs"));
    }
    @Test
    public void testInvalidDropEmptyInventory() {
        assertEquals("You have no items to drop!", game.checkCommand("drop", "keys"));
    }
    @Test
    public void testInvalidDropNotInInventory() {
        game.checkCommand("take", "keys");
        assertEquals("You don't have map!", game.checkCommand("drop", "map"));
    }
    @Test
    public void testDropInRoomWithNoItems() {
        game.checkCommand("take", "keys");
        game.checkCommand("go", "East");
        assertEquals("Dropped", game.checkCommand("drop", "keys"));
    }
    @Test
    public void testInvalidTakeNoItemsInRoom() {
        game.checkCommand("go", "East");
        assertEquals("This room does not have any items!",game.checkCommand("take", "keys"));
    }
    @Test
    public void testInvalidTakeItemNotInRoom() {
        assertEquals("map isn't in the room!", game.checkCommand("take", "map"));
    }
    @Test
    public void testValidExamine() {
        String examine = "You are in Tatooine" + "\n" +
                "Description: Tatooine is a desert planet isolated from the galaxy"  + "\n" +
                "Your current items: " + "\n" +
                "Items available in the room: keys-Use these keys to fly your spaceship across planets," + "\n" +
                "You can go in these directions: East,";
        assertEquals(examine, game.checkCommand("examine", ""));
    }
    @Test
    public void testExitCommand() {
        assertEquals("exit", game.checkCommand("exit", ""));
    }
    @Test
    public void testQuitCommand() {
        assertEquals("quit", game.checkCommand("quit", ""));
    }
}
