/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 10:14 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.GUI.view.panes;

import Splendor.GUI.graphics.CardIcon;
import Splendor.GUI.model.SplendorModel;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.GameSetting;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * This is a interface contains all the cards on the board
 */
public class CardPane {

    /** the model of the game*/
    private SplendorModel theModel;

    /** the root node of the CardPane*/
    private VBox rootPane;

    /** A StackPane list to show all the card information */
    private StackPane[][] cardStackList;

    /**
     * The constructor of the card pane, add cards to the pane
     * @param theModel the model of the game
     */
    public CardPane(SplendorModel theModel) {
        rootPane = null;
        this.theModel = theModel;
        cardStackList = new StackPane[GameSetting.NUM_CARD_LEVEL][GameSetting.NUM_CARD_ON_BOARD];
        implementCards();
    }

    /**
     * Initiate the cards, manifest token information needed, value, and color on each card.
     * @author Yili Wang
     */
    private void implementCards() {
        rootPane = initCardsOnBoard();
        for (int i = 0; i < GameSetting.NUM_CARD_LEVEL; i++) {
            ArrayList<Card> level = theModel.getSplendorGame().getCardOnTable().get(i);
            for (int j = 0; j < GameSetting.NUM_CARD_ON_BOARD; j++) {
                /** Add token type and number needed to buy the card */
                cardStackList[i][j].setBorder((new Border(new BorderStroke(Color.TRANSPARENT,
                        BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3)))));
                cardStackList[i][j].getChildren().add(addCardToTable(level.get(j)));
            }
        }
    }

    /**
     * Add card rows that contains the cards to the board
     * @return a card matrix of all the cards
     * @author Yida Chen
     */
    private VBox initCardsOnBoard() {
        VBox cardsOnBoard = new VBox(10);
        cardsOnBoard.setAlignment(Pos.CENTER);
        for (int cardCol = 0; cardCol < GameSetting.NUM_CARD_LEVEL; cardCol++) {
            HBox cardsRow = new HBox(10);
            cardsRow.setAlignment(Pos.CENTER);
            addCards(cardsRow, cardCol);
            cardsOnBoard.getChildren().add(cardsRow);
        }
        return cardsOnBoard;
    }

    /**
     * Add card and card information (value, cost, and discount color) to the table
     * @param card The card that will be added to the table
     * @return A HBox object contains the card background and card information.
     * @author Yida Chen
     */
    public HBox addCardToTable(Card card) {
        HBox cardInfo = new HBox(3);
        VBox tok = addCostInfoToCard(card);
        Text value = new Text(Integer.toString(card.getValue()));
        value.setId("VALUE");
        Rectangle cardType = new Rectangle(15,15);
        cardType.setFill(card.getColor().getColor());
        cardInfo.getChildren().addAll(tok, value, cardType);
        return cardInfo;
    }

    /**
     * Initiate card rectangles in each card row
     * The card row contains a level of cards
     * @author Yili Wang, Yida Chen
     */
    private void addCards(HBox cardsRow, int level) {
        for (int i = 0; i < GameSetting.NUM_CARD_ON_BOARD; i++) {
            StackPane stackCardRow = new StackPane();
            CardIcon cardRectangle= new CardIcon("L" + (level + 1));
            stackCardRow.getChildren().add(cardRectangle.getShape());
            /** Add a card to the StackPane row **/
            this.cardStackList[level][i] = stackCardRow;
            cardsRow.getChildren().add(stackCardRow);
        }
    }

    /**
     * Add the cost of tokens to the card
     * @param card the card that contains the cost information
     * @return A VBox contains the circles representation of token costs
     * @author Yida Chen
     */
    private VBox addCostInfoToCard(Card card) {
        /** Get the tokens cost of a card **/
        Hashtable<Token, Integer> tokens = new Hashtable<>(card.getPrice().getTokens());
        VBox tokenCircles = new VBox();
        tokenCircles.setAlignment(Pos.BOTTOM_LEFT);
        Set<Map.Entry<Token, Integer>> tokenCost = tokens.entrySet();
        for(Map.Entry<Token, Integer> cost : tokenCost) {
            StackPane cardInfo = new StackPane();
            /** If cards have cost in this type of tokens **/
            if (cost.getValue() != 0){
                /** Initiate the token cost info **/
                Circle token = new Circle(9);
                token.setFill(cost.getKey().getColor());
                Text numberOfTokens = new Text(Integer.toString(cost.getValue()));
                numberOfTokens.setId("TOKENNUM");
                cardInfo.getChildren().addAll(token, numberOfTokens);
            }
            tokenCircles.getChildren().add(cardInfo);
        }
        return tokenCircles;
    }

    /**
     * Getter method of the root
     * @return the VBox object contains the interface
     */
    public VBox getRootPane() { return rootPane; }

    /**
     * Getter method of the stack list
     * @return a 2D list contains all the stack pane representing cards
     */
    public StackPane[][] getCardStackList() { return cardStackList; }
}
