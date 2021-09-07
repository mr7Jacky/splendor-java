/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 11/11/19
 * Time: 9:08 AM
 *
 * Project: csci205FinalProject
 * Package: Splendor.game.components
 * Class: CardDeck
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.game.components.cards;

import Splendor.game.components.token.Token;
import Splendor.game.GameSetting;
import Splendor.helper.Storer;
import Splendor.game.components.token.TokenList;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;

/**
 * CardDeck class represents the card deck in the game.
 * It contains three levels of cards plus a special level of noble cards,
 * It also provides functions to shuffle cards, or to read data of cards from the csv file
 * @author Jacky Lin, Yida Chen, Wenyi Qian
 */
public class CardDeck {
    /** All cards in deck **/
    private ArrayList<ArrayList<Card>> cardInDeck;

    /**
     * CardDeck constructor
     * Initiates lists of first, second, third level cards and nobles respectively
     * @author Yida Chen
     */
    public CardDeck() {
        this.cardInDeck = new ArrayList<>();
        initCard();
    }

    /**
     * This method initializes the 3 different levels of cards.
     * It reads the data from a dat file and split them into 4 different {@link ArrayList} of different categories of cards.
     * For the first time: it will read data from a csv file and store as "cards.dat" file.
     * @author Jacky Lin
     */
    public void initCard() {
        Storer s = new Storer();
        File data;
        try { data = new File(this.getClass().getResource("Splendor/data").getPath() + "/card.dat"); }
        catch (Exception e) { data = null; }

        ArrayList<ArrayList<Card>> cards;
        if (data == null) {
            try {
                cards = CSVReader();
                s.store(cards);
            } catch (IOException e) { System.out.println("File is not found!"); }
        }
        cards = (ArrayList<ArrayList<Card>>) s.read();
        // Sequentially
        this.cardInDeck = cards;
    }

    /**
     * This method helps to read the CSV file and converts all the information into {@link ArrayList} of cards
     * @return a 2D{@link ArrayList} contains 4 {@link ArrayList}, each of which represents a category of cards
     * @throws IOException the exceptions to be handle
     * @author Jacky Lin
     */
    public ArrayList<ArrayList<Card>> CSVReader() throws IOException {
        String path = this.getClass().getResource("/Splendor/data").getPath() + "/Splendor.csv";
        BufferedReader csvReader = new BufferedReader(new FileReader(path));
        String line;
        // 4 different card category in the 0, 1, 2, 3 position of the array list
        ArrayList<ArrayList<Card>> res = new ArrayList<>();
        for (int i = 0; i < GameSetting.NUM_CARD_LEVEL + GameSetting.NUM_NOBLE; i++) {
            res.add(new ArrayList<Card>());
        }
        while ((line = csvReader.readLine()) != null) {
            String[] temp = line.split(",");
            Card card = readCardInfo(temp);
            res.get(card.getLevel()).add(card);
        }
        csvReader.close();
        return res;
    }

    /**
     * This method reads the information from a list
     * and creates a {@link Card} object which contains all the information
     * @param info the information of the card
     * @return a Card object
     * @author Jacky Lin
     */
    public Card readCardInfo(String[] info) {
        Card card;
        // Read the price of card as a token list
        TokenList tempList = new TokenList();
        for (int i = 1; i < 6; i++) {
            if (!info[i].equals("")) {
                int number = Integer.parseInt(info[i]);
                for (int j = 0; j < number; j++) { tempList.addToken(Token.values()[i - 1]); }
            }
        }
        // Add to the noble card
        if (info[0].equals("Noble")) { card = new Noble(tempList); }
        // Add to the normal card
        else { card = new Card(info[0], tempList, Integer.parseInt(info[6]), Token.valueOf(info[7])); }
        return card;
    }

    /**
     * Draw four initial cards from all levels at the beginning of the game
     * @return An ArrayList contains the initial draw of four cards from each level,
     *         and draw (NUM_PLAYER + 1) nobles.
     * @author Yida Chen
     */
    public ArrayList<Card> initDeal(int level) {
        ArrayList<Card> drewCard = new ArrayList<>();
        int numberOfCard;
        if (level == 3){ numberOfCard = GameSetting.NUM_PLAYER + 1; }
        else { numberOfCard = GameSetting.NUM_CARD_ON_BOARD; }
        for (int i = 0; i < numberOfCard; i++) {
            drewCard.add(this.cardInDeck.get(level).get(0));
            this.cardInDeck.get(level).remove(0);
        }
        return drewCard;
    }

    /**
     * Draw one card from a deck
     * @param cards An ArrayList of cards, representing a deck
     * @return A card from the deck
     * @author Yida Chen
     */
    public Card drawCard(ArrayList<Card> cards) {
        Card card = cards.get(0);
        cards.remove(card);
        return card;
    }

    /**
     * Shuffle the three levels of card respectively
     * @author Yida Chen
     */
    public void shuffle() { for (ArrayList<Card> cards: cardInDeck) { Collections.shuffle(cards); } }

    /**
     * Getter method for all cards in deck
     * @return a ArrayList of cards in deck, split into ArrayLists of different levels
     */
    public ArrayList<ArrayList<Card>> getCardInDeck() {
        return cardInDeck;
    }

    /**
     * Getter method for nobles on board
     * @return a ArrayList of nobles
     */
    public ArrayList<Card> getNobles() {
        return cardInDeck.get(3);
    }
}
