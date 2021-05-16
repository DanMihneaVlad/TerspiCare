package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.InvalidPhoneNumberException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
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

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class AddAppointmentTest {

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
    @DisplayName("Test empty text fields")
    void testEmptyTextFields(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        assertThat(UserService.DoctorsList()).size().isEqualTo(1);
        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#addAppointment");

        robot.clickOn("#submitAppointment");
        assertThat(UserService.getAllAppointments()).isEmpty();

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");

        robot.clickOn("#OK");

        robot.clickOn("#doctorChoice");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#submitAppointment");
        assertThat(UserService.getAllAppointments()).isEmpty();

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");

        robot.clickOn("#OK");
        robot.clickOn("#datePicker");
        robot.dropBy(70,0);
        robot.clickOn();
        robot.dropBy(0,150);
        robot.clickOn();
        robot.clickOn("#submitAppointment");
        assertThat(UserService.getAllAppointments()).isEmpty();

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");

        robot.clickOn("#OK");
        robot.clickOn("#hourChoice");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#submitAppointment");

        assertThat(UserService.getAllAppointments()).size().isEqualTo(1);

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Appointment was successfully created!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test attempt to make appointment on a weekend")
    void testWeekendDayError(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        assertThat(UserService.DoctorsList()).size().isEqualTo(1);
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
        robot.dropBy(60,150);
        robot.clickOn();
        robot.clickOn("#hourChoice");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#submitAppointment");

        assertThat(UserService.getAllAppointments()).isEmpty();

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Doctors don't work on a weekend!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Check an appointment is added successfully to the database")
    void testAppointmentIsMadeSuccessfully(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        assertThat(UserService.DoctorsList()).size().isEqualTo(1);
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
        robot.dropBy(0,150);
        robot.clickOn();
        robot.clickOn("#hourChoice");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#submitAppointment");

        assertThat(UserService.getAllAppointments()).size().isEqualTo(1);

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Appointment was successfully created!");

        robot.clickOn("#OK");
    }
}