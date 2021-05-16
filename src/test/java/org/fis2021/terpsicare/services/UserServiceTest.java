package org.fis2021.terpsicare.services;

import org.apache.commons.io.FileUtils;
import org.fis2021.terpsicare.exceptions.*;
import org.fis2021.terpsicare.model.Admin;
import org.fis2021.terpsicare.model.Appointment;
import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.model.Patient;
import org.junit.jupiter.api.*;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.testfx.assertions.api.Assertions.assertThat;

class UserServiceTest {

    public static final String ADMIN = "admin";
    public static final String PATIENT = "PATIENT";
    public static final String DOCTOR = "DOCTOR";
    public static final String DOCTOR1 = "DOCTOR1";
    public static final String PHONENUMBER = "0123456789";
    public static final int DATE = 9999;
    public static final String DAY = "MONDAY";
    public static final String HOUR = "12:00";
    public static final String NEWHOUR = "13:00";
    public static final String MESSAGE = "MESSAGE";
    public static final String NEWREPORT = "REPORT";
    public static final String REPLY = "REPLY";
    public static final String WRONGPASWWORD = "WRONGPASSWORD";

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
    @DisplayName("Patient is successfully added to the database")
    void testPatientIsAddedToDatabase() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
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
    @DisplayName("Doctor is successfully added to the database")
    void testDoctorIsAddedToDatabase() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
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

    @Test
    @DisplayName("Appointment is successfully added to the database")
    void testAppointmentIsAddedToDatabase() throws EmptyTextfieldsException, NotAvailableException, WeekendDayException, InvalidDateException {
        UserService.addAppointment(PATIENT, DOCTOR, DATE, DATE, DATE, DAY, HOUR, MESSAGE);
        assertThat(UserService.getAllAppointments()).isNotEmpty();
        assertThat(UserService.getAllAppointments()).size().isEqualTo(1);
        Appointment appointment = UserService.getAllAppointments().get(0);
        assertThat(appointment).isNotNull();
        assertThat(appointment.getUsername()).isEqualTo(PATIENT);
        assertThat(appointment.getDoctorName()).isEqualTo(DOCTOR);
        assertThat(appointment.getYear()).isEqualTo(DATE);
        assertThat(appointment.getMonth()).isEqualTo(DATE);
        assertThat(appointment.getDay()).isEqualTo(DATE);
        assertThat(appointment.getHour()).isEqualTo(HOUR);
        assertThat(appointment.getMessage()).isEqualTo(MESSAGE);
        assertThat(appointment.getReply()).isEqualTo(null);
    }

    @Test
    @DisplayName("Appointment is successfully edited and saved in the database")
    void testAppointmentIsEditedAndSaved() throws EmptyTextfieldsException, NotAvailableException, WeekendDayException, InvalidDateException {
        UserService.addAppointment(PATIENT, DOCTOR, DATE, DATE, DATE, DAY, HOUR, MESSAGE);
        UserService.editAppointment(UserService.getAllAppointments().get(0), NEWHOUR);
        Appointment appointment = UserService.getAllAppointments().get(0);
        assertThat(appointment.getHour()).isEqualTo(NEWHOUR);
    }

    @Test
    @DisplayName("Medical record is successfully edited")
    void testPatientMedicalRecordIsEditedAndSaved() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, PATIENT, PATIENT);
        UserService.editMedicalReport(UserService.getAllPatients().get(0), NEWREPORT);
        Patient patient = UserService.getAllPatients().get(0);
        assertThat(patient.getMedicalrecord()).isEqualTo(NEWREPORT);
    }

    @Test
    @DisplayName("Reply is successfully added to the appointment")
    void testReplyIsAddedToTheAppointment() throws EmptyTextfieldsException, NotAvailableException, WeekendDayException, InvalidDateException {
        UserService.addAppointment(PATIENT, DOCTOR, DATE, DATE, DATE, DAY, HOUR, MESSAGE);
        UserService.replyAppointment(UserService.getAllAppointments().get(0), REPLY);
        Appointment appointment = UserService.getAllAppointments().get(0);
        assertThat(appointment.getReply()).isEqualTo(REPLY);
    }

    @Test
    @DisplayName("Password is encoded")
    void testPasswordIsEncoded() {
        String encodedPassword = UserService.encodePassword(PATIENT, PATIENT);
        assertThat(PATIENT).isNotEqualTo(encodedPassword);
        assertThat(UserService.encodePassword(PATIENT, PATIENT)).isEqualTo(encodedPassword);
    }

    @Test
    @DisplayName("The correct doctor username is returned for a doctor name")
    void testDoctorUsernameForDoctorName() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, InvalidPhoneNumberException {
        UserService.addDoctor(DOCTOR, DOCTOR, DOCTOR, DOCTOR, DOCTOR, PHONENUMBER, DOCTOR);
        UserService.addDoctor(DOCTOR1, DOCTOR1, DOCTOR1, DOCTOR1, DOCTOR1, PHONENUMBER, DOCTOR1);
        assertThat(UserService.getDoctorUsername(DOCTOR)).isEqualTo(DOCTOR);
        assertThat(UserService.getDoctorUsername(DOCTOR)).isNotEqualTo(DOCTOR1);
        assertThat(UserService.getDoctorUsername(DOCTOR1)).isNotEqualTo(DOCTOR);
        assertThat(UserService.getDoctorUsername(DOCTOR1)).isEqualTo(DOCTOR1);
    }

    @Test
    @DisplayName("Username exists in database")
    void testUsernameExists() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, UsernameDoesNotExistException, InvalidPhoneNumberException {
        UserService.addDoctor(DOCTOR, DOCTOR, DOCTOR, DOCTOR, DOCTOR, PHONENUMBER, DOCTOR);
        UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, PATIENT, PATIENT);
        assertThat(UserService.checkUserExist(PATIENT)).isEqualTo("patient");
        assertThat(UserService.checkUserExist(DOCTOR)).isEqualTo("doctor");
        assertThat(UserService.checkUserExist(ADMIN)).isEqualTo("Admin");
    }

    @Test
    @DisplayName("Check user credentials")
    void testUserCredentials() throws EmptyTextfieldsException, WrongPasswordConfirmationException, UsernameAlreadyExistsException, UsernameDoesNotExistException, WrongPasswordException, InvalidPhoneNumberException {
        UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, PATIENT, PATIENT);
        assertThat(UserService.checkUserCredentials(PATIENT, PATIENT)).isEqualTo(1);
    }

    @Test
    @DisplayName("Invalid phone number")
    void testInvalidPhoneNumber() {
        assertThrows(InvalidPhoneNumberException.class, () -> {
            UserService.checkValidPhoneNumber("");
        });
        assertThrows(InvalidPhoneNumberException.class, () -> {
            UserService.checkValidPhoneNumber("0123");
        });
        assertThrows(InvalidPhoneNumberException.class, () -> {
            UserService.checkValidPhoneNumber("abcdefghij");
        });
        assertThrows(InvalidPhoneNumberException.class, () -> {
            UserService.checkValidPhoneNumber("012345678");
        });
        assertThrows(InvalidPhoneNumberException.class, () -> {
            UserService.checkValidPhoneNumber("01234567890");
        });
    }

    @Test
    @DisplayName("Username does not exist")
    void testUsernameDoesNotExist() {
        assertThrows(UsernameDoesNotExistException.class, () -> {
            UserService.checkUserExist("Doctor");
        });
        assertThrows(UsernameDoesNotExistException.class, () -> {
            UserService.checkUserExist("");
        });
        assertThrows(UsernameDoesNotExistException.class, () -> {
            UserService.checkUserExist(null);
        });
    }

    @Test
    @DisplayName("User can not be added twice")
    void testUserCanNotBeAddedTwice() {
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, PATIENT, PATIENT);
            UserService.checkUserDoesNotAlreadyExist(PATIENT);
        });
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.addDoctor(DOCTOR, DOCTOR, DOCTOR, DOCTOR, DOCTOR, PHONENUMBER, DOCTOR);
            UserService.checkUserDoesNotAlreadyExist(DOCTOR);
        });
        assertThrows(UsernameAlreadyExistsException.class, () -> {
            UserService.checkUserDoesNotAlreadyExist("admin");
        });
    }

    @Test
    @DisplayName("Password confirmation must be the same as the password")
    void testPasswordSameAsConfirmedPassword() {
        assertThrows(WrongPasswordConfirmationException.class, () -> {
            UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, WRONGPASWWORD, PATIENT);
        });
        assertThrows(WrongPasswordConfirmationException.class, () -> {
           UserService.addPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, null, PATIENT);
        });
    }

    @Test
    @DisplayName("The user cannot leave empty text fields")
    void testEmptyTextFieldsUser() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfieldsPatient("", PATIENT, PATIENT, PHONENUMBER, PATIENT);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfieldsPatient(PATIENT, "", PATIENT, PHONENUMBER, PATIENT);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfieldsPatient(PATIENT, PATIENT, "", PHONENUMBER, PATIENT);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfieldsPatient(PATIENT, PATIENT, PATIENT, "", PATIENT);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextfieldsPatient(PATIENT, PATIENT, PATIENT, PHONENUMBER, "");
        });
    }

    @Test
    @DisplayName("The admin cannot leave empty text fields when adding a doctor")
    void testEmptyTextFieldsDoctor() {
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextFieldsDoctor("", DOCTOR, DOCTOR, DOCTOR, DOCTOR, PHONENUMBER);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextFieldsDoctor(DOCTOR, "", DOCTOR, DOCTOR, DOCTOR, PHONENUMBER);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextFieldsDoctor(DOCTOR, DOCTOR, "", DOCTOR, DOCTOR, PHONENUMBER);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextFieldsDoctor(DOCTOR, DOCTOR, DOCTOR, "", DOCTOR, PHONENUMBER);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextFieldsDoctor(DOCTOR, DOCTOR, DOCTOR, DOCTOR, null, PHONENUMBER);
        });
        assertThrows(EmptyTextfieldsException.class, () -> {
            UserService.checkEmptyTextFieldsDoctor(DOCTOR, DOCTOR, DOCTOR, DOCTOR, DOCTOR, "");
        });
    }
}