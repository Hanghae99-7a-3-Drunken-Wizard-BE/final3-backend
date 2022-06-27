package com.example.game.exception;

public class UserException extends GlobalException{
    private GlobalExceptionType exceptionType;

    public UserException(GlobalExceptionType exceptionType){
        this.exceptionType = exceptionType;
    }

    @Override
    public GlobalExceptionType getExceptionType(){
        return exceptionType;
    }

}
