package projects.maze;

import org.junit.jupiter.api.Test;

import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    @Test
    public void testGetStartAndEnd() {
        Maze maze = new Maze(9);
        maze.getGrid().addCell(new Cell(new Coords(0, 0), CellStatus.S));
        maze.getGrid().addCell(new Cell(new Coords(0, 1), CellStatus.O));
        maze.getGrid().addCell(new Cell(new Coords(0, 2), CellStatus.E));
        maze.discoverAndSetupNeighbors();

        assertNotNull(maze.getStart());
        assertNotNull(maze.getEnd());
        assertEquals(CellStatus.S, maze.getStart().getStatus());
        assertEquals(CellStatus.E, maze.getEnd().getStatus());
    }

    @Test
    public void testSolveFindsPathInSimpleLine() {
        Maze maze = new Maze(9);
        maze.getGrid().addCell(new Cell(new Coords(0, 0), CellStatus.S));
        maze.getGrid().addCell(new Cell(new Coords(0, 1), CellStatus.O));
        maze.getGrid().addCell(new Cell(new Coords(0, 2), CellStatus.E));
        maze.discoverAndSetupNeighbors();

        assertTrue(maze.solve());

        Cell mid = maze.getGrid().getCell(new Coords(0, 1));
        assertEquals(CellStatus.P, mid.getStatus());
    }

    @Test
    public void testSolveReturnsFalseWhenBlocked() {
        Maze maze = new Maze(9);
        maze.getGrid().addCell(new Cell(new Coords(0, 0), CellStatus.S));
        maze.getGrid().addCell(new Cell(new Coords(0, 1), CellStatus.X));
        maze.getGrid().addCell(new Cell(new Coords(0, 2), CellStatus.E));
        maze.discoverAndSetupNeighbors();

        assertFalse(maze.solve());
    }

    @Test
    public void testSerializeWritesExpectedFormat() throws Exception {
        Maze maze = new Maze(4);
        maze.getGrid().addCell(new Cell(new Coords(0, 0), CellStatus.S));
        maze.getGrid().addCell(new Cell(new Coords(0, 1), CellStatus.E));
        maze.discoverAndSetupNeighbors();

        Path out = Files.createTempFile("maze", ".txt");
        maze.serialize(out.toString());

        String text = Files.readString(out);
        assertTrue(text.contains("S "));
        assertTrue(text.contains("E "));
    }
}
