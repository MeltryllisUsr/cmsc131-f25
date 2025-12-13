package projects.maze;

/**
 * Immutable coordinate pair (row, column) for grid addressing.
 */
public final class Coords {

    private final int row;
    private final int col;

    /**
     * Constructs a coordinate pair.
     *
     * @param row row index (0-based)
     * @param col column index (0-based)
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

    @Override
    public boolean equals(Object other) {
        if (!(other instanceof Coords)) { return false; }
        Coords o = (Coords) other;
        return row == o.row && col == o.col;
    }

    @Override
    public int hashCode() {
        return 31 * row + col;
    }

    @Override
    public String toString() {
        return "(" + row + "," + col + ")";
    }
}
