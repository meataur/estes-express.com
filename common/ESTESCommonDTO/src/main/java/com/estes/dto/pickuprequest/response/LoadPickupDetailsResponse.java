package com.estes.dto.pickuprequest.response;

import java.io.Serializable;

import com.estes.dto.common.GenericResponse;
import com.estes.dto.pickuprequest.common.PickupRequestForm;

/**
 * 
 * @author singhpa
 *
 */
public class LoadPickupDetailsResponse extends GenericResponse implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private PickupRequestForm pickupRequestForm;

	public PickupRequestForm getPickupRequestForm() {
		return pickupRequestForm;
	}

	public void setPickupRequestForm(PickupRequestForm pickupRequestForm) {
		this.pickupRequestForm = pickupRequestForm;
	}
	
}
