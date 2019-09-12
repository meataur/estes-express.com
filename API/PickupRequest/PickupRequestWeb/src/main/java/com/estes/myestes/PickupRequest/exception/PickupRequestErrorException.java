package com.estes.myestes.PickupRequest.exception;

import java.util.List;

import org.springframework.http.HttpStatus;

import com.estes.myestes.PickupRequest.web.dto.PickupRequestError;

import lombok.Getter;
import lombok.Setter;

public class PickupRequestErrorException  extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	
	@Getter
	private List<PickupRequestError> errors;

	public PickupRequestErrorException(List<PickupRequestError> errors) {
		super();
		this.errors = errors;
	}
}
