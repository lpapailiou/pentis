package logic;


import org.junit.Test;

import java.util.Arrays;

import static logic.Board.*;
import static logic.ShapeGenerator.getShape;
import static logic.ShapeGenerator.rotateShape;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class BoardTest {

    @Test
    void test() {
        moveShapeDownTest();
    }


    void directionTest() {
        int testWidth = 6;
        int testHeight = 6;
        int[][] testBoard = new int[testHeight][testWidth];
        int[] dirDown = {1,0};
        int[] dirUp = {-1,0};
        int[] dirLeft = {0,-1};
        int[] dirRight = {0,1};
        int[][] shape = getShape(testWidth);

        for (int i= 0; i < 5; i++) {
            moveShape(testBoard, shape, dirDown);
        }
        print(testBoard);
        moveShape(testBoard, shape, dirLeft);
        print(testBoard);
        moveShape(testBoard, shape, dirRight);
        print(testBoard);
        moveShape(testBoard, shape, dirDown);
        print(testBoard);
        moveShape(testBoard, shape, dirUp);
        print(testBoard);
    }


    void rotationTest() {
        int testWidth = 6;
        int testHeight = 6;
        int[][] testBoard = new int[testHeight][testWidth];
        int[] dirDown = {1,0};
        int[] dirNone = {0,0};
        int[][] shape = getShape(testWidth);

        for (int i= 0; i < 5; i++) {
            moveShape(testBoard, shape, dirDown);
        }
        print(testBoard);
        rotateShape(shape);
        moveShape(testBoard, shape, dirNone);
        print(testBoard);
        rotateShape(shape);
        moveShape(testBoard, shape, dirNone);
        print(testBoard);
        rotateShape(shape);
        moveShape(testBoard, shape, dirNone);
        print(testBoard);
        rotateShape(shape);
        moveShape(testBoard, shape, dirNone);
        print(testBoard);
    }

    void moveShapeDownTest() {
        int testWidth = 5;
        int testHeight = 4;
        int[][] testBoard = new int[testHeight][testWidth];
        testBoard[testHeight-1][0] = 1;
        int[] direction = {1,0};
        int[][] shape = getShape(testWidth);

        print(testBoard);
        moveDown(testBoard, shape, direction);
    }

    boolean moveDown(int[][] testBoard, int[][] shape, int[] direction) {
        boolean canMove = true;
        int counter = 0;
        boolean endGame = false;
        while (canMove) {
            counter++;
            canMove = moveShape(testBoard, shape, direction);
            print(testBoard);
            if (counter == 1 && !canMove) {
                endGame = true;
                System.out.println("game ends here");
            }
        }
        if (!endGame) {
            shape = getShape(testBoard[0].length);
            moveDown(testBoard, shape, direction);
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

        fullRowCheck(testBoard);

        print(testBoard);

        testBoard = new int[testHeight][testWidth];

        assertTrue(Arrays.deepEquals(testBoard, new int[testHeight][testWidth]));

        print(testBoard);
    }



}
