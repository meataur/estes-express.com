package com.estes.myestes.wrinquiry.exceptionHandler;

import java.sql.SQLException;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.web.HttpMediaTypeNotAcceptableException;
import org.springframework.web.HttpMediaTypeNotSupportedException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.ServletRequestBindingException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.multipart.support.MissingServletRequestPartException;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;

@RestControllerAdvice
public class CommonExceptionHandler {
	
	private final String defaultErrorCode="error";
	private final String defaultErrorMessage="An unexpected error occurs";
	
	@ExceptionHandler(value = { IllegalArgumentException.class, IllegalStateException.class })
	protected ResponseEntity<?> handleConflict(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(ex.getMessage().split(":")[0]);
		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()+" : IllegalArgumentException.class / IllegalStateException.class");

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	@ExceptionHandler(value = { HttpRequestMethodNotSupportedException.class })
	protected ResponseEntity<?> handleRequestMethodNotSupport(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(ex.getMessage().split(":")[0]);

		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()+" : HttpRequestMethodNotSupportedException.class");

		return new ResponseEntity<>(error, HttpStatus.METHOD_NOT_ALLOWED);
	}

	@ExceptionHandler(value = { HttpMediaTypeNotSupportedException.class })
	protected ResponseEntity<?> handleHttpMediaTypeNotSupport(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(ex.getMessage().split(":")[0]);

		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()+": HttpMediaTypeNotSupportedException.class");

		return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler(value = { HttpMediaTypeNotAcceptableException.class })
	protected ResponseEntity<?> handleHttpMediaTypeNotAcceptableException(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(ex.getMessage().split(":")[0]);

		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()+" : HttpMediaTypeNotAcceptableException.class");

		return new ResponseEntity<>(error, HttpStatus.UNSUPPORTED_MEDIA_TYPE);
	}

	@ExceptionHandler(value = { MissingServletRequestParameterException.class, ServletRequestBindingException.class,
			TypeMismatchException.class, HttpMessageNotReadableException.class, MissingServletRequestPartException.class

	})
	protected ResponseEntity<?> handleMissingServletRequestParameter(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		
		error.setMessage(ex.getMessage().split(":")[0]);

		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	
	
	@ExceptionHandler(value = { 
			BadSqlGrammarException.class, 
			SQLException.class, 
			DataAccessException.class,
			InvalidDataAccessApiUsageException.class,
			})
	protected ResponseEntity<?> handleBadSqlGrammarException(BadSqlGrammarException ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(defaultErrorMessage);
		// ex.printStackTrace();
		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}
