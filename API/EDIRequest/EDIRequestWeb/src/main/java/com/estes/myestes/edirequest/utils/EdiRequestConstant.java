/**
 * @author: Lakshman K
 *
 * Creation date: 11/12/2018
 */

package com.estes.myestes.edirequest.utils;

/**
 * Constants
 */
public interface EdiRequestConstant
{
	String APP_CONFIG = "edirequest.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	String IMAGE = "image";
	
	String EMAILS = "emails";
	String EDI_FROM_EMAIL = "ediFromEmail";
	String EDI_BCC_EMAIL = "ediBccEmail";
	
	 public static final String APP_JNDI = "jdbc/msedirequestds";
	 
	 public static final String APP_MAIL_JNDI = "mail/msedirequestemail";
	 
	 public static final String APP_PROPERTY_FILE = "ediRequest.properties";
	 
	 public static final String	MAIL_SUBJECT="Estes - EDI Communication Request: Reference #";
	 public static final String MAIL_BODY="Thank you for your request to set up EDI communications with Estes.  Your submission will be processed and an account representative will be in touch with you soon.\r\n\r\nIf you have any questions please do not hesitate to contact us at (804) 353-1900 Ext. 2600.\r\n\r\nThank you for choosing Estes.\r\n\r\n";

	/**
     * Library (schema) name
     */
	public static String DATA_SCHEMA = "FBFILES";
	/**
     * Library (schema) name
     */
	public static String PGM_SCHEMA = "FBPGMS";
	
	public static final String PRIMARY_EDI_CONTACT_TYPE="PRI";
	public static final String SECONDARY_EDI_CONTACT_TYPE="SEC";
	public static final String ACCOUNTS_PAYABLE_CONTACT_TYPE="ACT";
	public static final String BUSINESS_CONTACT_TYPE="BUS";
	public static final String TRAFFIC_CONTACT_TYPE="TRF";
	public static final String ADDITIONAL_CONTACT_TYPE="ADD";
	public static final String NO="N";
	
	public static String ALT_PGM_SCHEMA = "estesrtgy2";
	
	public static String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain

}
