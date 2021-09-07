/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/12/19
 * Time: 9:33 AM
 *
 * Project: lab
 * Package: lab
 * Class: CSCI205
 *
 * Description:
 *
 * ****************************************
 */

package Splendor.game.components.player;

import Splendor.game.components.cards.Card;
import Splendor.game.components.token.Token;
import Splendor.game.components.token.TokenList;
import Splendor.game.exceptions.ReservedCardOutOfRangeException;
import Splendor.game.exceptions.UnaffordablePurchaseException;
import Splendor.game.GameSetting;
import javafx.beans.property.SimpleStringProperty;

import java.util.ArrayList;
import java.util.Collection;

/**
 * This class represents the player in the game
 * Every player will have the following properties:
 *  1. a series of different types of tokens {@link TokenList}
 *  2. a list of bought cards {@link Splendor.game.components.cards.Card}
 *  3. an integer which indicates the player's current point
 *
 * A player has following four options:
 *  1. get 3 tokens, each of 3 different colors(if only 2 colors remaining then take 2, if there's only 1 color, take 1)
 *  2. get 2 tokens of same color
 *  3. buy 1 card
 *  4. reserve 1 card and get 1 golden token if there is one
 * @author Jacky Lin, Yida Chen, Wenyi Qian
 */
public class Player {
    /** the token the player has*/
    private TokenList tokenOwns;
    /** the cards the player has*/
    private TokenList cards;
    /** the points the player has*/
    private int point;
    /** the name of the player*/
    private String name;
    /** the number of the gold token*/
    private int goldToken;
    /** the list of reserved cards of the player*/
    private Card[] reservedCardDeck;
    /** the record of the number of reserved card*/
    public int openReservedCardIndex;
    /** Indicate if the player is an AI*/
    private boolean isAIPlayer;

    /**
     * at the beginning of the game, a player will have none tokens and cards with a score of 0
     * the name will be initialize according to personal choice
     * @param name the name of the player
     * @author Jacky Lin
     */
    public Player(String name, boolean isAIPlayer) {
        this.tokenOwns = new TokenList();
        this.cards = new TokenList();
        this.point = 0;
        this.name = name;
        this.goldToken = 0;
        this.reservedCardDeck = new Card[GameSetting.MAX_RESERVED_CARD_NUM];
        this.openReservedCardIndex = 0;
        this.isAIPlayer = isAIPlayer;
    }

    /**
     * This method allows the card to be purchased by the player.
     * After purchase, the cards the player has will increase.
     * the total score will increase according to the value of the card
     * the player's token will decrease the same amount as the price of the card
     * @param card the card to buy
     * @throws UnaffordablePurchaseException the exception to throw when there is insufficient token to buy a card
     * @author Jacky Lin
     */
    public Bill purchaseCard(Card card) throws UnaffordablePurchaseException {
        // Calculate how many gold token need to use to buy the card
        TokenList pay = new TokenList();
        int requiredGoldToken = 0;
        for (Token c : card.getPrice().getTokens().keySet()) {
            int tokenPaid = card.getPrice().getTokens().get(c) - cards.getTokens().get(c);
            int diff = tokenPaid - tokenOwns.getTokens().get(c);
            if (tokenPaid < 0) { continue; }
            pay.getTokens().put(c, tokenPaid);
            if (diff > 0) {
                pay.getTokens().put(c, tokenPaid - diff);
                requiredGoldToken += diff;
            }
        }
        //check if the card can be brought
        if ( requiredGoldToken <= this.goldToken) {
            // update info to player
            this.cards.addToken(card.getColor());
            this.point += card.getValue();
            for (Token c : card.getPrice().getTokens().keySet()) {
                int fee = pay.getTokens().get(c);
                for (int i = 0; i < fee; i++) { this.tokenOwns.removeToken(c); }
            }
            // remove the golden token from the user
            this.goldToken -= requiredGoldToken;
            return new Bill(pay,requiredGoldToken);
        } else {throw new UnaffordablePurchaseException("INSUFFICIENT FUND"); }
    }

    /**
     * This method is used when user get the tokens.
     * A player can get 3 different colors of tokens or get 2 same color of tokens in each turn
     * @param tokens a series of token to add
     * @author Jacky Lin
     */
    public void obtainTokens(Token...tokens) { this.tokenOwns.addToken(tokens); }

    /**
     * This method is used when user get the tokens.
     * A player can get 3 different colors of tokens or get 2 same color of tokens in each turn
     * @param tokens a collection of token to add
     * @author Jacky Lin
     */
    public void obtainTokens(Collection<Token> tokens) { for (Token t : tokens) { this.tokenOwns.addToken(t); } }

    /**
     * This method allows users to reserve a card
     * @param card the card to reserved
     * @param hasGoldToken a boolean indicate if there is gold token on the board
     * @throws ReservedCardOutOfRangeException this exception will be throw if player reserve too many cards
     * @author Jacky Lin, Yida Chen
     */
    public void reserveCard(Card card, boolean hasGoldToken) throws ReservedCardOutOfRangeException {
        if (findOpenReservedCardIndex() == -1) { throw new ReservedCardOutOfRangeException("LIMIT WARNING"); }
        // if there is still gold token on the board add the gold token to the player
        if (hasGoldToken) { this.goldToken ++; }
        this.reservedCardDeck[findOpenReservedCardIndex()] = card;
    }

    /**
     * Find a open space for the card in the player's reserved card deck
     * If there is no place opening (player cannot reserve another card as he/she reaches the reserving limits) return -1
     * @return The index of the opening place in the player's reserved card deck, if there is no place opening return -1.
     * @author Yida Chen
     */
    public int findOpenReservedCardIndex() {
        for (int i = 0; i < reservedCardDeck.length; i++) { if (reservedCardDeck[i] == null) { return i; } }
        return -1;
    }

    /**
     * Get the an ArrayList of SimpleStringProperty of the player's TokenList
     * @param tokens TokenList which contains the SimpleStringProperty we need
     * @return An ArrayList of SimpleStringProperty of the player's TokenList
     * @author Yida Chen
     */
    public ArrayList<SimpleStringProperty> getPlayerTokensProperty(TokenList tokens) {
        ArrayList<SimpleStringProperty> tokensProperty = new ArrayList<>();
        for (Token token: Token.values()) {
            try {
                SimpleStringProperty numTokens = new SimpleStringProperty(Integer.toString(tokens.getTokens().get(token)));
                tokensProperty.add(numTokens);
            } catch (NullPointerException e) {
                SimpleStringProperty numTokens = new SimpleStringProperty(Integer.toString(goldToken));
                tokensProperty.add(numTokens);
            }
        }
        return tokensProperty;
    }

    /**
     * Get the SimpleStringProperty of the player's prestige points (scores in the game)
     * @return A SimpleStringProperty of the player's prestige points
     * @author Yida Chen
     */
    public SimpleStringProperty getPlayerPointsProperty() {
        return new SimpleStringProperty(Integer.toString(point));
    }

    /**
     * Add the value of the noble card to the player's prestige points (scores in game)
     * @param noble The noble card to add
     * @author Jacky Lin
     */
    public void addNoble(Card noble) {this.point += noble.getValue();}

    /**
     * Getter method for the cards a player has
     * @return a token list indicate all the color of cards a user have
     */
    public TokenList getCards() {
        return cards;
    }

    /**
     * Getter method of the name of player
     * @return the String of the player's name
     */
    public String getName() { return name; }

    /**
     * Getter method of the tokens that a player owns
     * @return a TokenList of a player's tokens
     */
    public TokenList getTokenOwns() {
        return tokenOwns;
    }

    /**
     * Getter method of the score(point) that a player earns
     * @return a int representing points
     */
    public int getPoint() {
        return point;
    }

    /**
     * Getter method of a special kind token(Gold token) that a player owns
     * @return a int representing the amount of gold tokens that a player owns
     */
    public int getGoldToken() {
        return goldToken;
    }

    /**
     * Getter method of reserved cards that a player has
     * @return an ArrayList of cards which are reserved by the player
     */
    public Card[] getReservedCardDeck() {
        return reservedCardDeck;
    }

    /**
     * This method creates a string of all the information that a player has
     * @return a string contains the information of the player
     */
    @Override
    public String toString() {
        return "\nName: "+getName()+ "\nPoint: "+ getPoint()+
                "\nCards: "+ getCards()+ "\nTokens: "+ getTokenOwns() + "\nGold Token:" + getGoldToken();
    }

    /**
     * Getter method of the isAIPlayer variable
     * @return true is the player is AI, false if the player is not AI
     */
    public boolean isAIPlayer() {
        return isAIPlayer;
    }
}
