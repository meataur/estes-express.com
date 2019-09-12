package com.estes.myestes.profile.exception;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class AppException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	

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
    public AppException(Throwable cause) {
        super(cause);
    }
}
