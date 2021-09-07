/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/8/19
 * Time: 9:45 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.game;

/**
 * This class contains all the basic settings of the game, stored all as integers
 * @author Jacky Lin, Yida Chen, Wenyi Qian, Yili Wang
 */
public class GameSetting {
    /** the number of gem's type*/
    public final static int NUM_GEMS_TYPE = 5;
    /** the number of cards of each type on the board*/
    public final static int NUM_CARD_ON_BOARD = 4;
    /** the number of players in the game*/
    public static int NUM_PLAYER = 4;
    /** the max number of card can hold is 3*/
    public final static int MAX_RESERVED_CARD_NUM = 3;
    /** The number of cards' levels*/
    public final static int NUM_CARD_LEVEL = 3;
    /** The number of noble card is 1*/
    public final static int NUM_NOBLE = 1;
    /** The max point to win*/
    public static int WINNER_POINT = 15;
    /** The number of noble on the board*/
    public static int NOBLE_NUM = GameSetting.NUM_PLAYER + 1;
    /** The number of tokens on the board*/
    public static int TOKEN_NUM = GameSetting.NUM_PLAYER + 3;
    /** The AI number in the game*/
    public static int AI_NUM = 0;
    /** The number of the gold token*/
    public static int GOLD_TOKEN = NUM_PLAYER + 1;
    /** Enable debug mode*/
    public static boolean DEBUG_MODE = false;
    /** Setting of window size*/
    public static int Win_Height = 900;
    public static int Win_Width = 1300;

    /**
     * This method set the Ai number in the game
     * @param aiNum the number to set
     */
    public static void setAiNum(int aiNum) { GameSetting.AI_NUM = aiNum; }

    /**
     * This method set the player number in the game
     * @param PlayerNum the number to set
     */
    public static void setPlayerNum(int PlayerNum) {
        GameSetting.NUM_PLAYER = PlayerNum;
        GOLD_TOKEN = NUM_PLAYER + 1;
        TOKEN_NUM = GameSetting.NUM_PLAYER + 3;
        NOBLE_NUM = GameSetting.NUM_PLAYER + 1;
    }

    /**
     * This method set the winning in the game
     * @param point the number to set
     */
    public static void setWinnerPoint(int point) { GameSetting.WINNER_POINT = point; }
}
