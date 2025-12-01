package projects.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility for constructing a Maze instance from a text file.
 */
public class MazeReader {

    private static final int MAX_ROWS = 50;
    private static final int MAX_COLS = 50;

    /**
     * Loads a maze description from {@code filename} and returns a Maze instance.
     *
     * The file is expected to contain tokens separated by whitespace, where each
     * token is one of:
     * <ul>
     *   <li>S – start cell</li>
     *   <li>E – end cell</li>
     *   <li>O – open cell</li>
     *   <li>X – absent (blocked) cell</li>
     * </ul>
     */
    public static Maze load(String filename) {
        Maze maze = new Maze(MAX_ROWS * MAX_COLS);

        try (Scanner scanner = new Scanner(new File(filename))) {
            int row = 0;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) {
                    continue;
                }
                String[] tokens = line.split("\\s+");
                if (tokens.length > MAX_COLS) {
                    throw new IllegalArgumentException("Too many columns in maze file");
                }

                for (int col = 0; col < tokens.length; col++) {
                    String tok = tokens[col];
                    if (tok.isEmpty()) {
                        continue;
                    }
                    char ch = tok.charAt(0);
                    if (ch == 'X') {
                        // no cell at this location
                        continue;
                    }

                    CellStatus status;
                    switch (ch) {
                        case 'S':
                            status = CellStatus.START;
                            break;
                        case 'E':
                            status = CellStatus.END;
                            break;
                        case 'O':
                            status = CellStatus.OPEN;
                            break;
                        default:
                            throw new IllegalArgumentException("Unexpected token '" + ch + "' in maze file");
                    }

                    Coords coords = new Coords(row, col);
                    Cell cell = new Cell(coords, status);
                    boolean inserted = maze.addCell(cell);
                    if (!inserted) {
                        throw new IllegalStateException("Maze grid capacity exceeded");
                    }
                }

                row++;
                if (row > MAX_ROWS) {
                    throw new IllegalArgumentException("Too many rows in maze file");
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Maze file not found: " + filename, e);
        }

        // Once all cells are inserted, compute neighbor lists.
        maze.discoverAndSetupNeighbors();
        return maze;
    }
}
