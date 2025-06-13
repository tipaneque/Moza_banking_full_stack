package com.moza.bankingApi.exception;

public class BalanceNotEnoughException extends BadRequestException{
    public BalanceNotEnoughException(String message){
        super(message);
    }
}
