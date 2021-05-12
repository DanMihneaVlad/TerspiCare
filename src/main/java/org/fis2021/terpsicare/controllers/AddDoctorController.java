package org.fis2021.terpsicare.controllers;


import com.sun.javafx.charts.Legend;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
import org.fis2021.terpsicare.services.UserService;
import org.fis2021.terpsicare.AlertBox;

import java.io.IOException;


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
    private TextField descriptionField;

    @FXML
    private ChoiceBox medicalSpecialty;

    @FXML
    public void initialize() {
        medicalSpecialty.getItems().addAll("Cardiology", "Dermatology", "Family Medicine", "Gastroenterology", "Hematology", "Neurology", "Obstetrics and gynecology", "Ophthalmology", "Pediatrics", "Urology");
    }

    @FXML
    public void handleRegisterAction() {
        try {

            UserService.addDoctor(usernameField.getText(), passwordField.getText(), passwordConfirmation.getText(), nameField.getText(), (String)medicalSpecialty.getValue(), phonenumberField.getText(), descriptionField.getText());
            AlertBox.display("Succes", "Account created successfully!");
        } catch (UsernameAlreadyExistsException e) {
            AlertBox.display("Error","Username already exists!");
        } catch (EmptyTextfieldsException e) {
            AlertBox.display("Error","You cannot leave empty text fields!");
        } catch (WrongPasswordConfirmationException e) {
            AlertBox.display("Error","Wrong password confirmation!");
        }
    }

    public void handleAdmin(ActionEvent event) throws IOException {
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("admin.fxml"));
        CurrentStage.setTitle("HomePage");
        CurrentStage.setScene(new Scene(root, 800, 450));
        CurrentStage.show();
    }
}
