package application;

import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static application.Board.*;
import static application.Figure.getFigure;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class BoardTest {

    @Test
    void test() {
        moveFigureDownTest();
    }

    void moveFigureDownTest() {
        int testWidth = 5;
        int testHeight = 4;
        int[][] testBoard = new int[testHeight][testWidth];
        testBoard[testHeight-1][0] = 1;
        int[] direction = {1,0};
        int[][] figure = getFigure(testWidth);

        print(testBoard);
        moveDown(testBoard, figure, direction);
    }

    boolean moveDown(int[][] testBoard, int[][] figure, int[] direction) {
        boolean canMove = true;
        int counter = 0;
        boolean endGame = false;
        while (canMove) {
            counter++;
            canMove = moveFigure(testBoard, figure, direction);
            print(testBoard);
            if (counter == 1 && !canMove) {
                endGame = true;
                System.out.println("game ends here");
            }
        }
        if (!endGame) {
            figure = figure = getFigure(testBoard[0].length);
            moveDown(testBoard, figure, direction);
        }
        return endGame;
    }


    void removeRowTest() {
        int testWidth = 5;
        int testHeight = 6;
        int[][] testBoard = new int[testHeight][testWidth];
        testBoard[2] = new int[] {1, 1, 0, 0, 1};
        testBoard[3] = new int[] {1, 0, 1, 1, 1};
        testBoard[4] = new int[] {1, 1, 1, 1, 1};

        assertFalse(Arrays.deepEquals(testBoard, new int[testHeight][testWidth]));

        print(testBoard);

        stateCheck(testBoard);

        print(testBoard);

        testBoard = new int[testHeight][testWidth];

        assertTrue(Arrays.deepEquals(testBoard, new int[testHeight][testWidth]));

        print(testBoard);
    }



}
