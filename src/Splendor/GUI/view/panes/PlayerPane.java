/* *****************************************
 * CSCI205 - Software Engineering and Design
 * Fall 2019
 * Instructor: Prof. Brian King
 *
 * Name: Jacky Lin
 * Section: 9:00-9:52 AM
 * Date: 12/6/19
 * Time: 11:11 PM
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

import Splendor.GUI.graphics.PlayerIcon;
import Splendor.game.GameSetting;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;

public class PlayerPane {

    private VBox root;

    public PlayerPane() {
        initPlayers();
    }

    /**
     * Initiate the players and put player images onto the board
     * @author Jacky Lin
     */
    private void initPlayers() {
        root = new VBox(50);
        root.setAlignment(Pos.CENTER);
        for (int i = 0; i < GameSetting.NUM_PLAYER; i++) {
            root.getChildren().add(PlayerIcon.values()[i].getShape());
        }
    }

    /**
     * Getter method of the root VBox
     * @return the root VBox
     */
    public VBox getRoot() {
        return root;
    }
}
