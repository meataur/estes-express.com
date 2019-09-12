package com.estes.framework.exception;


/**
 * class to handle search exception
 * @author SinghPa
 *
 */
public class SearchException extends ESTESException {

	private static final long serialVersionUID = 1L;
	
	private String messageID;

	public SearchException() {
		super();
	}

	public SearchException(Exception e) {
		super(e);
	}

	public SearchException(String msg) {
		super(msg);
	}

	public String getMessageID() {
		return messageID;
	}

	public void setMessageID(String messageID) {
		this.messageID = messageID;
	}
}
