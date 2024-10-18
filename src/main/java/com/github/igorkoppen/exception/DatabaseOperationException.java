package com.github.igorkoppen.exception;

public class DatabaseOperationException extends RuntimeException{

    public DatabaseOperationException(String message) {
        super(message);
    }
}
