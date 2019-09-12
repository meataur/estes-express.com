package com.estes.dto.pickuprequest.response;

import java.io.Serializable;

import com.estes.dto.common.GenericResponse;

/**
 * 
 * @author singhpa
 *
 */
public class ValidatePickupInfoResponse extends GenericResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Boolean validateSuccess;
	
	public Boolean getValidateSuccess() {
		return validateSuccess;
	}

	public void setValidateSuccess(Boolean validateSuccess) {
		this.validateSuccess = validateSuccess;
	}
	
}
