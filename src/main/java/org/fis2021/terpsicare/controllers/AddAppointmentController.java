package org.fis2021.terpsicare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.stage.Stage;
import org.fis2021.terpsicare.services.UserService;

public class AddAppointmentController {

    @FXML
    private Button submitAppointment;

    @FXML
    private Button backButton;

    @FXML
    private ChoiceBox doctor;

    @FXML
    private ChoiceBox hour;

    @FXML
    public void initialize() {
        doctor.getItems().addAll(UserService.doctorListName());
        hour.getItems().addAll("8:00", "8:20", "8:40", "9:00", "9:20", "9:40", "10:00", "10:20", "10:40",
                "11:00", "11:20", "11:40", "12:00", "12:20", "12:40","13:00", "13:20", "13:40", "14:00", "14:20", "14:40",
                "15:00", "15:20", "15:40");
    }

    public void handleBackAction(ActionEvent event) throws Exception{
        Node node = (Node) event.getSource();
        Stage CurrentStage = (Stage) node.getScene().getWindow();
        Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePagePatient.fxml"));
        CurrentStage.setTitle("HomePage");
        CurrentStage.setScene(new Scene(root, 500, 500));
        CurrentStage.show();
    }
}
