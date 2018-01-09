package com.cto.edu.blog.facade.user.exception;

public class UserPasswordEmptyException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserPasswordEmptyException() {
        super("user.password.empty", null);
    }
}
