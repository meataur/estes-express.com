package com.estes.myestes.ImageViewing.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	@Getter	
	@Setter
	private String fieldName;
	
	@Getter	
	@Setter
	private String badData;
	
	public AppException() {
        super();
    }
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppException(String message) {
        super(message);
    }
    
    public AppException(String message, String fieldName, String badData) {
        super(message);
        this.fieldName = fieldName;
        this.badData = badData;
    }
    
    public AppException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public AppException(Throwable cause) {
        super(cause);
    }
}
