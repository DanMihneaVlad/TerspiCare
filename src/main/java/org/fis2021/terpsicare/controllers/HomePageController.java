package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.event.ActionEvent;
import javafx.scene.control.Button;
public class HomePageController {
    @FXML
    private Button Register;

    @FXML
    private Button LogIn;

    public void handleButtonActionRegister(ActionEvent event) throws Exception{
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        CurrentStage.setTitle("Register");
        CurrentStage.setScene(new Scene(root, 500, 500));
        CurrentStage.show();
    }

    public void handleButtonActionLogIn(ActionEvent event) throws Exception{
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("sample.fxml"));
        CurrentStage.setTitle("LogIn");
        CurrentStage.setScene(new Scene(root, 500, 500));
        CurrentStage.show();
    }
}
