package com.github.igorkoppen.exception;

public class AlreadyInUseException extends RuntimeException{
    public AlreadyInUseException(String message){
        super(message);
    }
}
