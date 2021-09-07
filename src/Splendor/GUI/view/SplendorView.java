package Splendor.GUI.view;

import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.panes.*;
import Splendor.game.components.cards.Card;
import Splendor.game.GameSetting;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * This class the view of the game, contains all the information user need to play the game
 * Based on the {@link javafx} we have the graphics effect on board
 * @author Yili Wang, Jacky Lin, Yida Chen
 */
public class SplendorView {
    /** The model of the game*/
    private SplendorModel theModel;
    /** the root node is a HBox*/
    private HBox root;
    /** the statistic information of player*/
    private StatsPane playerStats;
    /** the card pane on board*/
    private CardPane cardsOnBoard;
    /** the coins pane on board*/
    private TokensPane coinsPane;
    /** the noble pane on board*/
    private NoblePane noblePane;
    /** the quit and pass button*/
    private ButtonPane buttonPane;
    /** the player pane*/
    private PlayerPane playersPane;

    /**
     * The basic view of the game
     * Initiate the cards, tokens, nobel cards, and player information.
     * @param theModel the model of the game
     * @author Yili Wang, Jacky Lin, Yida Chen
     */
    public SplendorView(SplendorModel theModel) {
        //set up the model
        this.theModel = theModel;
        //initialize the root node
        root = new HBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPrefSize(GameSetting.Win_Width, GameSetting.Win_Height);
        //initialize other nodes
        initializeNodes();
    }

    /**
     * Add different panes onto our view
     */
    public void initializeNodes() {
        // The information pane that contains the statistics information of all players
        playerStats = new StatsPane();
        //Initiate the players and put player images onto the board
        playersPane = new PlayerPane();
        cardsOnBoard = new CardPane(theModel);
        //Initialize the token circle and number on the board.
        coinsPane = new TokensPane();
        //Put Noble card rectangles to the table
        noblePane = new NoblePane(theModel);
        //Initiate the pass turn button.
        buttonPane = new ButtonPane();
        root.getChildren().addAll(playerStats.getRootPane(), playersPane.getRoot(), cardsOnBoard.getRootPane(),
                coinsPane.getRootPane(), noblePane.getRoot(),buttonPane.getRoot());
    }

    /**
     * Getter method of the root
     * @return a HBox represents the root
     */
    public HBox getRoot() { return root; }

    /**
     * Getter method of the stack list
     * @return a 2D list contains all the stack pane representing cards
     */
    public StackPane[][] getCardStackList() { return cardsOnBoard.getCardStackList(); }

    /**
     * Getter method of the gem token list
     * @return a array list contains all the gems
     */
    public ArrayList<StackPane> getGemToken() { return coinsPane.getGemToken(); }

    /**
     * Getter method of the reserved card stack pane
     * @return a 2D array list contains the reserved card stack pane of players
     */
    public StackPane[][] getReserveCardStackPane() { return  playerStats.getReserveCardStackPane(); }

    /**
     * Getter method of the players prestige information
     * @return a ArrayList of the label contains the player's prestige info
     */
    public ArrayList<Label> getPlayersPrestige() { return playerStats.getPlayersPrestige(); }

    /**
     * Getter method of the players tokens information
     * @return a ArrayList of the label contains the player's tokens info
     */
    public ArrayList<ArrayList<Label>> getPlayersTokens() { return playerStats.getPlayersTokens(); }

    /**
     * Getter method of the players cards information
     * @return a ArrayList of the label contains the player's cards info
     */
    public ArrayList<ArrayList<Label>> getPlayersCard() { return playerStats.getPlayersCard(); }

    /**
     * Getter method for the nobel cards
     * @return a Array of StackPane of nobel cards
     */
    public StackPane[] getNobleList() { return noblePane.getNobleList(); }

    /**
     * Getter method for the passTurn Button
     * @return the passTurn button
     */
    public Circle getPassTurn() { return  buttonPane.getPassTurn(); }

    /**
     * Getter method of the "pass turn" stackPane
     * @return the "pass turn" stackPane
     */
    public StackPane getPassTurnStackPane() { return buttonPane.getPassTurnStackPane(); }

    /**
     * Getter method of the "quit game" button
     * @return the "quit game" button
     */
    public Circle getQuitGame() { return buttonPane.getQuitGame(); }

    /**
     * Getter method of the "quit game" stackPane
     * @return the "quit game" stackPane
     */
    public StackPane getQuitGameStackPane() { return buttonPane.getQuitGameStackPane(); }

    /**
     * Getter method of the "restart game" button
     * @return the "restart game" button
     */
    public Circle getRestartGame() { return buttonPane.getRestartGame();}

    /**
     * Getter method of the "restart game" stackPane
     * @return the "restart game" stackPane
     */
    public StackPane getRestartGameStackPane() { return buttonPane.getRestartGameStackPane();}

    /**
     * Add card and card information (value, cost, and discount color) to the table
     * @return A HBox object contains the card background and card information.
     */
    public HBox addCardToTable(Card targetCard) { return cardsOnBoard.addCardToTable(targetCard); }
}
