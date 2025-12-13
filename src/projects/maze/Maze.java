package projects.maze;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

/**
 * Maze model built on top of the provided {@link Grid} and {@link Cell} types.
 */
public class Maze {

    private final Grid grid;

    /**
     * Constructs a maze with capacity for at most {@code maxCells} cells.
     *
     * @param maxCells maximum number of cells
     */
    public Maze(int maxCells) {
        grid = new Grid(maxCells);
    }

    /** Returns the underlying grid instance. */
    public Grid getGrid() {
        return grid;
    }

    /**
     * Returns the first cell in the grid with the given status, or null if none exists.
     *
     * @param status the status to search for
     * @return first matching cell or null
     */
    public Cell getFirstCellWithStatus(CellStatus status) {
        if (status == null) { return null; }
        Cell[] cells = grid.getAllCells();
        for (Cell cell : cells) {
            if (cell.getStatus() == status) {
                return cell;
            }
        }
        return null;
    }

    /** Returns the start cell (status S). */
    public Cell getStart() {
        return getFirstCellWithStatus(CellStatus.S);
    }

    /** Returns the end cell (status E). */
    public Cell getEnd() {
        return getFirstCellWithStatus(CellStatus.E);
    }

    /**
     * Populates each cell's neighbor list.
     *
     * Neighbors are added in the order: North, East, South, West.
     * Only existing cells are added. Walls (status X) are still "cells" in the grid
     * but the solver will not traverse them.
     */
    public void discoverAndSetupNeighbors() {
        Cell[] cells = grid.getAllCells();
        for (Cell cell : cells) {
            Coords c = cell.getCoords();
            int r = c.getRow();
            int col = c.getCol();

            Cell north = grid.getCell(new Coords(r - 1, col));
            Cell east  = grid.getCell(new Coords(r, col + 1));
            Cell south = grid.getCell(new Coords(r + 1, col));
            Cell west  = grid.getCell(new Coords(r, col - 1));

            if (north != null) cell.addNeighbor(north);
            if (east  != null) cell.addNeighbor(east);
            if (south != null) cell.addNeighbor(south);
            if (west  != null) cell.addNeighbor(west);
        }
    }

    /**
     * Attempts to solve the maze by depth-first traversal with backtracking.
     *
     * @return true if a path from S to E exists
     */
    public boolean solve() {
        // reset explored flags (allow re-solving in tests)
        for (Cell c : grid.getAllCells()) {
            c.setExplored(false);
            if (c.getStatus() == CellStatus.P) {
                c.setStatus(CellStatus.O);
            }
        }

        Cell startCell = getStart();
        if (startCell == null) {
            throw new IllegalStateException("Maze has no start cell (S)");
        }
        return explorePath(startCell);
    }

    /**
     * Depth-first traversal helper.
     *
     * @param currentCell current cell being explored
     * @return true if end was found from this cell
     */
    private boolean explorePath(Cell currentCell) {
        if (currentCell == null) { return false; }

        if (currentCell.isExplored()) { return false; }
        currentCell.setExplored(true);

        if (currentCell.getStatus() == CellStatus.X) {
            return false;
        }
        if (currentCell.getStatus() == CellStatus.E) {
            return true;
        }

        boolean isStart = currentCell.getStatus() == CellStatus.S;
        if (!isStart && currentCell.getStatus() == CellStatus.O) {
            currentCell.setStatus(CellStatus.P);
        }

        Cell[] neighbors = currentCell.getNeighbors();
        int nCount = currentCell.getNeighborCount();
        for (int i = 0; i < nCount; i++) {
            Cell next = neighbors[i];
            if (next == null) { continue; }
            if (next.isExplored()) { continue; }

            if (explorePath(next)) {
                return true;
            }
        }

        if (!isStart && currentCell.getStatus() == CellStatus.P) {
            currentCell.setStatus(CellStatus.O);
        }
        return false;
    }

    /**
     * Writes the maze to an output file using space-separated symbols.
     * Cells absent from the grid are written as 'X'.
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
            for (Cell cell : cells) {
                int row = cell.getCoords().getRow();
                int col = cell.getCoords().getCol();
                if (row > maxRow) { maxRow = row; }
                if (col > maxCol) { maxCol = col; }
            }

            // write cell statuses, using 'X' for absent (inaccessible) cells
            for (int row = 0; row <= maxRow; row++) {
                for (int col = 0; col <= maxCol; col++) {
                    Cell maybeCell = grid.getCell(new Coords(row, col));
                    if (maybeCell != null) {
                        writer.write(maybeCell.getStatus().getSymbol());
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
