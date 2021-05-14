package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
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
class AddDoctorTest {

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
        Parent root = FXMLLoader.load(getClass().getResource("/addDoctor.fxml"));
        primaryStage.setTitle("Add Doctor");
        primaryStage.setScene(new Scene(root, 500, 500));
        primaryStage.show();
    }

    @Test
    @DisplayName("Test an attempt to add doctor with empty text fields")
    void emptyTextFieldsRegistrationTest(FxRobot robot) {
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#usernameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#passwordFieldDoctor");
        robot.write(PASSWORD);
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#passwordConfirmationDoctor");
        robot.write(PASSWORD);
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#nameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        robot.clickOn("#phonenumberFieldDoctor");
        robot.write(PHONENUMBER);
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("You cannot leave empty text fields!");
        robot.clickOn("#OK");

        assertThat(UserService.DoctorsList()).isEmpty();

        robot.clickOn("#medicalSpecialtyDoctor");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#registerDoctor");
        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Account created successfully!");
        robot.clickOn("#OK");

        assertThat(UserService.DoctorsList()).size().isEqualTo(1);

    }

    @Test
    @DisplayName("Test attempt to add doctor with an username that already is registered")
    void usernameAlreadyExistsRegistrationTest(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addPatient(USERNAME, PASSWORD , USERNAME, PHONENUMBER, PASSWORD, "Good");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        robot.clickOn("#usernameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldDoctor");
        robot.write(PASSWORD);
        robot.clickOn("#passwordConfirmationDoctor");
        robot.write(PASSWORD + "1");
        robot.clickOn("#nameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#phonenumberFieldDoctor");
        robot.write(PHONENUMBER);
        robot.clickOn("#medicalSpecialtyDoctor");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#descriptionField");
        robot.write("description");
        robot.clickOn("#registerDoctor");

        assertThat(UserService.DoctorsList()).isEmpty();

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Username already exists!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test attempt to add doctor when confirmed password is not the same as the password")
    void wrongPasswordConfirmationTest(FxRobot robot) {
        assertThat(UserService.DoctorsList()).isEmpty();
        robot.clickOn("#usernameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldDoctor");
        robot.write(PASSWORD);
        robot.clickOn("#passwordConfirmationDoctor");
        robot.write(PASSWORD + "1");
        robot.clickOn("#nameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#phonenumberFieldDoctor");
        robot.write(PHONENUMBER);
        robot.clickOn("#medicalSpecialtyDoctor");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#descriptionField");
        robot.write("description");
        robot.clickOn("#registerDoctor");

        assertThat(UserService.DoctorsList()).isEmpty();

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Wrong password confirmation!");

        robot.clickOn("#OK");
    }

    @Test
    @DisplayName("Test if the admin can successfully add a doctor if everything he entered is correct")
    void successfulRegistrationTest(FxRobot robot) {
        assertThat(UserService.DoctorsList()).isEmpty();
        robot.clickOn("#usernameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldDoctor");
        robot.write(PASSWORD);
        robot.clickOn("#passwordConfirmationDoctor");
        robot.write(PASSWORD);
        robot.clickOn("#nameFieldDoctor");
        robot.write(USERNAME);
        robot.clickOn("#phonenumberFieldDoctor");
        robot.write(PHONENUMBER);
        robot.clickOn("#medicalSpecialtyDoctor");
        robot.type(KeyCode.ENTER);
        robot.clickOn("#descriptionField");
        robot.write("description");
        robot.clickOn("#registerDoctor");

        assertThat(UserService.DoctorsList()).size().isEqualTo(1);

        Assertions.assertThat(robot.lookup("#text").queryText()).hasText("Account created successfully!");

        robot.clickOn("#OK");
    }

}