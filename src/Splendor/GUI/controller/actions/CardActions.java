/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 12/6/19
 * Time: 10:43 PM
 *
 * Project: csci205FinalProject
 * Package: Splendor.GUI.controller
 * Class: CardActions
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.GUI.controller.actions;

import Splendor.GUI.GUIUtility;
import Splendor.GUI.controller.SplendorController;
import Splendor.GUI.graphics.ShadowEffect;
import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import Splendor.data.soundEffect.SoundEffect;
import Splendor.game.components.player.Bill;
import Splendor.game.components.cards.Card;
import Splendor.game.exceptions.ReservedCardOutOfRangeException;
import Splendor.game.exceptions.UnaffordablePurchaseException;
import Splendor.game.GameSetting;
import Splendor.helper.InformationBox;
import javafx.scene.layout.StackPane;

/**
 * A class that contains all the actions on the card
 * Buy card: Purchase a card when player has enough tokens
 * Reserve card: Reserve a card when player doesn't have enough tokens
 * Buy reserved card: Buy the card player reserved when he/she has enough tokens
 * @author Yida Chen
 */
public class CardActions {
    /** the view of the splendor **/
    private SplendorView theView;
    /** the model of the splendor **/
    private SplendorModel theModel;
    /** the controller of the splendor **/
    private SplendorController theController;

    /**
     * The constructor of the CardActions class
     * @param theView the view of the splendor
     * @param theModel the model of the splendor
     * @param theController the controller of the splendor
     * @author Yida Chen
     */
    public CardActions(SplendorView theView, SplendorModel theModel, SplendorController theController) {
        this.theView = theView;
        this.theModel = theModel;
        this.theController = theController;
    }

    /**
     * Set actions onto the cards on table
     * Player can either purchase or reserve a card from the table during his/her turn
     * @author Yida Chen
     */
    public void setActionOnCard() {
        StackPane[][] cardsOnTable = theView.getCardStackList();
        for(int row = 0; row < GameSetting.NUM_CARD_LEVEL; row++) {
            for(int col = 0; col < GameSetting.NUM_CARD_ON_BOARD; col++) {
                StackPane currentCard = cardsOnTable[row][col];
                setChooseCardActions(currentCard);
                setBuyCardActions(currentCard, row, col);
            }
        }
    }

    /**
     * Set choosing card actions onto the cards
     * @param currentCard the current card being chosen
     * @author Yida Chen
     */
    private void setChooseCardActions(StackPane currentCard) {
        currentCard.setOnMouseEntered(event -> {
            currentCard.setEffect(ShadowEffect.GOLD.getShadow());
            GUIUtility.playClip(SoundEffect.CARD_FLIP_CLIP.getClip());
        });

        currentCard.setOnMouseExited(event -> { currentCard.setEffect(null); });
    }

    /**
     * Set buy cards actions onto the cards
     * @param currentCard the current card being bought
     * @param rowPos the row position of the card on the table
     * @param colPos the column position of the card on the table
     * @author Yida Chen, Jacky Lin
     */
    private void setBuyCardActions(StackPane currentCard, int rowPos, int colPos) {
        currentCard.setOnMouseClicked(mouseEvent -> {
            if (theModel.getAcquiredTokens().isEmpty()) {
                Card targetCard = theModel.getSplendorGame().getCardOnTable().get(rowPos).get(colPos);
                try {
                    purchaseCard(rowPos, colPos, targetCard);

                } catch (UnaffordablePurchaseException e) {
                    InformationBox infoBox = new InformationBox("Insufficient Funds!\n\nDo you want to reserve cards?",
                            true);
                    infoBox.getStage().show();
                    infoBox.getYes().setOnAction(event -> {
                        infoBox.getStage().close();
                        reserveCard(targetCard);
                    });
                    infoBox.getNo().setOnAction(event -> {
                        infoBox.getStage().close();
                    });
                }
                GUIUtility.updatePlayerStatistics(theView, theModel);
            }
            else {
                GUIUtility.showGameAlert("Cannot Buy or Reserve this Card",
                        "Cannot buy or reserve this card!",
                        "You are collecting tokens in this turn.\nOnly one of three actions can" +
                                " be done in each turn!");
            }
        });
    }

    /**
     * Purchase actions of the player
     * Purchase the card from the table and update a new card onto the board
     * @param rowPos The row position of the card on the table
     * @param colPos The column position of the card on the table
     * @param targetCard The targeted card chosen by player
     * @throws UnaffordablePurchaseException If the player doesn't have enough tokens
     *        to buy the card throw this exception
     * @author Yida Chen
     */
    private void purchaseCard(int rowPos, int colPos, Card targetCard) throws UnaffordablePurchaseException {
        Bill bill = theModel.getCurrentPlayer().purchaseCard(targetCard);
        theModel.getSplendorGame().getTokenOnBoard().addToken(bill.getPaidTokenPrice());
        theModel.getSplendorGame().addGoldToken(bill.getPaidGoldToken());
        GUIUtility.updateTokenOnBoard(theView,theModel);
        GUIUtility.playClip(SoundEffect.CARD_DEAL_CLIP.getClip());
        theView.getCardStackList()[rowPos][colPos].getChildren().clear();
        GUIUtility.updateCardOnBoard(targetCard, theModel, theView);
        theController.endTurn();
    }

    /**
     * Reserve card action of the player
     * Reserve a card from the table if player does not have enough tokens to buy
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
            GUIUtility.updatePlayerStatistics(theView, theModel);
            GUIUtility.playClip(SoundEffect.CARD_DEAL_CLIP.getClip());
            setActionsOnReserveCard(GUIUtility.getCurrentPlayerIndex(theModel));
            theController.endTurn();
        } catch (ReservedCardOutOfRangeException ex) {
            GUIUtility.showGameAlert("Cannot reserve more cards", "Cannot reserve more cards",
                    "Sorry, you have reached the reserving limit.");
        }
    }

    /**
     * Set actions onto the reserved cards
     * Player can buy his/her reserved cards when having enough tokens
     * @param playerIndex the index of the player in the players list of the SplendorGame
     * @author Yida Chen, Jacky Lin
     */
    private void setActionsOnReserveCard(int playerIndex) {
        for (int cardIndex = 0; cardIndex < theModel.getCurrentPlayer().getReservedCardDeck().length; cardIndex++) {
            Card reserveCard = theModel.getCurrentPlayer().getReservedCardDeck()[cardIndex];
            if (theModel.getAcquiredTokens().isEmpty() && reserveCard != null) {
                StackPane cardPane = theView.getReserveCardStackPane()[playerIndex][cardIndex];
                setChooseCardActions(cardPane);
                int col = cardIndex;
                cardPane.setOnMouseClicked(mouseEvent -> {
                    // If the cards are in the reserved card pane of the current player then current player do actions
                    if (GUIUtility.getCurrentPlayerIndex(theModel) == playerIndex) {
                        System.out.println("Player " + theModel.getCurrentPlayer().getName()+" Reserve Card");
                        try {
                            // Purchase the reserve card
                            purchaseReservedCard(reserveCard, col);
                            // Set off the actions on this card as the card has been bought by the players
                            cardPane.setOnMouseClicked(null);
                            cardPane.setOnMouseEntered(null);
                        } catch (UnaffordablePurchaseException e) {
                            System.out.println(reserveCard);
                            GUIUtility.showGameAlert("Insufficient Fund", "Insufficient Fund",
                                    "Sorry, you don't have enough tokens\n" +
                                            " to buy this card.");
                        } catch (NullPointerException e) {
                            cardPane.setOnMouseClicked(null);
                        }
                    }
                });
            }
        }
    }

    /**
     * Purchase a reserved card actions of the player
     * Purchase a reserved card from the current player's reserved card list
     * @param reserveCard The chosen reserved card
     * @param reservedCardIndex The index of the reserved card in the player's reserved card list
     * @throws UnaffordablePurchaseException Throws this exception if the player doesn't have enough tokens to
     *         buy the card.
     * @author Yida Chen
     */
    private void purchaseReservedCard(Card reserveCard, int reservedCardIndex) throws UnaffordablePurchaseException {
        GUIUtility.purchaseReservedCard(theModel,theView,reserveCard,reservedCardIndex);
        GUIUtility.playClip(SoundEffect.CARD_DEAL_CLIP.getClip());
        theController.endTurn();
    }

}
