package com.klaystakingservice.common.error.exception;

public class EmailNotFoundException extends RuntimeException{

    private String message;

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public EmailNotFoundException(String message){
        this.message = message;
    }
    //    public EmailNotFoundException(String errorMessage){
//        super(errorMessage);
//    }

}

