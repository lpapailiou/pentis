package application;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import logic.Board;
import util.ColorScheme;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;
import static logic.Shape.*;
import static util.Setting.*;

public class Game implements Initializable {

    private static Game instance;

    private static final int HEIGHT = 500;
    private static final int WIDTH = 300;

    private static final int PREVIEW_HEIGHT = 200;
    private static final int PREVIEW_WIDTH = 200;

    private static final  int CELL_PADDING = 3;

    private static int PADDING_VERTICAL = 20;
    private static int PADDING_HORIZONTAL = 20;
    private static int CELL_H = ((HEIGHT - 2*PADDING_VERTICAL) / BOARD_HEIGHT);
    private static int CELL_W = ((WIDTH - 2*PADDING_HORIZONTAL) / BOARD_WITH);

    private static int[][] board = new int[BOARD_HEIGHT][BOARD_WITH];
    private static int[][] shape = getShape(board[0].length);
    private static int[][] nextShape = getShape(board[0].length);
    private static GraphicsContext context;
    private static GraphicsContext previewContext;
    private static boolean isPaused = true;
    private static boolean isFinished = false;

    private static Text gameOverText = null;
    private static Text continueText = null;

    private static Font monoFont = new Font("Monospaced", 36);

    static {
        if (CELL_H < CELL_W) {
            CELL_W = CELL_H;
            PADDING_HORIZONTAL = (WIDTH - (CELL_H * BOARD_WITH)) / 2;
        } else if (CELL_H > CELL_W) {
            CELL_H = CELL_W;
            PADDING_VERTICAL = (HEIGHT - (CELL_W * BOARD_HEIGHT)) / 2;
        }
    }

    @FXML
    private Pane gamePane;

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

    @FXML
    private Pane previewPane;

    @FXML
    private VBox rightBox;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        context = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        Canvas previewCanvas = new Canvas(PREVIEW_WIDTH, PREVIEW_HEIGHT);
        previewContext = previewCanvas.getGraphicsContext2D();
        previewPane.getChildren().add(previewCanvas);

        drawBoard();
        drawPreview();
        isPaused = false;
        instance = this;
    }

    public static void handleDrop() {
        if (!isPaused && !isFinished) {
            int[] dir = new int[] {1, 0};
            boolean isDown = false;
            while (!isDown) {
                isDown = moveByKey(dir);
            }
        }
    }

    public void handlePause() {
        if (!isFinished) {
            isPaused = !isPaused;
            if (isPaused) {
                pausedLabel.setText("paused");
            } else {
                pausedLabel.setText("");
            }
        }
    }

    public static void handleNewGame(boolean newGame) {
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

    private static void reset() {
        board = new int[BOARD_HEIGHT][BOARD_WITH];
        shape = getShape(board[0].length);
        nextShape = getShape(board[0].length);
        drawBoard();
        drawPreview();
        gameOverText.setText("");
        continueText.setText("");
        getGame().level.setText("1");
        getGame().score.setText("0");
        isFinished = false;
        isPaused = false;
    }

    public static boolean moveByKey(int[] dir) {
        boolean isShapeDown = false;
        if (!isPaused && !isFinished) {
            boolean canMove = false;
            if (Arrays.equals(dir, new int[]{0, 0})) {
                int[][] rotatedShape = getRotateShape(shape);
                canMove = Board.moveShape(board, rotatedShape, dir);
                if (canMove) {
                    shape = rotatedShape;
                    drawBoard();
                }
            } else {
                canMove = Board.moveShape(board, shape, dir);
                drawBoard();
                if (canMove) {

                } else {
                    isShapeDown = true;
                    prepareNewShape();
                }
            }
        }
        return isShapeDown;
    }

    public static void endGame() {
        isFinished = true;
        displayEndGameTitle();
        displayEndGameText();
    }

    private static void displayEndGameTitle() {
        Text text;
        if (gameOverText == null) {
            text = new Text();
            text.setX(20);
            text.setY(200);
            text.setFill(COLOR_MODE.textColor);
            text.setFont(Font.font("Monospace", FontWeight.BOLD, 36));
            gameOverText = text;
            instance.gamePane.getChildren().add(text);
        } else {
            text = gameOverText;
        }
        text.setText("GAME OVER!!!");
    }

    private static void displayEndGameText() {
        Text text;
        if (continueText == null) {
            text = new Text();
            text.setX(20);
            text.setY(230);
            text.setFill(COLOR_MODE.textColor);
            text.setFont(Font.font("Monospace", FontWeight.BOLD, 22));
            continueText = text;
            instance.gamePane.getChildren().add(text);
        } else {
            text = continueText;
        }
        text.setText("try again? (y / n)");
    }

    public void updateLevel() {
        int value = Integer.parseInt(level.getText());
        value++;
        level.setText(""+value);
    }

    public void updatePoints() {
        int value = Integer.parseInt(score.getText());
        value = value + BOARD_WITH;
        score.setText(""+value);
    }

    private static void prepareNewShape() {
        shape = copy(nextShape);
        nextShape = getShape(board[0].length);
        drawPreview();
    }

    private static void drawBackground() {
        context.setFill(COLOR_MODE.backgroundColor);
        context.fillRect(CELL_PADDING*3, PADDING_VERTICAL - CELL_PADDING*3, WIDTH-PADDING_HORIZONTAL, HEIGHT-(PADDING_VERTICAL*2)+CELL_PADDING*6);
    }

    private static void drawPreviewBackground() {
        previewContext.setFill(COLOR_MODE.backgroundColor);
        previewContext.fillRect(CELL_PADDING*3, PADDING_VERTICAL - CELL_PADDING*3, PREVIEW_WIDTH-PADDING_HORIZONTAL, PREVIEW_HEIGHT-(PADDING_VERTICAL*2)+CELL_PADDING*6);
    }

    private static void drawBoard() {
        context.clearRect(0, 0, WIDTH, HEIGHT);
        drawBackground();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                drawCellAt(context, i, j, board[i][j], new int[]{0, 0});
            }
        }
    }

    private static void drawPreview() {
        previewContext.clearRect(0, 0, PREVIEW_WIDTH, PREVIEW_HEIGHT);
        drawPreviewBackground();
        int[][] previewShape = getPreviewShape(nextShape);
        int height = getMinMaxHeight(previewShape)[1]+1;
        int width = getMinMaxWidth(previewShape)[1]+1;
        int[] offset = new int[2];
        offset[0] = (PREVIEW_HEIGHT - (2* PADDING_VERTICAL) - (CELL_H*height))/2;
        offset[1] = (PREVIEW_WIDTH - (2* PADDING_HORIZONTAL) - (CELL_W*width))/2;
        for (int i = 0; i < previewShape.length; i++) {
            drawCellAt(previewContext, previewShape[i][0], previewShape[i][1], 1, offset);
        }
    }

    private static void drawCellAt(GraphicsContext context, int height, int width, int visibility, int[] offset) {
        Color color;
        if (visibility != 0) {

            if (visibility > 0) {
                color = COLOR_MODE.shape;
            } else {
                color = COLOR_MODE.activeShape;
            }

            int h = height * CELL_H + PADDING_VERTICAL + offset[0];
            int w = width * CELL_W + PADDING_HORIZONTAL + offset[1];
            int ch = CELL_H - CELL_PADDING;
            int cw = CELL_W - CELL_PADDING;

            context.setFill(color.darker());
            context.fillRoundRect(w, h, cw, ch, 10, 10);
            context.setFill(color.brighter());
            context.fillRoundRect(w, h, cw-2, ch-2, 8, 8);
            context.setFill(color);
            context.fillRoundRect(w+2, h+2, cw-4, ch-4, 6, 6);
        }
    }

    public static Game getGame() {
        return instance;
    }

}
