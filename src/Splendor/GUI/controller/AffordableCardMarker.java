/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/10/19
 * Time: 6:20 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.GUI.controller;

import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import Splendor.game.GameSetting;
import Splendor.game.components.cards.Card;
import Splendor.game.components.token.Token;
import Splendor.game.components.token.TokenList;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

/**
 * This class help the user to identify those card that is purchasable.
 * For each card that is affordable on the board, card will be indicated by a yellow frame
 * The Yellow Marker will be refresh in the beginning of each turn
 * @author Yili Wang
 */
public class AffordableCardMarker {

    /**
     * Clean the Card Borders set for the previous player
     * @param theView the View of the splendor
     * @author Yili Wang
     */
    public static void resetCardBorder(SplendorView theView, SplendorModel theModel){
        for (int i = 0; i < GameSetting.NUM_CARD_LEVEL; i++) {
            for (int j = 0; j < GameSetting.NUM_CARD_ON_BOARD; j++) {
                theView.getCardStackList()[i][j].setBorder((new Border(new BorderStroke(Color.TRANSPARENT,
                        BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3)))));
            }
        }
        AffordableCardMarker.resetReservedBorder(theView,theModel);
    }

    /**
     * Clean the reserved Card Borders set for the previous player
     * @param theView the View of the splendor
     * @author Yili Wang
     */
    private static void resetReservedBorder(SplendorView theView, SplendorModel theModel) {
        for (int i = 0; i < GameSetting.MAX_RESERVED_CARD_NUM; i++) {
            theView.getReserveCardStackPane()[Integer.parseInt(theModel.getCurrentPlayer().getName())][i].
                    setBorder((new Border(new BorderStroke(Color.TRANSPARENT,
                    BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3)))));
        }
    }

    /**
     * Check if there is any card the current player can buy and set border for this card
     * @param theView the View of the splendor
     * @author Yili Wang
     */
    public static void markAffordableCard(SplendorView theView, SplendorModel theModel) {
        for (int i = 0; i < theModel.getSplendorGame().getCardOnTable().size(); i++) {
            for (int j = 0; j < theModel.getSplendorGame().getCardOnTable().get(i).size(); j++) {
                // If card is affordable
                Card currentCard = theModel.getSplendorGame().getCardOnTable().get(i).get(j);
                if (currentCard!= null && isCardAffordable(currentCard,theModel)){
                    try {
                        // Mark the card with yellow ring
                        theView.getCardStackList()[i][j].setBorder(new Border(new BorderStroke(Color.YELLOW,
                                BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3))));
                    } catch (Exception e) {}
                }
            }
        }

        for (int i = 0; i < GameSetting.MAX_RESERVED_CARD_NUM; i++) {
            if (theModel.getCurrentPlayer().getReservedCardDeck()[i] != null) {
                if (AffordableCardMarker.isCardAffordable(theModel.getCurrentPlayer().getReservedCardDeck()[i], theModel)) {
                    try {
                        theView.getReserveCardStackPane()[Integer.parseInt(theModel.getCurrentPlayer().getName())][i].
                                setBorder(new Border(new BorderStroke(Color.YELLOW, BorderStrokeStyle.SOLID,
                                        new CornerRadii(10), new BorderWidths(3))));
                    } catch (Exception e) {
                    }
                }
            }
        }
    }

    /**
     * Check if a card is affordable by the current player
     * @param card the card to check
     * @return True if the card is affordable; not otherwise
     * @author Yili Wang, Jacky Lin
     */
    public static boolean isCardAffordable(Card card, SplendorModel theModel){
        // Calculate how many gold token need to use to buy the card
        if (card == null) { return false; }
        TokenList pay = new TokenList();
        int requiredGoldToken = 0;
        for (Token c : card.getPrice().getTokens().keySet()) {
            int tokenPaid = card.getPrice().getTokens().get(c) - theModel.getCurrentPlayer().getCards().getTokens().get(c);
            int diff = tokenPaid - theModel.getCurrentPlayer().getTokenOwns().getTokens().get(c);
            if (tokenPaid < 0) { continue; }
            pay.getTokens().put(c, tokenPaid);
            if (diff > 0) {
                pay.getTokens().put(c, tokenPaid - diff);
                requiredGoldToken += diff;
            }
        }
        if ( requiredGoldToken <= theModel.getCurrentPlayer().getGoldToken()) { return true; }
        else {return false;}
    }
}
