package org.fis2021.terpsicare.controllers;



import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import org.fis2021.terpsicare.AlertBox;
import org.fis2021.terpsicare.exceptions.*;



import static org.fis2021.terpsicare.services.UserService.checkUserCredentials;
import static org.fis2021.terpsicare.services.UserService.checkUserExist;


public class LogInController {


    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;

    @FXML
    private Button loginButton;

    @FXML
    private Button backButton;

    public void handleLoginAction(ActionEvent event) throws Exception, WrongPasswordException{

        try {

            String role = checkUserExist(usernameField.getText());
            int check = checkUserCredentials(usernameField.getText(), passwordField.getText());
            if (check == 1) {
                if (role.equals("patient")) {
                    Node node = (Node) event.getSource();
                    Stage CurrentStage = (Stage) node.getScene().getWindow();
                    Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePagePatient.fxml"));
                    CurrentStage.setTitle("HomePage");
                    CurrentStage.setScene(new Scene(root, 500, 500));
                    CurrentStage.show();

                } else {
                    if (role.equals("doctor")) {
                        Node node = (Node) event.getSource();
                        Stage CurrentStage = (Stage) node.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePageDoctor.fxml"));
                        CurrentStage.setTitle("HomePage");
                        CurrentStage.setScene(new Scene(root, 500, 500));
                        CurrentStage.show();
                    } else {
                        Node node = (Node) event.getSource();
                        Stage CurrentStage = (Stage) node.getScene().getWindow();
                        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin.fxml"));
                        CurrentStage.setTitle("HomePage");
                        CurrentStage.setScene(new Scene(root, 500, 500));
                        CurrentStage.show();
                    }
                }
            }
        } catch (UsernameDoesNotExistException e) {
            AlertBox.display("Error","Wrong password confirmation!");
        } catch (WrongPasswordException e) {
            AlertBox.display("Error","Wrong password!");
        }
    }
    public void handleBackAction(ActionEvent event) throws Exception {
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePage.fxml"));
        CurrentStage.setTitle("HomePage");
        CurrentStage.setScene(new Scene(root, 500, 500));
        CurrentStage.show();
    }

}
