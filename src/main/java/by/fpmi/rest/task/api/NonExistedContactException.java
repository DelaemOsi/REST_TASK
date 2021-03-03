package by.fpmi.rest.task.api;

public class NonExistedContactException extends Exception {
    public NonExistedContactException(String s) {
    }

    public NonExistedContactException() {
    }

    public NonExistedContactException(String message, Throwable cause) {
        super(message, cause);
    }
}
