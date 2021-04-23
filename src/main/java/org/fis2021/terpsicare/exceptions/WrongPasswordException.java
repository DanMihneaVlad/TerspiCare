package org.fis2021.terpsicare.exceptions;

public class WrongPasswordException extends Exception{
    public WrongPasswordException() {
        super(String.format("Wrong password ! "));
    }

}