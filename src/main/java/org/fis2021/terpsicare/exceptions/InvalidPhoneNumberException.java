package org.fis2021.terpsicare.exceptions;

public class InvalidPhoneNumberException extends Exception {
    public InvalidPhoneNumberException() {
        super(String.format("You entered an invalid phone number! "));
    }
}
