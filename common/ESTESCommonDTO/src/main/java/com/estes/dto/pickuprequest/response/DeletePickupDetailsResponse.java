package com.estes.dto.pickuprequest.response;

import java.io.Serializable;

import com.estes.dto.common.GenericResponse;

/**
 * 
 * @author singhpa
 *
 */
public class DeletePickupDetailsResponse extends GenericResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Boolean successInd;

	public Boolean getSuccessInd() {
		return successInd;
	}

	public void setSuccessInd(Boolean successInd) {
		this.successInd = successInd;
	}
	
}
