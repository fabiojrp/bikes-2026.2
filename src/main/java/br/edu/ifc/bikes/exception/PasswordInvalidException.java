package br.edu.ifc.bikes.exception;

public class PasswordInvalidException extends IllegalArgumentException {
    public PasswordInvalidException(String message) {
        super(message);
    }
}
