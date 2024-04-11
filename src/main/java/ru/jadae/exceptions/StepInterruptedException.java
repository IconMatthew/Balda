package ru.jadae.exceptions;

public class StepInterruptedException extends RuntimeException{

    private static final String message = "Invalid step for subsequence. Use cancel button.";

    public StepInterruptedException() {
        super(message);
    }

    public StepInterruptedException(String customMessage) {
        super(customMessage);
    }

}
