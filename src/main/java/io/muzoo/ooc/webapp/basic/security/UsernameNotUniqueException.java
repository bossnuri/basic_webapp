package io.muzoo.ooc.webapp.basic.security;

public class UsernameNotUniqueException extends UserServiceException{
    public UsernameNotUniqueException(String message) {
        super(message);
    }
}
