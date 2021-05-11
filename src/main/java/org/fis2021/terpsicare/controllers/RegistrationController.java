package org.fis2021.terpsicare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.fis2021.terpsicare.AlertBox;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
import org.fis2021.terpsicare.services.UserService;

public class RegistrationController {

    @FXML
    private PasswordField passwordField;
    @FXML
    private TextField usernameField;
    @FXML
    private TextField phonenumberField;
    @FXML
    private TextField nameField;
    @FXML
    private PasswordField passwordField2;
    @FXML
    private TextField medicalrecordField;
    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addPatient(usernameField.getText(), passwordField.getText(),nameField.getText(),phonenumberField.getText(), passwordField2.getText(), medicalrecordField.getText());
            AlertBox.display("Success","Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            AlertBox.display("Error","Username already exists!");
        } catch (EmptyTextfieldsException e) {
            AlertBox.display("Error","You cannot leave empty text fields!");
        } catch (WrongPasswordConfirmationException e) {
            AlertBox.display("Error","Wrong password confirmation!");
        }
    }

    public void handleBackAction(ActionEvent event) throws Exception{
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePage.fxml"));
        CurrentStage.setTitle("HomePage");
        CurrentStage.setScene(new Scene(root, 300, 275));
        CurrentStage.show();
    }

}