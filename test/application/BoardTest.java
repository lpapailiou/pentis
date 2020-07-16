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
        testMove();
    }

    void testMove() {
        int testWidth = 10;
        int testHeight = 10;
        int[][] testBoard = new int[testHeight][testWidth];
        int[] direction = {1,0};
        int[][] figure = getFigure(testWidth);
        System.out.println(Arrays.deepToString(figure));

        print(testBoard);
        moveDown(testBoard, figure, direction);
        figure = getFigure(testWidth);
        moveDown(testBoard, figure, direction);
    }

    void moveDown(int[][] testBoard, int[][] figure, int[] direction) {
        boolean canMove = true;
        while (canMove) {
            canMove = moveFigure(testBoard, figure, direction);
            print(testBoard);
        }
    }


    void testBoard() {
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
