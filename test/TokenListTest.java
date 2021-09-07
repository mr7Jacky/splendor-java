import Splendor.game.GameSetting;
import Splendor.game.components.token.Token;
import Splendor.game.components.token.TokenList;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * This class tests the functions in the TokenList class
 *
 * @author Jacky Lin, Wenyi Qian
 */

class TokenListTest {
    /**
     * the token list to test
     */
    private TokenList tokenList;

    /**
     * Initializes the token list for testing
     */
    @BeforeEach
    void setUp() {
        tokenList = new TokenList();
    }

    /**
     * Should be able to add one or more tokens at one time to the tokenList
     *
     * @author Jacky Lin, Wenyi Qian
     */
    @Test
    void addToken() {
        // Add 1 token of each color, expect for red, add 6 red tokens
        for (int i = 0; i < GameSetting.NUM_GEMS_TYPE; i++) {
            tokenList.addToken(Token.values()[i], Token.RED);
        }
        System.out.println(tokenList);
        // there should be 6 red tokens
        assertEquals(6, tokenList.getTokens().get(Token.RED));
        // tokens of the other colors should all be 1
        assertEquals(1, tokenList.getTokens().get(Token.GREEN));
        assertEquals(1, tokenList.getTokens().get(Token.WHITE));
        assertEquals(1, tokenList.getTokens().get(Token.BLUE));
        assertEquals(1, tokenList.getTokens().get(Token.BLACK));
    }

    /**
     * Should be able to remove one certain or more tokens from the tokenList
     *
     * @author Jacky Lin, Wenyi Qian
     */
    @Test
    void removeToken() {
        // remove 2 black tokens
        tokenList.removeToken(Token.BLACK, Token.BLACK);
        // since the amount of black token is 0, it retains 0
        assertEquals(0, tokenList.getTokens().get(Token.BLACK));
        // add 3 red tokens first
        for (int i = 0; i < 3; i++) {
            tokenList.addToken(Token.RED);
        }
        // remove 1 red
        tokenList.removeToken(Token.RED);
        // there should be 2 red tokens
        assertEquals(2, tokenList.getTokens().get(Token.RED));
    }

    /**
     * Compare two tokenLists
     * Should correctly return 0 for purchasable cards and how many tokens are needed if unpurchasable.
     *
     * @author Jacky Lin, Wenyi Qian
     */
    @Test
    void compareTokenList() {
        // Representing the cost of a card
        TokenList someCard = new TokenList();
        // Add 3 red to player's wealth
        for (int i = 0; i < 3; i++) {
            tokenList.addToken(Token.RED);
        }
        // The cost of the card is 4 red
        for (int i = 0; i < 4; i++) {
            someCard.addToken(Token.RED);
        }
        // Need 1 more red token to buy the card
        assertEquals(1, tokenList.compareTokenList(someCard));
        // now the card is purchasable
        tokenList.addToken(Token.RED);
        assertEquals(0, tokenList.compareTokenList(someCard));
    }
}