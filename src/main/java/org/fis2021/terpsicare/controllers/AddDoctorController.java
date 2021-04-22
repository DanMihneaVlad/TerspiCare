package org.fis2021.terpsicare.controllers;


import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
import org.fis2021.terpsicare.services.UserService;
import org.fis2021.terpsicare.AlertBox;


public class AddDoctorController {

    @FXML
    private TextField usernameField;

    @FXML
    private PasswordField passwordField;

    @FXML
    private PasswordField passwordConfirmation;

    @FXML
    private TextField nameField;

    @FXML
    private TextField phonenumberField;

    @FXML
    private ChoiceBox medicalSpecialty;

    @FXML
    public void initialize() {
        medicalSpecialty.getItems().addAll("Cardiology", "Dermatology", "Family Medicine", "Gastroenterology", "Hematology", "Neurology", "Obstetrics and gynecology", "Ophthalmology", "Pediatrics", "Urology");
    }

    @FXML
    public void handleRegisterAction() {
        try {
            UserService.addDoctor(usernameField.getText(), passwordField.getText(), passwordConfirmation.getText(), nameField.getText(), (String)medicalSpecialty.getValue(), phonenumberField.getText());
            AlertBox.display("Succes", "Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            AlertBox.display("Error","Username already exists!");
        } catch (EmptyTextfieldsException e) {
            AlertBox.display("Error","You cannot leave empty text fields!");
        } catch (WrongPasswordConfirmationException e) {
            AlertBox.display("Error","Wrong password confirmation!");
        }
    }
}
