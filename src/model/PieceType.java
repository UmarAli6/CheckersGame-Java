package model;

/**
 * Enum representing the colors if a piece.
 *
 * @author Rabi_S & Umar_A
 */
public enum PieceType {
    EMPTY(0), RED(1), RED_KING(2), BEIGE(3), BEIGE_KING(4);

    private final int index;

    /**
     * Attaches the index to a enum.
     *
     * @param index an {@code int} representing the index.
     */
    private PieceType(int index) {
        this.index = index;
    }

    /**
     * Returns the index value of a enum.
     *
     * @return an [@code int} representing the index.
     */
    public int getIndex() {
        return this.index;
    }
}
