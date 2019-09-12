package com.estes.myestes.BillOfLading.web.dto;


public enum Role {
	SHIPPER, CONSIGNEE, THIRD_PARTY, OTHER;
	
	public static Role getRole(String key){
		switch(key) {
	        case "S":
	            return Role.SHIPPER;
	        case "C":
	            return Role.CONSIGNEE;
	        case "3":
	            return Role.THIRD_PARTY;
	        case "4":
	            return Role.OTHER;
	        case "T":
	            return Role.THIRD_PARTY;
	        case "O":
	            return Role.OTHER;
	        case "THIRD PARTY":
	            return Role.THIRD_PARTY;
	        case "FOURTH PARTY":
	            return Role.OTHER;
	        default:
	        	return Role.SHIPPER;
		}
	}
	
	public  String getPickupRole(){
		switch(this) {
	        case SHIPPER:
	            return "S";
	        case CONSIGNEE:
	            return "C";
	        case THIRD_PARTY:
	            return "3";
	        case OTHER:
	            return "4";
	        default:
	        	return "C";
		}
	}
	public  String getNotifyParty(){
		switch(this) {
	        case SHIPPER:
	            return "SHIPPER";
	        case CONSIGNEE:
	            return "CONSIGNEE";
	        case THIRD_PARTY:
	            return "THIRD PARTY";
	        case OTHER:
	            return "FOURTH PARTY";
	        default:
	        	return "FOURTH PARTY";
		}
	}
	
	public  static Role getRoleFromNotifyParty(String notifyParty){
		switch(notifyParty) {
	        case "SHIPPER":
	            return SHIPPER;
	        case "CONSIGNEE":
	            return CONSIGNEE;
	        case "THIRD PARTY":
	            return THIRD_PARTY;
	        case "FOURTH PARTY":
	            return OTHER;
	        default:
	        	return OTHER;
		}
	}
}