package Splendor.GUI;

import Splendor.GUI.controller.SplendorController;
import Splendor.GUI.model.SplendorModel;
import Splendor.GUI.view.SplendorView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Main class of the Splendor game. Initiate the Model, view, and controller object of the
 * Simulator and start the GUI of the simulator.
 * @author Jacky Lin
 */
public class SplendorGUIMain extends Application {
    /** the model of the game*/
    private SplendorModel theModel;
    /** the view of the game*/
    private SplendorView theView;
    /** the the controller of the game*/
    private SplendorController theController;
    /** the stage of the game*/
    public static Stage stage = new Stage();

    /**
     * Game launcher of the class
     * @author Jacky Lin
     */
    public SplendorGUIMain() throws Exception { init(); }

    /**
     * Initiate the model, view, and controller of the Game
     * @throws Exception Exceptions during the initialization
     * @author Jacky Lin
     */
    @Override
    public void init() throws Exception {
        super.init();
        theModel = new SplendorModel();
        theView = new SplendorView(theModel);
        theController = new SplendorController(theModel, theView);
    }

    /**
     * Start the GUI of the game
     * @param primaryStage Primary stage of the Game
     * @author Jacky Lin
     */
    @Override
    public void start(Stage primaryStage) {
        stage = primaryStage;
        Scene scene = new Scene(theView.getRoot());
        // add the scene graph to the stage
        scene.getStylesheets().addAll(this.getClass().getResource("view/resource/Style.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Splendor");
        stage.sizeToScene();
        stage.setResizable(false);
        stage.show();
    }
}