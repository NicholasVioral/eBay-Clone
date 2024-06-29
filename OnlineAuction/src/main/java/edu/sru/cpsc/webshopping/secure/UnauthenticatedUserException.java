package edu.sru.cpsc.webshopping.secure;

public class UnauthenticatedUserException extends RuntimeException {
    public UnauthenticatedUserException() {
        super("User is not authenticated");
    }
}

