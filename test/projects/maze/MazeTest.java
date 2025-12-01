package projects.maze;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    @Test
    public void testGetStartAndEnd() {
        Maze maze = new Maze(4);

        Cell start = new Cell(new Coords(0, 0), CellStatus.START);
        Cell middle = new Cell(new Coords(0, 1), CellStatus.OPEN);
        Cell end = new Cell(new Coords(1, 1), CellStatus.END);

        maze.addCell(start);
        maze.addCell(middle);
        maze.addCell(end);

        assertSame(start, maze.getStart());
        assertSame(end, maze.getEnd());
    }

    @Test
    public void testDiscoverAndSetupNeighbors() {
        Maze maze = new Maze(4);

        Cell c00 = new Cell(new Coords(0, 0), CellStatus.START);
        Cell c01 = new Cell(new Coords(0, 1), CellStatus.OPEN);
        Cell c10 = new Cell(new Coords(1, 0), CellStatus.OPEN);
        Cell c11 = new Cell(new Coords(1, 1), CellStatus.END);

        maze.addCell(c00);
        maze.addCell(c01);
        maze.addCell(c10);
        maze.addCell(c11);

        maze.discoverAndSetupNeighbors();

        // c00 neighbors: (0,1) and (1,0)
        Coords[] n00 = c00.getNeighbors();
        assertEquals(2, n00.length);

        // Use a small helper to make assertions easier
        assertTrue(containsCoords(n00, 0, 1));
        assertTrue(containsCoords(n00, 1, 0));

        // c11 neighbors: (0,1) and (1,0)
        Coords[] n11 = c11.getNeighbors();
        assertEquals(2, n11.length);
        assertTrue(containsCoords(n11, 0, 1));
        assertTrue(containsCoords(n11, 1, 0));

        // c01 neighbors: (0,0) and (1,1)
        Coords[] n01 = c01.getNeighbors();
        assertEquals(2, n01.length);
        assertTrue(containsCoords(n01, 0, 0));
        assertTrue(containsCoords(n01, 1, 1));

        // c10 neighbors: (0,0) and (1,1)
        Coords[] n10 = c10.getNeighbors();
        assertEquals(2, n10.length);
        assertTrue(containsCoords(n10, 0, 0));
        assertTrue(containsCoords(n10, 1, 1));
    }

    private boolean containsCoords(Coords[] arr, int row, int col) {
        for (Coords c : arr) {
            if (c.getRow() == row && c.getCol() == col) {
                return true;
            }
        }
        return false;
    }
}
