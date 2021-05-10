package org.fis2021.terpsicare.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import org.fis2021.terpsicare.exceptions.*;
import org.fis2021.terpsicare.model.*;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static org.fis2021.terpsicare.services.FileSystemService.getPathToFile;

public class UserService {

    private static ObjectRepository<Patient> patientRepository;
    private static ObjectRepository<Doctor> doctorRepository;
    private static ObjectRepository<Admin> adminRepository;
    private static ObjectRepository<Appointment> appointmentRepository;
    private static String loggedInUsername = new String();
    public static void initDatabase() {
        Nitrite database = Nitrite.builder().filePath(getPathToFile(".terpsicare-users.db").toFile()).openOrCreate("test", "test");
        patientRepository = database.getRepository(Patient.class);
        doctorRepository = database.getRepository(Doctor.class);
        adminRepository = database.getRepository(Admin.class);
        appointmentRepository = database.getRepository(Appointment.class);
        int ok = 1;
        for (User user : adminRepository.find()) {
            if (Objects.equals("admin", user.getUsername())) {
                ok = 0;
                break;
            }
        }
        if (ok == 1) {
            Admin admin = new Admin("admin" , encodePassword("admin", "admin"), "Admin");
            adminRepository.insert(admin);
        }
    }

    public static void addDoctor(String username, String password, String confirmedPassword, String name, String medicalSpecialty, String phoneNumber, String description) throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkUserDoesNotAlreadyExist(username);
        checkPasswordSameAsConfirmedPassword(password, confirmedPassword);
        checkEmptyTextFieldsDoctor(username, password, confirmedPassword, name, medicalSpecialty, phoneNumber);
        doctorRepository.insert(new Doctor(username, encodePassword(username, password), name, medicalSpecialty, phoneNumber, description));
    }

    public static void addPatient(String username, String password,String name, String phone, String password2, String medicalrecord) throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkEmptyTextfieldsPatient(username, password, name, phone, password2);
        checkUserDoesNotAlreadyExist(username);
        checkPasswordSameAsConfirmedPassword(password, password2);
        patientRepository.insert(new Patient(username, encodePassword(username, password), name, phone, medicalrecord));
    }

    public static void addAppointment(String username, String doctorName, int year, int month, int day, String dayOfTheWeek, String hour, String message) throws EmptyTextfieldsException, NotAvailableException, WeekendDayException {
        checkEmptyTextfieldsAppointment(doctorName, year, hour);
        checkAvailability(doctorName, year, month, day, dayOfTheWeek, hour);
        String doctorUsername = getDoctorUsername(doctorName);
        String patientName = getPatientName();
        appointmentRepository.insert(new Appointment(username, patientName, doctorName, doctorUsername, year, month, day, dayOfTheWeek, hour, message));
    }

    private static void checkEmptyTextfieldsAppointment(String doctorName, int year, String hour) throws EmptyTextfieldsException {
        if (Objects.equals(doctorName, null))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(year, 0))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(hour, null))
            throw new EmptyTextfieldsException();
    }

    private static void checkAvailability(String doctorName, int year, int month, int day, String dayOfTheWeek, String hour) throws NotAvailableException, WeekendDayException {
        if (Objects.equals(dayOfTheWeek, "Saturday") || Objects.equals(dayOfTheWeek, "Sunday")) {
            throw new WeekendDayException();
        } else {
            for (Appointment app : appointmentRepository.find()) {
                if (Objects.equals(app.getDoctorName(), doctorName) && Objects.equals(app.getHour(), hour)
                && app.getYear() == year && app.getMonth() == month && app.getDay() == day) {
                    throw new NotAvailableException(doctorName, hour, year, month, day);
                }
            }
        }
    }

    private static void checkEmptyTextfieldsPatient(String username, String password, String name, String phone, String password2) throws EmptyTextfieldsException {
        if( Objects.equals(username,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(password,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(name,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(phone,""))
            throw new EmptyTextfieldsException();
        else if( Objects.equals(password2,""))
            throw new EmptyTextfieldsException();
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : patientRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }

        for (User user : doctorRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }

        for (User user : adminRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }
    }

    public static String checkUserExist(String username) throws UsernameDoesNotExistException{
        int ok = 0;
        String role = null;
        for (User user : patientRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                ok = 1;
                role = user.getRole();
                break;
            }
        }
        for (User user : doctorRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                ok = 1;
                role = user.getRole();
                break;
            }
        }
        if (Objects.equals(username,"admin")) {
            ok = 1;
            role = "Admin";
        }
        if (ok == 0) {
            throw new UsernameDoesNotExistException(username);
        } else {
            return role;
        }
    }

    private static void checkPasswordSameAsConfirmedPassword(String password, String confirmedPassword) throws WrongPasswordConfirmationException {
        if(!Objects.equals(password, confirmedPassword)) {
            throw new WrongPasswordConfirmationException();
        }
    }

    private static void checkEmptyTextFieldsDoctor(String username, String password, String confirmedPassword, String name, String medicalSpecialty, String phoneNumber) throws EmptyTextfieldsException {
        if (Objects.equals(username, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(password, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(confirmedPassword, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(name, ""))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(medicalSpecialty, null))
            throw new EmptyTextfieldsException();
        else if (Objects.equals(phoneNumber, ""))
            throw new EmptyTextfieldsException();
    }

    public static int checkUserCredentials(String username, String password) throws UsernameDoesNotExistException, WrongPasswordException {
        int oku=0, okp=0;
        for (User user : patientRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                oku = 1;
            }
            if (Objects.equals(encodePassword(username, password), user.getPassword())) {
                okp = 1;
            }
        }
        for (User user : doctorRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                oku = 1;
            }
            if (Objects.equals(encodePassword(username, password), user.getPassword())) {
                okp = 1;
            }
        }
        for (User user : adminRepository.find()) {
            if (Objects.equals(username, user.getUsername())) {
                oku = 1;
            }
            if (Objects.equals(encodePassword(username, password), user.getPassword())) {
                okp = 1;
            }
        }
        if ( oku == 0 ) {
            throw new UsernameDoesNotExistException(username);
        }
        if ( okp == 0 ) {
            throw new WrongPasswordException();
        }
        loggedInUsername = username;
        return 1;
    }

    public static String getLoggedInUsername() {
        return loggedInUsername;
    }

    public static String encodePassword(String salt, String password) {
        MessageDigest md = getMessageDigest();
        md.update(salt.getBytes(StandardCharsets.UTF_8));

        byte[] hashedPassword = md.digest(password.getBytes(StandardCharsets.UTF_8));

        // This is the way a password should be encoded when checking the credentials
        return new String(hashedPassword, StandardCharsets.UTF_8)
                .replace("\"", ""); //to be able to save in JSON format
    }

    private static MessageDigest getMessageDigest() {
        MessageDigest md;
        try {
            md = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("SHA-512 does not exist!");
        }
        return md;
    }

    public static List doctorListName() {
        List<String> doctor = new ArrayList<>();
        for (User doc : doctorRepository.find()) {
            doctor.add(((Doctor)doc).getName());
        }
        return doctor;
    }

    public static List DoctorsList() {
        List<Doctor> doctor = new ArrayList<>();
        for (User doc : doctorRepository.find()) {
                doctor.add((Doctor)doc);
        }
        return doctor;
    }

    public static String getDoctorUsername(String doctorName) {
        for (Doctor doc : doctorRepository.find()) {
            if (Objects.equals(doctorName, doc.getName()))
                return doc.getUsername();
        }
        return null;
    }

    public static String getPatientName() {
        for (Patient patient : patientRepository.find()) {
            if (Objects.equals(loggedInUsername, patient.getUsername())) {
                return patient.getName();
            }
        }
        return null;
    }

    public static List DoctorsListSpec(String spec) {
        List<Doctor> doctor = new ArrayList<>();
        for (User doc : doctorRepository.find()) {
            if (((Doctor) doc).getMedicalSpecialty().equals(spec)) {
                doctor.add((Doctor) doc);
            }
        }
        return doctor;
    }


    public static List AppointmentsList() {
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appo : appointmentRepository.find()) {
            if(appo.getUsername().equals(loggedInUsername)) {
               appointments.add(appo);
            }
        }
        return appointments;
    }

    public static List getAppointments() {
        List<Appointment> appointments = new ArrayList<>();
        for (Appointment appo : appointmentRepository.find()) {
            if (Objects.equals(appo.getDoctorUsername(), loggedInUsername)) {
                appointments.add(appo);
            }
        }
        return appointments;
    }
    public static void editAppointment(Appointment appo, String hour) throws EmptyTextfieldsException, NotAvailableException, WeekendDayException {
        if (Objects.equals(hour, null))
            throw new EmptyTextfieldsException();
        checkAvailability(appo.getDoctorName(), appo.getYear(), appo.getMonth(), appo.getDay(), appo.getDayOfTheWeek(), hour);
        appo.setHour(hour);
        appointmentRepository.update(appo);
    }

}
