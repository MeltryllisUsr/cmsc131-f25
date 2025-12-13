package projects.maze;

/**
 * Single square in a maze grid.
 */
public class Cell {

    private final Coords coords;
    private CellStatus status;

    private final Cell[] neighbors;
    private int neighborCount;

    private boolean explored;

    /**
     * Constructs a new cell with the given coordinates and status.
     *
     * @param coords cell coordinates
     * @param status initial status
     */
    public Cell(Coords coords, CellStatus status) {
        if (coords == null) {
            throw new IllegalArgumentException("coords must not be null");
        }
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.coords = coords;
        this.status = status;
        this.neighbors = new Cell[4];
        this.neighborCount = 0;
        this.explored = false;
    }

    /** Returns the coordinates for this cell. */
    public Coords getCoords() {
        return coords;
    }

    /** Returns the current status of this cell. */
    public CellStatus getStatus() {
        return status;
    }

    /** Sets the status of this cell. */
    public void setStatus(CellStatus status) {
        if (status == null) {
            throw new IllegalArgumentException("status must not be null");
        }
        this.status = status;
    }

    /** Returns whether this cell has been explored by the solver. */
    public boolean isExplored() {
        return explored;
    }

    /** Sets the explored flag used by the solver. */
    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    /**
     * Adds a neighbor to this cell.
     *
     * @param neighbor adjacent cell
     */
    public void addNeighbor(Cell neighbor) {
        if (neighbor == null) { return; }
        if (neighborCount >= neighbors.length) {
            throw new IllegalStateException("Too many neighbors (max 4)");
        }
        neighbors[neighborCount++] = neighbor;
    }

    /**
     * Returns the neighbors array. Only the first {@link #getNeighborCount()} entries are valid.
     */
    public Cell[] getNeighbors() {
        return neighbors;
    }

    /** Returns the number of neighbors currently attached to this cell. */
    public int getNeighborCount() {
        return neighborCount;
    }
}
