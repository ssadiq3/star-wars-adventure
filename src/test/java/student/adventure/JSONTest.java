package student.adventure;

import static org.junit.Assert.assertThat;

import org.hamcrest.CoreMatchers;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

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
}