package org.fis2021.terpsicare.model;

import java.util.Objects;

public class Notification {

    private String patientName;
    private String doctorName;
    private int day;
    private int month;
    private int year;
    private String dayOfTheWeek;
    private String newHour;
    private String oldHour;

    public Notification() {
    }

    public Notification(String patientName, String doctorName, int day, int month, int year, String dayOfTheWeek, String newHour, String oldHour) {
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.day = day;
        this.month = month;
        this.year = year;
        this.dayOfTheWeek = dayOfTheWeek;
        this.newHour = newHour;
        this.oldHour = oldHour;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getNewHour() {
        return newHour;
    }

    public void setNewHour(String newHour) {
        this.newHour = newHour;
    }

    public String getOldHour() {
        return oldHour;
    }

    public void setOldHour(String oldHour) {
        this.oldHour = oldHour;
    }


    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Notification that = (Notification) o;
        return day == that.day && month == that.month && year == that.year && Objects.equals(patientName, that.patientName) && Objects.equals(doctorName, that.doctorName) && Objects.equals(dayOfTheWeek, that.dayOfTheWeek) && Objects.equals(newHour, that.newHour) && Objects.equals(oldHour, that.oldHour);
    }

    @Override
    public int hashCode() {
        return Objects.hash(patientName, doctorName, day, month, year, dayOfTheWeek, newHour, oldHour);
    }
}

