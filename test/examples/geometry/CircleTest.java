package examples.geometry;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class CircleTest {

    private final double tol = 1e-6;

    @Test
    void testConstructorThrowsOnInvalidRadius() {
        Exception e = assertThrows(
            IllegalArgumentException.class,
            () -> {new Circle(new Point(0.0, 0.0), -1);}
        );
        assertEquals(
            "Parameter radius must be positive.",
            e.getMessage()
        );
    }

    @Test
    void testContains() {
        Point center = new Point(1.0, 1.0);
        double radius = 1.0;
        Circle circ = new Circle(center, radius);
        Point internal = new Point(0.5, 0.5);
        Point onBoundary = new Point(0.0, 1.0);
        Point external = new Point(0.0, 0.0);

        assertEquals(
            true,
            circ.contains(internal),
            "circ must contain an internal point"
        );

        assertEquals(
            true,
            circ.contains(onBoundary),
            "circ must contain a point on boundary"
        );

        assertEquals(
            false,
            circ.contains(external),
            "circ must not contain an external point"
        );
    }


    @Test
    void testGetArea() {
        Point center = new Point(1.0, 1.0);
        double radius = 2.0;
        Circle circ = new Circle(center, radius);

        assertEquals(
            Math.PI * Math.pow(radius, 2.0),
            circ.getArea(), 
            tol,
            "computer area must equal pi * r^2"
        );
    }

} // end: class CircleTest
