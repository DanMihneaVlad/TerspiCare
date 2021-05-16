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
class ViewPatientsTest {

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
    @DisplayName("All the patients are shown in the table")
    void testAllPatientsShown(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addPatient(USERNAME + "1", PASSWORD, USERNAME + "1", PHONENUMBER, PASSWORD, "Good");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        UserService.addDoctor(DOCTORUSERNAME + "1", PASSWORD, PASSWORD, DOCTORUSERNAME + "1", "Family Medicine", PHONENUMBER, "");
        assertThat(UserService.getAllPatients()).size().isEqualTo(2);
        assertThat(UserService.DoctorsList()).size().isEqualTo(2);

        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#addAppointment");
        robot.clickOn("#doctorChoice");
        robot.clickOn("DOCTORUSERNAME");
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

        assertThat(UserService.getAllAppointments()).size().isEqualTo(1);

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Appointment was successfully created!");

        robot.clickOn("#OK");

        robot.clickOn("#backAppointment");
        robot.clickOn("#patientLogout");

        robot.clickOn("#homePageLogIn");
        robot.clickOn("#usernameFieldLogIn");
        robot.write("USERNAME1");
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#addAppointment");
        robot.clickOn("#doctorChoice");
        robot.clickOn("DOCTORUSERNAME");
        robot.clickOn("#datePicker");
        robot.dropBy(70,0);
        robot.clickOn();
        robot.dropBy(-60,30);
        robot.clickOn();
        robot.dropBy(20,60);
        robot.clickOn();
        robot.clickOn("#hourChoice");
        robot.clickOn("12:00");
        robot.clickOn("#submitAppointment");

        assertThat(UserService.getAllAppointments()).size().isEqualTo(2);

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Appointment was successfully created!");

        robot.clickOn("#OK");

        robot.clickOn("#backAppointment");
        robot.clickOn("#patientLogout");

        robot.clickOn("#homePageLogIn");
        robot.clickOn("#usernameFieldLogIn");
        robot.write(DOCTORUSERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewPatients");
        robot.clickOn("USERNAME");
        robot.clickOn("USERNAME1");
        robot.clickOn("#backViewPatients");
        robot.clickOn("#logoutDoctor");

        robot.clickOn("#homePageLogIn");
        robot.clickOn("#usernameFieldLogIn");
        robot.write("DOCTORUSERNAME1");
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewPatients");
        robot.clickOn("No content in table");
    }

    @Test
    @DisplayName("Doctor can edit the medical record")
    void testEditRecord(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
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
        robot.clickOn("DOCTORUSERNAME");
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

        assertThat(UserService.getAllAppointments()).size().isEqualTo(1);

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Appointment was successfully created!");

        robot.clickOn("#OK");

        robot.clickOn("#backAppointment");
        robot.clickOn("#patientLogout");

        robot.clickOn("#homePageLogIn");
        robot.clickOn("#usernameFieldLogIn");
        robot.write(DOCTORUSERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewPatients");
        robot.clickOn("#editMedicalRecord");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Please select an entry to edit!");

        robot.clickOn("#OK");

        robot.clickOn("USERNAME");
        robot.clickOn("#editMedicalRecord");
        robot.clickOn("#submitMedicalRecordEdit");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Please enter something!");

        robot.clickOn("#OK");

        robot.clickOn("#medicalField");

        robot.write("Good");
        robot.clickOn("#submitMedicalRecordEdit");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Medical record was successfully edited!");

        robot.clickOn("#OK");

        robot.clickOn("#backViewPatients");
        robot.clickOn("#viewPatients");
        robot.clickOn("Good");
    }
}