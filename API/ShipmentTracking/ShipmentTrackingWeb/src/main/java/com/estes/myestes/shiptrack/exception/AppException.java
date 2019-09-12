package com.estes.myestes.shiptrack.exception;

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
	private String errorCode="error";

	public AppException() {
        super();
    }
    public AppException(String message, Throwable cause) {
        super(message, cause);
    }
    public AppException(String message) {
        super(message);
    }
    
    public AppException(HttpStatus httpStatus, String message) {
        super(message);
        this.httpStatus = httpStatus;
    }
    public AppException(HttpStatus httpStatus, String errorCode, String message ) {
        super(message);
        this.httpStatus = httpStatus;
        this.errorCode = errorCode;
    }
    
    public AppException(String errorCode, String message ) {
        super(message);
        this.errorCode = errorCode;
    }
    public AppException(Throwable cause) {
        super(cause);
    }
}