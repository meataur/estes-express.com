/**
 *
 */

package com.estes.myestes.claims.util;

/**
 * Constants
 */
public interface ClaimsConstant {
	/**
	 * Error codes
	 */
	public static final String ERROR_CODE = "ERROR";
	public static final String NOT_PARTY_TO_ERROR_CODE = "VAE1001"; // message not being looked up for this one
	public static final String NO_CLAIMS_FOR_DATE_ERROR_CODE = "CLE0007";
	public static final String NOT_VALID_SUB_ACCOUNT_ERROR_CODE = "CLE0004";
	
	/**
	 * JNDI Variables
	 */
	public static final String APP_MAIL_JNDI = "mail/msclaimsemail";
	public static final String APP_WEB4_JNDI = "jdbc/myestes-web4";
	public static final String APP_JNDI = "jdbc/myestes";
	
	/**
	 * Logging Info
	 */
	String CONFIG = "claims.config";
	String LOGGER = "logger";
	String LOCATION = "location";	
	/**
	 * Properties
	 */
	public static final String APP_EXT_PROP_IMAGE_SERVER_URL = "imageServerURL";
	public static final String APP_EXT_PROP_IMAGE_SERVER_PATH = "imageServerPath";
	public static final String APP_EXT_PROP_CLAIMS_DEPT_PHONE = "claimDeptPhone";
	public static final String APP_EXT_PROP_CLAIMS_INQUIRY_LINK = "claimsInquiryLink";
	public static final String APP_EXT_PROP_FTP_HOST = "ftpHost";
	public static final String APP_EXT_PROP_FTP_ALIAS = "ftpJ2CAlias";
	public static final String APP_EXT_PROP_FTP_PORT = "ftpPort";
	
	/*
	 * Constants
	 */
	public static String FILE_PATH = System.getProperty("file.separator") + "tmp" + System.getProperty("file.separator") + "claimsinquiry" + System.getProperty("file.separator");
	public static String EXCEL_FILE_EXTENSION = ".xls";
	public static String TEXT_FILE_EXTENSION = ".txt";
	public static String CSV_FILE_EXTENSION = ".csv";
	public static String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	public static String INVALID_CLAIMS = "invalidClaims";
	
}