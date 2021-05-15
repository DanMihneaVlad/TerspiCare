package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.apache.commons.io.FileUtils;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
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
class LogInTest {

    public static final String USERNAME = "USERNAME";
    public static final String PASSWORD = "PASSWORD";
    public static final String PHONENUMBER = "0123456789";

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
    @DisplayName("Test login attempt with an username that doesn't exist")
    void testUsernameDoesNotExistLogIn(FxRobot robot) {
        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Username does not exist!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test login attempt with wrong password")
    void testWrongPasswordLogIn(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD + "1");
        robot.clickOn("#loginButton");

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Wrong password!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test if a patient can log in with the correct credentials and is redirected to the patient home page")
    void testLogInPatient(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#addAppointment");
    }

    @Test
    @DisplayName("Test if a doctor can log in with the correct credentials and is redirected to the doctor home page")
    void testLogInDoctor(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addDoctor(USERNAME, PASSWORD, PASSWORD, USERNAME, "Cardiology", PHONENUMBER, "");
        assertThat(UserService.DoctorsList()).size().isEqualTo(1);
        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewPatients");
    }

    @Test
    @DisplayName("Test if the admin can log in with the correct credentials and is redirected to the admin home page")
    void testLogInAdmin(FxRobot robot) {
        assertThat(UserService.getAdmins()).size().isEqualTo(1);
        robot.clickOn("#usernameFieldLogIn");
        robot.write("admin");
        robot.clickOn("#passwordFieldLogIn");
        robot.write("admin");
        robot.clickOn("#loginButton");
        robot.clickOn("#addDoctors");
    }
}