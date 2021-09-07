/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/8/19
 * Time: 8:57 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.game.components.cards;

import Splendor.game.components.token.TokenList;

import java.io.Serializable;

/**
 * This class represents the noble class as a subset of {@link Card}
 * It will have the following features:
 *  1. It cannot be bought.
 *  2. It always has a value of 3 points.
 *  3. It will come to "visit" you as long as at end of the turn you fulfill the requirement of it.
 *  4. For each player, only one noble will come to visit at the end of the turn.
 *     If the player fulfill more than one nobles' requirements, s/he should select one to come.
 *  5. Unlike three levels of purchasable cards, it won't be refilled once "gone"
 * @author Jacky Lin, Wenyi Qian
 */
public class Noble extends Card implements Serializable {
    /** the value of the noble card*/
    private int value;
    /** the prerequisite of the noble card*/
    private TokenList requirement;
    /** the type of the noble card*/
    private int level;

    /**
     * The constructor of the noble card class
     * It initializes its value to 3 and card level as "Noble"
     * It is assigned to have certain price(cards) as prerequisite of a noble
     * @param requirement the prerequisites of the noble card
     * @author Jacky
     */
    public Noble(TokenList requirement) {
        this.value = 3;
        this.requirement = requirement;
        this.level = 3;
    }

    /**
     * This method creates a string of all the information that a noble has
     * @return a string contains the information of the noble
     */
    @Override
    public String toString() {
        return "\n----------\nLevel: Noble"+"\nRequirement: " + this.requirement + "\nValue: " + this.value + "\n----------\n";
    }

    /**
     * Getter method for the value(point)
     * @return the value of the noble
     */
    public int getValue() { return value; }

    /**
     * Getter method for the requirement
     * @return the requirement of the noble
     */
    public TokenList getPrice() { return requirement; }

    /**
     * Getter method for the level
     * @return the level of the card, here particularly being noble
     */
    public int getLevel() { return level; }
}
