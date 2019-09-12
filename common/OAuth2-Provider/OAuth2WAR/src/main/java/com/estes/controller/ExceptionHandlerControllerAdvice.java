package com.estes.controller;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.provider.error.DefaultWebResponseExceptionTranslator;
import org.springframework.security.oauth2.provider.error.WebResponseExceptionTranslator;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import com.estes.exception.CustomOauth2Exception;

@ControllerAdvice
public class ExceptionHandlerControllerAdvice {
	
	private WebResponseExceptionTranslator exceptionTranslator = new DefaultWebResponseExceptionTranslator();
	protected final Log logger = LogFactory.getLog(getClass());
	
    @ExceptionHandler(CustomOauth2Exception.class)
	public ResponseEntity<OAuth2Exception> handleException(Exception e) throws Exception {
		logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
		@SuppressWarnings("serial")
		CustomOauth2Exception e400 = new CustomOauth2Exception(e.getMessage()) {
			@Override
			public int getHttpErrorCode() {
				return 400;
			}
		};
		return exceptionTranslator.translate(e400);
	}
    
    
    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<OAuth2Exception> handleMissingParams(MissingServletRequestParameterException e) throws Exception {
        logger.info("Handling error: " + e.getClass().getSimpleName() + ", " + e.getMessage());
		@SuppressWarnings("serial")
		CustomOauth2Exception e400 = new CustomOauth2Exception(e.getMessage()) {
			@Override
			public int getHttpErrorCode() {
				return 400;
			}
		};
		
		return exceptionTranslator.translate(e400);
        
    }

}
