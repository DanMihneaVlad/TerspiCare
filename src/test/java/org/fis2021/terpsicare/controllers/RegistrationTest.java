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
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.testfx.assertions.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(ApplicationExtension.class)
class RegistrationTest {

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
        Parent root = FXMLLoader.load(getClass().getResource("/sample.fxml"));
        primaryStage.setTitle("Register");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test an attempt to register with empty text fields")
    void emptyTextFieldsRegistrationTest(FxRobot robot) {
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#phonenumberField");
        robot.write(PHONENUMBER);
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#nameField");
        robot.write(USERNAME);robot.clickOn("#registerButton");
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#medicalrecordField");
        robot.write("Good");
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        assertThat(UserService.getAllPatients()).isEmpty();

        robot.clickOn("#passwordField2");
        robot.write(PASSWORD);
        robot.clickOn("#registerButton");
        assertThat(robot.lookup("#text").queryText()).hasText("Account created successfully!");
        robot.clickOn("#OK");

        assertThat(UserService.getAllPatients()).size().isEqualTo(1);

    }

    @Test
    @DisplayName("Test attempt to register with an username that already is registered")
    void usernameAlreadyExistsRegistrationTest(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addPatient(USERNAME, PASSWORD , USERNAME, PHONENUMBER, PASSWORD, "Good");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#phonenumberField");
        robot.write(PHONENUMBER);
        robot.clickOn("#nameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField2");
        robot.write(PASSWORD);
        robot.clickOn("#medicalrecordField");
        robot.write("Good");
        robot.clickOn("#registerButton");

        assertThat(UserService.getAllPatients()).size().isEqualTo(1);

        assertThat(robot.lookup("#text").queryText()).hasText("Username already exists!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test attempt to register when confirmed password is not the same as the password")
    void wrongPasswordConfirmationTest(FxRobot robot) {
        assertThat(UserService.getAllPatients()).isEmpty();
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#phonenumberField");
        robot.write(PHONENUMBER);
        robot.clickOn("#nameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField2");
        robot.write(PASSWORD + "1");
        robot.clickOn("#medicalrecordField");
        robot.write("Good");
        robot.clickOn("#registerButton");

        assertThat(UserService.getAllPatients()).isEmpty();

        assertThat(robot.lookup("#text").queryText()).hasText("Wrong password confirmation!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test if the user can successfully register if everything he entered is correct")
    void successfulRegistrationTest(FxRobot robot) {
        assertThat(UserService.getAllPatients()).isEmpty();
        robot.clickOn("#usernameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField");
        robot.write(PASSWORD);
        robot.clickOn("#phonenumberField");
        robot.write(PHONENUMBER);
        robot.clickOn("#nameField");
        robot.write(USERNAME);
        robot.clickOn("#passwordField2");
        robot.write(PASSWORD);
        robot.clickOn("#medicalrecordField");
        robot.write("Good");
        robot.clickOn("#registerButton");

        assertThat(UserService.getAllPatients()).size().isEqualTo(1);

        assertThat(robot.lookup("#text").queryText()).hasText("Account created successfully!");

        robot.clickOn("#OK");
    }

}