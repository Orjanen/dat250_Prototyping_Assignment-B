package com.restdeveloper.app.ws.shared;

public class UnregisteredVoteForPrivatePollException extends RuntimeException{
    public UnregisteredVoteForPrivatePollException(String errorMessage){
        super(errorMessage);
    }
}
