package logic;

import java.util.Arrays;
import java.util.Random;

import static util.Setting.BLOCK_COUNT;

public class Shape {

    private static Random random = new Random();

    public static int[][] getRotateShape(int[][] shape) {
        int[][] rotatedShape = copy(shape);

        int[] minMaxHeight = getMinMaxHeight(shape);
        int[] minMaxWidht = getMinMaxWidth(shape);

        int minHeight = minMaxHeight[0];
        int maxHeight = minMaxHeight[1];
        int minWidth = minMaxWidht[0];
        int maxWidth = minMaxWidht[1];

        int offsetHeight = minHeight + ((maxHeight-minHeight) / 2);
        int offsetWidth = minWidth + ((maxWidth-minWidth) / 2);

        rotateClockwise(rotatedShape, offsetHeight, offsetWidth);

        return rotatedShape;
    }

    private static void rotateClockwise(int[][] shape, int offsetHeight, int offsetWidth) {

        for (int i = 0; i < shape.length; i++) {
            int height = shape[i][0];
            int width = shape[i][1];

            int offH = offsetHeight - height;
            int offW = offsetWidth - width;

            offW = (offW < 0) ? Math.abs(offW) : -offW;

            int newH = offsetHeight + offW;
            int newW = offsetWidth + offH;

            shape[i] = new int[] {newH, newW};
        }

    }

    public static int[][] getShape(int boardWith) {
        int[][] shape = new int[BLOCK_COUNT][2];
        shape = createShape(shape);
        adjust(shape);
        center(shape, boardWith);
        return shape;
    }

    public static int[][] getPreviewShape(int[][] shape) {
        int[][] copyShape = copy(shape);
        fit(copyShape);
        return copyShape;
    }

    public static int[][] copy(int[][] shape) {
        return Arrays.stream(shape).map(int[]::clone).toArray(int[][]::new);
    }

    private static void fit(int[][] shape) {
        int[] minMaxHeight = getMinMaxHeight(shape);
        int[] minMaxWidth = getMinMaxWidth(shape);

        if (minMaxHeight[0] < 0) {
            for (int i = 0; i < Math.abs(minMaxHeight[0]); i++) {
                moveDown(shape);
            }
        }

        if (minMaxWidth[0] > 0) {
            for (int i = 0; i < Math.abs(minMaxWidth[0]); i++) {
                moveLeft(shape);
            }
        }
    }

    private static void center(int[][] shape, int width) {
        int offset = getOffset(shape, width);
        for (int i = 0; i < shape.length; i++) {
            shape[i][1] = shape[i][1] + offset;
        }
    }

    private static int getOffset(int[][] shape, int width) {
        int minMaxWidth[] = getMinMaxWidth(shape);
        int min = minMaxWidth[0];
        int max = minMaxWidth[1];

        int offset = 0;
        int figWidth = max - min;
        offset = (width/2) - figWidth;
        return offset;
    }

    public static int[] getMinMaxWidth(int[][] shape) {
        int[] result = new int[2];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < shape.length; i++) {
            if (shape[i][1] < min) {
                min = shape[i][1];
            }
            if (shape[i][1] > max) {
                max = shape[i][1];
            }
        }

        result[0] = min;
        result[1] = max;

        return result;
    }

    public static int[] getMinMaxHeight(int[][] shape) {
        int[] result = new int[2];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int i = 0; i < shape.length; i++) {
            if (shape[i][0] < min) {
                min = shape[i][0];
            }
            if (shape[i][0] > max) {
                max = shape[i][0];
            }
        }

        result[0] = min;
        result[1] = max;

        return result;
    }

    private static void adjust(int[][] shape) {
        boolean readjust = false;
        for (int i = 0; i < BLOCK_COUNT; i++) {
            if (shape[i][0] >= 0) {
                moveUp(shape);
                readjust = true;
            }
        }

        for (int i = 0; i < BLOCK_COUNT; i++) {
            if (shape[i][1] < 0) {
                moveRight(shape);
                readjust = true;
            }
        }

        if (readjust) {
            adjust(shape);
        }
    }

    private static void moveUp(int[][] shape) {
        for (int i = 0; i < BLOCK_COUNT; i++) {
            shape[i][0] = --shape[i][0];
        }
    }

    private static void moveDown(int[][] shape) {
        for (int i = 0; i < BLOCK_COUNT; i++) {
            shape[i][0] = ++shape[i][0];
        }
    }

    private static void moveRight(int[][] shape) {
        for (int i = 0; i < BLOCK_COUNT; i++) {
            shape[i][1] = ++shape[i][1];
        }
    }

    private static void moveLeft(int[][] shape) {
        for (int i = 0; i < BLOCK_COUNT; i++) {
            shape[i][1] = --shape[i][1];
        }
    }

    private static int[][] createShape(int[][] shape) {
        for (int i = 1; i < BLOCK_COUNT; i++) {
            boolean success = false;
            while (!success) {
                success = addItem(shape, i);
            }
        }
        return shape;
    }

    private static boolean addItem(int[][] shape, int i) {
        int index = random.nextInt(i);
        int[] nextItemToJoin = new int[] {shape[index][0], shape[index][1]};
        int pos[] = getNext(nextItemToJoin);
        if (!exists(shape, pos)) {
            shape[i] = pos;
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

    private static boolean exists(int shape[][], int[] pos) {
        for (int i = 0; i < shape.length; i++) {
            if (shape[i][0] == pos[0] && shape[i][1] == pos[1]) {
                return true;
            }
        }
        return false;
    }

}
