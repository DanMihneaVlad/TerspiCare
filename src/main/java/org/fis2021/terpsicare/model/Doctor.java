package org.fis2021.terpsicare.model;

public class Doctor extends User {

    private String name;
    private String medicalSpecialty;
    private String phoneNumber;

    public Doctor(String username, String password, String role, String name, String medicalSpecialty, String phoneNumber) {
        super(username, password, role);
        this.name = name;
        this.medicalSpecialty = medicalSpecialty;
        this.phoneNumber = phoneNumber;
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

    @Override
    public String toString() {
        return "Doctor{" +
                "name='" + name + '\'' +
                ", medicalSpecialty='" + medicalSpecialty + '\'' +
                ", phoneNumber='" + phoneNumber + '\'' +
                '}';
    }
}
