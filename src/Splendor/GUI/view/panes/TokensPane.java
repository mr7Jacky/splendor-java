/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 10:14 PM
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

import Splendor.game.components.token.Token;
import Splendor.game.GameSetting;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

import java.util.ArrayList;

/**
 * This class is the token pane in the game
 * It contains all the token available with their quantities
 */
public class TokensPane {
    /** Coin radius and card size */
    public static final int RAD = 25;
    /** A list of all the tokens*/
    private ArrayList<StackPane> gemToken;
    /** The root pane of the VBox*/
    private VBox rootPane;

    /**
     * The constructors of the token pane
     */
    public TokensPane() { addTokens(); }

    /**
     * Initialize the token circle and number on the board.
     * All the token have a number of 3 + the number of the player
     * except the gold token, which have a number of 1 + the number of the player
     * @author Yili Wang, Jacky Lin
     */
    private void addTokens() {
        rootPane = new VBox(10);
        rootPane.setAlignment(Pos.CENTER);
        rootPane.setPrefWidth(90);
        gemToken = new ArrayList<>();
        for (int i = 0; i < GameSetting.NUM_GEMS_TYPE + 1; i++) {
            StackPane tokenWNum = new StackPane();
            Circle circle = new Circle(RAD);
            circle.setId("TOKEN_" + Token.values()[i]);
            Label numToken = new Label();
            numToken.setText(String.valueOf(GameSetting.TOKEN_NUM));
            if (String.valueOf(Token.values()[i]).equals("GOLD")) {
                numToken.setText(String.valueOf(GameSetting.GOLD_TOKEN)); }
            numToken.setId("NUMTOKEN");
            tokenWNum.getChildren().addAll(circle,numToken);
            gemToken.add(tokenWNum);
        }
        rootPane.getChildren().addAll(gemToken);
    }

    /**
     * Getter method of the root
     * @return a VBox of the root node
     */
    public VBox getRootPane() { return rootPane; }

    /**
     * Getter method of the gem token list
     * @return a array list contains all the gems
     */
    public ArrayList<StackPane> getGemToken() { return gemToken; }
}
