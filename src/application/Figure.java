package application;

import java.util.Random;

public class Figure {

    private static Random random = new Random();
    static final int ITEMS = 5;

    // TODO: implement method to rotate figure

    public static int[][] getFigure(int boardWith) {
        int[][] figure = new int[ITEMS][2];
        figure = createFigure(figure);
        adjust(figure);
        center(figure, boardWith);
        return figure;
    }

    private static void center(int[][] figure, int width) {
        int offset = getOffset(figure, width);
        for (int i = 0; i < figure.length; i++) {
            figure[i][1] = figure[i][1] + offset;
        }
    }

    private static int getOffset(int[][] figure, int width) {
        int min = width;
        int max = -10;
        int offset = 0;

        for (int i = 0; i < figure.length; i++) {
            if (figure[i][1] < min) {
                min = figure[i][1];
            }
            if (figure[i][1] > max) {
                max = figure[i][1];
            }
        }

        int figWidth = max - min;
        offset = (width/2) - figWidth;
        return offset;
    }

    private static void adjust(int[][] figure) {
        boolean readjust = false;
        for (int i = 0; i < ITEMS; i++) {
            if (figure[i][0] >= 0) {
                moveUp(figure);
                readjust = true;
            }
        }

        for (int i = 0; i < ITEMS; i++) {
            if (figure[i][1] < 0) {
                moveRight(figure);
                readjust = true;
            }
        }

        if (readjust) {
            adjust(figure);
        }
    }

    private static void moveUp(int[][] figure) {
        for (int i = 0; i < ITEMS; i++) {
            figure[i][0] = --figure[i][0];
        }
    }

    private static void moveRight(int[][] figure) {
        for (int i = 0; i < ITEMS; i++) {
            figure[i][1] = ++figure[i][1];
        }
    }

    private static int[][] createFigure(int[][] figure) {
        for (int i = 1; i < ITEMS; i++) {
            boolean success = false;
            while (!success) {
                success = addItem(figure, i);
            }
        }
        return figure;
    }

    private static boolean addItem(int[][] figure, int i) {
        int index = random.nextInt(i);
        int[] nextItemToJoin = new int[] {figure[index][0], figure[index][1]};
        int pos[] = getNext(nextItemToJoin);
        if (!exists(figure, pos)) {
            figure[i] = pos;
            return true;
        }
        return false;
    }

    private static int[] getNext(int[] pos) {
        int index = random.nextInt(2);      // row or col
        int next = random.nextInt(2);       // plus or minus
        next = next == 0 ? -1 : next;
        int[] newPos;
        if (index == 0) {
            newPos = new int[] {pos[0]+next, pos[1]};
        } else {
            newPos = new int[] {pos[0], pos[1]+next};
        }
        return newPos;
    }

    private static boolean exists(int figure[][], int[] pos) {
        for (int i = 0; i < figure.length; i++) {
            if (figure[i][0] == pos[0] && figure[i][1] == pos[1]) {
                return true;
            }
        }
        return false;
    }

}
