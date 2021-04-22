package org.fis2021.terpsicare.exceptions;

import java.util.Objects;

public class WrongPasswordConfirmationException extends Exception {

    public WrongPasswordConfirmationException() {
        super(String.format("The password is not the same as the confirmed password."));
    }
}
