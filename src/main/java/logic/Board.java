package logic;

import static application.Game.*;
import static logic.ShapeGenerator.*;
import static util.Setting.BOARD_HEIGHT;
import static util.Setting.BOARD_WITH;

public class Board {

    public static int[][] board = new int[BOARD_HEIGHT][BOARD_WITH];
    public static int[][] shape = getShape(board[0].length);
    public static int[][] nextShape = getShape(board[0].length);

    public static boolean moveShape(int[][] board, int[][] shape, int direction[]) {
        if (isGameOver(board)) {
            return true;
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
            if (isStuck) {
                setShapePermanently(board, shape);
                fullRowCheck(board);
                setNextShape();
                return true;
            }
        }
        return false;
    }

    // ---------------------------------- SHAPE HANDLING ----------------------------------

    private static void setActiveShapeOnBoard(int[][] board, int[][] shape) {
        removeActiveShapeFromBoard(board);
        for (int[] block : shape) {
            if (block[0] >= 0) {
                board[block[0]][block[1]] = -1;
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

    // ---------------------------------- STATE HANDLING ----------------------------------

    public static void resetBoard() {
        board = new int[BOARD_HEIGHT][BOARD_WITH];
        shape = getShape(board[0].length);
        nextShape = getShape(board[0].length);
    }

    private static void setNextShape() {
        shape = getCopyOfShape(nextShape);
        nextShape = getShape(board[0].length);
        drawPreview();
    }

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
        } else return target[0] < 0 || board[target[0]][target[1]] != 1;
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
