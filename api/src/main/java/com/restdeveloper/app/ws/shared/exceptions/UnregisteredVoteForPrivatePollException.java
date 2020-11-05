package com.restdeveloper.app.ws.shared.exceptions;

public class UnregisteredVoteForPrivatePollException extends RuntimeException {
    public UnregisteredVoteForPrivatePollException(String errorMessage) {
        super(errorMessage);
    }
}
