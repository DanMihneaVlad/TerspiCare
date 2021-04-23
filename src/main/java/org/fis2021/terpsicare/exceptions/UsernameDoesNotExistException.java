package org.fis2021.terpsicare.exceptions;

public class UsernameDoesNotExistException extends Exception {
    private String username;

    public UsernameDoesNotExistException(String username){
        super(String.format("An account with the username %s does not exist!",username));
        this.username = username;
    }

    public String getUsername(){
        return username;
    }
}