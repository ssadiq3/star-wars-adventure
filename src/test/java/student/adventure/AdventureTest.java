package student.adventure;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import student.server.AdventureException;
import student.server.Command;
import student.server.StarWarsAdventure;

import java.io.FileNotFoundException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;

public class AdventureTest {
    private StarWarsAdventure adventure;

    @Before
    public void setUp() {
        adventure = new StarWarsAdventure();
        adventure.reset();
    }

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test(expected =  AdventureException.class)
    public void testNegativeID() throws FileNotFoundException, AdventureException {
        adventure.newGame();
        adventure.getGame(-1);
        thrown.expect(AdventureException.class);
    }

    @Test(expected = AdventureException.class)
    public void testProperReset() throws FileNotFoundException, AdventureException {
        adventure.newGame();
        adventure.reset();
        adventure.getGame(0);
        thrown.expect(AdventureException.class);
    }

    @Test
    public void testValidCommand() throws FileNotFoundException, AdventureException {
        Command command = new Command("go", "East");
        adventure.newGame();
        adventure.getGame(0);
        adventure.executeCommand(0, command);
        adventure.getGame(0);
        assertEquals("Jabba's Cave", adventure.getGames().get(0).getCurrentRoom().getName());
    }

    @Test(expected = AdventureException.class)
    public void testDestroyGameNegativeID() throws AdventureException, FileNotFoundException {
        adventure.newGame();
        adventure.destroyGame(-1);
        thrown.expect(AdventureException.class);
    }

    @Test
    public void testDestroyGameIDWithNoInstance() throws FileNotFoundException, AdventureException {
        adventure.newGame();
        assertFalse(adventure.destroyGame(3));
    }
}
