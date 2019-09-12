package com.estes.myestes.rating.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.exception.ESTESException;

import lombok.Getter;
import lombok.Setter;

public class RatingErrorsException extends ESTESException {
	
	private static final long serialVersionUID = -9184899272856177781L;
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	@Getter
	@Setter
	private List<ServiceResponse> errors;
	
	public RatingErrorsException(HttpStatus badRequest, List<ServiceResponse> errors) {
		super();
		this.httpStatus = badRequest;
		this.errors = errors;
	}
}
