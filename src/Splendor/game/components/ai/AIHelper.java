/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/5/19
 * Time: 1:09 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.game.components.ai;

import Splendor.GUI.model.SplendorModel;
import Splendor.game.components.player.Player;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.GameSetting;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This class provides a variety of method that might used in the AI class
 * @author Jacky Lin
 */
public class AIHelper {
    /** the model of the game*/
    private SplendorModel theModel;

    /**
     * The constructor of the utility
     * It initialize the model of the game for the further use
     * @param theModel the model of the game
     * @author Yida Chen
     */
    public AIHelper(SplendorModel theModel) {
        this.theModel = theModel;
    }

    /**
     * initialize the score board with certain length
     * @param len the len of the score board
     * @author Jacky Lin
     */
    public ArrayList<Integer> initialScoreBoard(int len) {
        ArrayList<Integer> scoreBoard = new ArrayList<>();
        for (int j = 0; j < len; j++) { scoreBoard.add(0); }
        return (ArrayList<Integer>)scoreBoard.clone();
    }

    /**
     * This method read the nobles from the noble and count for color occurrence
     * If a color have more occurrence in noble,
     * The card with the same color will have a higher chance to be buy.
     * @author Jacky Lin
     */
    public Hashtable<String, Integer>  initNobleOccur() {
        ArrayList<Card> nobles = theModel.getSplendorGame().getNobles();
        Hashtable<String, Integer>  colorOccurrenceInNobles = new Hashtable<>();
        for (Card n : nobles) {
            for (Token t : n.getPrice().getTokens().keySet()) {
                int times = n.getPrice().getTokens().get(t);
                int discountGemNum = theModel.getCurrentPlayer().getCards().getTokens().get(t);
                //get the previous number of in the color occurrence table
                Integer prev = colorOccurrenceInNobles.get(t.name());
                if (prev == null) { prev = 0; }
                // if we have enough token to get the noble, we don't need to collect it anymore
                if (discountGemNum > times) { colorOccurrenceInNobles.put(t.name(),0); }
                else{ colorOccurrenceInNobles.put(t.name(), times + prev); }
            }
        }
        return colorOccurrenceInNobles;
    }

    /**
     * This method checks the token on the board,
     * the less the amount of one token, the more likely the AI will take that.
     * @author Jacky Lin
     */
    public ArrayList<Integer> checkTokenNumberOnBoard(ArrayList<Integer> tokenScore) {
        for (Token c: theModel.getSplendorGame().getTokenOnBoard().getTokens().keySet()) {
            int numOfTokenOnBoard = theModel.getSplendorGame().getTokenOnBoard().getTokens().get(c);
            switch (numOfTokenOnBoard) {
                case 0:
                    tokenScore.set(c.ordinal(), -1);
                    break;
                case 1:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 4);
                    break;
                case 2:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 3);
                    break;
                case 3:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 2);
                    break;
                case 4:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 1);
                    break;
                default: break;
            }
        }
        return tokenScore;
    }

    /**
     * This method will analysis the intended card and add token to the card
     * @param intendedCard the intended card to buy
     * @author Jacky Lin
     */
    public ArrayList<Integer> analysisIntendedCard( Card intendedCard) {
        ArrayList<Integer> tokenScore = initialScoreBoard(GameSetting.NUM_GEMS_TYPE);
        for (Token c : intendedCard.getPrice().getTokens().keySet()) {
            int diff = intendedCard.getPrice().getTokens().get(c) - theModel.getCurrentPlayer().getTokenOwns().getTokens().get(c)
                    - theModel.getCurrentPlayer().getCards().getTokens().get(c);
            // the less token one color need, the more need to have
            switch (diff) {
                case 1:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 2);
                    break;
                case 2:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 3);
                    break;
                case 3:
                    tokenScore.set(c.ordinal(), tokenScore.get(c.ordinal()) + 4);
                    break;
                default:
                    break;
            }
        }
        return tokenScore;
    }

    /**
     * This method compare the token of two token list to the certain card of specific color
     * @param card the card to compare
     * @param player the player try to buy the card
     * @param tokens the color to compare
     * @return a integer indicate how many more token need to to get the card
     */
    public int getTokenDiff(Card card, Player player, Token tokens) {
        return card.getPrice().getNumToken(tokens) - player.getTokenOwns().getNumToken(tokens) - player.getCards().getNumToken(tokens);
    }


    /**
     * Get the token from the highest point to the lowest point
     * @return a array list of token to obtain
     * @author Jacky Lin
     */
    public ArrayList<Token> getIntendedToken(ArrayList<Integer> tokenScore) {
        //get the most significant one
        ArrayList<Token> tokenToObtain = new ArrayList<>();
        while (tokenToObtain.size() < 3 && theModel.getSplendorGame().getTokenOnBoard().getSize() > 3) {
            int maxIndex = tokenScore.indexOf(Collections.max(tokenScore));
            tokenScore.set(maxIndex,-1);
            Token tokenToHave = Token.values()[maxIndex];
            if (theModel.getSplendorGame().getTokenOnBoard().getNumToken(tokenToHave) > 0) { tokenToObtain.add(tokenToHave); }
        }
        return tokenToObtain;
    }

    /**
     * This method help to identify if a card is affordable
     * @param intendedCard the card to buy
     * @return true if the player can buy, false if this player cannot buy it
     * @author Jacky Lin, Yida Chen
     */
    public boolean isAffordableCard(Card intendedCard) {
        for (Token c : intendedCard.getPrice().getTokens().keySet()) {
            if (getTokenDiff(intendedCard, theModel.getCurrentPlayer(), c) > theModel.getCurrentPlayer().getGoldToken()) {
                return false;
            }
        }
        return true;
    }
}
