package application;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Board {

    final static int HEIGHT = 5;
    final static int WIDTH = 5;
    public static int[][] board = new int[HEIGHT][WIDTH];

    public static boolean moveFigure(int[][] board, int[][] figure, int direction[]) {

        int[][] oldBoard = Arrays.copyOf(board, board.length);
        int[][] oldFigure = Arrays.copyOf(figure, figure.length);

        for (int i = 0; i < figure.length; i++) {
            figure[i] = new int[]{figure[i][0] + direction[0], figure[i][1] + direction[1]};
        }

        boolean canMove = true;
        boolean isStuck = false;

        for (int i = 0; i < figure.length; i++) {
            if (!isPositionFree(board, figure[i])) {
                canMove = false;
                if (direction[0] == 1) {
                    isStuck = true;
                }
            }
        }

        if (canMove) {
            boolean coordToBeCleared = true;
            for (int[] oldF : oldFigure) {
                boolean exists = false;
                for (int[] newF : figure) {
                    if (newF[0] == oldF[0] && newF[1] == oldF[1]) {
                        exists = true;
                    }
                }
                if (!exists) {
                    if (oldF[0] >= 0
                            && oldF[0] < board.length
                            && oldF[1] >= 0
                            && oldF[1] < board[oldF[0]].length
                            && coordToBeCleared) {
                        board[oldF[0]][oldF[1]] = 0;
                    }
                }
            }

            for (int i = 0; i < figure.length; i++) {
                move(board, figure[i]);
            }

        } else if (isStuck) {
            setFigure(board, oldBoard);
            return false;
        }

        return true;
    }

    private static void setFigure(int[][] board, int[][] boardToReset) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                board[i][j] = Math.abs(boardToReset[i][j]);
            }
        }
    }

    public static void move(int[][] board, int[] target) {
        if (target[0] >= 0 && target[1] >= 0 && board[target[0]][target[1]] != 1) {
            board[target[0]][target[1]] = -1;
        }
    }

    public static boolean isPositionFree(int[][] board, int[] target) {
        if (target.length != 2) {
            return false;
        } else if (target[0] >= board.length) {
            return false;
        } else if (target[1] < 0 || target[1] >= board[0].length) {
            return false;
        } else if (target[0] >= 0 && target[1] >= 0 && board[target[0]][target[1]] == 1) {
            return false;
        }
        return true;
    }

    public static void print(int[][] board) {
        for (int i = 0; i < board.length; i++) {
            for (int j = 0; j < board[i].length; j++) {
                System.out.print(board[i][j] + "\t");
            }
            System.out.println();
        }
        System.out.print("\n\n");
    }

    public static void stateCheck(int[][] board) {
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
                board[i] = new int[WIDTH];
            }
        }
    }

    public static void reset(int board[][]) {
        board = new int[HEIGHT][WIDTH];
    }

}
