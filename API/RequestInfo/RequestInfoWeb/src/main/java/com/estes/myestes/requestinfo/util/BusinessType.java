package com.estes.myestes.requestinfo.util;

import com.edps.email.EmailConstants;
import com.estes.framework.config.ESTESConfigUtil;

/**
 * Types of business done through Estes.
 */
public enum BusinessType {
	LTL("Estes Less-Than-Truckload", ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_TO_LTL, RequestInfoConstant.ADDRESS)), 
	L2L("Estes Level2 Logistics", ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_TO_L2L, RequestInfoConstant.ADDRESS)),  
	EFW("Estes Forwarding Worldwide", ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_TO_EFW, RequestInfoConstant.ADDRESS));
	
	private final String description, emailAddress;
	
	private BusinessType(String description, String emailAddress) {
		this.description = description;
		this.emailAddress = emailAddress;
	}
	
	public String getDescription() {
		return description;
	}
	
	public String getEmailAddress() {
		return emailAddress;
	}
	
	/**
	 * Returns the business type of the shipment.
	 * @param OT 'Origin Terminal' - first 3 digits of the PRO
	 */
//	public static BusinessType valueForOT(String OT) {
//		if(DatabaseUtility.isL2LShipment(OT)) { return L2L; } 
//		else if(DatabaseUtility.isEFWShipment(OT)) { return EFW; } 
//		else { return LTL; } 
//	}
}