/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 10:56 PM
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

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;

public class ButtonPane {

    /** the pass turn button Circle **/
    private Circle passTurn;
    /** the pass turn StackPane **/
    private StackPane passTurnStackPane;
    /** the quit game button Circle **/
    private Circle quitGame;
    /** the quit game StackPane **/
    private StackPane quitGameStackPane;
    /** the quit game button Circle **/
    private Circle restartGame;
    /** the quit game StackPane **/
    private StackPane restartGameStackPane;
    /** the root node of the button pane*/
    private VBox root;

    public ButtonPane() {
        initiateButton();
    }

    /**
     * Initiate the pass turn button.
     * By clicking the button, the player can end his/her turn.
     * @author Yida Chen
     */
    private void initiateButton() {
        root = new VBox(80);
        root.setAlignment(Pos.CENTER);
        passTurnStackPane = new StackPane();
        passTurnStackPane.setPrefSize(25,25);
        passTurn = new Circle(25);
        initiateButtonPane(passTurnStackPane, passTurn, "PASS");
        passTurn.setId("PASS_TURN_BUTTON_OFF");

        restartGameStackPane = new StackPane();
        restartGameStackPane.setPrefSize(25, 25);
        restartGame = new Circle(25);
        initiateButtonPane(restartGameStackPane, restartGame,"NEW");
        restartGame.setId("RESTART_GAME_BUTTON_OFF");

        quitGameStackPane = new StackPane();
        quitGameStackPane.setPrefSize(25,25);
        quitGame = new Circle(25);
        initiateButtonPane(quitGameStackPane, quitGame, "QUIT");
        quitGame.setId("QUIT_GAME_BUTTON_OFF");
        root.getChildren().addAll(passTurnStackPane, restartGameStackPane, quitGameStackPane);
    }

    /**
     * Initiate the "quit" and "pass" buttons
     * @param buttonStackPane the Pane that contains the buttons
     * @param buttonCircle the button circle to add text on
     * @param buttonText the text to add on
     */
    private void initiateButtonPane(StackPane buttonStackPane, Circle buttonCircle, String buttonText) {
        buttonStackPane.setPrefSize(30,30);
        Label passTurnLabel = new Label(buttonText);
        passTurnLabel.setId("BUTTON_VALUE");
        buttonStackPane.getChildren().addAll(buttonCircle, passTurnLabel);
    }

    /**
     * Getter method for the passTurn Button
     * @return the passTurn circle button
     */
    public Circle getPassTurn() { return passTurn; }

    /**
     * Getter method for the "passTurn" StackPane
     * @return the "passTurn" StackPane
     */
    public StackPane getPassTurnStackPane() { return passTurnStackPane; }

    /**
     * Getter method for the "restart" Button
     * @return the "restart" circle Button
     */
    public Circle getRestartGame() {
        return restartGame;
    }

    /**
     * Getter method for the "restart game" StackPane
     * @return the "restart game" StackPane
     */
    public StackPane getRestartGameStackPane() { return restartGameStackPane; }

    /**
     * Getter method for the "quit game" circle button
     * @return the "quit game" circle button
     */
    public Circle getQuitGame() { return quitGame; }

    /**
     * Getter method for the "quit game" StackPane
     * @return the "quit game" StackPane
     */
    public StackPane getQuitGameStackPane() { return quitGameStackPane; }

    /**
     * Getter method for root node
     * @return VBox of the root node
     */
    public VBox getRoot() { return root; }
}
