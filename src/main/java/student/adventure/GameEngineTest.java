package student.adventure;

import com.google.gson.Gson;
import com.google.gson.stream.JsonReader;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.FileNotFoundException;
import java.io.FileReader;

import static org.junit.Assert.assertEquals;
import static student.adventure.GameEngine.currentRoom;

public class GameEngineTest {
    private GameEngine game;
    @Before
    public void setUp() throws FileNotFoundException {
        game = new GameEngine("src/main/resources/starwars.json");
    }
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void testNullFile() throws FileNotFoundException {
        game = new GameEngine(null);
        thrown.expect(IllegalArgumentException.class);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() throws FileNotFoundException {
        game = new GameEngine("");
        thrown.expect(IllegalArgumentException.class);
    }
    @Test
    public void testValidGo() {
        for (Direction direction : game.roomMap.get(currentRoom).getDirections()) {
            System.out.println(direction.getDirectionName());
        }
        assertEquals("Went", game.checkCommand("go", "East"));
    }
    @Test
    public void testInValidGo() {
        assertEquals("I can't go far!", game.checkCommand("go", "far"));
    }
    


}
