package com.estes.myestes.PickupRequest.exceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.PickupRequest.exception.AppException;

@RestControllerAdvice
public class AppExceptionHandler {
    
    @ExceptionHandler(AppException.class)
	public ResponseEntity<?> exceptionHandler(AppException ex, WebRequest request) {
    	List<ServiceResponse> errors = new ArrayList<>();
    	ServiceResponse error = new ServiceResponse();
		error.setErrorCode("error");
		error.setMessage(ex.getMessage());
		ex.printStackTrace();
		ESTESLogger.log(
				ESTESLogger.ERROR,
				ex.getStackTrace()[0].getClassName(),
				ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()
			);
		errors.add(error);
		return new ResponseEntity<>(errors, ex.getHttpStatus());
	}
    
}