package projects.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testConstructorAndGetters() {
        Coords coords = new Coords(0, 1);
        Cell cell = new Cell(coords, CellStatus.O);

        assertEquals(coords, cell.getCoords());
        assertEquals(CellStatus.O, cell.getStatus());
        assertFalse(cell.isExplored());
        assertEquals(0, cell.getNeighborCount());
    }

    @Test
    public void testSetStatusAndExplored() {
        Cell cell = new Cell(new Coords(0, 0), CellStatus.O);

        cell.setStatus(CellStatus.S);
        assertEquals(CellStatus.S, cell.getStatus());

        cell.setExplored(true);
        assertTrue(cell.isExplored());
    }

    @Test
    public void testAddNeighbor() {
        Cell a = new Cell(new Coords(0, 0), CellStatus.O);
        Cell b = new Cell(new Coords(0, 1), CellStatus.O);

        a.addNeighbor(b);
        assertEquals(1, a.getNeighborCount());
        assertSame(b, a.getNeighbors()[0]);
    }
}
