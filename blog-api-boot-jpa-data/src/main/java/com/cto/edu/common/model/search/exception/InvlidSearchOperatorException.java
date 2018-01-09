package com.cto.edu.common.model.search.exception;


import com.cto.edu.common.model.search.SearchOperator;

/**
 * Version:1.0
 */
public final class InvlidSearchOperatorException extends SearchException {
	private static final long serialVersionUID = 1L;

	public InvlidSearchOperatorException(String searchProperty, String operatorStr) {
        this(searchProperty, operatorStr, null);
    }

    public InvlidSearchOperatorException(String searchProperty, String operatorStr, Throwable cause) {
        super("Invalid Search Operator searchProperty [" + searchProperty + "], " +
                "operator [" + operatorStr + "], must be one of " + SearchOperator.toStringAllOperator(), cause);
    }
}
