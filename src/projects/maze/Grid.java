package projects.maze;

/**
 * Sparse collection of Cell objects for a maze.
 * Internally uses a simple array with linear search by coordinates.
 */
public class Grid {

    private final Cell[] cells;
    private int cellCount;

    /**
     * Constructs a Grid backed by an array of the given maximum size.
     *
     * @param maxCells maximum number of cells that can be stored
     */
    public Grid(int maxCells) {
        this.cells = new Cell[maxCells];
        this.cellCount = 0;
    }

    /**
     * Inserts a cell into the grid.
     *
     * @param cell cell to insert
     * @return true if the cell was inserted, false if the grid is full
     */
    public boolean insertCell(Cell cell) {
        if (cellCount >= cells.length) {
            return false;
        }
        // BUGFIX: use cellCount then increment, do NOT pre-increment
        cells[cellCount] = cell;
        cellCount++;
        return true;
    }

    /**
     * Returns a copy of all cells currently stored in the grid.
     */
    public Cell[] getAllCells() {
        Cell[] result = new Cell[cellCount];
        System.arraycopy(cells, 0, result, 0, cellCount);
        return result;
    }

    /**
     * Returns the cell at the given coordinates, or {@code null} if
     * no cell exists at that position.
     */
    public Cell getCell(Coords coords) {
        if (coords == null) {
            return null;
        }
        for (int i = 0; i < cellCount; i++) {
            if (cells[i].getCoords().equals(coords)) {
                return cells[i];
            }
        }
        return null;
    }

    /**
     * Returns the number of cells stored in the grid.
     */
    public int size() {
        return cellCount;
    }
}
