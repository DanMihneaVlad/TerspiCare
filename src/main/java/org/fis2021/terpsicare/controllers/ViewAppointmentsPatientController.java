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
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import org.fis2021.terpsicare.AlertBox;
import org.fis2021.terpsicare.model.Appointment;
import org.fis2021.terpsicare.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentsPatientController implements Initializable {

    @FXML
    private TableView myTable;

    @FXML
    private ChoiceBox hourBox;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn docName = new TableColumn("Doctor Name");
        TableColumn day = new TableColumn("Day");
        TableColumn month = new TableColumn("Month");
        TableColumn year = new TableColumn("Year");
        TableColumn dayWeek =  new TableColumn("Day of the Week");
        TableColumn hour = new TableColumn("Hour");
        TableColumn message = new TableColumn("Message");
        TableColumn reply = new TableColumn("Reply");

        myTable.getColumns().addAll(docName, day, month, year, dayWeek, hour, message, reply);

        final ObservableList<Appointment> data = FXCollections.observableArrayList(UserService.AppointmentsList());

        docName.setCellValueFactory(new PropertyValueFactory<Appointment,String>("doctorName"));
        day.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("day"));
        month.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("month"));
        year.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("year"));
        dayWeek.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dayOfTheWeek"));
        hour.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("hour"));
        message.setCellValueFactory(new PropertyValueFactory<Appointment, String>("message"));
        reply.setCellValueFactory(new PropertyValueFactory<Appointment, String>("reply"));

        myTable.setItems(data);
    }

    public void editAppointment() {
        Appointment selected = (Appointment) myTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.display("Error", "Please select an entry to edit!");
        } else {
            System.out.println(selected);
            hourBox.setVisible(true);
            hourBox.getItems().addAll("8:00", "8:20", "8:40", "9:00", "9:20", "9:40", "10:00", "10:20", "10:40",
                    "11:00", "11:20", "11:40", "12:00", "12:20", "12:40","13:00", "13:20", "13:40", "14:00", "14:20", "14:40",
                    "15:00", "15:20", "15:40");

        }
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
