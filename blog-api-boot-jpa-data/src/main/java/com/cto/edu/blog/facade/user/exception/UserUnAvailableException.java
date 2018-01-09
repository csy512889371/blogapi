package com.cto.edu.blog.facade.user.exception;

public class UserUnAvailableException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserUnAvailableException() {
        super("user.unavailable", null);
    }
}
