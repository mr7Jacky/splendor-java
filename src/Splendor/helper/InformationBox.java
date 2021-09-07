package Splendor.helper;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

/**
 * Create information box which contains yes-no buttons
 * @author Jacky Lin
 */
public class InformationBox{
    /** the second stage to show*/
    private Stage stage;
    /** root node is a VBox*/
    private VBox root;
    /** a yes button*/
    private Button yes;
    /** a no button*/
    private Button no;

    /**
     * Constructor of a information box
     * Initialize all the variable
     * @param s the string to show
     * @author Jacky Lin
     */
    public InformationBox(String s, boolean hasButton) {
        initRoot();
        // create two labels
        Label info = new Label(s);
        info.setAlignment(Pos.CENTER);
        root.getChildren().addAll(info);
        if (hasButton) {
            initButtons();
        } else {
            yes = new Button("OK");
            yes.setOnAction(event -> {
                stage.close();
            });
            root.getChildren().addAll(yes);
        }

        initStage();
    }

    /**
     * Create a yes-no panel
     */
    private void initButtons() {
        HBox buttons = new HBox(50);
        yes = new Button("Yes!");
        no = new Button("No!");
        buttons.getChildren().addAll(yes,no);
        buttons.setAlignment(Pos.CENTER);
        root.getChildren().addAll(buttons);
    }

    /**
     * This method initializes the root node with CENTER Alignment and 10 spacing
     * @author Jacky Lin
     */
    private void initRoot() {
        root = new VBox(10);
        root.setAlignment(Pos.CENTER);
        root.setPadding(new Insets(10));
        root.setPrefWidth(300);
        root.setPrefHeight(150);
    }

    /**
     * This method initializes the stage, add title and root to the stage
     * @author Jacky Lin
     */
    private void initStage() {
        stage = new Stage();
        stage.setTitle("Attentions");
        stage.setScene(new Scene(root));
        stage.sizeToScene();
    }

    /**
     * Getter method for the stage
     * @return the secondary stage
     */
    public Stage getStage() {
        return stage;
    }

    /**
     * Getter method for the yes button
     * @return yes-button
     */
    public Button getYes() {
        return yes;
    }

    /**
     * Getter method for the no button
     * @return no-button
     */
    public Button getNo() {
        return no;
    }
}
