package com.elotech.biblioteca.exception;

public class RecursoJaExisteException extends RuntimeException {
    
    public RecursoJaExisteException(String message) {
        super(message);
    }
    
    public RecursoJaExisteException(String message, Throwable cause) {
        super(message, cause);
    }
}