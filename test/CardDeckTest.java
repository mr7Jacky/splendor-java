import Splendor.game.GameSetting;
import Splendor.game.components.cards.Card;
import Splendor.game.components.cards.CardDeck;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * This class tests the functions in the CardDeck class
 *
 * @author Wenyi Qian
 */
class CardDeckTest {

    /**
     * A card deck to test
     */
    private CardDeck deck;

    /**
     * Initialize the card deck
     */
    @BeforeEach
    void setup() {
        deck = new CardDeck();
    }

    /**
     * Check if the CSVReader reads the raw data of cards successfully
     *
     * @throws IOException
     * @author Wenyi Qian
     */
    @Test
    void CSVReader() throws IOException {
        ArrayList<ArrayList<Card>> cards = deck.CSVReader();
        // level 1 cards
        assertEquals(40, cards.get(0).size());
        // level 2 cards
        assertEquals(29, cards.get(1).size());
        // level 3 cards
        assertEquals(20, cards.get(2).size());
        // noble cards
        assertEquals(10, cards.get(3).size());
    }

    /**
     * Check if initDeal deals the correct number of cards of correct levels
     *
     * @author Wenyi Qian
     */
    @Test
    void initDeal() {
        // Initial drawing cards of each level
        ArrayList<Card> levelOneCards = deck.initDeal(0);
        ArrayList<Card> levelTwoCards = deck.initDeal(1);
        ArrayList<Card> levelThreeCards = deck.initDeal(2);
        ArrayList<Card> nobleCards = deck.initDeal(3);
        // The number of all levels of cards should be 4 except for nobles
        assertEquals(4, levelOneCards.size());
        assertEquals(4, levelTwoCards.size());
        assertEquals(4, levelThreeCards.size());
        // Default being 4 players, so 5 nobles are expected
        assertEquals(5, nobleCards.size());
        // Check if all cards are of corresponding levels
        for (int i = 0; i < GameSetting.NUM_CARD_ON_BOARD; i++) {
            assertEquals(0, levelOneCards.get(i).getLevel());
            assertEquals(1, levelTwoCards.get(i).getLevel());
            assertEquals(2, levelThreeCards.get(i).getLevel());
            assertEquals(3, nobleCards.get(i).getLevel());
        }
    }

    /**
     * Check if shuffle randomizes the cards in the card deck
     *
     * @author Wenyi Qian
     */
    @Test
    void shuffle() {
        // Create a new deck
        CardDeck newDeck = new CardDeck();
        // The first level 1 card should be the same without shuffling
        assertEquals(deck.drawCard(deck.getCardInDeck().get(0)), newDeck.drawCard(newDeck.getCardInDeck().get(0)));
        // Draw the first level 2 card from original deck
        Card aCard = deck.drawCard(deck.getCardInDeck().get(1));
        // Shuffle the new deck and draw the first level 2 card
        newDeck.shuffle();
        Card aNewCard = newDeck.drawCard(newDeck.getCardInDeck().get(1));
        // Very unlikely to draw the same card after shuffling
        assertNotEquals(aCard, aNewCard);
    }
}