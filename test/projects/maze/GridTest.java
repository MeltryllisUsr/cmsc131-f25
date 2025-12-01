package projects.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    @Test
    public void testInsertCellAndGetAllCells() {
        Grid grid = new Grid(3);

        Cell c0 = new Cell(new Coords(0, 0), CellStatus.OPEN);
        Cell c1 = new Cell(new Coords(0, 1), CellStatus.START);
        Cell c2 = new Cell(new Coords(1, 0), CellStatus.END);

        assertTrue(grid.insertCell(c0));
        assertTrue(grid.insertCell(c1));
        assertTrue(grid.insertCell(c2));

        // Grid is now full, further insert should fail
        assertFalse(grid.insertCell(new Cell(new Coords(2, 2), CellStatus.OPEN)));

        Cell[] cells = grid.getAllCells();
        assertEquals(3, cells.length);

        // Order should match insertion order
        assertSame(c0, cells[0]);
        assertSame(c1, cells[1]);
        assertSame(c2, cells[2]);
    }

    @Test
    public void testGetCellByCoords() {
        Grid grid = new Grid(5);

        Cell c0 = new Cell(new Coords(2, 3), CellStatus.OPEN);
        Cell c1 = new Cell(new Coords(0, 0), CellStatus.START);

        grid.insertCell(c0);
        grid.insertCell(c1);

        Cell found1 = grid.getCell(new Coords(2, 3));
        assertNotNull(found1);
        assertSame(c0, found1);

        Cell found2 = grid.getCell(new Coords(0, 0));
        assertNotNull(found2);
        assertSame(c1, found2);

        // Coords that have no cell should return null
        assertNull(grid.getCell(new Coords(9, 9)));
    }
}
