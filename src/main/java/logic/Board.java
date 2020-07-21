package logic;

import java.util.Arrays;

import static application.Game.endGame;
import static application.Game.getGame;
import static logic.ShapeGenerator.copy;
import static logic.ShapeGenerator.moveShapeBy;

public class Board {

    public static boolean moveShape(int[][] board, int[][] shape, int direction[]) {

        if (isGameOver(board)) {
            return false;
        }

        int[][] oldBoard = copy(board);
        int[][] oldShape = copy(shape);

        moveShapeBy(shape, direction);

        boolean canMove = true;
        boolean isStuck = false;

        for (int i = 0; i < shape.length; i++) {
            if (!isPositionFree(board, shape[i])) {
                canMove = false;
                if (direction[0] == 1) {
                    isStuck = true;
                }
            }
        }

        if (canMove) {
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] < 0) {
                        board[i][j] = 0;
                    }
                }
            }

            for (int i = 0; i < shape.length; i++) {
                move(board, shape[i]);
            }

        } else {
            if (Arrays.equals(direction, new int[2])) {
                return false;
            }
            for (int i = 0; i < shape.length; i++) {
                shape[i] = oldShape[i];
            }
            if (isStuck) {
                setShape(board, oldBoard);
                stateCheck(board);
                return false;
            }
        }
        return true;
    }

    private static void setShape(int[][] board, int[][] boardToReset) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Math.abs(boardToReset[i][j]);
            }
        }
    }

    public static void move(int[][] board, int[] target) {
        if (target[0] >= 0 && target[1] >= 0 && target[0] < board.length  && target[1] < board[0].length && board[target[0]][target[1]] != 1) {
            board[target[0]][target[1]] = -1;
        }
    }

    public static boolean isPositionFree(int[][] board, int[] target) {
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



    public static void stateCheck(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            if (isRowFull(board[i])) {
                removeRowAt(i, board);
            }
        }
    }

    public static boolean isGameOver(int[][] board) {
        for (int i = 0; i < board[0].length; i++) {
            if (board[0][i] == 1) {
                endGame();
                return true;
            }
        }
        return false;
    }

    // ---------------------------------- REMOVE FULL ROW ----------------------------------

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

    // ---------------------------------- PRINT TO CONSOLE ----------------------------------

    public static void print(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.print("\n\n");
    }

}
