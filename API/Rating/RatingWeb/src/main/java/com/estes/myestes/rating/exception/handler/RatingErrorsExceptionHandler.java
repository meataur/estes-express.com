package com.estes.myestes.rating.exception.handler;

import java.util.List;

import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.exception.RatingErrorsException;

@RestControllerAdvice
public class RatingErrorsExceptionHandler {
    
    @ExceptionHandler(RatingErrorsException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
	public ResponseEntity<?> exceptionHandler(RatingErrorsException ex, WebRequest request) {
    	List<ServiceResponse> errors = ex.getErrors();
		ESTESLogger.log(
				ESTESLogger.DEBUG,
				ex.getStackTrace()[0].getClassName(),
				ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()
			);
		return new ResponseEntity<>(errors, ex.getHttpStatus());
	}

}