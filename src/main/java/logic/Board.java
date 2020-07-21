package logic;

import java.util.Arrays;

import static application.Game.endGame;
import static application.Game.getGame;
import static logic.ShapeGenerator.*;

public class Board {

    public static boolean moveShape(int[][] board, int[][] shape, int direction[]) {
        if (isGameOver(board)) {
            return false;
        }
        int[][] oldShape = getCopyOfShape(shape);
        boolean canMove = true;
        boolean isStuck = false;

        moveShapeBy(shape, direction);

        for (int[] block : shape) {
            if (!isPositionFree(board, block)) {
                canMove = false;
                if (direction[0] == 1) {
                    isStuck = true;
                }
            }
        }

        if (canMove) {
            setActiveShapeOnBoard(board, shape);
        } else {
            replaceShape(shape, oldShape);
            if (Arrays.equals(direction, new int[2])) {
                return false;
            }
            if (isStuck) {
                setShapePermanently(board, shape);
                fullRowCheck(board);
                return false;
            }
        }
        return true;
    }

    // ---------------------------------- SHAPE HANDLING ----------------------------------

    private static void setActiveShapeOnBoard(int[][] board, int[][] shape) {
        removeActiveShapeFromBoard(board);
        for (int i = 0; i < shape.length; i++) {
            if (shape[i][0] >= 0 ) {
                board[shape[i][0]][shape[i][1]] = -1;
            }
        }
    }

    private static void removeActiveShapeFromBoard(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                if (board[i][j] < 0) {
                    board[i][j] = 0;
                }
            }
        }
    }

    private static void setShapePermanently(int[][] board, int[][] shape) {
        for (int[] block : shape) {
            if (block[0] >= 0) {
                board[block[0]][block[1]] = 1;
            }
        }
    }

    // ---------------------------------- FULL ROW HANDLING ----------------------------------

    static void fullRowCheck(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (isRowFull(board[i])) {
                removeRowAt(i, board);
            }
        }
    }

    private static boolean isRowFull(int[] row) {
        for (int cell: row) {
            if (cell == 0) {
                return false;
            }
        }
        return true;
    }

    private static void removeRowAt(int index, int[][] board) {
        for (int i = index; i >= 0; i--) {
            if (i != 0) {
                board[i] = board[i - 1];
            } else {
                board[i] = new int[board[i].length];
            }
        }
        getGame().updatePoints();
    }

    // ---------------------------------- STATE CHECKS ----------------------------------

    private static boolean isGameOver(int[][] board) {
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == 1) {
                endGame();
                return true;
            }
        }
        return false;
    }

    private static boolean isPositionFree(int[][] board, int[] target) {
        if (target.length != 2) {
            return false;
        } else if (target[0] >= board.length) {
            return false;
        } else if (target[1] < 0) {
            return false;
        } else if (target[1] >= board[0].length) {
            return false;
        } else if (target[0] >= 0 && board[target[0]][target[1]] == 1) {
            return false;
        }
        return true;
    }

    // ---------------------------------- HELPER METHODS ----------------------------------

    static void print(int[][] board) {
        for (int[] row : board) {
            for (int cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
        System.out.print("\n\n");
    }

}
