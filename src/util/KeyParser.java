package util;

import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.Arrays;

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
        }
        return dir;
    }

}
