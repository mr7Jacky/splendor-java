/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/17/19
 * Time: 10:25 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.GUI.graphics;

import javafx.scene.shape.Rectangle;

/**
 * This class provides the general shape for all the cards on the board
 * @author Jacky Lin
 */
public class CardIcon {
    /** the width of the card*/
    public static final int CARD_WIDTH = 60;
    /** the height of the card*/
    public static final int CARD_HEIGHT = 80;
    /** the shape of the card is a rectangle*/
    private Rectangle shape;

    /**
     * The constructor of the cards shape, initialize all the properties and set up all
     * @author Jacky Lin
     */
    public CardIcon(String ID) {
        shape = new Rectangle();
        shape.setWidth(CARD_WIDTH);
        shape.setHeight(CARD_HEIGHT);
        shape.setArcWidth(18.0);
        shape.setArcHeight(24.0);
        shape.setId(ID);
    }

    /**
     * Getter method for the shape
     * @return a rectangle object for the shape
     */
    public Rectangle getShape() { return shape; }
}
