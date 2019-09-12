package com.estes.myestes.PickupRequest.exceptionHandler;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.PickupRequest.exception.PickupRequestErrorException;
import com.estes.myestes.PickupRequest.web.dto.PickupRequestError;

@RestControllerAdvice
public class PickupRequestErrorExceptionHandler {
	
	@Autowired
	private Environment env;
	
 	@ExceptionHandler(PickupRequestErrorException.class)
	public ResponseEntity<?> pickupRequestErrorException(PickupRequestErrorException ex, WebRequest request) {
    	List<ServiceResponse> errors = new ArrayList<>();
    	
    	for(PickupRequestError pickupRequestError : ex.getErrors()){
    		ServiceResponse error = new ServiceResponse();
    		error.setErrorCode(pickupRequestError.getCode());
    		error.setMessage(pickupRequestError.getDescription());
    		
    		error.setFieldName(pickupRequestError.getField());
    		
    		if(env.containsProperty(pickupRequestError.getField())){
    			error.setFieldName(env.getProperty(pickupRequestError.getField()));
    		}else{
    			ESTESLogger.log(getClass(), "Error field not mapped: "+pickupRequestError.toString());
    		}
    		
    		error.setBadData(pickupRequestError.getBadData());
    		ESTESLogger.log(
    				ESTESLogger.ERROR,
    				ex.getStackTrace()[0].getClassName(),
    				ex.getStackTrace()[0].getMethodName(),
    				ex.getMessage()
    			);
    		errors.add(error);
    	}
    	
    	
    	
		return new ResponseEntity<>(errors, ex.getHttpStatus());
	}

}
