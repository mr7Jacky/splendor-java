/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/14/19
 * Time: 9:54 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.game.exceptions;

/**
 * This exception will be thrown when player cannot afford to buy the card
 * @author Jacky Lin
 */
public class UnaffordablePurchaseException extends Exception{
    /**
     * The constructor of the exception
     * @param message the message to print
     */
    public UnaffordablePurchaseException(String message) {
        super(message);
    }
}
