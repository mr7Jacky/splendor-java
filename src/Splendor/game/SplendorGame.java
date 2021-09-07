/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 11/13/19
 * Time: 9:12 AM
 *
 * Project: csci205FinalProject
 * Package: Splendor.game
 * Class: SplendorGame
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.game;

import Splendor.game.components.player.Player;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.components.cards.CardDeck;
import Splendor.game.components.token.TokenList;

import java.util.ArrayList;

/**
 * This class represent the Splendor game
 * It contains some components of the game
 * @author Jacky Lin, Yida Chen
 */
public class SplendorGame {
    /** List of players **/
    private ArrayList<Player> players;

    /** All cards on table **/
    private ArrayList<ArrayList<Card>> cardOnTable;

    /** The noble cards available on table **/
    private ArrayList<Card> nobleCards;

    /** Card deck **/
    private CardDeck cardDeck;

    /** the token on the board*/
    private TokenList tokenOnBoard;

    /** the number of the golden token*/
    private int goldTokenNum;

    /** Constructor of the SplendorGame **/
    public SplendorGame(){ initGame(); }

    /**
     * Constructor of the SplendorGame
     * @author Yida Chen
     */
    public void initGame(){
        this.players = new ArrayList<>();
        this.cardOnTable = new ArrayList<>();
        // Initiate the card deck
        this.cardDeck = new CardDeck();
        initTokenOnBoard();
        // Add cards to deck
        cardDeck.initCard();
        // Shuffle the cards
        cardDeck.shuffle();
        // Draw initial cards onto the table
        drawInitialCardsToTable();
    }

    /**
     * This method initialize the token on the board
     * @author Jacky Lin
     */
    private void initTokenOnBoard() {
        this.tokenOnBoard = new TokenList();
        for (int i = 0; i < GameSetting.NUM_GEMS_TYPE; i++) {
            for (int j = 0; j < GameSetting.TOKEN_NUM; j++) { this.tokenOnBoard.addToken(Token.values()[i]); }
        }
        this.goldTokenNum = GameSetting.GOLD_TOKEN;
    }

    /**
     * Draw initial cards onto the table
     * @author Yida Chen
     */
    private void drawInitialCardsToTable() {
        /** Draw cards from deck and put cards on table **/
        for (int i = 0; i < GameSetting.NUM_CARD_LEVEL + GameSetting.NUM_NOBLE; i++) {
            cardOnTable.add(new ArrayList<>());
            cardOnTable.get(i).addAll(cardDeck.initDeal(i));
        }

        this.nobleCards = new ArrayList<>(cardOnTable.get(GameSetting.NUM_CARD_LEVEL));
    }

    /**
     * Add player to the game
     * @param player Player object represent the player in game
     * @author Yida Chen
     */
    public void addPlayerToGame(Player player){ players.add(player); }

    /**
     * Remove card from the table after the cards are purchased by the player
     * @param card card to remove from the table
     * @author Yida Chen, Jacky Lin
     */
    public Card removeCardFromTable(Card card){
        int cardPos = cardOnTable.get(card.getLevel()).indexOf(card);
        cardOnTable.get(card.getLevel()).remove(card);
        if (!cardDeck.getCardInDeck().get(card.getLevel()).isEmpty()) {
            Card newCard = cardDeck.drawCard(cardDeck.getCardInDeck().get(card.getLevel()));
            cardOnTable.get(card.getLevel()).add(cardPos, newCard);
            return newCard;
        }
        cardOnTable.get(card.getLevel()).add(cardPos, null);
        return null;
    }

    /**
     * Getter method of the player list
     * @return a list of the player
     */
    public ArrayList<Player> getPlayers() { return players; }

    /**
     * Getter method of the card on the board
     * @return a 2D array list contains all the cards on the board
     */
    public ArrayList<ArrayList<Card>> getCardOnTable() { return cardOnTable; }


    /**
     * Getter method of the nobles that can be got on the board
     * @return return the array list contains the nobles on the board
     */
    public ArrayList<Card> getNobles() { return nobleCards; }

    /**
     * Getter method of all the nobles on the board
     * @return return the array list contains the nobles on the board
     */
    public ArrayList<Card> getNoblesFull() { return cardOnTable.get(GameSetting.NUM_CARD_LEVEL); }

    /**
     * Remove the noble card from the noble card list if the noble card is got by players
     * @param nobleToRemove the noble card to remove
     */
    public void removeNobles(Card nobleToRemove) {
        nobleCards.remove(nobleToRemove);
    }

    /**
     * Getter method of the token on the board
     * @return a token list represent the token on the board
     */
    public TokenList getTokenOnBoard() { return tokenOnBoard; }

    /**
     * After the player purchase cards, they may need to return some gold token back to the board
     * @param num the num player need to return
     * @author Jacky Lin
     */
    public void addGoldToken(int num) { goldTokenNum += num; }

    /**
     * When the player reserve card, if there is still gold token, the gold token decrease by 1\
     * @author Jacky Lin
     */
    public void removeGoldToken() { goldTokenNum -= 1; }

    /**
     * Getter method for the gold token number
     * @return integer indicate the number of remaining gold token on the board
     */
    public int getGoldTokenNum() { return goldTokenNum; }
}
