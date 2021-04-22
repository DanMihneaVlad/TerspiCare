package org.fis2021.terpsicare.exceptions;


public class EmptyTextfieldsException extends Exception{
    public EmptyTextfieldsException() {
        super(String.format("You must complete all fields! "));
    }
}