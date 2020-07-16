package application;

import java.util.Arrays;

public class Board {

    final static int HEIGHT = 5;
    final static int WIDTH = 5;
    public static int[][] board = new int[HEIGHT][WIDTH];

    public static boolean moveFigure(int[][] board, int[][] figure, int direction[]) {

        // TODO: method ugly; should stop one step earlier

        int[][] oldBoard = Arrays.copyOf(board, board.length);

        int[][] oldFigure = Arrays.copyOf(figure, figure.length);

        for (int i = 0; i < figure.length; i++) {
            int oldRow = figure[i][0];
            int oldCol = figure[i][1];
            figure[i] = new int[]{oldRow + direction[0], oldCol + direction[1]};
        }

        boolean reset = false;

        for (int i = 0; i < figure.length; i++) {
            int oldRow = figure[i][0]- direction[0];
            int oldCol = figure[i][1]- direction[1];
            if (isPositionFree(board, figure[i])) {
                move(board, figure[i]);
                boolean coordToBeCleared = true;
                for (int[] f: figure) {
                    if (f[0] == oldFigure[i][0] && f[1] == oldFigure[i][1]) {
                        coordToBeCleared = false;
                    }
                }

                if (oldRow >= 0
                        && oldRow < board.length
                        && oldCol >= 0
                        && oldCol < board[i].length
                        && coordToBeCleared) {
                    board[oldRow][oldCol] = 0;
                }

            } else if (direction[0] == 1) {
                reset = true;
            }
        }

        if (reset) {
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
