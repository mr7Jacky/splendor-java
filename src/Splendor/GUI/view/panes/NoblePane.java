/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 10:44 PM
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
import Splendor.GUI.model.SplendorModel;
import Splendor.game.components.token.Token;
import Splendor.game.components.cards.Card;
import Splendor.game.GameSetting;
import javafx.geometry.Pos;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Map;
import java.util.Set;

/**
 * The noble pane that contains all the nobles on the boards with their information
 */
public class NoblePane {
    /** A StackPane list to show all the noble card information */
    private StackPane[] nobleList;
    /** The model of the game*/
    private SplendorModel theModel;
    /** The root node of the pane*/
    private VBox root;

    /**
     * The constructors of the noble pane
     * It initialize the noble on the view
     */
    public NoblePane(SplendorModel theModel) {
        nobleList = new StackPane[GameSetting.NOBLE_NUM];
        this.theModel = theModel;
        addNobles();
        implementNobles();
    }

    /**
     * Put Noble card rectangles to the table
     * @author Yili Wang
     */
    private void addNobles() {
        root = new VBox(5);
        root.setAlignment(Pos.CENTER);
        for (int i = 0; i < GameSetting.NOBLE_NUM; i++) {
            StackPane noble = new StackPane();
            CardIcon rectangleNobleCard = new CardIcon("NOBLE");
            noble.getChildren().add(rectangleNobleCard.getShape());
            nobleList[i] = noble;
            root.getChildren().add(noble);
        }
    }

    /**
     * Initiate the Noble cards, manifest token information
     * needed and values on each noble card.
     * @author Yili Wang
     */
    private void implementNobles() {
        ArrayList<Card> noble = theModel.getSplendorGame().getNoblesFull();
        for (int i = 0; i < GameSetting.NOBLE_NUM; i++) {
            Hashtable<Token, Integer> tokens = new Hashtable<>(noble.get(i).getPrice().getTokens());
            HBox cardInfo = new HBox(10);
            VBox tokenBox = new VBox();
            tokenBox.setAlignment(Pos.BOTTOM_LEFT);
            Set<Map.Entry<Token, Integer>> cardRequirements = tokens.entrySet();

            /** Put token information onto the card*/
            for(Map.Entry<Token, Integer> requirements : cardRequirements) {
                StackPane nobelStackPane = new StackPane();
                if (requirements.getValue() != 0){
                    Rectangle nobel = new Rectangle(15, 15);
                    nobel.setId("REQ");
                    nobel.setFill(requirements.getKey().getColor());
                    Text numOfCardRequirement = new Text(Integer.toString(requirements.getValue()));
                    numOfCardRequirement.setId("TOKENNUM");
                    nobelStackPane.getChildren().addAll(nobel, numOfCardRequirement);
                }
                tokenBox.getChildren().add(nobelStackPane);
            }
            Text value = new Text(Integer.toString(noble.get(i).getValue()));
            value.setId("TOKENVAL");
            cardInfo.getChildren().addAll(tokenBox, value);
            nobleList[i].getChildren().add(cardInfo);
        }
    }

    /**
     * Getter method for the nobel cards
     * @return a Array of StackPane of nobel cards
     */
    public StackPane[] getNobleList() { return nobleList; }

    /**
     * Getter method for the root node
     * @return VBox of the root node
     */
    public VBox getRoot() { return root; }
}
