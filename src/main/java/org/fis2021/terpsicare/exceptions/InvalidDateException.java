package org.fis2021.terpsicare.exceptions;

public class InvalidDateException extends Exception{
    public InvalidDateException() {
        super(String.format("You choose an invalid date!"));
    }

}
