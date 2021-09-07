/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 11/11/19
 * Time: 9:09 AM
 *
 * Project: csci205FinalProject
 * Package: Splendor.game.components
 * Class: SplendorModel
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.GUI.model;

import Splendor.GUI.graphics.PlayerIcon;
import Splendor.GUI.graphics.ShadowEffect;
import Splendor.GUI.view.SplendorView;
import Splendor.game.SplendorGame;
import Splendor.game.components.ai.AIComponent;
import Splendor.game.components.player.Player;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.components.token.TokenList;
import Splendor.game.GameSetting;
import javafx.scene.control.Alert;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.Random;


/**
 * This class represent the class of the Model in GUI
 * It holds several objects to store the information of the game,
 * which include the game {@link SplendorGame}, current player {@link Player}, an ArrayList of {@link Token}
 * and an int indicate the next player's index
 * @author Jacky Lin, Yida Chen, Yili Wang
 */
public class SplendorModel {
    /** the splendor game to play*/
    private SplendorGame splendorGame;
    /** the indexOf the next player*/
    private int nextPlayerINum;
    /** the current player*/
    private Player currentPlayer;
    /** the token player select in current turn*/
    private ArrayList<Token> acquiredTokens;

    /**
     * The constructor of the Model
     * It initialize instance variable and initialize the game starting the first player
     * @author Jacky Lin, Yida Chen
     */
    public SplendorModel(){
        newSplendorGame();
    }

    /**
     * Initiate a new splendor game
     * @author Yida Chen
     */
    public void newSplendorGame() {
        // Instantiate the objects
        splendorGame = new SplendorGame();
        acquiredTokens = new ArrayList<>();
        // Initialize the variables
        splendorGame.initGame();
        initPlayerInGame();
        nextPlayerINum = 0;
        // Start the first player
        changeToNextPlayer();
    }

    /**
     * This method initialize the player and AI components in the game
     * The first nth player with be the human player and the rest will be the AI
     * @author Jacky Lin
     */
    private void initPlayerInGame() {
        for (int i = 0; i < GameSetting.NUM_PLAYER - GameSetting.AI_NUM; i++) {
            splendorGame.addPlayerToGame(new Player(String.valueOf(i),false));
        }
        for (int i = 0; i < GameSetting.AI_NUM; i++) {
            splendorGame.addPlayerToGame(new Player(String.valueOf(i+(GameSetting.NUM_PLAYER - GameSetting.AI_NUM)),true));
        }
    }

    /**
     * Check the winner of the game
     * As long as someone have a score larger than the winning points, this player is win.
     * It will ask if the user want to play again. If yes, initialize the game.
     * @author Jacky Lin
     */
    public boolean checkWinner() {
        if (currentPlayer.getPoint() >= GameSetting.WINNER_POINT) {
            return true;
        }
        return false;
    }

    /**
     * check if the player fulfills any noble requirements and return the card
     * @return the noble card if the noble card requirements are fulfilled
     */
    public Card checkAchievedNoble() {
        Card nobleChecked = null;
        for (Card nobleCard : splendorGame.getNobles()) {
            if (this.currentPlayer.getCards().compareTokenList(nobleCard.getPrice()) == 0) {
                nobleChecked = nobleCard;
                break;
            }
        }
        return nobleChecked;
    }

    /**
     * This method helps to change to the next player
     * It will change the indicator of the current player
     * @author Jacky Lin, Yili Wang
     */
    public void changeToNextPlayer() {
        // Move the player
        if (nextPlayerINum > 0){
            PlayerIcon.values()[nextPlayerINum-1].getShape().setEffect(ShadowEffect.BLACK.getShadow());
        }
        if (nextPlayerINum == 0){
            PlayerIcon.values()[splendorGame.getPlayers().size()-1].getShape().setEffect(ShadowEffect.BLACK.getShadow());
        }
        // Set the current player of the game to the instance variable currentPlayer
        currentPlayer = splendorGame.getPlayers().get(nextPlayerINum);
        // Set the current player Icon with a gold ring
        PlayerIcon.values()[nextPlayerINum].getShape().setEffect(ShadowEffect.GOLD.getShadow());
        // Clear the record of tokens acquired by the previous player
        acquiredTokens.clear();
        nextPlayerINum ++;
        // Turn all the way back, if this player is the last player of the player list
        if (nextPlayerINum == splendorGame.getPlayers().size()) { nextPlayerINum = 0; }
    }

    /**
     * This method allows the AI involving the game, the AI will do the action accordingly
     * @param theView the view of the game for the change
     * @author Jacky Lin
     */
    public void executeAIAction(SplendorView theView) {
        Random random = new Random();
        AIComponent aiActions = new AIComponent(this, theView, random.nextFloat());
        aiActions.pickActions();
    }

    /**
     * This method will check if the current player selection is ended
     * It will forbid the user to get the same color of token when they try to obtain 3 tokens
     * @param token the token color of the player choose
     * @return true if the player has end turn, false the player is not
     * @author Jacky Lin
     */
    public boolean checkIsPlayerEndTurn(Token token) {
        switch (acquiredTokens.size()) {
            // If player has not yet acquired any tokens
            case 0:
                // Acquire the token
                acquiredTokens.add(token);
                return false;
            // If player has acquired one token
            case 1:
                // Acquire another token
                if (acquiredTokens.get(0).equals(token)) { return true; }
                else {
                    acquiredTokens.add(token);
                    return false;
                }
            // If player has acquired tokens in a same color
            case 2:
                // Raise alert, you cannot acquired more than two tokens from a same color
                for (Token tokens : acquiredTokens) {
                    if (tokens.equals(token)) {
                        this.currentPlayer.getTokenOwns().removeToken(token);
                        this.getSplendorGame().getTokenOnBoard().addToken(token);
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("WARNING!");
                        alert.setContentText("You cannot have the same color when you get tokens!");
                        alert.show();
                        return false;
                    }
                }
                return true;
            // Other Circumstance player has not ended the turn
            default:
                return false;
        }
    }

    /**
     * Getter method for the current game
     * @return the current splendor game object
     */
    public SplendorGame getSplendorGame() { return splendorGame; }

    /**
     * Getter method for the current player
     * @return current player
     */
    public Player getCurrentPlayer() { return currentPlayer; }

    /**
     * Getter method for the acquired token
     * @return a array list of tokens
     */
    public ArrayList<Token> getAcquiredTokens() { return acquiredTokens; }
}
