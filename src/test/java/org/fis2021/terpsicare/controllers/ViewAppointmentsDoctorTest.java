package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.terpsicare.exceptions.*;
import org.fis2021.terpsicare.services.FileSystemService;
import org.fis2021.terpsicare.services.UserService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.testfx.api.FxRobot;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class ViewAppointmentsDoctorTest {

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONENUMBER = "0123456789";
    public static final String DOCTORUSERNAME = "DOCTORUSERNAME";

    @BeforeEach
    void setUp() throws Exception {
        FileSystemService.APPLICATION_FOLDER = ".test-terpsicare";
        FileSystemService.initDirectory();
        FileUtils.cleanDirectory(FileSystemService.getApplicationHomeFolder().toFile());
        UserService.initDatabase();
    }

    @AfterEach
    void tearDown() {
        UserService.getDatabase().close();
        System.out.println("After Each");
    }

    @Start
    void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/LogIn.fxml"));
        primaryStage.setTitle("View Doctors");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    @Test
    @DisplayName("All appointments are shown in the table")
    void testAllAppointmentsShown(FxRobot robot) throws InvalidPhoneNumberException, WrongPasswordConfirmationException, EmptyTextfieldsException, UsernameAlreadyExistsException, NotAvailableException, WeekendDayException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        UserService.addAppointment(USERNAME, DOCTORUSERNAME, 9999, 12, 30, "Monday", "8:00", "message");
        UserService.addAppointment(USERNAME, DOCTORUSERNAME, 9999, 11, 30, "Tuesday", "12:00", "");

        robot.clickOn("#usernameFieldLogIn");
        robot.write(DOCTORUSERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewAppointments");

        robot.clickOn("11");
        robot.clickOn("Monday");
        robot.clickOn("8:00");
        robot.clickOn("message");

        robot.clickOn("Tuesday");
        robot.clickOn("12:00");
    }
}