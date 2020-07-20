package application;

import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.util.Arrays;
import java.util.ResourceBundle;

import static application.Board.moveFigure;
import static application.Figure.getFigure;
import static application.Figure.getRotatedFigure;
import static util.Setting.BOARD_HEIGHT;
import static util.Setting.BOARD_WITH;

public class Game implements Initializable {

    private static final int HEIGHT = 500;
    private static final int WIDTH = 300;

    private static final  int CELL_PADDING = 3;

    private static int PADDING_VERTICAL = 20;
    private static int PADDING_HORIZONTAL = 20;
    private static int CELL_H = ((HEIGHT - 2*PADDING_VERTICAL) / BOARD_HEIGHT);
    private static int CELL_W = ((WIDTH - 2*PADDING_HORIZONTAL) / BOARD_WITH);

    private static int[][] board = new int[BOARD_HEIGHT][BOARD_WITH];
    private static int[][] figure = getFigure(board[0].length);
    private static int[] direction = {1, 0};

    private static GraphicsContext context;

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
    private HBox rootContainer;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Canvas canvas = new Canvas(WIDTH, HEIGHT);
        context = canvas.getGraphicsContext2D();
        gamePane.getChildren().add(canvas);

        drawBoard();

    }

    public static void moveByKey(int[] dir) {
        boolean canMove = false;
        if (Arrays.equals(dir, new int[]{0, 0})) {
            int[][] rotatedFigure = getRotatedFigure(figure);
            canMove = Board.moveFigure(board, rotatedFigure, dir);
            if (canMove) {
                figure = rotatedFigure;
                drawBoard();
            }
        } else {
            canMove = Board.moveFigure(board, figure, dir);
            drawBoard();
            if (!canMove) {
                figure = getFigure(board[0].length);
            }
        }
    }

    private static void drawBackground() {
        context.setFill(Color.FLORALWHITE);
        context.fillRect(CELL_PADDING*3, PADDING_VERTICAL - CELL_PADDING*3, WIDTH-PADDING_HORIZONTAL, HEIGHT-(PADDING_VERTICAL*2)+CELL_PADDING*6);
    }

    private static void drawBoard() {
        context.clearRect(0, 0, WIDTH, HEIGHT);
        drawBackground();
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                drawCellAt(i, j, board[i][j]);
            }
        }
    }

    private static void drawCellAt(int height, int width, int visibility) {
        if (visibility != 0) {
            if (visibility > 0) {
                context.setFill(Color.LIGHTBLUE);
            } else {
                context.setFill(Color.LIGHTSALMON);
            }
            int h = height * CELL_H + PADDING_VERTICAL;
            int w = width * CELL_W + PADDING_HORIZONTAL;
            context.fillRoundRect(w, h, CELL_W - CELL_PADDING, CELL_H - CELL_PADDING, 10, 10);
        }
    }

}
