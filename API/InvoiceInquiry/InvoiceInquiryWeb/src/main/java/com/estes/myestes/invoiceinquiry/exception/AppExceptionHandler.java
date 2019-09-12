package com.estes.myestes.invoiceinquiry.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.estes.framework.logger.ESTESLogger;

@RestControllerAdvice
public class AppExceptionHandler {
	
	@ExceptionHandler(AppException.class)
	protected ResponseEntity<?> handleAppException(AppException ex, WebRequest request) {
		ex.printStackTrace();
		try{
			
			ESTESLogger.log(ESTESLogger.ERROR, 
					"at class - "+ex.getStackTrace()[0].getClassName(),
					", method : "+ex.getStackTrace()[0].getMethodName(),
				" message - "+ex.getServiceResponseList().get(0).getMessage()+" at line no - "+ex.getStackTrace()[0].getLineNumber()); 
		}catch(Exception e){
			ESTESLogger.log(ESTESLogger.ERROR, "AppExceptionHandler.class", e.getMessage(), "handleAppException()");
		}
		
		if(ex.getHttpStatus()==HttpStatus.BAD_REQUEST){
			
			return new ResponseEntity<>(ex.getServiceResponseList(), ex.getHttpStatus());
		}else{
			return new ResponseEntity<>(ex.getServiceResponseList().get(0), ex.getHttpStatus());
		}
	}
}
