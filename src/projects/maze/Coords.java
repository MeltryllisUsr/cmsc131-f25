package projects.maze;

/**
 * Immutable row/column coordinate pair in the maze grid.
 */
public class Coords {

    private final int row;
    private final int col;

    /**
     * Constructs a coordinate with the given row and column.
     *
     * @param row zero-based row index
     * @param col zero-based column index
     */
    public Coords(int row, int col) {
        this.row = row;
        this.col = col;
    }

    /** Returns the row index. */
    public int getRow() {
        return row;
    }

    /** Returns the column index. */
    public int getCol() {
        return col;
    }

    /**
     * Compares this coordinate to another {@link Coords}.
     */
    public boolean equals(Coords other) {
        if (other == null) {
            return false;
        }
        return this.row == other.row && this.col == other.col;
    }

    /**
     * Simple hash-code based on row and column.
     */
    public int hashCode() {
        return row * 31 + col;
    }
}
