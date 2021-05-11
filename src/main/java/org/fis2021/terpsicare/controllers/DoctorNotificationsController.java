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
import org.fis2021.terpsicare.AlertBox;
import org.fis2021.terpsicare.model.Appointment;
import org.fis2021.terpsicare.model.Notification;
import org.fis2021.terpsicare.services.UserService;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class DoctorNotificationsController implements Initializable {

    @FXML
    private TableView notificationsTable;

    @Override
    public void initialize(URL url, ResourceBundle rb) {

        TableColumn name = new TableColumn("Name");
        TableColumn day = new TableColumn("Day");
        TableColumn month = new TableColumn("Month");
        TableColumn year = new TableColumn("Year");
        TableColumn dayOfTheWeek = new TableColumn("Day of The Week");
        TableColumn oldHour = new TableColumn("Old Hour");
        TableColumn newHour = new TableColumn("New Hour");
        notificationsTable.getColumns().addAll(name, day, month, year, dayOfTheWeek, oldHour, newHour);

        final ObservableList<Notification> data = FXCollections.observableArrayList(UserService.getDoctorNotifications());

        name.setCellValueFactory(new PropertyValueFactory<Notification, String>("patientName"));
        day.setCellValueFactory(new PropertyValueFactory<Notification, Integer>("day"));
        month.setCellValueFactory(new PropertyValueFactory<Notification, Integer>("month"));
        year.setCellValueFactory(new PropertyValueFactory<Notification, Integer>("year"));
        dayOfTheWeek.setCellValueFactory(new PropertyValueFactory<Notification, String>("dayOfTheWeek"));
        oldHour.setCellValueFactory(new PropertyValueFactory<Notification, String>("oldHour"));
        newHour.setCellValueFactory(new PropertyValueFactory<Notification, String>("newHour"));

        notificationsTable.setItems(data);
    }

    public void markAsViewed(ActionEvent event) throws Exception {
        Notification selected = (Notification) notificationsTable.getSelectionModel().getSelectedItem();
        if (selected == null) {
            AlertBox.display("Error", "Please select a notification!");
        } else {
            UserService.deleteNotification(selected);
            AlertBox.display("Success", "Notification was successfully marked as viewed!");
            final ObservableList<Notification> data = FXCollections.observableArrayList(UserService.getDoctorNotifications());
            notificationsTable.setItems(data);
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
