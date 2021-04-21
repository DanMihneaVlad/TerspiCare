package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AdminController {

    @FXML
    private Button addDoctor;

    @FXML
    public void handleButtonAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage currentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addDoctor.fxml"));
        currentStage.setTitle("Add Doctor");
        currentStage.setScene(new Scene(root, 500, 500));
        currentStage.show();
    }

}
