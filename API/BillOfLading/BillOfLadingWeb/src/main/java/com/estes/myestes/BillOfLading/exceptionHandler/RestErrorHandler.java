package com.estes.myestes.BillOfLading.exceptionHandler;

import static com.estes.myestes.BillOfLading.util.Constants.DEFAULT_ERROR_CODE;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.estes.dto.common.ServiceResponse;
 
@RestControllerAdvice
public class RestErrorHandler {
 
    private MessageSource messageSource;
 
    @Autowired
    public RestErrorHandler(MessageSource messageSource) {
        this.messageSource = messageSource;
    }
 
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<?> processValidationError(MethodArgumentNotValidException ex) {
        BindingResult result = ex.getBindingResult();
        List<FieldError> fieldErrors = result.getFieldErrors();
        
        List<ObjectError> objectErrors = result.getGlobalErrors();
        
        List<ServiceResponse> errors =  processFieldErrors(fieldErrors,objectErrors);
        
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
    
   
 
    private List<ServiceResponse> processFieldErrors(List<FieldError> fieldErrors,List<ObjectError> objectErrors) {
    	List<ServiceResponse> errors = new ArrayList<>();
 
        for (FieldError fieldError: fieldErrors) {
            String localizedErrorMessage = resolveLocalizedErrorMessage(fieldError);
            
            ServiceResponse serviceResponse = new ServiceResponse(DEFAULT_ERROR_CODE, localizedErrorMessage,fieldError.getField());
            /**
             *  TODO serviceResponse badData should be Object. 
             */
            //serviceResponse.setBadData(fieldError.getRejectedValue());
            
            errors.add(serviceResponse);
        }
        for (ObjectError objectError: objectErrors) {
        	
            String localizedErrorMessage = resolveLocalizedErrorMessage(objectError);
            
        	 ServiceResponse serviceResponse = new ServiceResponse(DEFAULT_ERROR_CODE, localizedErrorMessage,objectError.getObjectName());
             
            errors.add(serviceResponse);
        }
 
        return errors;
    }
    
    private String resolveLocalizedErrorMessage(ObjectError objectError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        return messageSource.getMessage(objectError, currentLocale);
    }
    
    private String resolveLocalizedErrorMessage(FieldError fieldError) {
        Locale currentLocale =  LocaleContextHolder.getLocale();
        return messageSource.getMessage(fieldError, currentLocale);
    }
}