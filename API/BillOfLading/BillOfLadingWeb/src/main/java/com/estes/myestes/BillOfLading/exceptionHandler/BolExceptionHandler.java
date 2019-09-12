package com.estes.myestes.BillOfLading.exceptionHandler;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.exception.BolException;

@RestControllerAdvice
public class BolExceptionHandler {
	
	@Autowired
	private Environment env;
	
	@ExceptionHandler(BolException.class)
	protected ResponseEntity<?> handleBolException(BolException ex, WebRequest request) {
		ex.printStackTrace();
		try{
			AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
			ESTESLogger.log(ESTESLogger.ERROR, 
					"at class - "+ex.getStackTrace()[0].getClassName(),
					", method : "+ex.getStackTrace()[0].getMethodName(),
				" message - "+ex.getServiceResponseList().get(0).getMessage()+" at line no - "+ex.getStackTrace()[0].getLineNumber() + 
				" for user -"+auth.getUsername()); 
		}catch(Exception e){
			ESTESLogger.log(ESTESLogger.ERROR, "BolExceptionHandler.class", e.getMessage(), "handleBolException()");
		}
		
		if(ex.getHttpStatus()==HttpStatus.BAD_REQUEST){
			
			return new ResponseEntity<>(processServiceResponseList(ex.getServiceResponseList()), ex.getHttpStatus());
		}else{
			return new ResponseEntity<>(ex.getServiceResponseList().get(0), ex.getHttpStatus());
		}
	}
	
	private List<ServiceResponse> processServiceResponseList(List<ServiceResponse> serviceResponseList){
		
		for(ServiceResponse serviceResponse : serviceResponseList){
			/**
			 * Get the field name from the properties file
			 */
			if(env.containsProperty(serviceResponse.getErrorCode())
					&& ( serviceResponse.getErrorCode().startsWith("BOL") || 
							serviceResponse.getErrorCode().startsWith("PKE"))){
				serviceResponse.setFieldName(env.getProperty(serviceResponse.getErrorCode()).trim());
			}
		}
		return serviceResponseList;
		
	}
}
