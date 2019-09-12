package com.estes.services.common.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.estes.dto.common.ServiceResponse;

public class ValidationException extends RuntimeException
{
	private static final long serialVersionUID = 1L;
	
	public static final String DEFAULT_ERROR_CODE="error";
	public static final String DEFAULT_ERROR_MESSAGE="An unexpected error occured.";


	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	private List<ServiceResponse> serviceResponseList = new ArrayList<>();

	public ValidationException( List<ServiceResponse> serviceResponseList) {
	        super();
	        this.serviceResponseList.addAll(serviceResponseList);
    }
	
	public ValidationException(Map<String, String> errors){
		super();
		if(errors.isEmpty()==false){
			for(Map.Entry<String,String> entry : errors.entrySet()){
				 serviceResponseList.add(new ServiceResponse(entry.getKey(),entry.getValue()));
			}
		}
	}

	public ValidationException(ServiceResponse serviceResponse) {
        super();
        serviceResponseList.add(serviceResponse);
	}
	
	public ValidationException() {
        super();
	}

	public ValidationException(String errorCode, String message) {
		serviceResponseList.add(new ServiceResponse(errorCode,message));
	}

	public ValidationException(HttpStatus notFound, String defaultErrorCode, String message) {
		httpStatus=notFound;
		serviceResponseList.add(new ServiceResponse(defaultErrorCode,message));
	}

	public void addServiceResponse(ServiceResponse serviceResponse){
		serviceResponseList.add(serviceResponse);
	}
	
	public boolean hasAnyException(){
		return serviceResponseList.size() > 0;
	}

	public HttpStatus getHttpStatus() {
		return httpStatus;
	}

	public void setHttpStatus(HttpStatus httpStatus) {
		this.httpStatus = httpStatus;
	}

	public List<ServiceResponse> getServiceResponseList() {
		return serviceResponseList;
	}

	public void setServiceResponseList(List<ServiceResponse> serviceResponseList) {
		this.serviceResponseList = serviceResponseList;
	}

}
