package com.odontomed.exception;

public class DniAlreadyRegistered extends RuntimeException{
    public DniAlreadyRegistered(String message){
        super(message);
    }
}
