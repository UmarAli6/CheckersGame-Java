package model;

/**
 * Class representing a Piece.
 *
 * @author Rabi_S & Umar_A
 */
public class Piece {

    private PieceType type;

    /**
     * Creates a Piece.
     *
     * @param type an enum object of type {@code PieceType} representing the
     * piece type.
     */
    public Piece(PieceType type) {
        this.type = type;
    }

    /**
     * Set the PieceType to EMPTY (kills the piece).
     */
    public void kill() {
        this.type = PieceType.EMPTY;
    }

    /**
     * Changes the PieceType.
     *
     * @param type an enum object of type {@code PieceType} representing the
     * piece type.
     */
    public void changeType(PieceType type) {
        this.type = type;
    }

    /**
     * Returns the PieceType.
     *
     * @return type an enum object of type {@code PieceType} representing
     * the piece type.
     */
    public PieceType getType() {
        return this.type;
    }
}
