package com.estes.framework.exception;
/**
 * class to handle list exception
 * @author SinghPa
 *
 */
public class ListHandlerException extends SearchException {

	private static final long serialVersionUID = 1L;

	public ListHandlerException() {
		super();
	}

	public ListHandlerException(Exception e) {
		super(e);
	}

	public ListHandlerException(String msg) {
		super(msg);
	}
}
