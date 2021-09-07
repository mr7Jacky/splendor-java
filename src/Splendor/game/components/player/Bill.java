/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/2/19
 * Time: 1:46 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.game.components.player;

import Splendor.game.components.token.TokenList;

/**
 * This class is a container class used when a player tries to buy a card
 * with certain amount of normal tokens and gold tokens.
 * @author Jacky Lin
 */
public class Bill {

    /** the normal token part*/
    private TokenList paidTokenPrice;

    /** the gold token part*/
    private int paidGoldToken;

    /**
     * The constructor of the Bill class
     * @param tokenPrice the price of the normal tokens part
     * @param goldTokenPrice the price of the gold token part
     */
    public Bill(TokenList tokenPrice, int goldTokenPrice) {
        this.paidTokenPrice = tokenPrice;
        this.paidGoldToken = goldTokenPrice;
    }

    /**
     * The getter method of the token price
     * @return the token list indicate the normal token part
     */
    public TokenList getPaidTokenPrice() { return paidTokenPrice; }

    /**
     * The Getter method of the gold token price
     * @return a integer indicate the gold token part
     */
    public int getPaidGoldToken() { return paidGoldToken; }
}
