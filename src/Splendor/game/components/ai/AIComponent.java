/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52AM
 * Date: 11/18/19
 * Time: 7:52 PM
 *
 * Project: JavaTest
 * Package: Lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.game.components.ai;

import Splendor.GUI.GUIUtility;
import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import Splendor.game.components.player.Bill;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.exceptions.ReservedCardOutOfRangeException;
import Splendor.game.exceptions.UnaffordablePurchaseException;
import Splendor.game.GameSetting;
import Splendor.game.components.token.TokenList;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;

/**
 * This class provides an AI to carry out one of the actions a players can have.
 * It will read the game model and do the actions for the current players automatically
 * There is a factor that decide different behaviours AI can have
 * @author Jacky Lin, Yida Chen
 */
public class AIComponent {
    /** the color occurrence of the noble price for the judgements*/
    private Hashtable<String, Integer> colorOccurrenceInNobles;
    /** the model of the game*/
    private SplendorModel theModel;
    /** the view of the splendor **/
    private SplendorView theView;
    /** the factor controls different behaviour of the ai*/
    private float factor;
    /** the utility that facilitate the ai class*/
    private AIHelper aiHelper;

    /**
     * The constructor of the AI class
     * It initialize some instance variables and read the data from the model
     * @author Jacky Lin
     */
    public AIComponent(SplendorModel theModel, SplendorView theView, float factor) {
        this.theModel = theModel;
        this.aiHelper = new AIHelper(theModel);
        this.colorOccurrenceInNobles = aiHelper.initNobleOccur();
        this.theView = theView;
        this.factor = factor;

    }

    /**
     * The class will decide which action to used depending on the current situation on the board
     * It will help the current player to do the action
     * @author Jacky Lin
     */
    public void pickActions() {
        // create a array list store all cards on board
        ArrayList<Card> cardsToPick = new ArrayList<>();
        for (int i = 0; i < GameSetting.NUM_CARD_LEVEL; i++) {
            for (Card card : theModel.getSplendorGame().getCardOnTable().get(i)) {
                if (card != null) { cardsToPick.add(card); }
            }
        }
        Card intendedCard = pickCard(cardsToPick);
        System.out.println("intended card"+intendedCard);
        if (theModel.getCurrentPlayer().getTokenOwns().getSize() < 4) {
            System.out.println(1);
            //get token
            pickTokens(intendedCard);
        } else {
            //purchase card
            ArrayList<Card> affordableCard = new ArrayList<>();
            boolean processNextStep = true;
            for (int i = 0; i < theModel.getCurrentPlayer().getReservedCardDeck().length; i++) {
                Card currentCard = theModel.getCurrentPlayer().getReservedCardDeck()[i];
                if (currentCard != null && aiHelper.isAffordableCard(currentCard)) {
                    try {
                        GUIUtility.purchaseReservedCard(theModel,theView,theModel.getCurrentPlayer().getReservedCardDeck()[i],i);
                        GUIUtility.removeCardFromReservePane(theView, theModel,GUIUtility.getCurrentPlayerIndex(theModel),i);
                        processNextStep = false;
                    } catch (UnaffordablePurchaseException ignored){ }
                }
            }
            if (processNextStep) {
                // create a list of affordable card
                for (Card card : cardsToPick) {
                    if (aiHelper.isAffordableCard(card)) { affordableCard.add(card); }
                }
                if (!affordableCard.isEmpty()) {
                    Card affordableIntendedCard = pickCard(affordableCard);
                    AIPlayerPurchaseCard(affordableIntendedCard);
                } else if (theModel.getSplendorGame().getTokenOnBoard().getSize() > 3) {
                    getTokenForIntendedCard(intendedCard);
                } else {
                    reserveCard(intendedCard);
                }
            }
        }
        //update card
        GUIUtility.updateTokenOnBoard(theView,theModel);
    }

    /**
     * Let AI reserve a card from the table and update the informations
     * @param targetCard The targeted card chosen by the player
     * @author Yida Chen, Jacky Lin
     */
    private void reserveCard(Card targetCard) {
        int reserveIndex = theModel.getCurrentPlayer().findOpenReservedCardIndex();
        try {
            boolean hasGoldenToken = theModel.getSplendorGame().getGoldTokenNum() > 0;
            theModel.getCurrentPlayer().reserveCard(targetCard, hasGoldenToken);
            //update the information
            if (hasGoldenToken) { theModel.getSplendorGame().removeGoldToken(); }
            GUIUtility.updateTokenOnBoard(theView,theModel);
            GUIUtility.updateCardOnBoard(targetCard, theModel, theView);
            GUIUtility.updateCardToReservePane(theView, theModel, targetCard, reserveIndex);
        } catch (ReservedCardOutOfRangeException ignored) { }
    }

    /**
     * This method allows AI Player to buy the card and update card on the board
     * @param intendedCard the intended card to buy
     * @author Yida Chen, Jacky Lin
     */
    private void AIPlayerPurchaseCard(Card intendedCard){
        // handle the exception and buy the card
        try {
            Bill bill = theModel.getCurrentPlayer().purchaseCard(intendedCard);
            theModel.getSplendorGame().getTokenOnBoard().addToken(bill.getPaidTokenPrice());
            theModel.getSplendorGame().addGoldToken(bill.getPaidGoldToken());
            GUIUtility.updateCardOnBoard(intendedCard, theModel, theView);
        }
        catch (UnaffordablePurchaseException e) { e.printStackTrace(); }
    }

    /**
     * This method helps get tokens according to the card AI wants to buy
     * @param intendedCard the card to buy
     * @author Jacky Lin
     */
    private void getTokenForIntendedCard(Card intendedCard) {
        ArrayList<Token> tokenObtain = new ArrayList<>();
        for (Token c : intendedCard.getPrice().getTokens().keySet()) {
            int diff = aiHelper.getTokenDiff(intendedCard,theModel.getCurrentPlayer(),c);
            if (diff > 0 && theModel.getSplendorGame().getTokenOnBoard().getNumToken(c) > 0) {
                tokenObtain.add(c);
                if (tokenObtain.size() == 3) { break; }
            }
        }
        theModel.getCurrentPlayer().obtainTokens(tokenObtain);
        for (Token t : tokenObtain) { this.theModel.getSplendorGame().getTokenOnBoard().removeToken(t); }
    }

    /**
     * This method decide which token to pick, according to 2 parameter:
     *  1. the token needed to purchase the intended card
     *  2. the token number on the board, the less the certain token is on the board,
     *  the more willing to obtain that token. If the token on the board is 0, the willingness will become 0
     * @param intendedCard the intended card to buy
     * @author Jacky Lin
     */
    private void pickTokens(Card intendedCard) {
        ArrayList<Integer> tokenScore = aiHelper.analysisIntendedCard(intendedCard);
        aiHelper.checkTokenNumberOnBoard(tokenScore);
        ArrayList<Token> tokenToObtain = aiHelper.getIntendedToken(tokenScore);
        this.theModel.getCurrentPlayer().obtainTokens(tokenToObtain);
        for (Token t : tokenToObtain) { this.theModel.getSplendorGame().getTokenOnBoard().removeToken(t); }
    }


    /**
     * This class check all the cards in the array list and pick the most needed cards
     * @param cards the card list to have
     * @return the cards to purchase or reserve
     * @author Jacky Lin
     */
    public Card pickCard(ArrayList<Card> cards) {
        ArrayList<Integer> cardScore = aiHelper.initialScoreBoard(cards.size());
        TokenList colorNeedMostList = new TokenList();
        for (int i = 0; i < cards.size(); i++) {
            Card currentCard = cards.get(i);
            int score = 0;
            // the level affect the score
            score += (2 - currentCard.getLevel()) * 3 + 1;
            // when it is too expensive, the total number of color all noble have, used in the later calculations
            if (theModel.getCurrentPlayer().getTokenOwns().compareTokenList(currentCard.getPrice()) > 2) {
                colorNeedMostList = ExpensiveCardChecker(currentCard);
            }
            // check the occurrence of the card
            for (String colorName : colorOccurrenceInNobles.keySet()) {
                if (colorName.equals(currentCard.getColor().name()) ){
                    int occurrenceScore= colorOccurrenceInNobles.get(colorName);
                    score += occurrenceScore * Math.pow(this.factor,2);
                }
            }
            //if card is almost affordable, we should consider to reserved the card, but it should be worth reserve
            score += currentCard.getValue() * Math.sqrt(factor);
            //if card contains the most need color, we should more buy
            score += colorNeedMostList.getTokens().get(currentCard.getColor()) * Math.sqrt(1 - factor);
            cardScore.set(i,score);
        }
        // find the card with the highest score and return the card
        int maxScore = Collections.max(cardScore);
        return cards.get(cardScore.indexOf(maxScore));
    }

    /**
     * Check if current player can buy the card
     * If the card is too expensive, we should skip the card
     * But at the same time we should pay more attention to those basic cards need for the expensive card
     * @param card the current card to check
     * @return A list of tokens that AI need most now
     * @author Jacky Lin
     */
    private TokenList ExpensiveCardChecker(Card card) {
        TokenList colorNeedMostList = new TokenList();
        for (Token token : card.getPrice().getTokens().keySet()) {
            //update the need most list
            int needRate = colorNeedMostList.getNumToken(token) + aiHelper.getTokenDiff(card,theModel.getCurrentPlayer(),token);
            colorNeedMostList.getTokens().put(token,needRate);
        }
        return colorNeedMostList;
    }
}