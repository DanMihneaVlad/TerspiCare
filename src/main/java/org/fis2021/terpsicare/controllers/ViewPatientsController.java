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
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.fis2021.terpsicare.AlertBox;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.NotAvailableException;
import org.fis2021.terpsicare.exceptions.WeekendDayException;
import org.fis2021.terpsicare.model.Appointment;
import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.model.Patient;
import org.fis2021.terpsicare.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewPatientsController implements Initializable {

    @FXML
    private TableView patientsTable;

    @FXML
    private TextField medicalField;

    @FXML
    private Button submit;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn name = new TableColumn("Name");
        TableColumn phone = new TableColumn("Phone number");
        TableColumn medicalRecord = new TableColumn("Medical record");
        patientsTable.getColumns().addAll(name, phone, medicalRecord);

        final ObservableList<Patient> data = FXCollections.observableArrayList(UserService.patientsList());

        name.setCellValueFactory(new PropertyValueFactory<Patient, String>("name"));
        phone.setCellValueFactory(new PropertyValueFactory<Patient, String>("phonenumber"));
        medicalRecord.setCellValueFactory(new PropertyValueFactory<Patient, String>("medicalrecord"));

        patientsTable.setItems(data);

    }
    public void handleEditMedicalRecord() {
        Patient selected = (Patient) patientsTable.getSelectionModel().getSelectedItem();

        if (selected == null) {
            AlertBox.display("Error", "Please select an entry to edit!");
        } else {
            medicalField.setVisible(true);
            submit.setVisible(true);

        }
    }
    public void submitEdit() {
        try {
            Patient selected = (Patient) patientsTable.getSelectionModel().getSelectedItem();
            String med = (String) medicalField.getText();
            UserService.editMedicalReport(selected, med);
            AlertBox.display("Success", "Medical record was successfully edited!");
            final ObservableList<Patient> data = FXCollections.observableArrayList(UserService.patientsList());
            patientsTable.setItems(data);

        } catch (EmptyTextfieldsException e) {
            AlertBox.display("Error", "Please enter something!");
        }
    }
    public void handleHome(ActionEvent event) throws Exception {
        try {
            Node node = (Node) event.getSource();
            Stage CurrentStage = (Stage) node.getScene().getWindow();
            Parent root = FXMLLoader.load(getClass().getClassLoader().getResource("HomePageDoctor.fxml"));
            CurrentStage.setTitle("HomePage");
            CurrentStage.setScene(new Scene(root, 600, 450));
            CurrentStage.show();
        } catch (IOException e) {
            System.out.println("Error");
        }
    }
}
