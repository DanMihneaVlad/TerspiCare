package org.fis2021.terpsicare.model;

public class Notification {

    private Appointment appointment;
    private String oldHour;

    public Notification(Appointment appointment, String oldHour) {
        this.appointment = appointment;
        this.oldHour = oldHour;
    }

    public Appointment getAppo() {
        return appointment;
    }

    public void setAppo(Appointment appointment) {
        this.appointment = appointment;
    }

    public String getOldHour() {
        return oldHour;
    }

    public void setOldHour(String oldHour) {
        this.oldHour = oldHour;
    }
}
