package application;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import util.KeyParser;

public class Pentis extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        try {
            FXMLLoader loader = new FXMLLoader(Pentis.class.getClassLoader().getResource("resources/Game.fxml"));
            Parent root = loader.load();
            Scene scene = new Scene(root, 500, 500);
            scene.getStylesheets().add(Pentis.class.getClassLoader().getResource("resources/style.css").toExternalForm());
            stage.setScene(scene);
            stage.setMinHeight(539);
            stage.setMinWidth(538);
            stage.setTitle("Pentis");
            stage.getIcons().add(new Image("resources/pentis.png"));
            stage.show();

            setUpKeyParser(scene);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpKeyParser(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            Game.moveByKey(KeyParser.handleKeyPress(e));
            e.consume();
        });
    }


}