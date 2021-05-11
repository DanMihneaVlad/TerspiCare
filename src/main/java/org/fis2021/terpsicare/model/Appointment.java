package org.fis2021.terpsicare.model;

import org.dizitart.no2.objects.Id;

import java.util.UUID;

public class Appointment {

    @Id
    private String id;
    private String username;
    private String patientName;
    private String doctorName;
    private String doctorUsername;
    private int year;
    private int month;
    private int day;
    private String dayOfTheWeek;
    private String hour;
    private String message;
    private String reply;

    public Appointment() {
    }

    public Appointment(String username, String patientName, String doctorName, String doctorUsername, int year, int month, int day, String dayOfTheWeek, String hour, String message) {
        UUID uniqueId = UUID.randomUUID();
        this.id = uniqueId.toString();
        this.username = username;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.doctorUsername = doctorUsername;
        this.year = year;
        this.month = month;
        this.day = day;
        this.dayOfTheWeek = dayOfTheWeek;
        this.hour = hour;
        this.message = message;
        this.reply = null;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public int getMonth() {
        return month;
    }

    public void setMonth(int month) {
        this.month = month;
    }

    public int getDay() {
        return day;
    }

    public void setDay(int day) {
        this.day = day;
    }

    public String getDayOfTheWeek() {
        return dayOfTheWeek;
    }

    public void setDayOfTheWeek(String dayOfTheWeek) {
        this.dayOfTheWeek = dayOfTheWeek;
    }

    public String getHour() {
        return hour;
    }

    public void setHour(String hour) {
        this.hour = hour;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }

    public String getDoctorUsername() {
        return doctorUsername;
    }

    public void setDoctorUsername(String doctorUsername) {
        this.doctorUsername = doctorUsername;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getReply() {
        return reply;
    }

    public void setReply(String reply) {
        this.reply = reply;
    }

    public String getPatientName() {
        return patientName;
    }

    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }

    @Override
    public String toString() {
        return "Appointment{" +
                "id='" + id + '\'' +
                ", username='" + username + '\'' +
                ", patientName='" + patientName + '\'' +
                ", doctorName='" + doctorName + '\'' +
                ", doctorUsername='" + doctorUsername + '\'' +
                ", year=" + year +
                ", month=" + month +
                ", day=" + day +
                ", dayOfTheWeek='" + dayOfTheWeek + '\'' +
                ", hour='" + hour + '\'' +
                ", message='" + message + '\'' +
                ", reply='" + reply + '\'' +
                '}';
    }
}
