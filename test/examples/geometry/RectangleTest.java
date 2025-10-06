package examples.geometry;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class RectangleTest {

    private Point p1, p2;
    private Rectangle rect;
    private final double tol = 1e-6;

    @BeforeEach
    void setup() {
        p1 = new Point(0.0, 0.0);
        p2 = new Point(1.0, 1.0);
        rect = new Rectangle(p1, p2);
    }

    @Test
    void testConstructorThrowsOnInvalidInput() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> {new Rectangle(p2, p1);}
        );

        assertEquals(
            "Points ll and ur are incorrectly oriented.",
            e.getMessage()
        );
    }

    @Test
    void testContains() {
        Point internal = new Point(0.5, 0.5);
        assertEquals(
            true,
            rect.contains(internal)
        );

        Point external = new Point(2.0, 2.0);
        assertEquals(
            false,
            rect.contains(external)
        );

        Point onBoundary = p1; // aliasing
        assertEquals(
            true,
            rect.contains(onBoundary)
        );
    }

    @Test
    void testArea() {
        assertEquals(
            1.0,
            rect.getArea(),
            tol
        );
    }

} // end class RectangleTest
