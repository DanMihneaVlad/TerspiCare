package org.fis2021.terpsicare.controllers;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
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
import org.testfx.framework.junit5.ApplicationExtension;
import org.testfx.framework.junit5.Start;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(ApplicationExtension.class)
class ViewDoctorsTest {

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
    @DisplayName("All the doctors are shown in the table")
    void testAllDoctorsShown(FxRobot robot) throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addPatient(USERNAME, PASSWORD, USERNAME, PHONENUMBER, PASSWORD, "");
        UserService.addDoctor(DOCTORUSERNAME, PASSWORD, PASSWORD, DOCTORUSERNAME, "Cardiology", PHONENUMBER, "description");
        UserService.addDoctor(DOCTORUSERNAME + "1", PASSWORD, PASSWORD, DOCTORUSERNAME + "1", "Family Medicine", PHONENUMBER, "");
        UserService.addDoctor(DOCTORUSERNAME + "2", PASSWORD, PASSWORD, DOCTORUSERNAME + "2", "Family Medicine", PHONENUMBER, "");
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        assertThat(UserService.DoctorsList()).size().isEqualTo(3);

        robot.clickOn("#usernameFieldLogIn");
        robot.write(USERNAME);
        robot.clickOn("#passwordFieldLogIn");
        robot.write(PASSWORD);
        robot.clickOn("#loginButton");
        robot.clickOn("#viewDoctors");

        robot.clickOn("DOCTORUSERNAME");
        robot.clickOn("DOCTORUSERNAME1");
        robot.clickOn("DOCTORUSERNAME2");

        robot.clickOn("#medicalS");
        robot.clickOn("Urology");
        robot.clickOn("No content in table");

        robot.clickOn("#medicalS");
        robot.clickOn("Cardiology");
        robot.clickOn("DOCTORUSERNAME");

        robot.clickOn("#medicalS");
        robot.clickOn("Pediatrics");
        robot.clickOn("No content in table");

        robot.clickOn("#medicalS");
        robot.clickOn("Family Medicine");
        robot.clickOn("DOCTORUSERNAME1");
        robot.clickOn("DOCTORUSERNAME2");
    }
}