package projects.maze;

import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Program entry point for the maze solver.
 */
public class Main {

    /**
     * Reads a maze file, solves it, and writes the output if a solution exists.
     */
    public static void main(String[] args) {
        if (args.length < 1) {
            System.out.println("Usage: java projects.maze.Main <input-csv-file>");
            return;
        }

        String inputFile = args[0];

        try {
            Maze maze = new Maze();
            maze.read(inputFile);

            boolean solved = maze.solve();
            if (solved) {
                Path p = Paths.get(inputFile);
                String base = p.getFileName().toString();
                String outName = base + ".solution.txt";
                boolean ok = maze.write(outName);
                if (!ok) {
                    System.out.println("Failed to write solution file.");
                }
            } else {
                System.out.println("No solution exists for maze: " + inputFile);
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
