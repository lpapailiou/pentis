package util;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class KeyParser {

    public static int[] handleKeyPress(KeyEvent e) {
        KeyCode key = e.getCode();
        int dir[] = {0, 0};
        switch (key) {
            case UP:
                dir = new int[] {0, 0};
                break;
            case DOWN:
                dir = new int[] {1, 0};
                break;
            case LEFT:
                dir = new int[] {0, -1};
                break;
            case RIGHT:
                dir = new int[] {0, 1};
                break;
            case SPACE:
                dir = new int[] {-9, 0};
                break;
            case P:
                dir = new int[] {-9, 1};
                break;
            case ENTER:
            case Y:
                dir = new int[] {-9, 2};
                break;
            case N:
                dir = new int[] {-9, 3};
                break;
        }
        return dir;
    }

}
