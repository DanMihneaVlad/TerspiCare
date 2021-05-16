package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
import org.testfx.assertions.api.Assertions;
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

@ExtendWith(ApplicationExtension.class)
class ViewAppointmentsPatientTest {

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
        primaryStage.setTitle("Log In");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }
    @Test
    @DisplayName("All appointments are shown in the table")
    void testAllAppointmentsShown(FxRobot robot) throws InvalidPhoneNumberException, WrongPasswordConfirmationException, EmptyTextfieldsException, UsernameAlreadyExistsException, NotAvailableException, WeekendDayException, InvalidDateException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        UserService.addAppointment(USERNAME, DOCTORUSERNAME, 9999, 12, 30, "Monday", "8:00", "message");
        UserService.addAppointment(USERNAME, DOCTORUSERNAME, 9999, 11, 30, "Tuesday", "12:00", "");

        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewAppointmentsPatient");

        robot.clickOn("11");
        robot.clickOn("Monday");
        robot.clickOn("8:00");
        robot.clickOn("message");

        robot.clickOn("Tuesday");
        robot.clickOn("12:00");
    }

    @Test
    @DisplayName("Test if notifications are shown successfully")
    void testEditAppointment(FxRobot robot) throws InvalidPhoneNumberException, WrongPasswordConfirmationException, EmptyTextfieldsException, UsernameAlreadyExistsException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");

        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#addAppointment");
        robot.clickOn("#doctorChoice");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#datePicker");
        robot.dropBy(70,0);
        robot.clickOn();
        robot.dropBy(-60,30);
        robot.clickOn();
        robot.dropBy(20,60);
        robot.clickOn();
        robot.clickOn("#hourChoice");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#submitAppointment");
        robot.clickOn("#OK");
        robot.clickOn("#backAppointment");

        robot.clickOn("#viewAppointmentsPatient");

        robot.clickOn("#editAppointment");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Please select an entry to edit!");
        robot.clickOn("#OK");

        robot.clickOn("8:00");
        robot.clickOn("#editAppointment");
        robot.clickOn("#hourBox");
        robot.clickOn("11:00");
        robot.clickOn("#submitEdit");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Appointment was successfully edited!");
        robot.clickOn("#OK");

        robot.clickOn("#homePatient");
        robot.clickOn("#patientLogout");

        robot.clickOn("#homePageLogIn");
        robot.clickOn("#usernameFieldLogIn");
        robot.write(DOCTORUSERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");

        robot.clickOn("#viewNotifications");

        robot.clickOn("#markAsViewed");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Please select a notification!");
        robot.clickOn("#OK");

        robot.clickOn("11:00");
        robot.clickOn("#markAsViewed");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Notification was successfully marked as viewed!");
        robot.clickOn("#OK");
        robot.clickOn("No content in table");
    }

}