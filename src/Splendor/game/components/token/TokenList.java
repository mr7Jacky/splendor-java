/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 11/8/19
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

package Splendor.game.components.token;

import Splendor.game.GameSetting;

import java.io.Serializable;
import java.util.Hashtable;
import java.util.Objects;

/**
 * This class is a helper class that provides multiple functions to make changes with a series of tokens,
 * Such as providing some method to compare between two token list.
 * @author Jacky Lin
 */
public class TokenList implements Serializable {
    /** The hash table contains all the colors of tokens and their quantities. */
    private Hashtable<Token, Integer> tokens;
    /** The total number of tokens in the list*/
    private int size;

    /**
     * The constructor of the token list class, it initializes the token hash table and
     * adds all 5 normal tokens to the list, each with 0 as initial numbers.
     * The gold token is not included.
     */
    public TokenList() {
        this.tokens = new Hashtable<>();
        for (int i = 0; i < GameSetting.NUM_GEMS_TYPE; i++) {
            this.tokens.put(Token.values()[i],0);
        }
        this.size = 0;
    }

    /**
     * This method adds all given tokens to the hashtable.
     * @author Jacky Lin
     */
    public void addToken(Token...tokens) {
        for(Token t: tokens){
            int temp = this.tokens.get(Token.values()[t.ordinal()]);
            this.tokens.put(Token.values()[t.ordinal()],temp + 1);
            this.size ++;
        }
    }


    /**
     * This method adds all given tokens to the hashtable, through another the token list
     * @author Jacky Lin
     */
    public void addToken(TokenList tokens) {
        for(Token t: tokens.getTokens().keySet()){
            int temp = this.tokens.get(Token.values()[t.ordinal()]);
            int increment = tokens.getNumToken(t);
            this.tokens.put(Token.values()[t.ordinal()],temp + increment);
            this.size += increment;
        }
    }

    /**
     * This method removes the given tokens to the hashtable.
     * @author Jacky Lin
     */
    public void removeToken(Token...tokens) {
        for(Token t: tokens){
            int temp = this.tokens.get(Token.values()[t.ordinal()]);
            if (temp > 0) {
                this.tokens.put(Token.values()[t.ordinal()], temp - 1);
                this.size --;
            } else { this.tokens.put(Token.values()[t.ordinal()], 0); }
        }
    }


    /**
     * This method compares two TokenList, in order to check if the player can buy a certain
     * card with current tokens without using golden ones.
     * Number of tokens in the parameter tokenList is supposed to be larger than this.tokens
     * @param tokenList the other TokenList to compare, typically the cost of a card
     * @return 0 if purchasable,
     *         otherwise an int indicating how many tokens are needed to be equal to the other TokenList
     * @author Jacky Lin
     */
    public int compareTokenList(TokenList tokenList) {
        int colorDiffer = 0;
        for (int i = 0; i < GameSetting.NUM_GEMS_TYPE; i++) {
            int diff = tokenList.getTokens().get(Token.values()[i]) - this.tokens.get(Token.values()[i]);
            if (diff > 0) { colorDiffer += diff; }
        }
        return colorDiffer;
    }

    /**
     * Prints tokens as a hashtable
     * @return a string indicated token color and amount
     */
    @Override
    public String toString() {
        return this.tokens.toString();
    }

    /**
     * Compare if two token lists are equal
     * @param o the token list to compare with
     * @return true if the token list is the same; false otherwise.
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TokenList tokenList = (TokenList) o;
        return Objects.equals(tokens, tokenList.tokens);
    }

    /**
     * Getter method for tokens
     * @return the tokens
     */
    public Hashtable<Token, Integer> getTokens() {
        return tokens;
    }

    /**
     * Getter method for the token list size
     * @return the size of the token list
     */
    public int getSize() { return size; }

    /**
     * Getter method for the number of a type of tokens in the list
     * @param token the type of token to check
     * @return the number of the tokens
     */
    public Integer getNumToken(Token token){
        return tokens.get(token);
    }
}
