package Splendor;

import Splendor.GUI.GUIUtility;
import Splendor.GUI.SplendorGUIMain;
import Splendor.game.GameSetting;
import Splendor.helper.SettingSlider;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * This class build the main stage and launcher of different mode
 * @author Jacky Lin
 */
public class GameLauncher extends Application {
    /** the root node of the launcher*/
    private VBox root;
    /** the play mode launcher trigger*/
    private Button playMode;
    /** the exit the game label*/
    private Button exit;
    /** the ai number slider*/
    private SettingSlider AINum;
    /** the player number slider*/
    private SettingSlider playerNum;
    /** the stage of the launcher*/
    public static Stage stage;
    /** the winning point of the slider*/
    private SettingSlider pointNum;

    /**
     * main method to launch the game
     * @param args args
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * the start method for the app set up the stage
     * @param primaryStage the primary stage to setup
     * @author Jacky Lin
     */
    @Override
    public void start(Stage primaryStage) {
        initRoot();
        initSlider();
        initButtons();
        Scene scene = new Scene(root);
        stage = primaryStage;
        stage.setScene(scene);
        stage.sizeToScene();
        stage.setTitle("Splendor");
        stage.show();
    }

    /**
     * This method initialize the root node with CENTER Alignment and 10 spacing
     * @author Jacky Lin
     */
    private void initRoot() {
        root = new VBox(30);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(30));
    }

    /**
     * initialize the buttons
     * @author Jacky Lin
     */
    private void initButtons() {
        initPlayMode();
        initExit();
        HBox buttons = new HBox(50);
        buttons.setAlignment(Pos.CENTER);
        buttons.getChildren().addAll(playMode, exit);
        root.getChildren().addAll(buttons);
    }

    /**
     * initialize the exit label
     * @author Jacky Lin
     */
    private void initExit() {
        exit = new Button("Exit Game");
        exit.setOnMouseClicked(event -> { System.exit(1); });
    }

    /**
     * initialize the play mode label
     * @author Jacky Lin
     */
    private void initPlayMode() {
        playMode = new Button("PLAY");
        initPlayModeController();
    }

    /**
     * add the action to the play mode label
     * @author Jacky Lin
     */
    private void initPlayModeController() {
        playMode.setOnMouseClicked(event -> {
            if (AINum.getTheSlider().getValue() < playerNum.getTheSlider().getValue()) {
                GameSetting.setAiNum((int) AINum.getTheSlider().getValue());
                GameSetting.setPlayerNum((int) playerNum.getTheSlider().getValue());
                GameSetting.setWinnerPoint((int) pointNum.getTheSlider().getValue());
                SplendorGUIMain mm = null;
                try { mm = new SplendorGUIMain();}
                catch (Exception e) { e.printStackTrace(); }
                mm.start(SplendorGUIMain.stage);
                stage.close();
            } else {
                GUIUtility.showGameAlert("Attention!","Too many AI players","The player number must be larger than the AI number");
            }
        });
    }

    /**
     * this method initialize the text field and button
     * @author Jacky Lin
     */
    private void initSlider() {
        Label AILabel = new Label("Input the AI number you want to player with.");
        AINum = new SettingSlider(0,3, 0);
        Label playerLabel = new Label("Input the total player number you want to play.");
        playerNum = new SettingSlider(1,4, 1);
        Label pointLabel = new Label("Input the winning point you want to play.");
        pointNum = new SettingSlider(5, 20, 15);
        root.getChildren().addAll(AILabel, AINum.getTheSlider(), playerLabel, playerNum.getTheSlider(),pointLabel,pointNum.getTheSlider());
    }
}
