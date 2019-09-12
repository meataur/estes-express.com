package com.estes.dto.pickuprequest.request;

import java.io.Serializable;

import com.estes.dto.common.GenericRequest;
import com.estes.dto.pickuprequest.common.PickupRequestForm;

public class CreatePickupRequest extends GenericRequest implements Serializable{
	
	private static final long serialVersionUID = 1L;
	
	private PickupRequestForm pickupRequestForm;
	
	public PickupRequestForm getPickupRequestForm() {
		return pickupRequestForm;
	}

	public void setPickupRequestForm(PickupRequestForm pickupRequestForm) {
		this.pickupRequestForm = pickupRequestForm;
	}
}
