package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import org.fis2021.terpsicare.AlertBox;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
import org.fis2021.terpsicare.services.UserService;

public class RegistrationController {

    @FXML
    private Text registrationMessage;
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
            registrationMessage.setText("Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            AlertBox.display("Error","Username already exists!");
        } catch (EmptyTextfieldsException e) {
            AlertBox.display("Error","You cannot left empty test fields!");
        } catch (WrongPasswordConfirmationException e) {
            AlertBox.display("Error","Wrong password confirmation!");
        }
    }
}