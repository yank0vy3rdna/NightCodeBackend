package ru.project.auth.services.userServices.exceptions;

public class UserAlreadyExistException extends Exception {

    public UserAlreadyExistException() {
    }

    public UserAlreadyExistException(String message) {
        super(message);
    }

    public UserAlreadyExistException(String message, Throwable cause) {
        super(message, cause);
    }
}
