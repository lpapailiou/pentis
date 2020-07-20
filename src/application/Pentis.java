package application;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;
import javafx.util.Duration;
import util.KeyParser;
import static application.Game.getGame;
import static util.Setting.COLOR_MODE;
import static util.Setting.DELAY;

public class Pentis extends Application {

    private int counter;

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
            scene.setFill(COLOR_MODE.appBackground);
            stage.setScene(scene);
            stage.setMinHeight(539);
            stage.setMinWidth(538);
            stage.setMaxHeight(539);
            stage.setMaxWidth(538);
            stage.setTitle("Pentis");
            stage.getIcons().add(new Image("resources/pentis.png"));
            stage.show();

            setUpKeyParser(scene);
            setUpTimer();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setUpKeyParser(Scene scene) {
        scene.addEventFilter(KeyEvent.KEY_PRESSED, e -> {
            int[] result = KeyParser.handleKeyPress(e);
            if (result[0] > -9) {
                Game.moveByKey(result);
            } else {
                if (result[1] == 0) {
                    Game.handleDrop();
                } else if (result[1] == 1) {
                    getGame().handlePause();
                } else if (result[1] == 2) {
                    Game.handleNewGame(true);
                } else if (result[1] == 3) {
                    Game.handleNewGame(false);
                }
            }
            e.consume();
        });
    }

    private void setUpTimer() {
        Timeline timeline = new Timeline(new KeyFrame(Duration.millis(DELAY),  event -> {
            Game.moveByKey(new int[]{1, 0});
            counter++;

            if (counter == 50) {
                getGame().updateLevel();
                counter = 0;
                // TODO: make faster
            }

        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }


}