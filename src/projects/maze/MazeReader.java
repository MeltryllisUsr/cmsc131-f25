package projects.maze;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * Utility for constructing a {@link Maze} instance from a maze text file.
 */
public class MazeReader {

    private static final int MAX_ROWS = 50;
    private static final int MAX_COLS = 50;

    /**
     * Loads a maze description from {@code filename} and returns a Maze instance.
     *
     * Input format:
     * - Each line is a row of tokens.
     * - Tokens may be separated by commas or whitespace.
     * - Valid cell tokens: S, E, O, X, H.
     *
     * @param filename maze file name
     * @return populated Maze
     */
    public static Maze load(String filename) {
        Maze maze = new Maze(MAX_ROWS * MAX_COLS);

        int row = 0;
        try (Scanner scanner = new Scanner(new File(filename))) {
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine().trim();
                if (line.isEmpty()) { continue; }

                String[] tokens;
                if (line.contains(",")) {
                    tokens = line.split(",");
                } else {
                    tokens = line.split("\\s+");
                }

                if (tokens.length > MAX_COLS) {
                    throw new IllegalArgumentException("Too many columns in maze file");
                }

                for (int col = 0; col < tokens.length; col++) {
                    String t = tokens[col].trim();
                    if (t.isEmpty()) { continue; }
                    if (t.length() != 1) {
                        throw new IllegalArgumentException("Invalid token: " + t);
                    }
                    char ch = t.charAt(0);
                    CellStatus status = CellStatus.fromChar(ch);
                    Cell cell = new Cell(new Coords(row, col), status);
                    maze.getGrid().addCell(cell);
                }

                row++;
                if (row > MAX_ROWS) {
                    throw new IllegalArgumentException("Too many rows in maze file");
                }
            }
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("Maze file not found: " + filename, e);
        }

        maze.discoverAndSetupNeighbors();
        return maze;
    }
}
