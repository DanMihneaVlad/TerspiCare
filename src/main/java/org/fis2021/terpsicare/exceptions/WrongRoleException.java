package org.fis2021.terpsicare.exceptions;

public class WrongRoleException extends Exception{
    public WrongRoleException() {
        super(String.format("You selected the incorrect role! "));
    }
}