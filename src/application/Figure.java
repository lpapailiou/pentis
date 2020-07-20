package application;

import java.util.Arrays;
import java.util.Random;

import static util.Setting.BLOCK_COUNT;

public class Figure {

    private static Random random = new Random();

    public static int[][] getRotatedFigure(int[][] figure) {
        int[][] rotatedFigure = Arrays.copyOf(figure, figure.length);

        int[] minMaxHeight = getMinMaxHeight(figure);
        int[] minMaxWidht = getMinMaxWidth(figure);

        int minHeight = minMaxHeight[0];
        int maxHeight = minMaxHeight[1];
        int minWidth = minMaxWidht[0];
        int maxWidth = minMaxWidht[1];

        int offsetHeight = minHeight + ((maxHeight-minHeight) / 2);
        int offsetWidth = minWidth + ((maxWidth-minWidth) / 2);

        rotateClockwise(rotatedFigure, offsetHeight, offsetWidth);

        return rotatedFigure;
    }

    private static void rotateClockwise(int[][] figure, int offsetHeight, int offsetWidth) {

        for (int i = 0; i < figure.length; i++) {
            int height = figure[i][0];
            int width = figure[i][1];

            int offH = offsetHeight - height;
            int offW = offsetWidth - width;

            offW = (offW < 0) ? Math.abs(offW) : -offW;

            int newH = offsetHeight + offW;
            int newW = offsetWidth + offH;

            figure[i] = new int[] {newH, newW};
        }

    }

    public static int[][] getFigure(int boardWith) {
        int[][] figure = new int[BLOCK_COUNT][2];
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
        int minMaxWidth[] = getMinMaxWidth(figure);
        int min = minMaxWidth[0];
        int max = minMaxWidth[1];

        int offset = 0;
        int figWidth = max - min;
        offset = (width/2) - figWidth;
        return offset;
    }

    private static int[] getMinMaxWidth(int[][] figure) {
        int[] result = new int[2];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < figure.length; i++) {
            if (figure[i][1] < min) {
                min = figure[i][1];
            }
            if (figure[i][1] > max) {
                max = figure[i][1];
            }
        }

        result[0] = min;
        result[1] = max;

        return result;
    }

    private static int[] getMinMaxHeight(int[][] figure) {
        int[] result = new int[2];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < figure.length; i++) {
            if (figure[i][0] < min) {
                min = figure[i][0];
            }
            if (figure[i][0] > max) {
                max = figure[i][0];
            }
        }

        result[0] = min;
        result[1] = max;

        return result;
    }

    private static void adjust(int[][] figure) {
        boolean readjust = false;
        for (int i = 0; i < BLOCK_COUNT; i++) {
            if (figure[i][0] >= 0) {
                moveUp(figure);
                readjust = true;
            }
        }

        for (int i = 0; i < BLOCK_COUNT; i++) {
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
        for (int i = 0; i < BLOCK_COUNT; i++) {
            figure[i][0] = --figure[i][0];
        }
    }

    private static void moveRight(int[][] figure) {
        for (int i = 0; i < BLOCK_COUNT; i++) {
            figure[i][1] = ++figure[i][1];
        }
    }

    private static int[][] createFigure(int[][] figure) {
        for (int i = 1; i < BLOCK_COUNT; i++) {
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
        int index = random.nextInt(2);
        int next = random.nextInt(2);
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
