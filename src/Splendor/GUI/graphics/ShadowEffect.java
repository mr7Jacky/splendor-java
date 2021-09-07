package Splendor.GUI.graphics;


import javafx.scene.effect.DropShadow;
import javafx.scene.paint.Color;

/**
 * This class provides three types of the shadow effects: gold and black
 * @author Jacky Lin
 */
public enum ShadowEffect {

    GOLD(Color.GOLD), BLACK(Color.BLACK);
    /** the shadow effect*/
    private DropShadow shadow;

    /**
     * the constructor of the shadow
     * @param shadowColor the color of the shadow
     * @author Jacky Lin
     */
    ShadowEffect(Color shadowColor) {
        /** the color of the shadow*/
        this.shadow = new DropShadow(+25d, 0d, +2d, shadowColor);
    }

    /**
     * the getter method of the shadow
     * @return the shadow
     */
    public DropShadow getShadow() { return shadow; }
}
