package Splendor.game.components.token;

import javafx.scene.paint.Color;

import java.io.Serializable;

/**
 * This class contains all tokens of all different colors
 * @author Jacky Lin
 */
public enum Token implements Serializable {
    /** There are 5 normal tokens with different colors with {@link Color} aesthetic properties and one gold token*/
    WHITE(Color.WHITE),BLUE(Color.BLUE),GREEN(Color.GREEN),RED(Color.RED),BLACK(Color.BLACK),GOLD(Color.GOLD);
    /** The color of the token color*/
    private Color color;

    /**
     * The constructor of the tokens
     * @param color The color property of the token color
     */
    Token(Color color) {
        this.color = color;
    }

    /**
     * Getter method for the color
     * @return The color of curtain color token
     */
    public Color getColor() {
        return color;
    }
}
