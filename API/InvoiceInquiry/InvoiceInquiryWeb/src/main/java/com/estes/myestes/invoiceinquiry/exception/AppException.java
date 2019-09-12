package com.estes.myestes.invoiceinquiry.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.estes.dto.common.ServiceResponse;

import lombok.Getter;
import lombok.Setter;

public class AppException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	@Getter	
	@Setter
	private List<ServiceResponse> serviceResponseList = new ArrayList<>();

	public AppException( List<ServiceResponse> serviceResponseList) {
	        super();
	        this.serviceResponseList.addAll(serviceResponseList);
    }
	
	public AppException(Map<String, String> errors){
		super();
		if(errors.isEmpty()==false){
			for(Map.Entry<String,String> entry : errors.entrySet()){
				 serviceResponseList.add(new ServiceResponse(entry.getKey(),entry.getValue()));
			}
		}
	}

	public AppException(ServiceResponse serviceResponse) {
        super();
        serviceResponseList.add(serviceResponse);
	}
	
	public AppException() {
        super();
	}

	public AppException(String errorCode, String message) {
		serviceResponseList.add(new ServiceResponse(errorCode,message));
	}

	public AppException(String code, String message, String field) {
		serviceResponseList.add(new ServiceResponse(code,message,field));
	}

	public AppException(HttpStatus notFound, String defaultErrorCode, String message) {
		httpStatus=notFound;
		serviceResponseList.add(new ServiceResponse(defaultErrorCode,message));
	}

	public void addServiceResponse(ServiceResponse serviceResponse){
		serviceResponseList.add(serviceResponse);
	}
	
	public boolean hasAnyException(){
		return serviceResponseList.size() > 0;
	}
	
	
	
}
