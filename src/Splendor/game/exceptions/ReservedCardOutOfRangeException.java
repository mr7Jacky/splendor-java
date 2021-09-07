/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/14/19
 * Time: 9:35 PM
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
 * This class should be called when user is still trying to reserved cards,
 * while they have reach the limitation of max number of card can be reserved
 * @author Jacky Lin
 */
public class ReservedCardOutOfRangeException extends Exception{
    /**
     * The constructor of the exception
     * @param message the message to print
     */
    public ReservedCardOutOfRangeException(String message) {
        super(message);
    }
}
