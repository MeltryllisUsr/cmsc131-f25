package projects.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class GridTest {

    @Test
    public void testAddAndGetCell() {
        Grid grid = new Grid(10);
        Cell c = new Cell(new Coords(2, 3), CellStatus.O);
        grid.addCell(c);

        assertEquals(1, grid.size());
        assertSame(c, grid.getCell(new Coords(2, 3)));
        assertNull(grid.getCell(new Coords(9, 9)));
    }

    @Test
    public void testGetAllCellsReturnsTrimmedArray() {
        Grid grid = new Grid(5);
        grid.addCell(new Cell(new Coords(0, 0), CellStatus.O));
        grid.addCell(new Cell(new Coords(0, 1), CellStatus.O));

        Cell[] all = grid.getAllCells();
        assertEquals(2, all.length);
    }
}
