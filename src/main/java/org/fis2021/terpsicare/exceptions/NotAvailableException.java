package org.fis2021.terpsicare.exceptions;

public class NotAvailableException extends Exception {

    private String doctorName;
    private String hour;
    private int year;
    private int month;
    private int day;

    public NotAvailableException(String doctorName, String hour, int year, int month, int day) {
        super(String.format("The doctor %s is not available at the date %d.%d.%d, at the hour %s!", doctorName, day, month, year, hour));
    }
}
