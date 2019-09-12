package com.estes.myestes.wrinquiry.exception;

import java.util.Map;

import org.springframework.http.HttpStatus;

import lombok.Getter;
import lombok.Setter;

public class StoreProcedureCallException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;
	
	@Getter
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	@Getter
	@Setter
	private Map<String,String> errorCodes;
	
	public StoreProcedureCallException() {
        super();
    }
   
    public StoreProcedureCallException(Map<String,String> errorCodes) {
        super();
        this.errorCodes = errorCodes;
    }
    
    public StoreProcedureCallException(HttpStatus httpStatus, Map<String,String> errorCodes) {
        super();
        this.httpStatus = httpStatus;
        this.errorCodes = errorCodes;
    }
}
