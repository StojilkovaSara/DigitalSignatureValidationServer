package com.example.signaturevalidationserver.model.exceptions;

public class NoSignatureException extends Throwable{
    public NoSignatureException(String message){
        super(message);
    }
}
