package ru.jadae.exceptions;

public class InvalidFormedWord extends RuntimeException{

    private static final String message = "Formed word is invalid!";

    public InvalidFormedWord() {
        super(message);
    }

}