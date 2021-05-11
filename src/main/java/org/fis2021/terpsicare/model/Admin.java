package org.fis2021.terpsicare.model;

import org.dizitart.no2.objects.InheritIndices;

@InheritIndices
public class Admin extends User {

    public Admin() {
    }

    public Admin(String username, String password, String role) {
        super(username, password, role);
    }

}
