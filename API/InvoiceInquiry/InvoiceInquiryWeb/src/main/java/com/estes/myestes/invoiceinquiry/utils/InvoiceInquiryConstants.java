/**
 * @author: Todd Allen
 *
 * Creation date: 06/15/2018
 */

package com.estes.myestes.invoiceinquiry.utils;

/**
 * Constants
 */
public interface InvoiceInquiryConstants
{
	String APP_CONFIG = "invoiceinquiry.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	String DOCS = "documents";
	String MOUNT_LOC = "mountLocation";
	String DOCVIEW_LOC = "docviewLocation";
	String FILE_LOC = "fileLocation";
	String PAYMENTS = "payments";
	String VERIFY_URL = "verifyUrl";
	String FINALIZE = "finalize";

	/**
	 * Default error code for ReST service calls
	 */
	String DEFAULT_ERROR_CODE = "error";
	/**
	 * Default error message for ReST service calls
	 */
	String DEFAULT_ERROR_MSG = "An unexpected error occurred.";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
