package org.fis2021.terpsicare.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;

import org.fis2021.terpsicare.exceptions.*;
import org.fis2021.terpsicare.model.Patient;
import org.fis2021.terpsicare.model.User;
import org.fis2021.terpsicare.model.Doctor;
import org.fis2021.terpsicare.model.Admin;


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

    public static void initDatabase() {
        Nitrite database = Nitrite.builder().filePath(getPathToFile(".terpsicare-users.db").toFile()).openOrCreate("test", "test");
        patientRepository = database.getRepository(Patient.class);
        doctorRepository = database.getRepository(Doctor.class);
        adminRepository = database.getRepository(Admin.class);
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

    public static void addDoctor(String username, String password, String confirmedPassword, String name, String medicalSpecialty, String phoneNumber) throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkUserDoesNotAlreadyExist(username);
        checkPasswordSameAsConfirmedPassword(password, confirmedPassword);
        checkEmptyTextFieldsDoctor(username, password, confirmedPassword, name, medicalSpecialty, phoneNumber);
        doctorRepository.insert(new Doctor(username, encodePassword(username, password), name, medicalSpecialty, phoneNumber));
    }

    public static void addPatient(String username, String password,String name, String phone, String password2, String medicalrecord) throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkEmptyTextfieldsPatient(username, password, name, phone, password2);
        checkUserDoesNotAlreadyExist(username);
        checkPasswordSameAsConfirmedPassword(password, password2);
        patientRepository.insert(new Patient(username, encodePassword(username, password), name, phone, medicalrecord));
    }

    private static void checkEmptyTextfieldsPatient(String username, String password, String name, String phone, String password2) throws EmptyTextfieldsException{
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
        for(User user : patientRepository.find()) {
            if(Objects.equals(username, user.getUsername())) {
                oku = 1;
            }
            if(Objects.equals(encodePassword(username, password), user.getPassword())) {
                okp = 1;
            }
        }

        for(User user : doctorRepository.find()) {
            if(Objects.equals(username, user.getUsername())) {
                oku = 1;
            }
            if(Objects.equals(encodePassword(username, password), user.getPassword())) {
                okp = 1;
            }
        }

        for(User user : adminRepository.find()) {
            if(Objects.equals(username, user.getUsername())) {
                oku = 1;
            }
            if(Objects.equals(encodePassword(username, password), user.getPassword())) {
                okp = 1;
            }
        }

        if ( oku == 0 ) {
            throw new UsernameDoesNotExistException(username);
        }
        if ( okp == 0 ) {
            throw new WrongPasswordException();
        }

        return 1;
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

    public static List DoctorsList() {
        List<Doctor> doctor = new ArrayList<>();
        for (User doc : doctorRepository.find()) {
                doctor.add((Doctor)doc);
        }
        return doctor;
    }
}
