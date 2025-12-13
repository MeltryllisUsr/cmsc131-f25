package projects.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CoordsTest {

    @Test
    public void testEqualsAndHashCode() {
        Coords a = new Coords(1, 2);
        Coords b = new Coords(1, 2);
        Coords c = new Coords(2, 1);

        assertEquals(a, b);
        assertEquals(a.hashCode(), b.hashCode());
        assertNotEquals(a, c);
    }

    @Test
    public void testToString() {
        Coords a = new Coords(3, 4);
        assertEquals("(3,4)", a.toString());
    }
}
