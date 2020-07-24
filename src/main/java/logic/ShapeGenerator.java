package logic;

import java.util.Arrays;
import java.util.Random;

import static util.Setting.BLOCK_COUNT;

public class ShapeGenerator {

    private static Random random = new Random();

    // ---------------------------------- GET SHAPE ----------------------------------

    static int[][] getShape(int boardWith) {
        int[][] shape = createShape();
        setToInitialPosition(shape, boardWith);
        return shape;
    }

    public static int[][] getPreviewShape(int[][] shape) {
        int[][] copyShape = getCopyOfShape(shape);
        setToPreviewPosition(copyShape);
        return copyShape;
    }

    // ---------------------------------- CREATE SHAPE ----------------------------------

    private static int[][] createShape() {
        int[][] shape = new int[BLOCK_COUNT][2];
        for (int i = 1; i < BLOCK_COUNT; i++) {
            boolean success = false;
            while (!success) {
                success = addBlock(shape, i);
            }
        }
        return shape;
    }

    private static boolean addBlock(int[][] shape, int i) {
        int index = random.nextInt(i);
        int[] nextItemToJoin = new int[] {shape[index][0], shape[index][1]};
        int[] block = getNextAdjacentBlock(nextItemToJoin);
        if (!blockExists(shape, block)) {
            shape[i] = block;
            return true;
        }
        return false;
    }

    private static int[] getNextAdjacentBlock(int[] block) {
        int index = random.nextInt(2);
        int next = random.nextInt(2);
        next = next == 0 ? -1 : next;
        int[] newBlock;
        if (index == 0) {
            newBlock = new int[] {block[0]+next, block[1]};
        } else {
            newBlock = new int[] {block[0], block[1]+next};
        }
        return newBlock;
    }

    private static boolean blockExists(int[][] shape, int[] compareBlock) {
        for (int[] block : shape) {
            if (block[0] == compareBlock[0] && block[1] == compareBlock[1]) {
                return true;
            }
        }
        return false;
    }

    // ---------------------------------- SHAPE POSITIONING ----------------------------------

    static void moveShapeBy(int[][] shape, int[] direction) {
        if (Arrays.equals(direction, new int[2])) {
            rotateShape(shape);
        } else {
            for (int i = 0; i < shape.length; i++) {
                shape[i][0] = shape[i][0] + direction[0];
                shape[i][1] = shape[i][1] + direction[1];
            }
        }
    }

    private static void setToInitialPosition(int[][] shape, int boardWidth) {
        int[] minMaxHeight = getMinMaxDimension(shape, 0);
        int[] minMaxWidth = getMinMaxDimension(shape, 1);
        int center = minMaxWidth[1] - (minMaxWidth[1] - minMaxWidth[0])/2;

        if (minMaxHeight[1] != -1) {
            moveShapeBy(shape, new int[]{-(minMaxHeight[1]+1), 0});
        }

        if (center != (boardWidth/2)) {
            moveShapeBy(shape, new int[]{0, ((boardWidth/2)-center-1)});
        }
    }

    private static void setToPreviewPosition(int[][] shape) {
        int[] minMaxHeight = getMinMaxDimension(shape, 0);
        int[] minMaxWidth = getMinMaxDimension(shape, 1);

        if (minMaxHeight[0] != 0) {
            moveShapeBy(shape, new int[]{-minMaxHeight[0], 0});
        }

        if (minMaxWidth[0] != 0) {
            moveShapeBy(shape, new int[]{0, -minMaxWidth[0]});
        }
    }

    static void rotateShape(int[][] shape) {
        int[] minMaxHeight = getMinMaxDimension(shape, 0);
        int[] minMaxWidht = getMinMaxDimension(shape, 1);
        int offsetHeight = minMaxHeight[0] + ((minMaxHeight[1]-minMaxHeight[0]) / 2);
        int offsetWidth = minMaxWidht[0] + ((minMaxWidht[1]-minMaxWidht[0]) / 2);
        rotateClockwise(shape, offsetHeight, offsetWidth);
    }

    private static void rotateClockwise(int[][] shape, int offsetHeight, int offsetWidth) {
        for (int i = 0; i < shape.length; i++) {
            int offH = offsetHeight - shape[i][0];
            int offW = offsetWidth - shape[i][1];

            offW = (offW < 0) ? Math.abs(offW) : -offW;

            shape[i] = new int[] {offsetHeight + offW, offsetWidth + offH};
        }
    }

    // ---------------------------------- HELPER METHODS ----------------------------------

    static int[][] getCopyOfShape(int[][] shape) {
        return Arrays.stream(shape).map(int[]::clone).toArray(int[][]::new);
    }

    static void replaceShape(int[][] shape, int[][] resettingShape) {
        for (int i = 0; i < shape.length; i++) {
            shape[i][0] = resettingShape[i][0];
            shape[i][1] = resettingShape[i][1];
        }
    }

    public static int[] getMinMaxDimension(int[][] shape, int dimension) {
        int[] result = new int[2];
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;

        for (int[] block : shape) {
            if (block[dimension] < min) {
                min = block[dimension];
            }
            if (block[dimension] > max) {
                max = block[dimension];
            }
        }

        result[0] = min;
        result[1] = max;

        return result;
    }

}
