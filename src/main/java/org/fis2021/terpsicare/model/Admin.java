package org.fis2021.terpsicare.model;

import static org.fis2021.terpsicare.services.UserService.encodePassword;

public class Admin extends User {

    public Admin() {
    }

    public Admin(String username, String password, String role) {
        super(username, password, role);
    }

}
