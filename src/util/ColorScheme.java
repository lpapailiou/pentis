package util;

import javafx.scene.paint.Color;

public enum ColorScheme {

    LIGHT_MODE(Color.TRANSPARENT, Color.LIGHTBLUE, Color.LIGHTSALMON, Color.BLACK, Color.FLORALWHITE),
    DARK_MODE(Color.BLACK, Color.AQUA, Color.FUCHSIA, Color.LIME, Color.DARKGRAY);

    public Color appBackground;
    public Color activeShape;
    public Color shape;
    public Color textColor;
    public Color backgroundColor;

    ColorScheme(Color appBackground, Color shape, Color activeShape, Color textColor, Color backgroundColor) {
        this.appBackground = appBackground;
        this.shape = shape;
        this.activeShape = activeShape;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }
}
