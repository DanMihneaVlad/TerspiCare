package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;
import javafx.event.ActionEvent;

public class AdminController {

    @FXML
    private Button addDoctor;

    @FXML
    public void handleAddDoctor(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage currentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("addDoctor.fxml"));
        currentStage.setTitle("Add Doctor");
        currentStage.setScene(new Scene(root, 500, 500));
        currentStage.show();
    }

    public void handleBackAction(ActionEvent event) throws Exception{
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePage.fxml"));
        CurrentStage.setTitle("HomePage");
        CurrentStage.setScene(new Scene(root, 300,275));
        CurrentStage.show();
    }

}
