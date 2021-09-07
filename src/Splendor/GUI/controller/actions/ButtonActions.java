/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Yida Chen
 * Section: CSCI 205 9:00 AM
 * Date: 12/6/19
 * Time: 10:57 PM
 *
 * Project: csci205FinalProject
 * Package: Splendor.GUI.controller
 * Class: ButtonActions
 *
 * Description:
 *
 * ****************************************
 */
package Splendor.GUI.controller.actions;

import Splendor.GUI.SplendorGUIMain;
import Splendor.GUI.controller.SplendorController;
import Splendor.GUI.view.SplendorView;
import Splendor.GameLauncher;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

/**
 * A class containing actions of the buttons
 * Pass Turn button: Pass the turn of the current player
 * New game button Start a new game (Go back to main menu)
 * Quit game button: Quit the game (End the whole game)
 * @author Yida Chen
 */
public class ButtonActions {
    /** the view of the splendor **/
    private SplendorView theView;
    /** the controller of the splendor **/
    private SplendorController theController;

    /**
     * The constructor of the ButtonActions class
     * @param theView the view of the splendor
     * @param theController the controller of the splendor
     * @author Yida Chen
     */
    public ButtonActions(SplendorView theView, SplendorController theController) {
        this.theView = theView;
        this.theController = theController;
    }

    /**
     * Initiate the "pass turn" and "quit game" buttons
     */
    public void setActionsOnButtons() {
        setActionOnPassTurnButton();
        setActionOnRestartGameButton();
        setActionOnQuitGameButton();
    }

    /**
     * Set actions on the pass turn button Circle.
     * By clicking the button, player can end his/her turn
     * @author Yida Chen
     */
    private void setActionOnPassTurnButton() {
        setChooseButtonActions(theView.getPassTurnStackPane(), theView.getPassTurn(), "PASS_TURN_BUTTON_ON", "PASS_TURN_BUTTON_OFF");
        theView.getPassTurnStackPane().setOnMouseClicked(mouseEvent -> {
            theController.endTurn();
        });
    }

    /**
     * Set actions on the restart button.
     * By clicking the button, player can restart the game
     * @author Yida Chen
     */
    private void setActionOnRestartGameButton() {
        setChooseButtonActions(theView.getRestartGameStackPane(),theView.getRestartGame(), "RESTART_GAME_BUTTON_ON", "RESTART_GAME_BUTTON_OFF");
        theView.getRestartGameStackPane().setOnMouseClicked(mouseEvent -> {
            SplendorGUIMain.stage.close();
            GameLauncher.stage.show();
        });
    }

    /**
     * Set actions on the quit game button Circle
     * By clicking the button, player can quit the game
     * @author Yida Chen
     */
    private void setActionOnQuitGameButton() {
        setChooseButtonActions(theView.getQuitGameStackPane(), theView.getQuitGame(), "QUIT_GAME_BUTTON_ON", "QUIT_GAME_BUTTON_OFF");
        theView.getQuitGameStackPane().setOnMouseClicked(mouseEvent -> {
            SplendorGUIMain.stage.close();
            System.exit(1);
        });
    }

    /**
     * Set choose button actions
     * When entering the button StackPane, enlarge the button Circle.
     * When leaving the button Circle, shrink the button Circle to its normal size
     * @param buttonStackPane The button StackPane
     * @param buttonCircle The button Circle
     * @param cssIdButtonOn The cssId for Button on effect
     * @param cssIdButtonOff The cssId for Button off effect
     * @author Yida Chen
     */
    private void setChooseButtonActions(StackPane buttonStackPane, Circle buttonCircle, String cssIdButtonOn, String cssIdButtonOff) {
        buttonStackPane.setOnMouseEntered(mouseEvent -> {
            buttonCircle.setId(cssIdButtonOn);
        });
        buttonStackPane.setOnMouseExited(mouseEvent -> {
            buttonCircle.setId(cssIdButtonOff);
        });
    }
}
