package projects.maze;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.*;

public class MazeTest {

    /**
     * Helper to create a maze CSV file under data/.
     */
    private Path makeMazeFile(String fileName, String contents) throws IOException {
        Path dir = Paths.get("data");
        Files.createDirectories(dir);
        Path path = dir.resolve(fileName);
        Files.writeString(path, contents);
        return path;
    }

    @Test
    public void testReadParsesGridAndStartEnd() throws IOException {
        String csv =
                "X,X,X\n" +
                "S,O,E\n" +
                "X,X,X\n";

        Path path = makeMazeFile("maze_read_test.csv", csv);

        Maze maze = new Maze();
        maze.read(path.toString());

        String expected =
                "XXX" + System.lineSeparator() +
                "SOE" + System.lineSeparator() +
                "XXX";

        assertEquals(expected, maze.toString());
    }

    @Test
    public void testSolveFindsPathOnSimpleMaze() throws IOException {
        String csv =
                "X,X,X,X\n" +
                "S,O,O,E\n" +
                "X,X,X,X\n";

        Path path = makeMazeFile("maze_solve_simple.csv", csv);

        Maze maze = new Maze();
        maze.read(path.toString());

        assertTrue(maze.solve(), "Maze should be solvable");

        String solved = maze.toString();
        // Path must contain '*' somewhere between S and E
        assertTrue(solved.contains("*"));
        assertTrue(solved.contains("S"));
        assertTrue(solved.contains("E"));
    }

    @Test
    public void testSolveReturnsFalseWhenNoPath() throws IOException {
        String csv =
                "X,X,X,X\n" +
                "S,X,X,E\n" +
                "X,X,X,X\n";

        Path path = makeMazeFile("maze_unsolvable.csv", csv);

        Maze maze = new Maze();
        maze.read(path.toString());

        assertFalse(maze.solve(), "Maze should not be solvable");
    }

    @Test
    public void testReadThrowsWhenMissingStartOrEnd() throws IOException {
        // Missing S
        String csvNoStart =
                "X,X,X\n" +
                "O,O,E\n" +
                "X,X,X\n";

        Path p1 = makeMazeFile("maze_no_start.csv", csvNoStart);

        Maze maze1 = new Maze();
        assertThrows(IllegalArgumentException.class,
                () -> maze1.read(p1.toString()));

        // Missing E
        String csvNoEnd =
                "X,X,X\n" +
                "S,O,O\n" +
                "X,X,X\n";

        Path p2 = makeMazeFile("maze_no_end.csv", csvNoEnd);

        Maze maze2 = new Maze();
        assertThrows(IllegalArgumentException.class,
                () -> maze2.read(p2.toString()));
    }

    @Test
    public void testWriteProducesOutputFile() throws IOException {
        String csv =
                "X,X,X,X\n" +
                "S,O,O,E\n" +
                "X,X,X,X\n";

        Path inPath = makeMazeFile("maze_write_input.csv", csv);

        Maze maze = new Maze();
        maze.read(inPath.toString());
        assertTrue(maze.solve());

        String outName = "maze_write_input.csv.solution.txt";
        Path outPath = Paths.get(outName);

        // Clean up if from previous run
        Files.deleteIfExists(outPath);

        assertTrue(maze.write(outName), "write() should return true");
        assertTrue(Files.exists(outPath), "Solution file should be created");
    }
}
