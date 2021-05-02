package org.fis2021.terpsicare.controllers;


import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.stage.Stage;

import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewDoctorsController implements Initializable {

    @FXML
    private Label label;

    @FXML
    private TableView myTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn name = new TableColumn("NAME");
        TableColumn phone = new TableColumn("PHONE");
        TableColumn medicalspec = new TableColumn("MEDICAL SPECIALITY");
        myTable.getColumns().addAll(name, phone, medicalspec);

        final ObservableList<Doctor> data = FXCollections.observableArrayList(UserService.DoctorsList());

        name.setCellValueFactory(new PropertyValueFactory<Doctor,String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<Doctor,String>("phoneNumber"));
        medicalspec.setCellValueFactory(new PropertyValueFactory<Doctor,String>("medicalSpecialty"));

        myTable.setItems(data);

    }
    public void handleHome(ActionEvent event) throws Exception{
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePagePatient.fxml"));
            CurrentStage.setTitle("HomePage");
            CurrentStage.setScene(new Scene(root, 500, 500));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }

}

