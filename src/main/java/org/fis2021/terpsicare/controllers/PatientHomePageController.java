package org.fis2021.terpsicare.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class PatientHomePageController {

    @FXML
    private Button logoutButton;

    @FXML
    private Button addAppointment;

    @FXML
    public void handleLogout(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePage.fxml"));
            CurrentStage.setTitle("HomePage");
            CurrentStage.setScene(new Scene(root, 300, 245));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    @FXML
    public void handleAddAppointment(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("AddAppointment.fxml"));
            CurrentStage.setTitle("Add Appointment");
            CurrentStage.setScene(new Scene(root, 500, 500));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void handleViewDoctors(ActionEvent event) throws Exception{
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ViewDoctors.fxml"));
            CurrentStage.setTitle("View Doctors");
            CurrentStage.setScene(new Scene(root, 500, 500));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

    public void handleViewAppointments(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("ViewAppointmentsPatient.fxml"));
            CurrentStage.setTitle("View Appointments");
            CurrentStage.setScene(new Scene(root, 600, 450));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
