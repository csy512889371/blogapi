package com.cto.edu.blog.facade.user.exception;


import com.cto.edu.common.exception.BaseException;

public class UserException extends BaseException {

	private static final long serialVersionUID = 1L;

	public UserException(String code, Object[] args) {
        super("user", code, args, null);
    }

}
