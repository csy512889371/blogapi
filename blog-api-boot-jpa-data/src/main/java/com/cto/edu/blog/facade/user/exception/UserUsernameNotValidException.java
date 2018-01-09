package com.cto.edu.blog.facade.user.exception;

public class UserUsernameNotValidException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserUsernameNotValidException() {
        super("user.username.not.valid", null);
    }
}
