/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 12/6/19
 * Time: 11:02 PM
 *
 * Project: csci205FinalProject
 * Package: Splendor.GUI.controller
 * Class: TokensActions
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.GUI.controller.actions;

import Splendor.GUI.GUIUtility;
import Splendor.GUI.controller.SplendorController;
import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import Splendor.GUI.view.panes.TokensPane;
import Splendor.data.soundEffect.SoundEffect;
import Splendor.game.components.token.Token;
import Splendor.game.GameSetting;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * A class with all the actions of the tokens on the board
 * Obtain tokens: Acquire one token from one type of tokens
 * Choose tokens: Move the mouse (cursor) onto the tokens
 * @author Yida Chen, Jacky Lin
 */
public class TokensActions {
    /** the view of the splendor **/
    private SplendorView theView;
    /** the model of the splendor **/
    private SplendorModel theModel;
    /** the controller of the splendor **/
    private SplendorController theController;

    /**
     * The constructor of the TokensActions class
     * @param theView the view of the splendor
     * @param theModel the model of the splendor
     * @param theController the controller of the splendor
     * @author Yida Chen
     */
    public TokensActions(SplendorView theView, SplendorModel theModel, SplendorController theController) {
        this.theView = theView;
        this.theModel = theModel;
        this.theController = theController;
    }

    /**
     * Set actions onto the Tokens on table
     * When mouse clicked events onto the tokens, player can collect a token when clicking on the tokens
     * When mouse entered the tokens, enlarge the radius of the tokens
     * When mouse exited the tokens, change the radius of the tokens to its original scale
     * @author Yida Chen, Jacky Lin, Yili Wang
     */
    public void setActionsOnToken() {
        ArrayList<StackPane> tokens = theView.getGemToken();
        for (int i = 0; i < GameSetting.NUM_GEMS_TYPE; i++) {
            int tokenPos = i;
            StackPane TokenStack = tokens.get(i);
            Circle currentToken = (Circle) TokenStack.getChildren().get(0);
            // Set the on mouse clicked events onto the tokens
            TokenStack.setOnMouseClicked(mouseEvent -> {
                if (Integer.parseInt(((Label)TokenStack.getChildren().get(1)).getText()) <= 0){
                    GUIUtility.showGameAlert("Insufficient tokens", null, "Oops, the tokens are insufficient. You can't get tokens!");
                }else {
                    obtainTokens(tokenPos);
                }
            });
            setChooseTokenActions(TokenStack, currentToken);
        }
    }

    /**
     * Set actions on choosing tokens
     * When entering the token circle, the token circle will be enlarged to notify the player
     * When leaving the token circle, the token circle will be shrink to normal size
     * @param tokenStack token StackPane
     * @param currentToken currentToken Circle
     * @author Jacky Lin
     */
    private void setChooseTokenActions(StackPane tokenStack, Circle currentToken) {
        // When mouse entered the tokens, enlarge the radius of the tokens
        tokenStack.setOnMouseEntered(event -> {
            double newRadius = currentToken.getRadius() * 1.2;
            currentToken.setRadius(newRadius);
        });
        // When mouse exited the tokens, change the radius of the tokens to its original scale
        tokenStack.setOnMouseExited(event -> {
            currentToken.setRadius(TokensPane.RAD);
        });
    }

    /**
     * Obtain the tokens from the board
     * Update the obtained tokens to the player's statistics pane and
     * update the remaining tokens to the board
     * @param tokenPos The position of the tokens on board (which token to obtain)
     * @author Yida Chen
     */
    private void obtainTokens(int tokenPos) {
        Token tokenChose = Token.values()[tokenPos];
        theModel.getCurrentPlayer().obtainTokens(tokenChose);
        theModel.getSplendorGame().getTokenOnBoard().removeToken(tokenChose);
        if (theModel.checkIsPlayerEndTurn(tokenChose)) {
            theController.endTurn();
        }
        GUIUtility.updateTokenOnBoard(theView,theModel);
        // update the information pane
        GUIUtility.playClip(SoundEffect.COIN_CLIP.getClip());
        GUIUtility.updatePlayerStatistics(theView, theModel);
    }
}
