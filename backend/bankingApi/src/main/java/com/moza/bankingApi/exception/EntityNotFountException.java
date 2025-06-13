package com.moza.bankingApi.exception;

public class EntityNotFountException extends BadRequestException{
    public EntityNotFountException(String message){
        super(message);
    }
}
