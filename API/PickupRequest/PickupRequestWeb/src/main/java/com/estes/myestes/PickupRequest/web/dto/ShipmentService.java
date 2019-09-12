package com.estes.myestes.PickupRequest.web.dto;

public enum ShipmentService {
	EFW, GUARANTEED, LTL;
	
	public static String getValue(ShipmentService shipmentService){
		if(shipmentService==null){
			return "LTL";
		}
		switch(shipmentService) {
	        case EFW:
	            return "Air";
	        case GUARANTEED:
	            return "Gold"; 
	        case LTL: 
	            return "LTL";
	        default:
	        	return "LTL";
		} 
	}
}