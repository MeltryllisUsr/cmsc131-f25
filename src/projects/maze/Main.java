package projects.maze;

/**
 * Application entry point for Maze phase 1/2.
 */
public class Main {

    /**
     * Runs the maze loader, solver, and serializer.
     *
     * @param args command-line arguments (expects input filename)
     */
    public static void main(String[] args) {
        Maze maze = MazeReader.load("sample_maze.txt");

        // Phase 2: attempt solve before serializing
        maze.solve();

        maze.serialize("sample_maze_output.txt");
    }
}
