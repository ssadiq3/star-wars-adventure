package student.adventure;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javax.validation.constraints.Null;
import java.io.FileNotFoundException;


public class JSONTest {
    @Before
    public void setUp() {
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected = IllegalArgumentException.class)
    public void testNullFile() throws FileNotFoundException {
        Input input = new Input(null);
        thrown.expect(IllegalArgumentException.class);
    }
    @Test(expected = IllegalArgumentException.class)
    public void testEmptyFile() throws FileNotFoundException {
        Input input = new Input("");
        thrown.expect(IllegalArgumentException.class);
    }
    @Test(expected = NullPointerException.class)
    public void testBadSchemaJSON() throws FileNotFoundException {
        Input input = new Input("src/main/resources/JSON_testfiles/bad_schema.json");
        thrown.expect(NullPointerException.class);
    }
    @Test
    public void testValidJSON() throws FileNotFoundException {
        GameEngine game = new GameEngine();
        String examine = "You are in Tatooine" + "\n" +
                "Description: Tatooine is a desert planet isolated from the galaxy"  + "\n" +
                "Your current items: " + "\n" +
                "Items available in the room: keys-Use these keys to fly your spaceship across planets," + "\n" +
                "You can go in these directions: East,";
        assertEquals(examine, game.checkCommand("examine", ""));
    }
}