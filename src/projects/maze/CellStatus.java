package projects.maze;

/**
 * Status values for a maze cell.
 */
public enum CellStatus {
    /** Wall / blocked cell. */
    X('X'),
    /** Open / traversable cell. */
    O('O'),
    /** Start cell. */
    S('S'),
    /** End cell. */
    E('E'),
    /** Path marker used by the solver. */
    P('*');

    private final char symbol;

    CellStatus(char symbol) {
        this.symbol = symbol;
    }

    /** Returns the single-character symbol used when serializing the maze. */
    public char getSymbol() {
        return symbol;
    }

    /**
     * Converts an input character to a CellStatus.
     * Accepts: X, O, S, E, H (treated as X).
     *
     * @param ch input character
     * @return corresponding CellStatus
     */
    public static CellStatus fromChar(char ch) {
        char up = Character.toUpperCase(ch);
        return switch (up) {
            case 'X' -> X;
            case 'H' -> X;
            case 'O' -> O;
            case 'S' -> S;
            case 'E' -> E;
            case '*' -> P;
            default -> throw new IllegalArgumentException("Invalid cell character: " + ch);
        };
    }
}
