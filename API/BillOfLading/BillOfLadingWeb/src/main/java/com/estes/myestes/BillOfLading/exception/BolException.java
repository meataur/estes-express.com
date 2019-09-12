package com.estes.myestes.BillOfLading.exception;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;

import com.estes.dto.common.ServiceResponse;

import lombok.Getter;
import lombok.Setter;

public class BolException extends RuntimeException{
	
	private static final long serialVersionUID = 1L;
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;
	
	@Getter	
	@Setter
	private List<ServiceResponse> serviceResponseList = new ArrayList<>();

	public BolException( List<ServiceResponse> serviceResponseList) {
	        super();
	        this.serviceResponseList.addAll(serviceResponseList);
    }
	
	public BolException(Map<String, String> errors){
		super();
		if(errors.isEmpty()==false){
			for(Map.Entry<String,String> entry : errors.entrySet()){
				 serviceResponseList.add(new ServiceResponse(entry.getKey(),entry.getValue()));
			}
		}
	}

	public BolException(ServiceResponse serviceResponse) {
        super();
        serviceResponseList.add(serviceResponse);
	}
	
	public BolException() {
        super();
	}

	public BolException(String errorCode, String message) {
		serviceResponseList.add(new ServiceResponse(errorCode,message));
	}

	public BolException(String code, String message, String field) {
		serviceResponseList.add(new ServiceResponse(code,message,field));
	}

	public BolException(HttpStatus notFound, String defaultErrorCode, String message) {
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
