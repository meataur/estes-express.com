package com.estes.myestes.ImageViewing.exceptionHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.ImageViewing.exception.StoreProcedureCallException;
import com.estes.myestes.ImageViewing.web.dao.iface.ErrorDAO;

@RestControllerAdvice
public class StoreProcedureCallErrorHandler {
	
	
	private ErrorDAO errorDAO;
	
	@Autowired
	public void setErrorDAO(ErrorDAO errorDAO){
		this.errorDAO = errorDAO;
	}
	
	@ExceptionHandler(StoreProcedureCallException.class)
    public ResponseEntity<?> processValidationError(StoreProcedureCallException ex) {
		List<ServiceResponse> errors =  new ArrayList<>();
		
		Map<String,String> errorCodes = ex.getErrorCodes();
		
        for(Map.Entry<String,String> entry : errorCodes.entrySet()){
        	
        	errors.add(new ServiceResponse(entry.getKey(), errorDAO.getErrorMessage(entry.getValue())));
        }

		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
}
