package com.bookmarks.TypeAhead.Exceptions;

public class UserDoesNotExists extends RuntimeException {
    public UserDoesNotExists(String message) {
        super(message);
    }
}
