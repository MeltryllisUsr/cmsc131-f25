package projects.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class CellTest {

    @Test
    public void testConstructorAndGetters() {
        Coords coords = new Coords(2, 3);
        Cell cell = new Cell(coords, CellStatus.OPEN);

        assertEquals(2, cell.getCoords().getRow());
        assertEquals(3, cell.getCoords().getCol());
        assertEquals(CellStatus.OPEN, cell.getStatus());
        assertFalse(cell.isExplored());
        assertNotNull(cell.getNeighbors());
        assertEquals(0, cell.getNeighbors().length);
    }

    @Test
    public void testSetStatusAndExplored() {
        Coords coords = new Coords(0, 0);
        Cell cell = new Cell(coords, CellStatus.EMPTY);

        cell.setStatus(CellStatus.START);
        assertEquals(CellStatus.START, cell.getStatus());

        cell.setExplored(true);
        assertTrue(cell.isExplored());

        cell.setExplored(false);
        assertFalse(cell.isExplored());
    }

    @Test
    public void testSetNeighbors() {
        Coords coords = new Coords(1, 1);
        Cell cell = new Cell(coords, CellStatus.OPEN);

        Coords n1 = new Coords(0, 1);
        Coords n2 = new Coords(2, 1);

        cell.setNeighbors(new Coords[]{n1, n2});

        Coords[] neighbors = cell.getNeighbors();
        assertEquals(2, neighbors.length);
        assertEquals(0, neighbors[0].getRow());
        assertEquals(1, neighbors[0].getCol());
        assertEquals(2, neighbors[1].getRow());
        assertEquals(1, neighbors[1].getCol());
    }
}
