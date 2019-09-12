package com.estes.myestes.shiptrack.exception.handler;

import java.sql.DataTruncation;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.TypeMismatchException;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.dao.EmptyResultDataAccessException;
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
import org.springframework.web.servlet.NoHandlerFoundException;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;

@RestControllerAdvice
public class CommonExceptionHandler {
	
	private final String defaultErrorCode="error";
	private final String defaultErrorMessage="An unexpected error occurs";
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
    @Order(Ordered.HIGHEST_PRECEDENCE)
	public ResponseEntity<?> handleHttpMessageNotReadableException(HttpMessageNotReadableException ex, WebRequest request) {
		List<ServiceResponse> errors = new ArrayList<>();
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode("error");
		
		error.setMessage(ex.getMessage());
		
		if(ex.getCause()!=null){
			error.setMessage("Invalid input");
			
			String message = ex.getCause().getMessage();
			if(message.contains("through reference chain:")){
				String str = message.split("through reference chain:")[1];
				Pattern p = Pattern.compile("\"\\w+\"");
		        Matcher m = p.matcher(str);
		        StringBuilder fieldName = new StringBuilder();
		        while(m.find()) {
		        	
		        	if(fieldName.length()>0){
		        		fieldName.append(".");
		        	}
		        	fieldName.append(m.group().replaceAll("\"", ""));
		        	
		        }
		        error.setFieldName(fieldName.toString());
			}
			if(message.contains("Enum")){
				error.setBadData(message.split("from String \"")[1].split("\":")[0]);
				error.setSeverity("The value can be anyone of "+message.split("Enum instance names:")[1].split("\n")[0]);
			}else if(message.contains("java.util.Date")){
				error.setMessage("Invalid date input");
				error.setBadData(message.split("from String \"")[1].split("\":")[0]);
				//error.setSeverity("Expected format "+message.split("Expected format ")[1].split("\n")[0]);
			}else if(message.contains("java.time.LocalTime")){
				error.setMessage("Invalid Time input");
				error.setBadData(message.split("from String \"")[1].split("\":")[0]);
				//error.setSeverity("Expected format "+message.split("Expected format ")[1].split("\n")[0]);
			}
		}
		
	    
		ESTESLogger.log(
				ESTESLogger.ERROR,
				ex.getStackTrace()[0].getClassName(),
				ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()
			);
		errors.add(error);
		return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
	}

	
	
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
			TypeMismatchException.class,MissingServletRequestPartException.class

	})
	protected ResponseEntity<?> handleMissingServletRequestParameter(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		
		error.setMessage(ex.getMessage().split(":")[0]);

		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.CONFLICT);
	}

	
	
	@ExceptionHandler(value = { BadSqlGrammarException.class })
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected ResponseEntity<?> handleBadSqlGrammarException(BadSqlGrammarException ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(defaultErrorMessage);
		ex.printStackTrace();
		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
	@ExceptionHandler(value = { EmptyResultDataAccessException.class })
	@Order(Ordered.LOWEST_PRECEDENCE)
	protected ResponseEntity<?> handleEmptyResultDataAccessException(EmptyResultDataAccessException ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(defaultErrorMessage);
		ex.printStackTrace();
		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	
//	@ExceptionHandler(value = { DataAccessException.class })
//	@Order(Ordered.HIGHEST_PRECEDENCE)
//	protected ResponseEntity<?> handleDataAccessException(DataAccessException ex, WebRequest request) {
//		ServiceResponse error = new ServiceResponse();
//		error.setErrorCode(defaultErrorCode);
//		error.setMessage(defaultErrorMessage);
//		ex.printStackTrace();
//		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
//				ex.getMessage());
//		
//		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	
//	@ExceptionHandler(value = { InvalidDataAccessApiUsageException.class })
//	@Order(Ordered.HIGHEST_PRECEDENCE)
//	protected ResponseEntity<?> handleInvalidDataAccessApiUsageException(Exception ex, WebRequest request) {
//		ServiceResponse error = new ServiceResponse();
//		error.setErrorCode(defaultErrorCode);
//		error.setMessage(defaultErrorMessage);
//		// ex.printStackTrace();
//		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
//				ex.getMessage());
//
//		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
//	}

	@ExceptionHandler(value = { NoHandlerFoundException.class })
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected ResponseEntity<?> handleNoHandlerFoundException(Exception ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(defaultErrorMessage);

		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage());

		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
	@ExceptionHandler(value = { DataTruncation.class })
	@Order(Ordered.HIGHEST_PRECEDENCE)
	protected ResponseEntity<?> handleDataTruncation(DataTruncation ex, WebRequest request) {
		ServiceResponse error = new ServiceResponse();
		error.setErrorCode(defaultErrorCode);
		error.setMessage(defaultErrorMessage);
		ex.printStackTrace();
		
		ESTESLogger.log(ESTESLogger.ERROR, ex.getStackTrace()[0].getClassName(), ex.getStackTrace()[0].getMethodName(),
				ex.getMessage()+" Transfer size: "+ex.getTransferSize()+" Data Size:"+ex.getDataSize()+" : "+ ex.getIndex());
		return new ResponseEntity<>(error, HttpStatus.INTERNAL_SERVER_ERROR);
	}
	
}
