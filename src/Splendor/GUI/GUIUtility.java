/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/30/19
 * Time: 9:38 PM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.GUI;

import Splendor.GUI.graphics.CardIcon;
import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import Splendor.game.components.player.Bill;
import Splendor.game.components.player.Player;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.exceptions.UnaffordablePurchaseException;
import Splendor.game.GameSetting;
import javafx.beans.property.SimpleStringProperty;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;

import javax.sound.sampled.Clip;
import java.util.ArrayList;

/**
 * This method provides a variety of methods facilitating the three class of GUI collaborate with one another
 * @author Jacky Lin, Yida Chen
 */
public class GUIUtility {
    /**
     * This method update the token quantity information on the board
     * It will bind the information in the view with the information in the model
     *
     * @param theView the view of the game
     * @param theModel the model of the game
     * @author Jacky Lin, Yili Wang
     */
    public static void updateTokenOnBoard(SplendorView theView, SplendorModel theModel) {
        ArrayList<StackPane> tokens = theView.getGemToken();
        for (Token key : theModel.getSplendorGame().getTokenOnBoard().getTokens().keySet()) {
            ((Label) tokens.get(key.ordinal()).getChildren().get(1)).textProperty().bind(
                    new SimpleStringProperty(theModel.getSplendorGame().getTokenOnBoard().getNumToken(key).toString()));
        }
        ((Label) tokens.get(GameSetting.NUM_GEMS_TYPE).getChildren().get(1)).textProperty().bind(
                new SimpleStringProperty(String.valueOf(theModel.getSplendorGame().getGoldTokenNum())));
    }

    /**
     * Update/draw a new card to the table after player purchase or reserve a card from the table
     *
     * @param card the card brought by player or reserved by player
     * @author Yida Chen, Jacky Lin
     */
    public static void updateCardOnBoard(Card card, SplendorModel theModel, SplendorView theView) {
        //get the position of the card for updating
        int rowPos = 0;
        int colPos = 0;
        for (ArrayList<Card> levelCards : theModel.getSplendorGame().getCardOnTable()) {
            if (levelCards.indexOf(card) != -1) {
                rowPos = theModel.getSplendorGame().getCardOnTable().indexOf(levelCards);
                colPos = levelCards.indexOf(card);
            }
        }
        //remove the card from the table
        Card newCard = theModel.getSplendorGame().removeCardFromTable(card);
        theView.getCardStackList()[rowPos][colPos].getChildren().clear();
        // add card to board
        if (newCard != null) {
            HBox cardBox = theView.addCardToTable(newCard);
            CardIcon cardRec = new CardIcon("L" + (rowPos + 1));
            theView.getCardStackList()[rowPos][colPos].getChildren().add(cardRec.getShape());
            theView.getCardStackList()[rowPos][colPos].getChildren().add(cardBox);
        }
    }

    /**
     * This method updates the player information on the board
     * It will bind the information in the view with the information in the model
     * @param theView the view of the game
     * @param theModel the model of the game
     * @author Jacky Lin, Yida Chen
     */
    public static void updatePlayerStatistics(SplendorView theView, SplendorModel theModel) {
        // For each player
        for (int j = 0; j < theView.getPlayersPrestige().size(); j++) {
            Player currentPlayer = theModel.getSplendorGame().getPlayers().get(j);
            theView.getPlayersPrestige().get(j).textProperty().bind(currentPlayer.getPlayerPointsProperty());
            // Update the tokens they have
            for (int tokenIndex = 0; tokenIndex <= GameSetting.NUM_GEMS_TYPE; tokenIndex++) {
                theView.getPlayersTokens().get(j).get(tokenIndex).textProperty().bind(
                       currentPlayer.getPlayerTokensProperty(currentPlayer.getTokenOwns()).get(tokenIndex));
            }
            // Update the card they possessed
            for (int cardIndex = 0; cardIndex < GameSetting.NUM_GEMS_TYPE; cardIndex++) {
                theView.getPlayersCard().get(j).get(cardIndex).textProperty().bind(
                        currentPlayer.getPlayerTokensProperty(currentPlayer.getCards()).get(cardIndex));
            }
        }
    }

    /**
     * Update the cards to the reserve pane of the player when player reserves a card.
     * @param theView the view of the game
     * @param targetCard The target card.
     * @param reserveIndex The position of the card display on the player's reserve pane
     * @author Yida Chen
     */
    public static void updateCardToReservePane(SplendorView theView, SplendorModel theModel, Card targetCard, int reserveIndex) {
        // Get card StackPane
        StackPane currentPane = theView.getReserveCardStackPane()[getCurrentPlayerIndex(theModel)][reserveIndex];
        // Clear the current Pane
        currentPane.getChildren().clear();
        // Draw a new card from card deck
        HBox cardHBox = theView.addCardToTable(targetCard);
        // Add new card to the card pane
        CardIcon cardRectangle= new CardIcon("L" + (targetCard.getLevel() + 1));
        currentPane.getChildren().add(cardRectangle.getShape());
        currentPane.getChildren().add(cardHBox);
    }

    /**
     * Get the index of the current player in the players list in SplendorModel object
     * @return the index of the current player in the player list in the SplendorGame
     * @author Yida Chen
     */
    public static int getCurrentPlayerIndex(SplendorModel theModel) {
        return theModel.getSplendorGame().getPlayers().indexOf(theModel.getCurrentPlayer());
    }

    /**
     * Remove the card from the player's reserved cards pane after the player purchase his/her
     * reserved card.
     * @param theView the view of the game
     * @param theModel the model of the game
     * @param playerIndex The index of the player in the players list of the SplendorGame object
     * @param cardIndex The index of the card in the player's reserved card list.
     * @author Yida Chen
     */
    public static void removeCardFromReservePane(SplendorView theView, SplendorModel theModel, int playerIndex, int cardIndex) {
        theView.getReserveCardStackPane()[playerIndex][cardIndex].getChildren().clear();
        theModel.getCurrentPlayer().getReservedCardDeck()[cardIndex] = null;
        // Add empty card pane
        CardIcon rectangleCard = new CardIcon("L" + 4);
        theView.getReserveCardStackPane()[playerIndex][cardIndex].getChildren().add(rectangleCard.getShape());
    }

    /**
     * This method play clips of audio files during the game
     * Adding sound effect to certain actions in game
     * @param clip the sound to play
     * @author Yida Chen
     */
    public static void playClip(Clip clip) {
        try {
            if (clip.isRunning()) { clip.stop(); }
            clip.start();
            // loop the clip by one time
            clip.loop(1);
        } catch (Exception e) {
            System.out.println("Exception when play the clip.");
        }
    }

    /**
     * Show game alert to the player when their actions are not following the rules
     * of the game
     * @author Jacky Lin, Yida Chen, Yili Wang
     */
    public static void showGameAlert(String title, String headerText, String contentText) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle(title);
        alert.setHeaderText(headerText);
        alert.setContentText(contentText);
        alert.showAndWait();
    }

    /**
     * Purchase a reserved card actions of the player
     * Purchase a reserved card from the current player's reserved card list
     * @param reserveCard The chosen reserved card
     * @param reservedCardIndex The index of the reserved card in the player's reserved card list
     * @throws UnaffordablePurchaseException Throws this exception if the player doesn't have enough tokens to buy the card.
     * @author Yida Chen
     */
    public static void purchaseReservedCard(SplendorModel theModel, SplendorView theView ,Card reserveCard, int reservedCardIndex) throws UnaffordablePurchaseException {
        int playerIndex = GUIUtility.getCurrentPlayerIndex(theModel);
        Bill bill = theModel.getCurrentPlayer().purchaseCard(reserveCard);
        theModel.getSplendorGame().addGoldToken(bill.getPaidGoldToken());
        theModel.getSplendorGame().getTokenOnBoard().addToken(bill.getPaidTokenPrice());
        // Update the tokens, statistics, and remove the purchased card from reserved card pane
        GUIUtility.updateTokenOnBoard(theView,theModel);
        GUIUtility.updatePlayerStatistics(theView, theModel);
        GUIUtility.removeCardFromReservePane(theView, theModel, playerIndex, reservedCardIndex);
    }
}
