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
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.model.Patient;
import org.fis2021.terpsicare.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewPatientsController implements Initializable {

    @FXML
    private TableView patientsTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn name = new TableColumn("Name");
        TableColumn phone = new TableColumn("Phone number");
        TableColumn medicalRecord = new TableColumn("Medical record");
        patientsTable.getColumns().addAll(name, phone, medicalRecord);

        final ObservableList<Doctor> data = FXCollections.observableArrayList(UserService.patientsList());

        name.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<Patient, String>("phonenumber"));
        medicalRecord.setCellValueFactory(new PropertyValueFactory<Patient, String>("medicalrecord"));

        patientsTable.setItems(data);

    }

    public void handleHome(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePageDoctor.fxml"));
            CurrentStage.setTitle("HomePage");
            CurrentStage.setScene(new Scene(root, 500, 500));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
