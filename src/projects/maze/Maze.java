package projects.maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Maze model backed by a Grid of Cell objects.
 * Provides the queries and neighbor discovery needed for later phases.
 */
public class Maze {

    private final Grid grid;

    /**
     * Constructs a Maze with capacity for at most {@code maxCells} cells.
     *
     * @param maxCells maximum number of cells that can be stored in the grid
     */
    public Maze(int maxCells) {
        this.grid = new Grid(maxCells);
    }

    /** Returns the underlying grid. */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Inserts a cell into the underlying grid.
     *
     * @param cell cell to insert
     * @return true if the cell was inserted, false if the grid is full
     */
    public boolean addCell(Cell cell) {
        return grid.insertCell(cell);
    }

    /** Returns the first cell with status START, or {@code null} if none exists. */
    public Cell getStart() {
        return getFirstCellWithStatus(CellStatus.START);
    }

    /** Returns the first cell with status END, or {@code null} if none exists. */
    public Cell getEnd() {
        return getFirstCellWithStatus(CellStatus.END);
    }

    /**
     * Helper used by {@link #getStart()} and {@link #getEnd()} to
     * locate the first cell with a given status.
     *
     * @param status status to search for
     * @return first cell with the given status, or {@code null} if not found
     */
    private Cell getFirstCellWithStatus(CellStatus status) {
        Cell[] cells = grid.getAllCells();
        for (Cell cell : cells) {
            if (cell.getStatus() == status) {
                return cell;
            }
        }
        return null;
    }

    /**
     * Discovers all 4-way neighbors (up, down, left, right) for every cell
     * in the grid and stores them on each Cell.
     */
    public void discoverAndSetupNeighbors() {
        Cell[] cells = grid.getAllCells();
        for (Cell cell : cells) {
            Coords here = cell.getCoords();
            int row = here.getRow();
            int col = here.getCol();

            // At most four neighbors
            Coords[] tmp = new Coords[4];
            int n = 0;

            // up
            Coords up = new Coords(row - 1, col);
            if (grid.getCell(up) != null) {
                tmp[n++] = up;
            }
            // down
            Coords down = new Coords(row + 1, col);
            if (grid.getCell(down) != null) {
                tmp[n++] = down;
            }
            // left
            Coords left = new Coords(row, col - 1);
            if (grid.getCell(left) != null) {
                tmp[n++] = left;
            }
            // right
            Coords right = new Coords(row, col + 1);
            if (grid.getCell(right) != null) {
                tmp[n++] = right;
            }

            // Trim to actual neighbor count
            Coords[] neighbors = new Coords[n];
            for (int i = 0; i < n; i++) {
                neighbors[i] = tmp[i];
            }
            cell.setNeighbors(neighbors);
        }
    }

    /**
     * Assumes grid cell has a getStatus() method.
     *
     * @param filename output filename
     */
    public void serialize(String filename) {
        Cell[] cells = grid.getAllCells();

        FileWriter writer;
        try {
            writer = new FileWriter(new File(filename));
            // discover dimension of maze's underlying grid
            int maxRow = 0, maxCol = 0;
            int idxCell;
            for (idxCell = 0; idxCell < cells.length; idxCell++) {
                int row = cells[idxCell].getCoords().getRow();
                int col = cells[idxCell].getCoords().getCol();
                if (row > maxRow) { maxRow = row; }
                if (col > maxCol) { maxCol = col; }
            }

            // write cell statuses, using 'X' for absent (inaccessible) cells
            idxCell = 0;
            for (int row = 0; row <= maxRow; row++) {
                for (int col = 0; col <= maxCol; col++) {
                    Cell maybeCell = grid.getCell(
                        new Coords(row, col)
                    );
                    if (maybeCell != null) {
                        writer.write(maybeCell.getStatus().name());
                        idxCell++;
                    } else {
                        writer.write('X');
                    }
                    writer.write(' ');
                }
                writer.write(System.lineSeparator());
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
