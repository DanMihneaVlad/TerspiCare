package org.fis2021.terpsicare.model;

import static org.fis2021.terpsicare.services.UserService.encodePassword;

public class Admin extends User {

    private static Admin admin = null;

    private Admin(String username, String password, String role) {
        super(username, password, role);
    }

    public static Admin getInstance() {
        if(admin == null)
            admin = new Admin("admin", encodePassword("admin", "admin"), "Admin");

        return admin;
    }

}
