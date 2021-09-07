import Splendor.game.components.cards.Card;
import Splendor.game.components.cards.Noble;
import Splendor.game.components.player.Player;
import Splendor.game.components.token.Token;
import Splendor.game.components.token.TokenList;
import Splendor.game.exceptions.ReservedCardOutOfRangeException;
import Splendor.game.exceptions.UnaffordablePurchaseException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * This class tests the functions in the Player class
 *
 * @author Wenyi Qian
 */
class PlayerTest {

    /**
     * The player to test
     */
    private Player player;
    /**
     * A card instance
     */
    private Card card;
    /**
     * A tokenList representing the price of a card
     */
    private TokenList price = new TokenList();

    /**
     * Initializes a player, and a card and a noble for him to buy
     */
    @BeforeEach
    void setup() {
        player = new Player("Test Player",false);
        price.addToken(Token.RED, Token.BLACK);
        card = new Card("I", price, 1, Token.RED);
    }

    /**
     * Insufficient tokens when buying a card
     *
     * @throws UnaffordablePurchaseException
     * @author Wenyi Qian
     */
    @DisplayName("player: Insufficient tokens")
    @Test
    void purchaseCardException() throws UnaffordablePurchaseException {
        // Can't afford
        assertThrows(UnaffordablePurchaseException.class,
                () -> player.purchaseCard(card));
    }

    /**
     * Check if can buy a card when having enough tokens
     *
     * @throws UnaffordablePurchaseException
     * @author Wenyi Qian
     */
    @Test
    void purchaseCard() throws UnaffordablePurchaseException {
        player.obtainTokens(Token.BLACK);
        player.obtainTokens(Token.RED);
        player.purchaseCard(card);
        assertEquals(new TokenList(), player.getTokenOwns());
    }

    /**
     * Reserve a card when already having 3 cards reserved
     *
     * @throws ReservedCardOutOfRangeException
     * @author Wenyi Qian
     */
    @DisplayName("reserveCard: Number of reserved cards exceed maximum(3)")
    @Test
    void reserveCardException() throws ReservedCardOutOfRangeException {
        player.reserveCard(card, true);
        player.reserveCard(card, true);
        player.reserveCard(card, true);
        assertThrows(ReservedCardOutOfRangeException.class,
                () -> player.reserveCard(card, true));
    }

    /**
     * Check if can get a gold token when there is one after reserving a card
     *
     * @throws ReservedCardOutOfRangeException
     * @author Wenyi Qian
     */
    @Test
    void reserveCard() throws ReservedCardOutOfRangeException {
        player.reserveCard(card, true);
        assertEquals(1, player.getGoldToken());
        // No gold tokens, should still be 1
        player.reserveCard(card, false);
        assertEquals(1, player.getGoldToken());
    }

    /**
     * Check if this function can find the available index when reserving card
     *
     * @author Wenyi Qian
     */
    @Test
    void findOpenReservedCardIndex() throws ReservedCardOutOfRangeException {
        // should be 0 because no cards are reserved currently
        assertEquals(0, player.findOpenReservedCardIndex());
        // reserve a card
        player.reserveCard(card, true);
        // should be 1 now because 1 card is reserved
        assertEquals(1, player.findOpenReservedCardIndex());
        // Now cannot no longer reserve any card
        player.reserveCard(card, true);
        player.reserveCard(card, true);
        // should be -1
        assertEquals(-1, player.findOpenReservedCardIndex());
    }
}