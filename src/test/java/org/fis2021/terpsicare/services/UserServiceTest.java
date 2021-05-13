package org.fis2021.terpsicare.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.terpsicare.exceptions.EmptyTextfieldsException;
import org.fis2021.terpsicare.exceptions.UsernameAlreadyExistsException;
import org.fis2021.terpsicare.exceptions.WrongPasswordConfirmationException;
import org.fis2021.terpsicare.model.Admin;
import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.model.Patient;
import org.junit.jupiter.api.*;

import java.io.IOException;

import static org.testfx.assertions.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceTest {

    public static final String ADMIN = "admin";
    public static final String PATIENT = "PATIENT";
    public static final String DOCTOR = "DOCTOR";
    public static final String PHONENUMBER = "0123456789";

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before Class");
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After Class");
    }

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

    @Test
    @DisplayName("Database is initialized, and there are no entries in the patients, doctors and appointments tables and one entry in the admins table")
    void databaseIsInitializedAndNoPatientAndNoDoctorsAndNoAppointmentsIsPersisted() {
        assertThat(UserService.getAllPatients()).isNotNull();
        assertThat(UserService.getAllPatients()).isEmpty();
        assertThat(UserService.DoctorsList()).isNotNull();
        assertThat(UserService.DoctorsList()).isEmpty();
        assertThat(UserService.getAllAppointments()).isNotNull();
        assertThat(UserService.getAllAppointments()).isEmpty();
        assertThat(UserService.getAdmins()).isNotEmpty();
        assertThat(UserService.getAdmins()).size().isEqualTo(1);
        Admin admin = UserService.getAdmins().get(0);
        assertThat(admin).isNotNull();
        assertThat(admin.getUsername()).isEqualTo(ADMIN);
        assertThat(admin.getPassword()).isEqualTo(UserService.encodePassword(ADMIN, ADMIN));
        assertThat(admin.getRole()).isEqualTo("Admin");
    }

    @Test
    @DisplayName("Patient is added to the database")
    void testPatientIsAddedToDatabase() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, PATIENT, PATIENT);
        assertThat(UserService.getAllPatients()).isNotEmpty();
        assertThat(UserService.getAllPatients()).size().isEqualTo(1);
        Patient patient = UserService.getAllPatients().get(0);
        assertThat(patient).isNotNull();
        assertThat(patient.getUsername()).isEqualTo(PATIENT);
        assertThat(patient.getName()).isEqualTo(PATIENT);
        assertThat(patient.getRole()).isEqualTo("patient");
        assertThat(patient.getPassword()).isEqualTo(UserService.encodePassword(PATIENT, PATIENT));
        assertThat(patient.getMedicalrecord()).isEqualTo(PATIENT);
        assertThat(patient.getPhonenumber()).isEqualTo(PHONENUMBER);
    }

    @Test
    @DisplayName("Doctor is added to the database")
    void testDoctorIsAddedToDatabase() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException {
        UserService.addDoctor(DOCTOR, DOCTOR, DOCTOR, DOCTOR, DOCTOR, PHONENUMBER, DOCTOR);
        assertThat(UserService.DoctorsList()).isNotEmpty();
        assertThat(UserService.DoctorsList()).size().isEqualTo(1);
        Doctor doctor = UserService.DoctorsList().get(0);
        assertThat(doctor).isNotNull();
        assertThat(doctor.getUsername()).isEqualTo(DOCTOR);
        assertThat(doctor.getName()).isEqualTo(DOCTOR);
        assertThat(doctor.getRole()).isEqualTo("doctor");
        assertThat(doctor.getPassword()).isEqualTo(UserService.encodePassword(DOCTOR, DOCTOR));
        assertThat(doctor.getDescription()).isEqualTo(DOCTOR);
        assertThat(doctor.getMedicalSpecialty()).isEqualTo(DOCTOR);
        assertThat(doctor.getPhoneNumber()).isEqualTo(PHONENUMBER);
    }

}