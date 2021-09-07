import Splendor.game.SplendorGame;
import Splendor.game.components.player.Player;
import Splendor.game.components.cards.Card;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

/**
 * This class tests the functions in the SplendorGame class
 *
 * @author Wenyi Qian
 */
class SplendorGameTest {

    /**
     * A splendor game instance
     */
    private SplendorGame game;

    /**
     * A random player
     */
    private Player player1;

    /**
     * Initializes a game and a player called Q
     */
    @BeforeEach
    void setup() {
        game = new SplendorGame();
        player1 = new Player("Q",false);
    }

    /**
     * Check if a player can be added to the game
     *
     * @author Wenyi Qian
     */
    @Test
    void addPlayerToGame() {
        game.addPlayerToGame(player1);
        assertEquals(player1, game.getPlayers().get(0));
    }

    /**
     * Check when a card is removed from the table, if a new card will be refilled
     *
     * @author Wenyi Qian
     */
    @Test
    void removeCardFromTable() {
        ArrayList<ArrayList<Card>> cards = game.getCardOnTable();
        Card card = cards.get(0).get(0);
        Card newCard = game.removeCardFromTable(card);
        // Should not be the same card because the function returns the new card being refilled
        assertNotEquals(card, newCard);
        // Should be another level 1 card
        assertEquals(0, newCard.getLevel());
        // Draw all level 1 cards
        for (int i = 0; i < 35; i++) {
            card = cards.get(0).get(0);
            game.removeCardFromTable(card);
        }
        // Should be null because there are no 1 level cards
        assertEquals(null, game.removeCardFromTable(cards.get(0).get(0)));
    }

}