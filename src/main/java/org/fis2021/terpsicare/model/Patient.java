package org.fis2021.terpsicare.model;

import org.dizitart.no2.objects.Id;
import org.dizitart.no2.objects.InheritIndices;

import java.util.Objects;

@InheritIndices
public class Patient extends User{

    private String name;
    private String phonenumber;
    private String medicalrecord;

    public Patient() {
    }

    public Patient(String username, String p, String name, String phonenumber, String medicalrecord) {
        super(username,p,"patient");
        this.name = name;
        this.phonenumber = phonenumber;
        this.medicalrecord = medicalrecord;
    }

    public String getName() {
        return name;
    }

    public String getPhonenumber() {
        return phonenumber;
    }

    public String getMedicalrecord() {
        return medicalrecord;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Patient patient = (Patient) o;
        return name.equals(patient.name) && phonenumber.equals(patient.phonenumber) && medicalrecord.equals(patient.medicalrecord);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, phonenumber, medicalrecord);
    }
}
