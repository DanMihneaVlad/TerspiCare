package org.fis2021.terpsicare.exceptions;

public class WrongPasswordConfirmationException extends Exception{
    public WrongPasswordConfirmationException() {
        super(String.format("Reconfirm the password (you entered two different ones) ! "));
    }
}
