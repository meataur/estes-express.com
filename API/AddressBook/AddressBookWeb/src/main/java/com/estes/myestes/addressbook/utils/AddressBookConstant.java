/**
 * @author: Todd Allen
 *
 * Creation date: 05/18/2018
 */

package com.estes.myestes.addressbook.utils;

/**
 * Constants
 */
public interface AddressBookConstant
{
	String APP_CONFIG = "addressbook.config";
	String LOGGER = "logger";
	String LOCATION = "location";

	/** 
	 * ID of the application in the Application Master File and Application Block File.
	 * <p>
	 * The record for the Address Book application in the My Estes Application Master File:
	 * SELECT * FROM ESTESRTGY2.QNP220 WHERE QWMENU = 'EBG10O101'
	 * <p>
	 * The records for My Estes user profiles which are blocked from Address Book in the Application Block File:
	 * SELECT * FROM ESTESRTGY2.QNP302 WHERE QZAPP = 'EBG10O101' AND QZBLTP = 2
	 */
	public static final String APP_ID = "EBG10O101";

    /**
     * Key to file of calling application info.
     */
	public static final String CALLED_BY = "JVA3061001";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
