package model;

import static java.lang.Math.abs;

/**
 *
 * @author Rabi_S & Umar_A
 */
public class CheckersLogic {

    private static final int ROW = 8, COL = 8;
    private Piece[][] thePieces;
    private Player playerRed;
    private Player playerBeige;

    /**
     * Create a new Player object and initializes the game.
     */
    public CheckersLogic() {
        thePieces = new Piece[ROW][COL];
        playerRed = new Player("Red", true);
        playerBeige = new Player("Beige", false);
        initGame();
    }

    /**
     * Initialize the game. Sets out the correct pieces on the correct place.
     */
    public void initGame() {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    if (row < 3) {
                        thePieces[row][col] = new Piece(PieceType.BEIGE);
                    } else if (row > 4) {
                        thePieces[row][col] = new Piece(PieceType.RED);
                    } else {
                        thePieces[row][col] = new Piece(PieceType.EMPTY);
                    }
                } else {
                    thePieces[row][col] = new Piece(PieceType.EMPTY);
                }
            }
        }
    }

    /**
     * Resets the game.
     */
    public void resetGame() {
        playerRed = new Player("Red", true);
        playerBeige = new Player("Beige", false);
        initGame();
    }

    /**
     * Jumps or moves the piece by changing the type of the destination to the
     * source type and changing the source to empty. Changes the turn and ticks
     * the number of moves. If the player jumps and there is a another jump to
     * be made directly after, the turn is not switched.
     *
     * @param fromRow a {@code int} representing the row to jump/move from.
     * @param fromCol a {@code int} representing the col to jump/move from.
     * @param toRow a {@code int} representing the row to jump/move to.
     * @param toCol a {@code int} representing the col to jump/move to.
     */
    public void makeJumpOrMove(int fromRow, int fromCol, int toRow, int toCol) {
        thePieces[toRow][toCol].changeType(thePieces[fromRow][fromCol].getType());
        thePieces[fromRow][fromCol].kill();

        if (abs(fromRow - toRow) == 2) {
            thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].kill();
        }

        if (toRow == 0 && thePieces[toRow][toCol].getType() == PieceType.RED) {
            thePieces[toRow][toCol].changeType(PieceType.RED_KING);
        }
        if (toRow == 7 && thePieces[toRow][toCol].getType() == PieceType.BEIGE) {
            thePieces[toRow][toCol].changeType(PieceType.BEIGE_KING);
        }

        if (playerRed.getTurn()) {
            if ((abs(fromRow - toRow) == 2) && isJump(legalJumps(toRow, toCol))) {
                playerRed.tickNrOfMoves();
            } else {
                playerRed.tickNrOfMoves();
                playerRed.toggleTurn();
                playerBeige.toggleTurn();
            }
        } else if (playerBeige.getTurn()) {
            if ((abs(fromRow - toRow) == 2) && isJump(legalJumps(toRow, toCol))) {
                playerBeige.tickNrOfMoves();
            } else {
                playerBeige.tickNrOfMoves();
                playerBeige.toggleTurn();
                playerRed.toggleTurn();
            }
        }
    }

    /**
     * Returns a boolean array contain the possible locations for a jump.
     *
     * @param fromRow a {@code int} representing the row to jump from.
     * @param fromCol a {@code int} representing the col to jump from.
     * @return a {@code boolean} array.
     */
    public boolean[][] legalJumps(int fromRow, int fromCol) {
        boolean legalJumps[][] = new boolean[8][8];

        if ((playerRed.getTurn() && (thePieces[fromRow][fromCol].getType() == PieceType.RED || thePieces[fromRow][fromCol].getType() == PieceType.RED_KING))
                || (playerBeige.getTurn() && (thePieces[fromRow][fromCol].getType() == PieceType.BEIGE || thePieces[fromRow][fromCol].getType() == PieceType.BEIGE_KING))) {
            if (isLegalJump(fromRow, fromCol, fromRow - 2, fromCol - 2)) {
                legalJumps[fromRow - 2][fromCol - 2] = true;
            }
            if (isLegalJump(fromRow, fromCol, fromRow + 2, fromCol - 2)) {
                legalJumps[fromRow + 2][fromCol - 2] = true;
            }
            if (isLegalJump(fromRow, fromCol, fromRow - 2, fromCol + 2)) {
                legalJumps[fromRow - 2][fromCol + 2] = true;
            }
            if (isLegalJump(fromRow, fromCol, fromRow + 2, fromCol + 2)) {
                legalJumps[fromRow + 2][fromCol + 2] = true;
            }
        }
        return legalJumps;
    }

    /**
     * Returns a boolean array contain the possible locations for a move if no
     * mandatory jump is present.
     *
     * @param fromRow a {@code int} representing the row to move from.
     * @param fromCol a {@code int} representing the col to move from.
     * @return a {@code boolean} array.
     */
    public boolean[][] legalMoves(int fromRow, int fromCol) {
        boolean mandatoryJumps = false;
        boolean legalMoves[][] = new boolean[8][8];

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (playerRed.getTurn() && (thePieces[row][col].getType() == PieceType.RED || thePieces[row][col].getType() == PieceType.RED_KING)) {
                    if (isJump(legalJumps(row, col))) {
                        mandatoryJumps = true;
                    }
                }
                if (playerBeige.getTurn() && (thePieces[row][col].getType() == PieceType.BEIGE || thePieces[row][col].getType() == PieceType.BEIGE_KING)) {
                    if (isJump(legalJumps(row, col))) {
                        mandatoryJumps = true;
                    }
                }
            }
        }

        if (!mandatoryJumps) {
            if ((playerRed.getTurn() && (thePieces[fromRow][fromCol].getType() == PieceType.RED || thePieces[fromRow][fromCol].getType() == PieceType.RED_KING))
                    || (playerBeige.getTurn() && (thePieces[fromRow][fromCol].getType() == PieceType.BEIGE || thePieces[fromRow][fromCol].getType() == PieceType.BEIGE_KING))) {
                if (isLegalMove(fromRow, fromCol, fromRow - 1, fromCol - 1)) {
                    legalMoves[fromRow - 1][fromCol - 1] = true;
                }
                if (isLegalMove(fromRow, fromCol, fromRow + 1, fromCol - 1)) {
                    legalMoves[fromRow + 1][fromCol - 1] = true;
                }
                if (isLegalMove(fromRow, fromCol, fromRow - 1, fromCol + 1)) {
                    legalMoves[fromRow - 1][fromCol + 1] = true;
                }
                if (isLegalMove(fromRow, fromCol, fromRow + 1, fromCol + 1)) {
                    legalMoves[fromRow + 1][fromCol + 1] = true;
                }
            }
        }
        return legalMoves;
    }

    /**
     * Checks if the two dimensional array contains a jump (if true).
     *
     * @param jumpTo a two dimensional {@code boolean} array representing the
     * destination the player wants to jump/move to. from.
     * @return a {@code boolean} true or false.
     *
     */
    public boolean isJump(boolean[][] jumpTo) {
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (jumpTo[row][col]) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Checks if the jump is legal (according to rules). Returns true if the
     * jumps is legal.
     *
     * @param fromRow a {@code int} representing the row to jump/move from.
     * @param fromCol a {@code int} representing the col to jump/move from.
     * @param toRow a {@code int} representing the row to jump/move to.
     * @param toCol a {@code int} representing the col to jump/move to.
     * @return a {@code boolean} true or false.
     */
    private boolean isLegalJump(int fromRow, int fromCol, int toRow, int toCol) {
        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        if (thePieces[toRow][toCol].getType() != PieceType.EMPTY) {
            return false;
        }
        if (thePieces[fromRow][fromCol].getType() == PieceType.EMPTY) {
            return false;
        }
        if ((thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].getType() == PieceType.EMPTY)) {
            return false;
        }
        if (((thePieces[fromRow][fromCol].getType() == thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].getType()))) {
            return false;
        }
        if ((thePieces[fromRow][fromCol].getType() == PieceType.RED) && (thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].getType() == PieceType.RED_KING)) {
            return false;
        }
        if ((thePieces[fromRow][fromCol].getType() == PieceType.BEIGE) && (thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].getType() == PieceType.BEIGE_KING)) {
            return false;
        }
        if ((thePieces[fromRow][fromCol].getType() == PieceType.RED_KING) && (thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].getType() == PieceType.RED)) {
            return false;
        }
        if ((thePieces[fromRow][fromCol].getType() == PieceType.BEIGE_KING) && (thePieces[(fromRow + toRow) / 2][(fromCol + toCol) / 2].getType() == PieceType.BEIGE)) {
            return false;
        }
        return true;
    }

    /**
     * Checks if the move is legal (according to rules). Returns true if the
     * move is legal.
     *
     * @param fromRow a {@code int} representing the row to jump/move from.
     * @param fromCol a {@code int} representing the col to jump/move from.
     * @param toRow a {@code int} representing the row to jump/move to.
     * @param toCol a {@code int} representing the col to jump/move to.
     * @return a {@code boolean} true or false.
     */
    private boolean isLegalMove(int fromRow, int fromCol, int toRow, int toCol) {
        if (toRow < 0 || toRow > 7 || toCol < 0 || toCol > 7) {
            return false;
        }
        if (thePieces[toRow][toCol].getType() != PieceType.EMPTY) {
            return false;
        }
        if ((thePieces[fromRow][fromCol].getType() == PieceType.RED && fromRow - toRow == -1) || (thePieces[fromRow][fromCol].getType() == PieceType.BEIGE && fromRow - toRow == 1)) {
            return false;
        }
        if (abs(fromRow - toRow) != 1) {
            return false;
        }
        if (thePieces[fromRow][fromCol].getType() == PieceType.EMPTY) {
            return false;
        }
        return true;
    }

    /**
     * Checks if game is over. Returns true if the is game over.
     *
     * @return a {@code boolean} true or false.
     */
    public boolean isGameOver() {
        if (playerRed.getTurn()) {
            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    if (thePieces[row][col].getType() == PieceType.RED || thePieces[row][col].getType() == PieceType.RED_KING) {
                        if (isJump(legalJumps(row, col)) || isJump(legalMoves(row, col))) {
                            return false;
                        }
                    }
                }
            }
        } else if (playerBeige.getTurn()) {
            for (int row = 0; row < ROW; row++) {
                for (int col = 0; col < COL; col++) {
                    if (thePieces[row][col].getType() == PieceType.BEIGE || thePieces[row][col].getType() == PieceType.BEIGE_KING) {
                        if (isJump(legalJumps(row, col)) || isJump(legalMoves(row, col))) {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }

    /**
     * Set the shadow array according to canMoveTo array. Stores fromRow and
     * fromCol.
     *
     * @param canMoveTo a two dimensional {@code boolean} array representing the
     * possible jumps/moves.
     * @param fromRow a {@code int} representing the row to jump/move from.
     * @param fromCol a {@code int} representing the col to jump/move from.
     * @return a two dimensional {@code boolean} array containing the possible
     * jumps/moves and the fromRow and toRow.
     */
    public int[][] setShadowPos(boolean[][] canMoveTo, int fromRow, int fromCol) {
        int[][] shadowArr = new int[9][8];

        shadowArr[8][0] = fromRow;
        shadowArr[8][1] = fromCol;

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (canMoveTo[row][col] == true) {
                    shadowArr[row][col] = thePieces[fromRow][fromCol].getType().getIndex();
                }
            }
        }
        return shadowArr;
    }

    /**
     * Returns the positions of all pieces.
     *
     * @return a two dimensional {@code int} array containing the positions of
     * all the pieces
     */
    public int[][] getPiecePos() {
        int[][] copy = new int[ROW][COL];
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                copy[row][col] = thePieces[row][col].getType().getIndex();
            }
        }
        return copy;
    }

    /**
     * Returns the player name.
     *
     * @return a {@code string} representing the name.
     */
    public String getPlayerName() {
        if (playerRed.getTurn()) {
            return playerBeige.getName();
        } else {
            return playerRed.getName();
        }
    }

    /**
     * Returns the number of moves for Red.
     *
     * @return an {@code int} representing the name.
     */
    public int getRedNrOfMoves() {
        return playerRed.getNrOfMoves();
    }

    /**
     * Returns the number of moves for Beige.
     *
     * @return an {@code int} representing the number of moves.
     */
    public int getBeigeNrOfMoves() {
        return playerBeige.getNrOfMoves();
    }

    /**
     * Changes the player name.
     *
     * @param name {@code string} representing the name.
     */
    public void setPlayerName(String name) {
        if (playerRed.getTurn()) {
            playerBeige.setName(name);
        } else {
            playerRed.setName(name);
        }
    }
}
