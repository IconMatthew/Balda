package ru.jadae.exceptions;

public class InitException extends RuntimeException {

    private static final String message = "You've already set this parameter!";

    public InitException(String customMessage) {
        super(customMessage);
    }
}

