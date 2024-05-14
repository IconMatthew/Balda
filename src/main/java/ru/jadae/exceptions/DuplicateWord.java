package ru.jadae.exceptions;

public class DuplicateWord extends RuntimeException {

    private static final String message = "This word was already formed or is in the middle lane";

    public DuplicateWord() {
        super(message);
    }
}