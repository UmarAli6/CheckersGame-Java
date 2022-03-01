package checkers;

import java.io.IOException;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

import model.CheckersLogic;
import view.CheckersView;

/**
 *
 * @author Rabi_S & Umar_A
 */
public class App extends Application {

    @Override
    public void start(Stage stage) throws IOException, ClassNotFoundException {

        CheckersLogic model = new CheckersLogic();
        CheckersView view = new CheckersView(model);

        Scene scene = new Scene(view, 640, 665);
        stage.setTitle("Royal Checkers");
        stage.setScene(scene);
        stage.sizeToScene();
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}