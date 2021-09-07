/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 10:01 PM
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
import Splendor.game.components.token.Token;
import Splendor.game.GameSetting;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

/**
 * This class create a pane that including all the information of the player and their reserved cards
 */
public class StatsPane {
    /** The prestige points of the player **/
    private ArrayList<Label> playersPrestige;
    /** The possessed cards information of the player */
    private ArrayList<ArrayList<Label>> playersCard;
    /** The possessed tokens information of the player */
    private ArrayList<ArrayList<Label>> playersTokens;
    /** The reserved card StackPane **/
    private StackPane[][] reserveCardStackPane;
    /** The root of the stats pane*/
    private VBox rootPane;

    /**
     * Constructor of the statistic pane
     */
    public StatsPane() {
        reserveCardStackPane = new StackPane[4][3];
        initiatePlayerStat();
    }

    /**
     * Initiate the player statistic pane
     * Statistics include the cards and tokens possessed by Player,
     * and player's prestige information
     * @author Yida Chen
     */
    public void initiatePlayerStat() {
        // The information pane that contains the statistics information of all players
        rootPane = new VBox(20);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPrefWidth(450);
        // Initiate the ArrayLists contain the players' statistics information
        playersPrestige = new ArrayList<>();
        playersCard = new ArrayList<>();
        playersTokens = new ArrayList<>();
        for (int i = 0; i < GameSetting.NUM_PLAYER; i++) {
            playersCard.add(new ArrayList<>());
            playersTokens.add(new ArrayList<>());
            HBox thisPlayerAllInfo = new HBox(10);
            thisPlayerAllInfo.getChildren().add(initCurrentPlayerReservePane(i));

            VBox thisPlayerStatPane = new VBox(10);
            // Initiate the points information
            addPlayersPointLabelToStat(thisPlayerStatPane);
            // Initiate the player's cards information
            addPlayerCardsToStat(i, thisPlayerStatPane);
            // Initiate the player's tokens information
            addPlayerTokensToStat(i, thisPlayerStatPane);
            // Add all information to the HBox
            thisPlayerAllInfo.getChildren().add(thisPlayerStatPane);
            // Add this player's information box to the information pane
            rootPane.getChildren().add(thisPlayerAllInfo);
        }
    }
    /**
     * Initiate the reserved card pane of the current player
     * @param playerIndex The index of the current player in the player list of the SplendorGame
     * @return The initial reserved card pane of the current player
     * @author Yida Chen
     */
    private HBox initCurrentPlayerReservePane(int playerIndex) {
        HBox reserveList = new HBox(10);
        reserveList.setMaxSize(CardIcon.CARD_WIDTH,CardIcon.CARD_HEIGHT);
        for (int j = 0; j < 3; j++) {
            StackPane reserveCard = new StackPane();
            CardIcon cardIcon = new CardIcon("L" + 4);
            reserveCard.getChildren().add(cardIcon.getShape());
            reserveCardStackPane[playerIndex][j] = reserveCard;
            reserveCardStackPane[playerIndex][j].setBorder((new Border(new BorderStroke(Color.TRANSPARENT,
                    BorderStrokeStyle.SOLID, new CornerRadii(10), new BorderWidths(3)))));
            reserveList.getChildren().add(reserveCard);
        }
        return reserveList;
    }

    /**
     * Add player's prestige points information to the player's statistics pane
     * @param thisPlayerStatPane The statistics pane of this player
     * @author Yida Chen
     */
    private void addPlayersPointLabelToStat(VBox thisPlayerStatPane) {
        /** Initiate the Prestige points information to the player's statistics pane**/
        Label prestigePoint = new Label("0" );
        prestigePoint.setBackground(new Background(new BackgroundFill(Color.GOLD, CornerRadii.EMPTY, Insets.EMPTY)));
        prestigePoint.setPrefSize(70,30);
        prestigePoint.setTextFill(Color.BLACK);
        prestigePoint.setId("STATVALUE");
        prestigePoint.setBorder(new Border(new BorderStroke(Color.DARKGOLDENROD, BorderStrokeStyle.SOLID, null, new BorderWidths(5))));
        prestigePoint.setAlignment(Pos.CENTER);

        /** Add the prestige points label to the ArrayList **/
        playersPrestige.add(prestigePoint);

        thisPlayerStatPane.getChildren().add(prestigePoint);
    }

    /**
     * Add the statistics of cards possessed by player information to the statistics pane
     * @param playerIndex The index of the player in the player list of SplendorGame
     * @param thisPlayerStatPane The player statistics pane of this player
     * @author Yida Chen
     */
    private void addPlayerCardsToStat(int playerIndex, VBox thisPlayerStatPane) {
        HBox hboxCards = new HBox(10);
        for (int numOfCards = 0; numOfCards < GameSetting.NUM_GEMS_TYPE; numOfCards++) {
            StackPane stackPanePlayerCards = new StackPane();

            /** Initiate the card rectangle **/
            Rectangle card = new Rectangle(40,40);
            card.setFill(Token.values()[numOfCards].getColor());
            Label cardNum = new Label("0");
            cardNum.setTextFill(Color.ORANGE);
            cardNum.setId("STATVALUE");

            /** Add label to the playersCard ArrayList **/
            playersCard.get(playerIndex).add(cardNum);
            stackPanePlayerCards.getChildren().addAll(card, cardNum);
            hboxCards.getChildren().add(stackPanePlayerCards);
        }
        thisPlayerStatPane.getChildren().add(hboxCards);
    }

    /**
     * Add the statistics about the tokens owned by player to the information pane
     * @param playerIndex The index of the player in the player list of SplendorGame
     * @param thisPlayerStatPane The player statistics pane of this player
     * @author Yida Chen
     */
    private void addPlayerTokensToStat(int playerIndex, VBox thisPlayerStatPane) {
        HBox hboxTokens = new HBox(10);
        for (int numOfToken = 0; numOfToken <= GameSetting.NUM_GEMS_TYPE; numOfToken++) {
            /** Initiate the token circles **/
            StackPane stackPanePlayerTokens = new StackPane();
            Circle token = new Circle(15);
            token.setId("TOKEN_" + Token.values()[numOfToken]);

            /** Initiate the number of tokens Label **/
            Label tokenNum = new Label("0");
            tokenNum.setTextFill(Color.DIMGRAY);
            tokenNum.setId("STATVALUE");

            playersTokens.get(playerIndex).add(tokenNum);
            stackPanePlayerTokens.getChildren().addAll(token, tokenNum);
            hboxTokens.getChildren().add(stackPanePlayerTokens);
        }
        thisPlayerStatPane.getChildren().add(hboxTokens);
    }

    /**
     * Getter method of the root pane of the statistic pane
     * @return a VBox of the root pane
     */
    public VBox getRootPane() { return rootPane; }

    /**
     * Getter method of the reserved card stack pane
     * @return a 2D array list contains the reserved card stack pane of players
     */
    public StackPane[][] getReserveCardStackPane() { return reserveCardStackPane; }

    /**
     * Getter method of the players prestige information
     * @return a ArrayList of the label contains the player's prestige info
     */
    public ArrayList<Label> getPlayersPrestige() {
        return playersPrestige;
    }

    /**
     * Getter method of the players tokens information
     * @return a ArrayList of the label contains the player's tokens info
     */
    public ArrayList<ArrayList<Label>> getPlayersTokens() {
        return playersTokens;
    }

    /**
     * Getter method of the players cards information
     * @return a ArrayList of the label contains the player's cards info
     */
    public ArrayList<ArrayList<Label>> getPlayersCard() {
        return playersCard;
    }
}
