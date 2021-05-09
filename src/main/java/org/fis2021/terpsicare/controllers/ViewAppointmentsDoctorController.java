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
import org.fis2021.terpsicare.model.Appointment;
import org.fis2021.terpsicare.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class ViewAppointmentsDoctorController implements Initializable {

    @FXML
    private TableView myTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn name = new TableColumn("Name");
        TableColumn day = new TableColumn("Day");
        TableColumn month = new TableColumn("Month");
        TableColumn year = new TableColumn("Year");
        TableColumn dayOfTheWeek = new TableColumn("Day of The Week");
        TableColumn hour = new TableColumn("Hour");
        TableColumn message = new TableColumn("Message");
        TableColumn reply = new TableColumn("Reply");
        myTable.getColumns().addAll(name, day, month, year, dayOfTheWeek, hour, message, reply);

        final ObservableList<Appointment> data = FXCollections.observableArrayList(UserService.getAppointments());

        name.setCellValueFactory(new PropertyValueFactory<Appointment, String>("patientName"));
        day.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("day"));
        month.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("month"));
        year.setCellValueFactory(new PropertyValueFactory<Appointment, Integer>("year"));
        dayOfTheWeek.setCellValueFactory(new PropertyValueFactory<Appointment, String>("dayOfTheWeek"));
        hour.setCellValueFactory(new PropertyValueFactory<Appointment, String>("hour"));
        message.setCellValueFactory(new PropertyValueFactory<Appointment, String>("message"));
        reply.setCellValueFactory(new PropertyValueFactory<Appointment,String>("reply"));

        myTable.setItems(data);
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
