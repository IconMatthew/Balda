package ru.jadae.exceptions;

public class PlayerInitException extends RuntimeException {

    private static final String message = "Invalid player init data!";

    public PlayerInitException(String customMessage) {
        super(customMessage);
    }
}