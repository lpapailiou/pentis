package application;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import logic.Board;
import util.ColorScheme;
import java.net.URL;
import java.util.ResourceBundle;

import static logic.Board.*;
import static logic.ShapeGenerator.*;
import static util.Setting.*;

public class Game implements Initializable {

    @FXML
    private Pane gamePane;

    @FXML
    private Pane previewPane;

    @FXML
    private Label gameOverTitle;

    @FXML
    private Label gameOverText;

    @FXML
    private Label scoreLabel;

    @FXML
    private Label score;

    @FXML
    private Label levelLabel;

    @FXML
    private Label level;

    @FXML
    public Label pausedLabel;

    private static Game instance;

    private static int counterLimit = 10;
    private static int counter;
    private static Timeline timeline;

    private static GraphicsContext context;
    private static GraphicsContext previewContext;

    private FadeTransition transitionPause = null;
    private FadeTransition transitionGameOverTitle = null;
    private FadeTransition transitionGameOverText = null;

    static boolean isPaused = true;
    static boolean isFinished = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        context = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        Canvas previewCanvas = new Canvas(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        previewContext = previewCanvas.getGraphicsContext2D();
        previewPane.getChildren().add(previewCanvas);

        setupLabels();

        drawBoard();
        drawPreview();
        isPaused = false;
        instance = this;
        setUpTimer();
    }

    // ---------------------------------- KEY PRESS HANDLING ----------------------------------

    static boolean moveByKey(int[] dir) {
        boolean isShapeDown = false;
        if (!isPaused && !isFinished) {
            isShapeDown = Board.moveShape(board, shape, dir);
            drawBoard();
        }
        return isShapeDown;
    }

    static void handleDrop() {
        if (!isPaused && !isFinished) {
            int[] dir = new int[] {1, 0};
            boolean isDown = false;
            while (!isDown) {
                isDown = moveByKey(dir);
            }
        }
    }

    void handlePause() {
        if (!isFinished) {
            isPaused = !isPaused;
            if (isPaused) {
                pausedLabel.setText("paused");
                transitionPause = new FadeTransition(Duration.millis(700), pausedLabel);
                transitionPause.setFromValue(1.0);
                transitionPause.setToValue(0.0);
                transitionPause.setCycleCount(Animation.INDEFINITE);
                transitionPause.play();
            } else {
                transitionPause.stop();
                pausedLabel.setText("");
            }
        }
    }

    static void handleNewGame(boolean newGame) {
        if (isFinished) {
            if (newGame) {
                reset();
            } else {
                try {
                    Platform.exit();
                    System.exit(0);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }

    static void switchMode() {
        if (!isFinished) {
            if (COLOR_MODE == ColorScheme.LIGHT_MODE) {
                COLOR_MODE = ColorScheme.DARK_MODE;
            } else {
                COLOR_MODE = ColorScheme.LIGHT_MODE;
            }
            instance.gamePane.getScene().setFill(COLOR_MODE.appBackground);
            drawBoard();
            drawPreview();
            instance.setupLabels();
        }
    }

    // ---------------------------------- TIMER HANDLING ----------------------------------

    private void setUpTimer() {
        timeline = new Timeline(new KeyFrame(Duration.millis(DELAY), event -> {
            if (!isPaused && !isFinished) {
                moveByKey(new int[]{1, 0});
                counter++;
                if (counter == counterLimit) {
                    updateLevel();
                    counter = 0;
                    counterLimit = counterLimit + 10;
                    if (timeline != null) {
                        timeline.setRate(timeline.getRate() + 0.05);
                    }
                }
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    private static void resetTimeline() {
        timeline.setRate(1.0);
        counterLimit = 10;
        counter = 0;
    }

    // ---------------------------------- GAME ACTION HANDLING ----------------------------------

    void updateLevel() {
        int value = Integer.parseInt(level.getText());
        value++;
        level.setText(""+value);
    }

    public void updatePoints() {
        int value = Integer.parseInt(score.getText());
        value = value + BOARD_WITH;
        score.setText(""+value);
    }

    public static void endGame() {
        isFinished = true;
        getGame().startGameOverAnimation();
    }

    // ---------------------------------- GAME OVER DIALOG HANDLING ----------------------------------

    private void startGameOverAnimation() {
        drawGameOverBackground();
        gameOverTitle.setText("GAME OVER!!!");
        gameOverText.setText("try again? (y / n)");
        transitionGameOverTitle = animateGameOverDialog(gameOverTitle);
        transitionGameOverText = animateGameOverDialog(gameOverText);
    }

    private FadeTransition animateGameOverDialog(Label label) {
        FadeTransition transition = new FadeTransition(Duration.millis(700), label);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();
        return transition;
    }

    private void stopGameOverAnimation() {
        gameOverTitle.setText("");
        gameOverText.setText("");
        getGame().transitionGameOverTitle.stop();
        getGame().transitionGameOverText.stop();
        getGame().level.setText("1");
        getGame().score.setText("0");
    }

    // ---------------------------------- DRAW HANDLING ----------------------------------

    private static void drawBoard() {
        if (!isFinished) {
            context.clearRect(0, 0, WIDTH, HEIGHT);
            drawBackground();
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    drawCellAt(context, i, j, board[i][j], new int[]{0, 0}, false);
                }
            }
        }
    }

    public static void drawPreview() {
        previewContext.clearRect(0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);
        drawPreviewBackground();
        int[][] previewShape = getPreviewShape(nextShape);
        int height = getMinMaxDimension(previewShape, 0)[1]+1;
        int width = getMinMaxDimension(previewShape, 1)[1]+1;
        int[] offset = new int[2];
        int cell_h = BLOCK_COUNT > 5 ? (CELL_H*5/BLOCK_COUNT*2) : CELL_H;
        int cell_w = BLOCK_COUNT > 5 ? (CELL_W*5/BLOCK_COUNT*2) : CELL_W;
        offset[0] = (PREVIEW_HEIGHT - (2* PADDING_VERTICAL) - (cell_h*height))/2;
        offset[1] = (PREVIEW_WIDTH - (2* PADDING_HORIZONTAL) - (cell_w*width))/2;
        for (int i = 0; i < previewShape.length; i++) {
            drawCellAt(previewContext, previewShape[i][0], previewShape[i][1], 2, offset, true);
        }
    }

    private static void drawCellAt(GraphicsContext context, int height, int width, int visibility, int[] offset, boolean isPreview) {
        Color color;
        if (visibility != 0) {

            if (visibility == 2) {
                color = COLOR_MODE.preview;
            } else if (visibility == 1) {
                color = COLOR_MODE.shape;
            } else {
                color = COLOR_MODE.activeShape;
            }

            int cell_h = isPreview && BLOCK_COUNT > 5 ? (CELL_H*5/BLOCK_COUNT*2) : CELL_H;
            int cell_w = isPreview && BLOCK_COUNT > 5 ? (CELL_W*5/BLOCK_COUNT*2) : CELL_W;

            int h = height * cell_h + PADDING_VERTICAL + offset[0];
            int w = width * cell_w + PADDING_HORIZONTAL + offset[1];
            int ch = cell_h - CELL_PADDING;
            int cw = cell_w - CELL_PADDING;

            context.setFill(color.darker());
            context.fillRoundRect(w, h, cw, ch, 10, 10);
            context.setFill(color.brighter());
            context.fillRoundRect(w, h, cw-2, ch-2, 8, 8);
            context.setFill(color);
            context.fillRoundRect(w+2, h+2, cw-4, ch-4, 6, 6);
        }
    }

    private static void drawBackground() {
        context.setFill(COLOR_MODE.backgroundColor);
        context.fillRect(CELL_PADDING*3, PADDING_VERTICAL - CELL_PADDING*3, WIDTH-PADDING_HORIZONTAL, HEIGHT-(PADDING_VERTICAL*2)+CELL_PADDING*6);
    }

    private static void drawPreviewBackground() {
        previewContext.setFill(COLOR_MODE.backgroundColor);
        previewContext.fillRect(CELL_PADDING*3, PADDING_VERTICAL - CELL_PADDING*3, PREVIEW_WIDTH-PADDING_HORIZONTAL, PREVIEW_HEIGHT-(PADDING_VERTICAL*2)+CELL_PADDING*6);
        previewContext.setFill(COLOR_MODE.appBackground);
        previewContext.fillRect(CELL_PADDING*3+5, PADDING_VERTICAL - CELL_PADDING*3+5, PREVIEW_WIDTH-PADDING_HORIZONTAL-10, PREVIEW_HEIGHT-(PADDING_VERTICAL*2)+CELL_PADDING*6-10);
    }

    private static void drawGameOverBackground() {
        context.setFill(COLOR_MODE.appBackground);
        context.fillRect(0, HEIGHT/2-80, WIDTH, 110);
    }

    // ---------------------------------- HELPER METHODS ----------------------------------

    public static Game getGame() {
        return instance;
    }

    private void setupLabels() {
        Label[] labelList = {gameOverTitle, gameOverText, scoreLabel, score, levelLabel, level, pausedLabel};
        for (Label label : labelList) {
            label.setTextFill(COLOR_MODE.textColor);
        }
    }

    private static void reset() {
        resetTimeline();
        resetBoard();
        drawPreview();
        getGame().stopGameOverAnimation();
        isFinished = false;
        isPaused = false;
        drawBoard();
    }
}
