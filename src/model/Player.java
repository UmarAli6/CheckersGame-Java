package model;

/**
 * Class representing a Player of the game.
 *
 * @author Rabi_S & Umar_A
 */
public class Player {

    private int noOfMoves;
    private boolean turn;
    private String name;

    /**
     * Create a new Player object.
     *
     * @param name a {@code String} representing the name of the player.
     * @param turn a {@code boolean} representing the turn.
     */
    public Player(String name, boolean turn) {
        this.noOfMoves = 0;
        this.turn = turn;
        this.name = name;
    }

    /**
     * Ticks (++) the number of moves.
     */
    public void tickNrOfMoves() {
        this.noOfMoves++;
    }

    /**
     * Toggles the turn to true or false.
     */
    public void toggleTurn() {
        this.turn = !this.turn;
    }

    /**
     * Returns the number of moves.
     *
     * @return an {@code int} representing the number of moves.
     */
    public int getNrOfMoves() {
        return this.noOfMoves;
    }

    /**
     * Returns the turn.
     *
     * @return a true or false {@code boolean} representing the turn.
     */
    public boolean getTurn() {
        return this.turn;
    }

    /**
     * Returns the name.
     *
     * @return {@code String} representing the name.
     */
    public String getName() {
        return this.name;
    }

    /**
     * Sets the name.
     *
     * @param name a {@code String} representing the name.
     */
    public void setName(String name) {
        this.name = name;
    }
}
