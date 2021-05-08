package org.fis2021.terpsicare.exceptions;

public class WeekendDayException extends Exception {

    public WeekendDayException() {
        super(String.format("Doctors don't work on a weekend!"));
    }
}
