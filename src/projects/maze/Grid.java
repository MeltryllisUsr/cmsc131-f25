package projects.maze;

/**
 * Simple fixed-capacity collection of {@link Cell} objects.
 *
 * Cells are looked up by linear search on their {@link Coords}.
 */
public class Grid {

    private final Cell[] cells;
    private int count;

    /**
     * Constructs a grid with room for at most {@code maxCells} cells.
     *
     * @param maxCells maximum number of cells
     */
    public Grid(int maxCells) {
        if (maxCells <= 0) {
            throw new IllegalArgumentException("maxCells must be > 0");
        }
        this.cells = new Cell[maxCells];
        this.count = 0;
    }

    /** Returns the number of cells currently stored. */
    public int size() {
        return count;
    }

    /**
     * Adds a cell to the grid.
     *
     * @param cell cell to add
     */
    public void addCell(Cell cell) {
        if (cell == null) {
            throw new IllegalArgumentException("cell must not be null");
        }
        if (count >= cells.length) {
            throw new IllegalStateException("Grid is full");
        }
        cells[count++] = cell;
    }

    /**
     * Returns the cell at the given coordinates, or null if absent.
     *
     * @param coords coordinate lookup key
     * @return matching cell or null
     */
    public Cell getCell(Coords coords) {
        if (coords == null) { return null; }
        for (int i = 0; i < count; i++) {
            if (cells[i].getCoords().equals(coords)) {
                return cells[i];
            }
        }
        return null;
    }

    /**
     * Returns all cells currently in the grid.
     *
     * @return a new array of length {@link #size()}
     */
    public Cell[] getAllCells() {
        Cell[] out = new Cell[count];
        for (int i = 0; i < count; i++) {
            out[i] = cells[i];
        }
        return out;
    }
}
