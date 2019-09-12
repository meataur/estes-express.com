package com.estes.framework.exception;
/**
 * 	Base Exception class for estes
 * @author SinghPa
 *
 */
public class ESTESException extends Exception {

private static final long serialVersionUID = 8036267823176042811L;

private String[] arguments;

private String messageID;

private String message;

public String getMessage() {
	return message;
}

public void setMessage(String message) {
	this.message = message;
}

public String getMessageID() {
	return messageID;
}

public void setMessageID(String messageID) {
	this.messageID = messageID;
}

public ESTESException() {
	super();
}

public ESTESException(Exception e) {
	super(e);
}

public ESTESException(String msg) {
	super(msg);
}

public ESTESException(String msg, Throwable exception) {
	super(msg, exception);
}

public ESTESException(String messageID, String msg, String[] arguments) {
	super(msg);
	setMessageID(messageID);
	setArguments(arguments);
}

public ESTESException(String messageID, String msg) {
	super(msg);
	setMessageID(messageID);
}

public String[] getArguments() {
	return arguments;
}

public void setArguments(String[] arguments) {
	this.arguments = arguments;
}

}
