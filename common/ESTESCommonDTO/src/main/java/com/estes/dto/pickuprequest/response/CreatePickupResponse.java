package com.estes.dto.pickuprequest.response;

import java.io.Serializable;

import com.estes.dto.common.GenericResponse;

/**
 * 
 * @author singhpa
 *
 */
public class CreatePickupResponse extends GenericResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private Boolean successInd;
	
	private Integer pickupId;
	
	private String requestNumber;

	public Boolean getSuccessInd() {
		return successInd;
	}

	public void setSuccessInd(Boolean successInd) {
		this.successInd = successInd;
	}

	public Integer getPickupId() {
		return pickupId;
	}

	public void setPickupId(Integer pickupId) {
		this.pickupId = pickupId;
	}

	public String getRequestNumber() {
		return requestNumber;
	}

	public void setRequestNumber(String requestNumber) {
		this.requestNumber = requestNumber;
	}
	
}
