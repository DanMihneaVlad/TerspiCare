package org.fis2021.terpsicare.services;

import org.dizitart.no2.Nitrite;
import org.dizitart.no2.objects.ObjectRepository;
import org.fis2021.terpsicare.exceptions.*;
import org.fis2021.terpsicare.model.Patient;
import org.fis2021.terpsicare.model.User;


import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Objects;

import static org.fis2021.terpsicare.services.FileSystemService.getPathToFile;


public class UserService {

    private static ObjectRepository<User> userRepository;

    public static void initDatabase() {
        Nitrite database = Nitrite.builder()
                .filePath(getPathToFile("terpsicare-users.db").toFile())
                .openOrCreate("test", "test");

        userRepository = database.getRepository(User.class);
    }


    public static void addPatient(String username, String password,String name, String phone, String password2, String medicalrecord) throws UsernameAlreadyExistsException, WrongPasswordConfirmationException, EmptyTextfieldsException {
        checkEmptyTextfields(username,password,name,phone,password2, medicalrecord);
        checkUserDoesNotAlreadyExist(username);
        checkPasswordConfirmation(password,password2);
        userRepository.insert(new Patient(username, encodePassword(username, password), name, phone, medicalrecord));
    }

    private static void checkEmptyTextfields(String username, String password,String name, String phone, String password2, String medicalrecord) throws EmptyTextfieldsException{
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
        else if( Objects.equals(medicalrecord,""))
            throw new EmptyTextfieldsException();
    }

    public static void checkUserDoesNotAlreadyExist(String username) throws UsernameAlreadyExistsException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
                throw new UsernameAlreadyExistsException(username);
        }

    }

    public static String checkUserExist(String username) throws UsernameDoesNotExistException{
        int ok = 0;
        String role = null;
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername())){
                ok = 1;
                role = user.getRole();
                break;
            }
        }
        if(ok == 0){
            throw new UsernameDoesNotExistException(username);
        }else{
            return role;
        }
    }
    
    public static ObjectRepository<User> getUserRepository() {
        return userRepository;
    }

    private static void checkPasswordConfirmation(String password, String password2) throws WrongPasswordConfirmationException {
        if( !Objects.equals(password,password2))
            throw new WrongPasswordConfirmationException();
    }

    public static int checkUserCredentials(String username, String password) throws UsernameDoesNotExistException, WrongPasswordException {
        int oku=0,okp=0;
        for(User user : userRepository.find()){
            if(Objects.equals(username,user.getUsername())) {
                oku = 1;
            }
            if(Objects.equals(encodePassword(username,password),user.getPassword()))
                okp = 1;
        }
        if( oku == 0 ){
            throw new UsernameDoesNotExistException(username);
        }
        if ( okp == 0 ){
            throw new WrongPasswordException();
        }

        return 1;
    }

    private static String encodePassword(String salt, String password) {
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

    public static String getUserRole(String username, String password) throws UsernameDoesNotExistException, WrongPasswordException {
        for (User user : userRepository.find()) {
            if (Objects.equals(username, user.getUsername()))
            {
                if (Objects.equals(encodePassword(username,password), user.getPassword()))
                    return user.getRole();
                else
                    throw new WrongPasswordException();
            }
        }
        throw new UsernameDoesNotExistException(username);
    }


}