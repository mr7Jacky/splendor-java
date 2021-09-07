/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/4/19
 * Time: 4:52 PM
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

import Splendor.game.components.token.Token;
import Splendor.game.components.token.TokenList;

import java.io.Serializable;
import java.util.Objects;

/**
 * This class represents the card object in the game.
 * Each card has one of the six gem colors and provide discount when purchasing other cards of the same color
 * integer 0, 1, 2 represent the level I, II, III, and level 3 represent the noble
 * There are four types of cards:
 *  1. One level cards, which have the lowest price and have 1 or no value.
 *     This kind of cards can be purchased in the early game for discount.
 *     Will be refilled to keep always 4 cards on the board.
 *
 *  2. Second level cards, which have medium price and have 1 to 3 value.
 *     This kind of cards can be purchased in the middle game to earn some points.
 *     Will be refilled to keep always 4 cards on the board.
 *
 *  3. Third level cards, which have highest price and have 3 to 5 value.
 *     This kind of cards can be purchased in the endgame to earn large amount of points.
 *     Will be refilled to keep always 4 cards on the board.
 *
 *  4. The Noble cards, which always have a value of 3. Cannot be bought.
 *     This kind of cards check its cost, which is some sets of cards of particular colors, every turn.
 *     If a player fulfills a noble's requirement, the noble will 'visit' the player to provide 3 points.
 *     Only one noble card can 'visit' one player each turn.
 *     If a player simultaneously fulfills multiple nobles' requirements, s/he should choose one of them.
 *     Will not be refilled. Starts with (number of player + 1) nobles on the board.
 *
 * @author Jacky Lin, Wenyi Qian
 */
public class Card implements Serializable {
    /** the value of the card*/
    private int value;
    /** the price of the card*/
    private TokenList price;
    /** the color property of the card*/
    private Token color;
    /** the level or type of the card*/
    private int level;

    /**
     * default constructor of the card class
     */
    public Card () {}

    /**
     * the constructor of the card class, initializes all the information of the card
     * @param level the level o type of the card
     * @param price the price of the card
     * @param value the value of the card
     * @param color the color of the card
     * @author Jacky Lin
     */
    public Card(String level, TokenList price, int value, Token color) {
        this.value = value;
        this.price = price;
        this.color = color;
        this.level = levelConverter(level);
    }

    /**
     * Getter method for the level
     * @return the level of the card
     */
    public int getLevel() { return level; }

    /**
     * This method creates a string of all the information of a card
     * @return a string contains the information of the card
     */
    @Override
    public String toString() {
        return "\n----------\nLevel: " + (this.level+1) + "\nPrice: " + this.price + "\nValue: " + this.value +
                "\nColor: " + this.color.name() + "\n----------\n";
    }

    /**
     * Getter method for the value
     * @return the value of the card
     */
    public int getValue() { return value; }

    /**
     * Getter method for the price
     * @return the price of the card
     */
    public TokenList getPrice() { return price; }

    /**
     * Getter method for the color
     * @return the color of the card
     */
    public Token getColor() { return color; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Card card = (Card) o;
        return value == card.value &&
                level == card.level &&
                Objects.equals(price, card.price) &&
                color == card.color;
    }

    /**
     * This class convert the level of the card into the integer
     * @param level the string represent the level
     * @return the integer represents the level
     */
    public int levelConverter(String level) {
        switch (level) {
            case "I": return 0;
            case "II": return 1;
            case "III": return 2;
            default: return 3;
        }
    }
}
