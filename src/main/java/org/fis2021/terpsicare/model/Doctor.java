package org.fis2021.terpsicare.model;

import java.util.ArrayList;

public class Doctor extends User {

    private String name;
    private String medicalSpecialty;
    private String phoneNumber;
    private String description;

    public Doctor(){
    }

    public Doctor(String username, String password, String name, String medicalSpecialty, String phoneNumber, String description) {
        super(username, password, "doctor");
        this.name = name;
        this.medicalSpecialty = medicalSpecialty;
        this.phoneNumber = phoneNumber;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMedicalSpecialty() {
        return medicalSpecialty;
    }

    public void setMedicalSpecialty(String medicalSpecialty) {
        this.medicalSpecialty = medicalSpecialty;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", medicalSpecialty='" + medicalSpecialty + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
