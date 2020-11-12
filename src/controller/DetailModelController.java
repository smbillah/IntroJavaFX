/**
 * Sample Skeleton for 'DetailModelView.fxml' Controller Class
 */
package controller;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import model.Student;

public class DetailModelController {

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="backButton"
    private Button backButton; // Value injected by FXMLLoader

    @FXML // fx:id="labelID"
    private Label labelID; // Value injected by FXMLLoader

    @FXML // fx:id="labelValue"
    private Label labelValue; // Value injected by FXMLLoader

    @FXML // fx:id="image"
    private ImageView image; // Value injected by FXMLLoader

    // going back to previous scene    
    @FXML
    void backButtonAction(ActionEvent event) {
        // option 1: get current stage -- from event
        Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        
        //  option 2: get current stage -- from backbutton        
        // Stage stage = (Stage)backButton.getScene().getWindow();
        
        if (previousScene != null) {
            stage.setScene(previousScene);
        }

    }

    Student selectedModel;
    Scene previousScene;

    public void setPreviousScene(Scene scene) {
        previousScene = scene;
        backButton.setDisable(false);

    }

    public void initData(Student model) {
        selectedModel = model;
        labelID.setText(model.getId().toString());
        labelValue.setText(model.getName());

        try {
            // path points to /resource/images/
            String imagename = "/resource/images/" + model.getName() + ".png";
            Image profile = new Image(getClass().getResourceAsStream(imagename));
            image.setImage(profile);

        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert backButton != null : "fx:id=\"backButtong\" was not injected: check your FXML file 'DetailModelView.fxml'.";
        assert labelID != null : "fx:id=\"labelID\" was not injected: check your FXML file 'DetailModelView.fxml'.";
        assert labelValue != null : "fx:id=\"labelValue\" was not injected: check your FXML file 'DetailModelView.fxml'.";
        assert image != null : "fx:id=\"image\" was not injected: check your FXML file 'DetailModelView.fxml'.";

        backButton.setDisable(true);

    }
}
