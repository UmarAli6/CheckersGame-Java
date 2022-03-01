package view;

import java.io.IOException;
import java.util.Optional;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import model.CheckersLogic;

/**
 *
 * @author Rabi_S & Umar_A
 */
public class CheckersView extends VBox {

    private static final int ROW = 8, COL = 8;
    private final CheckersController controller;
    private final Alert alert = new Alert(Alert.AlertType.INFORMATION);
    private Button[][] buttons;
    private GridPane mainView;

    public CheckersView(CheckersLogic model) throws IOException, ClassNotFoundException {
        this.controller = new CheckersController(model, this);

        initButtons();
        mainView = initPieces();
        initEventHandlers(controller);

        this.getChildren().addAll(createMenues(controller), mainView);
    }

    public void updateView(CheckersController controller, int[][] shadowArr) {
        mainView.getChildren().clear();
        this.getChildren().clear();

        updateButtons(shadowArr);

        mainView = setPieces(shadowArr);
        addEventHandlers(controller, shadowArr);

        this.getChildren().addAll(createMenues(controller), mainView);
    }

    private void initButtons() {
        int[][] pieces = controller.getPiecePos();
        buttons = new Button[8][8];

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    switch (pieces[row][col]) {
                        case 1: {
                            Button piece = new Button();
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #C62D1F; -fx-background-radius: 30;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 2: {
                            Button piece = new Button("♛");
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #C62D1F; -fx-background-radius: 30; -fx-font-size: 25;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 3: {
                            Button piece = new Button();
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #FACB82; -fx-background-radius: 30;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 4: {
                            Button piece = new Button("♛");
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #FACB82; -fx-background-radius: 30; -fx-font-size: 25;");
                            buttons[row][col] = piece;
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
    }

    private GridPane initPieces() {
        GridPane piecePane = new GridPane();
        piecePane.setBackground(new Background(new BackgroundImage(getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        int[][] pieces = controller.getPiecePos();
        piecePane.setPadding(new Insets(10, 10, 10, 10));
        piecePane.setVgap(20.0);
        piecePane.setHgap(20.0);
        piecePane.setMinSize(640.0, 640.0);

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    switch (pieces[row][col]) {
                        case 1: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 2: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 3: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 4: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        default:
                            Rectangle tile = new Rectangle();
                            tile.setWidth(60);
                            tile.setHeight(60);
                            tile.setFill(Color.web("#573E1A"));
                            piecePane.add(tile, col, row);
                            break;
                    }
                }
            }
        }
        return piecePane;
    }

    private void initEventHandlers(CheckersController controller) {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                controller.handleButtonEvent();
            }
        };

        int[][] piecePos = controller.getPiecePos();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (piecePos[row][col] != 0) {
                    buttons[row][col].setOnAction(event);
                }
            }
        }
    }

    private MenuBar createMenues(CheckersController controller) {
        Menu fileMenu = new Menu("File");
        Menu resetMenu = new Menu("Reset");
        Menu rulesMenu = new Menu("Rules");
        Menu highscoreMenu = new Menu("Highscores");

        MenuItem exitItem = new MenuItem("Exit");
        exitItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                Platform.exit();
            }
        });

        MenuItem resetItem = new MenuItem("Reset");
        resetItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleResetEvent();
            }
        });

        MenuItem rulesItem = new MenuItem("Rules");
        rulesItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleRulesEvent();
            }
        });

        MenuItem highscoreItem = new MenuItem("Highscores");
        highscoreItem.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                controller.handleHighscoreEvent();
            }
        });

        fileMenu.getItems().add(exitItem);
        resetMenu.getItems().add(resetItem);
        rulesMenu.getItems().add(rulesItem);
        highscoreMenu.getItems().add(highscoreItem);

        MenuBar menuBar = new MenuBar();
        menuBar.getMenus().addAll(fileMenu, resetMenu, rulesMenu, highscoreMenu);
        return menuBar;
    }

    public void updateButtons(int[][] shadowArr) {

        int[][] pieces = controller.getPiecePos();
        buttons = new Button[8][8];

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    switch (shadowArr[row][col]) {
                        case 1: {
                            Button piece = new Button();
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #C62D1F; -fx-opacity: 0.5; -fx-background-radius: 30;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 2: {
                            Button piece = new Button("♛");
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #C62D1F; -fx-opacity: 0.5; -fx-background-radius: 30; -fx-font-size: 25;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 3: {
                            Button piece = new Button();
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #FACB82; -fx-opacity: 0.5; -fx-background-radius: 30;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 4: {
                            Button piece = new Button("♛");
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #FACB82; -fx-opacity: 0.5; -fx-background-radius: 30; -fx-font-size: 25;");
                            buttons[row][col] = piece;
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    switch (pieces[row][col]) {
                        case 1: {
                            Button piece = new Button();
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #C62D1F; -fx-background-radius: 30;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 2: {
                            Button piece = new Button("♛");
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #C62D1F; -fx-background-radius: 30; -fx-font-size: 25;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 3: {
                            Button piece = new Button();
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #FACB82; -fx-background-radius: 30;");
                            buttons[row][col] = piece;
                            break;
                        }
                        case 4: {
                            Button piece = new Button("♛");
                            piece.setPrefSize(60, 60);
                            piece.setStyle("-fx-background-color: #FACB82; -fx-background-radius: 30; -fx-font-size: 25;");
                            buttons[row][col] = piece;
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
    }

    public GridPane setPieces(int[][] shadowArr) {
        GridPane piecePane = new GridPane();
        piecePane.setBackground(new Background(new BackgroundImage(getImage(), BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                BackgroundSize.DEFAULT)));

        int[][] pieces = controller.getPiecePos();
        piecePane.setPadding(new Insets(10, 10, 10, 10));
        piecePane.setVgap(20.0);
        piecePane.setHgap(20.0);
        piecePane.setMinSize(640.0, 640.0);

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    switch (pieces[row][col]) {
                        case 1: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 2: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 3: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 4: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        default:
                            Rectangle tile = new Rectangle();
                            tile.setWidth(60);
                            tile.setHeight(60);
                            tile.setFill(Color.web("#573E1A"));
                            piecePane.add(tile, col, row);
                            break;
                    }
                }
            }
        }

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (row % 2 != col % 2) {
                    switch (shadowArr[row][col]) {
                        case 1: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 2: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 3: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        case 4: {
                            piecePane.add(buttons[row][col], col, row);
                            break;
                        }
                        default:
                            break;
                    }
                }
            }
        }
        return piecePane;
    }

    private void addEventHandlers(CheckersController controller, int[][] shadowArr) {
        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                controller.handleButtonEvent();
            }
        };

        EventHandler<ActionEvent> eventShadow = new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent e) {
                controller.handleShadowButtonEvent(shadowArr);
            }
        };

        int[][] piecePos = controller.getPiecePos();

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (piecePos[row][col] != 0) {
                    buttons[row][col].setOnAction(event);
                }
            }
        }

        for (int row = 0; row < ROW; row++) {
            for (int col = 0; col < COL; col++) {
                if (shadowArr[row][col] != 0) {
                    buttons[row][col].setOnAction(eventShadow);
                }
            }
        }
    }

    public String showGameOver(String winner, int nrOfMoves) {
        TextInputDialog dialog = new TextInputDialog("name");
        dialog.setTitle("Game Over");
        dialog.setHeaderText("Congratulations, " + winner + "!\nYou won with " + nrOfMoves + " moves.");
        dialog.setContentText("Please enter your name:");

        Optional<String> result = dialog.showAndWait();
        
        return result.get();
    }

    public void showRules(String message) {
        alert.setTitle("Rules");
        alert.setHeaderText("Rules of the game");
        alert.setContentText(message);
        alert.getDialogPane().setMinHeight(280);
        alert.show();
    }

    public void showHighscores(String highscore) {
        alert.setTitle("Highscores");
        alert.setHeaderText("Leaderboard");
        alert.setContentText("Moves:\t\t\tName:\n\n" + highscore);
        alert.show();
    }
    
    public void showAlert(String highscore) {
        alert.setTitle("Alert");
        alert.setHeaderText("");
        alert.setContentText("highscore");
        alert.show();
    }

    public Button[][] getButtonArr() {
        return this.buttons;
    }

    private Image getImage() {
        Image image = new Image("file:src\\resources\\board.png");
        
        return image;
    }
    
}
