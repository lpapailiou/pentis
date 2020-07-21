package util;

import javafx.scene.paint.Color;

public enum ColorScheme {

    LIGHT_MODE(Color.WHITE, Color.LIGHTBLUE, Color.LIGHTSALMON, Color.LIGHTGRAY, Color.BLACK, Color.FLORALWHITE),
    DARK_MODE(Color.BLACK, Color.AQUA, Color.FUCHSIA, Color.GRAY, Color.LIME, Color.DIMGREY);

    public Color appBackground;
    public Color activeShape;
    public Color shape;
    public Color preview;
    public Color textColor;
    public Color backgroundColor;

    ColorScheme(Color appBackground, Color shape, Color activeShape, Color preview, Color textColor, Color backgroundColor) {
        this.appBackground = appBackground;
        this.shape = shape;
        this.activeShape = activeShape;
        this.preview = preview;
        this.textColor = textColor;
        this.backgroundColor = backgroundColor;
    }
}
