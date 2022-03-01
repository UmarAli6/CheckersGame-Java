package view;

import java.io.IOException;
import javafx.scene.control.Button;

import model.CheckersLogic;

/**
 *
 * @author Rabi_S & Umar_A
 */
public class CheckersController {
    
    private static final int ROW = 8, COL = 8;
    private final CheckersLogic model;
    private final CheckersView view;
    private final Highscore highscore;
    
    public CheckersController(CheckersLogic model, CheckersView view) throws IOException, ClassNotFoundException {
        this.model = model;
        this.view = view;
        this.highscore = new Highscore();
        highscore.deSerFromFile("highscore.ser");
    }
    
    public void handleButtonEvent() {
        Button[][] buttons = view.getButtonArr();
        
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    if (buttons[row][col] != null) {
                        if (buttons[row][col].isArmed()) {
                            boolean[][] canJumpTo = model.legalJumps(row, col);
                            if (!model.isJump(canJumpTo)) {
                                boolean[][] canMoveTo = model.legalMoves(row, col);
                                if (model.isJump(canMoveTo)) {
                                    view.updateView(this, model.setShadowPos(canMoveTo, row, col));
                                }
                            } else {
                                view.updateView(this, model.setShadowPos(canJumpTo, row, col));
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void handleShadowButtonEvent(int[][] shadowArr) {
        Button[][] buttons = view.getButtonArr();
        
        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    if (buttons[row][col] != null) {
                        if (buttons[row][col].isArmed()) {
                            model.makeJumpOrMove(shadowArr[8][0], shadowArr[8][1], row, col);
                            shadowArr = new int[9][8];
                            view.updateView(this, shadowArr);
                            
                            if (model.isGameOver()) {
                                String name;
                                if ("Red".equals(model.getPlayerName())) {
                                    name = view.showGameOver(model.getPlayerName(), model.getRedNrOfMoves());
                                    model.setPlayerName(name);
                                    highscore.addPlayersToList(name, model.getRedNrOfMoves());
                                    
                                } else {
                                    name = view.showGameOver(model.getPlayerName(), model.getBeigeNrOfMoves());
                                    model.setPlayerName(name);
                                    highscore.addPlayersToList(name, model.getBeigeNrOfMoves());
                                }
                                try {
                                    highscore.serToFile("highscore.ser");
                                } catch (IOException ex) {
                                    view.showAlert("Error writing to file. Highscores not saved");
                                }
                                handleResetEvent();
                            }
                        }
                    }
                }
            }
        }
    }
    
    public void handleResetEvent() {
        model.resetGame();
        
        int[][] shadowArr = new int[9][8];
        view.updateView(this, shadowArr);
    }
    
    public void handleRulesEvent() {
        view.showRules("The opponent with the red pieces moves first. "
                + "Pieces may only move one diagonal space forward "
                + "(towards their opponents pieces) in the beginning of the game. "
                + "Pieces must stay on the dark squares. To capture an opposing piece, "
                + "\"jump\" over it by moving two diagonal spaces in the direction of the the opposing piece. "
                + "When a piece is crowned by moving to opposing side, it gains the ability to move diagonally in all four directions");
    }
    
    public void handleHighscoreEvent() {
        view.showHighscores(highscore.toString());
    }
    
    public int[][] getPiecePos() {
        return model.getPiecePos();
    }
}
