package util;

public class Setting {

    public static final int BLOCK_COUNT = 5;
    public static final int BOARD_HEIGHT = 17;
    public static final int BOARD_WITH = 10;

    public static final int DELAY = 500;

    public static ColorScheme COLOR_MODE = ColorScheme.LIGHT_MODE;
    //public static final ColorScheme COLOR_MODE = ColorScheme.DARK_MODE;

    public static final int HEIGHT = 500;
    public static final int WIDTH = 300;

    public static final int PREVIEW_HEIGHT = 200;
    public static final int PREVIEW_WIDTH = 200;

    public static final  int CELL_PADDING = 3;

    public static int PADDING_VERTICAL = 20;
    public static int PADDING_HORIZONTAL = 20;
    public static int CELL_H = ((HEIGHT - 2*PADDING_VERTICAL) / BOARD_HEIGHT);
    public static int CELL_W = ((WIDTH - 2*PADDING_HORIZONTAL) / BOARD_WITH);

    static {
        if (CELL_H < CELL_W) {
            CELL_W = CELL_H;
            PADDING_HORIZONTAL = (WIDTH - (CELL_H * BOARD_WITH)) / 2;
        } else if (CELL_H > CELL_W) {
            CELL_H = CELL_W;
            PADDING_VERTICAL = (HEIGHT - (CELL_W * BOARD_HEIGHT)) / 2;
        }
    }
}
