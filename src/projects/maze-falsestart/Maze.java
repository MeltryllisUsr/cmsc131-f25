package projects.maze;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents a maze and provides methods to load, solve, and write it.
 */
public class Maze {

    private char[][] grid;
    private int rows = 0;
    private int cols = 0;
    private int startRow = -1, startCol = -1;
    private int endRow = -1, endCol = -1;

    private static final int MAX_SIZE = 50;

    /**
     * Reads a CSV-formatted maze file into the internal grid.
     */
    public void read(String filename) throws IOException {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        if (lines.isEmpty()) {
            throw new IllegalArgumentException("Input file is empty: " + filename);
        }

        List<char[]> rowsList = new ArrayList<>();

        for (String line : lines) {
            String[] tokens = line.split(",");
            if (cols == 0) {
                cols = tokens.length;
                if (cols > MAX_SIZE) {
                    throw new IllegalArgumentException("Maze exceeds maximum width of " + MAX_SIZE);
                }
            } else if (tokens.length != cols) {
                throw new IllegalArgumentException("All rows must have the same number of columns");
            }

            char[] rowChars = new char[cols];
            for (int c = 0; c < cols; c++) {
                String tok = tokens[c].trim();
                if (tok.length() != 1) {
                    throw new IllegalArgumentException("Invalid token: '" + tok + "'");
                }

                char ch = Character.toUpperCase(tok.charAt(0));
                if (ch != 'S' && ch != 'E' && ch != 'O' && ch != 'X') {
                    throw new IllegalArgumentException("Unexpected cell character: " + ch);
                }

                rowChars[c] = ch;
            }

            rowsList.add(rowChars);
            if (rowsList.size() > MAX_SIZE) {
                throw new IllegalArgumentException("Maze exceeds maximum height of " + MAX_SIZE);
            }
        }

        rows = rowsList.size();
        grid = new char[rows][cols];

        for (int r = 0; r < rows; r++) {
            char[] row = rowsList.get(r);
            for (int c = 0; c < cols; c++) {
                grid[r][c] = row[c];

                if (grid[r][c] == 'S') {
                    startRow = r;
                    startCol = c;
                } else if (grid[r][c] == 'E') {
                    endRow = r;
                    endCol = c;
                }
            }
        }

        if (startRow < 0 || endRow < 0) {
            throw new IllegalArgumentException("Maze must contain one S and one E.");
        }
    }

    /**
     * Attempts to solve the maze and returns true if a path is found.
     */
    public boolean solve() {
        return solveFrom(startRow, startCol);
    }

    /**
     * Recursive helper that searches for a path from the given position.
     */
    private boolean solveFrom(int r, int c) {
        if (r < 0 || r >= rows || c < 0 || c >= cols) return false;
        if (r == endRow && c == endCol) return true;

        char cur = grid[r][c];

        if (cur == 'X' || cur == '*') return false;

        boolean isStart = (cur == 'S');
        if (!isStart) grid[r][c] = '*';

        if (solveFrom(r - 1, c)) return true;
        if (solveFrom(r + 1, c)) return true;
        if (solveFrom(r, c - 1)) return true;
        if (solveFrom(r, c + 1)) return true;

        if (!isStart) grid[r][c] = 'O';
        return false;
    }

    /**
     * Writes the current maze grid to a text file and prints it to standard output.
     */
    public boolean write(String outFilename) {
        Path outPath = Paths.get(outFilename);
        try (BufferedWriter writer = Files.newBufferedWriter(outPath)) {
            for (int r = 0; r < rows; r++) {
                writer.write(new String(grid[r]));
                writer.newLine();
            }
            writer.flush();

            System.out.println("----- Maze output (" + outFilename + ") -----");
            for (int r = 0; r < rows; r++) {
                System.out.println(new String(grid[r]));
            }
            System.out.println("-------------------------------------------");

            return true;
        } catch (IOException e) {
            System.err.println("Error writing maze to " + outFilename + ": " + e.getMessage());
            return false;
        }
    }

    /**
     * Returns a string representation of the maze grid.
     */
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        for (int r = 0; r < rows; r++) {
            sb.append(new String(grid[r]));
            if (r < rows - 1) sb.append(System.lineSeparator());
        }
        return sb.toString();
    }
}
