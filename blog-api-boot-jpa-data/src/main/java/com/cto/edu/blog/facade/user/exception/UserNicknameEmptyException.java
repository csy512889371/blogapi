package com.cto.edu.blog.facade.user.exception;

public class UserNicknameEmptyException extends UserException {

	private static final long serialVersionUID = 1L;

	public UserNicknameEmptyException() {
        super("user.nickname.empty", null);
    }
}
