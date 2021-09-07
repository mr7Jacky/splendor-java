/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/4/19
 * Time: 9:52 AM
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

import Splendor.GUI.GUIUtility;
import Splendor.GUI.SplendorGUIMain;
import Splendor.GUI.controller.actions.ButtonActions;
import Splendor.GUI.controller.actions.CardActions;
import Splendor.GUI.controller.actions.TokensActions;
import Splendor.GUI.graphics.PlayerIcon;
import Splendor.GUI.graphics.ShadowEffect;
import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import Splendor.GameLauncher;
import Splendor.game.components.cards.Card;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

import java.util.Optional;

/**
 * Controller of the Splendor Game class. Set actions on the visual components in
 * Splendor View.
 * @author Yida Chen, Jacky Lin
 */
public class SplendorController {
    /** The model of the Splendor game **/
    private SplendorModel theModel;
    /** The view of the Splendor game **/
    private SplendorView theView;

    /**
     * The constructor of the SplendorController class
     * @param theModel The model of the Splendor game
     * @param theView The view of the Splendor game
     * @author Yida Chen
     */
    public SplendorController(SplendorModel theModel, SplendorView theView){
        this.theModel = theModel;
        this.theView = theView;
        // Initiate the controller
        initiateController();
    }

    /**
     * Initiate the controller
     * Set actions on Cards, Tokens, pass turn button, quit game button.
     * @author Yida Chen
     */
    public void initiateController() {
        // Set actions onto the cards
        CardActions actionsOnCard = new CardActions(theView, theModel, this);
        actionsOnCard.setActionOnCard();

        // Set actions onto the tokens
        TokensActions tokensActions = new TokensActions(theView, theModel, this);
        tokensActions.setActionsOnToken();

        // Set actions onto buttons
        ButtonActions actionsOnButton = new ButtonActions(theView, this);
        actionsOnButton.setActionsOnButtons();
    }

    /**
     * This method will be called when the player end their turns.
     * It end the Turn of this player and move the game to the next player
     * @author Jacky Lin, Yili Wang
     */
    public void endTurn() {
        // Check whether there is any noble card being achieved by the players
        Card nobleChecked = theModel.checkAchievedNoble();
        // If there is any
        if (nobleChecked != null){
            updateAchieveNobleCard(nobleChecked);
            GUIUtility.updatePlayerStatistics(theView,theModel);
        }
        // If player is winning the game show end of game result and ask for restarting
        if (theModel.checkWinner()) {
            GUIUtility.updatePlayerStatistics(theView, theModel);
            // End the game and ask the user whether users want to play the game again
            endGame();
            return;
        }
        // Otherwise change to next player
        theModel.changeToNextPlayer();
        // Off-mark the card border
        AffordableCardMarker.resetCardBorder(theView, theModel);
        AffordableCardMarker.markAffordableCard(theView,theModel);
        // If next player is AI, do AI actions, end turn, update statistics, and change to next player
        if (theModel.getCurrentPlayer().isAIPlayer()) {
            theModel.executeAIAction(theView);
            GUIUtility.updatePlayerStatistics(theView, theModel);
            endTurn();
        }
    }

    /**
     * If there are some noble cards' requirement being achieved by the players,
     * update the corresponding player's icon onto the noble card, and remove the
     * noble card from the Game. (The other player can acquired)
     * @param nobleChecked the noble card to check
     * @author Jacky Lin, Yili Wang
     */
    public void updateAchieveNobleCard(Card nobleChecked) {
        // Index of the achieved noble card
        int nobleIndex = theModel.getSplendorGame().getNoblesFull().indexOf(nobleChecked);
        // Index of the player who achieve noble card
        int playerIndex = Integer.parseInt(theModel.getCurrentPlayer().getName());

        // Set up the player icon
        Circle playerIcon = new Circle(15);
        playerIcon.setStroke(Color.BLACK);
        String playerImageUrl = PlayerIcon.values()[playerIndex].getUrl();
        Image playerIconImage = new Image(playerImageUrl,false);
        playerIcon.setFill(new ImagePattern(playerIconImage));
        playerIcon.setEffect(ShadowEffect.BLACK.getShadow());

        // Add the Icon image upto the noble card
        theView.getNobleList()[nobleIndex].getChildren().add(playerIcon);
        theModel.getCurrentPlayer().addNoble(nobleChecked);

        // Remove the noble card from the game, so no one else could take that
        theModel.getSplendorGame().removeNobles(nobleChecked);
    }

    /**
     * If game is end show game end confirmation
     * Ask the user whether he/she wish to start a new round of game
     * or go back to the main menu
     * or quit the game
     * @author Yida Chen
     */
    private void endGame() {
        // Initiate the end of game confirmation box
        Alert winningResult = new Alert(Alert.AlertType.CONFIRMATION);
        winningResult.setTitle("End of the Game");
        winningResult.setHeaderText("Game over!");
        winningResult.setContentText(theModel.getCurrentPlayer().getName()+ " win the game!");

        // Initiate play again, main menu, and exit buttons
        ButtonType playAgain = new ButtonType("Play again");
        ButtonType mainMenu = new ButtonType(("Main Menu"));
        ButtonType exit = new ButtonType("Exit");
        winningResult.getButtonTypes().setAll(playAgain, mainMenu, exit);
        Optional<ButtonType> feedback = winningResult.showAndWait();

        // run different action according to users selections
        // If player wish to play the game again
        if (feedback.get() == playAgain) {
            PlayerIcon.values()[GUIUtility.getCurrentPlayerIndex(theModel)].getShape().setEffect(ShadowEffect.BLACK.getShadow());
            theView.getRoot().getChildren().clear();
            theModel.newSplendorGame();
            theView.initializeNodes();
            // renew the controller
            initiateController();
        }
        // If player wish to go back to main menu
        else if(feedback.get() == mainMenu) {
            PlayerIcon.values()[GUIUtility.getCurrentPlayerIndex(theModel)].getShape().setEffect(ShadowEffect.BLACK.getShadow());
            SplendorGUIMain.stage.close();
            GameLauncher.stage.show();
        }
        // If player choose quit or close the window
        else {
            SplendorGUIMain.stage.close();
            System.exit(1);
        }
    }
}
