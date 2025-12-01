package projects.maze;

/**
 * Single square in a maze grid.
 * Stores its coordinates, status, exploration flag, and neighbors.
 */
public class Cell {

    private final Coords coords;
    private CellStatus status;
    private boolean explored;
    private Coords[] neighbors;

    /**
     * Constructs a Cell with given coordinates and status.
     *
     * @param coords coordinates of this cell in the grid
     * @param status initial status of the cell
     */
    public Cell(Coords coords, CellStatus status) {
        this.coords = coords;
        this.status = status;
        this.explored = false;
        this.neighbors = new Coords[0];
    }

    /**
     * Constructs a Cell with given coordinates and EMPTY status.
     */
    public Cell(Coords coords) {
        this(coords, CellStatus.EMPTY);
    }

    /** Returns this cell's coordinates. */
    public Coords getCoords() {
        return coords;
    }

    /** Returns this cell's status. */
    public CellStatus getStatus() {
        return status;
    }

    /** Sets this cell's status. */
    public void setStatus(CellStatus status) {
        this.status = status;
    }

    /** Returns whether this cell has been explored. */
    public boolean isExplored() {
        return explored;
    }

    /** Marks this cell as explored or unexplored. */
    public void setExplored(boolean explored) {
        this.explored = explored;
    }

    /** Returns an array of neighbor coordinates. */
    public Coords[] getNeighbors() {
        return neighbors;
    }

    /**
     * Replaces the entire neighbor list.
     *
     * @param neighbors new neighbor coordinates (may be empty, but not null)
     */
    public void setNeighbors(Coords[] neighbors) {
        this.neighbors = (neighbors == null) ? new Coords[0] : neighbors;
    }
}
