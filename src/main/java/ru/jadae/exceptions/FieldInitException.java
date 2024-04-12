package ru.jadae.exceptions;

public class FieldInitException extends RuntimeException {

    private static final String message = "Invalid field init data!";

    public FieldInitException(String customMessage) {
        super(customMessage);
    }
}