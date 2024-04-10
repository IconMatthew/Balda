package ru.jadae.Exceptions;

public class StepInterruptedException extends RuntimeException{

    private static final String message = "Invalid step for subsequence. Use cancel button.";

    public StepInterruptedException() {
        super(message);
    }

    public StepInterruptedException(String customMessage) {
        super(customMessage);
    }

}
