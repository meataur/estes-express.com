/**
 * @author: Lakshman K
 *
 * Creation date: 11/12/2018
 */

package com.estes.myestes.onlinereporting.utils;

/**
 * Constants
 */
public interface OnlineReportingConstant
{
	String APP_CONFIG = "onlinereporting.config";
	String LOGGER = "logger";
	String LOCATION = "location";
	
	public static final String CREATE_PGM_JOB = "WEB";
	public static final String MARK_FOR_DELETION = "D";
	public static final String REPORT_TYPE_FILE = "FBFILES.OLR00P001";
	public static final String REPORT_SCHEDULE_FILE = "FBFILES.OLR00P002";
	public static final String FUTURE_END_DATE = "12/31/2039";//IBM MAX FUTURE DATE AT THE TIME THIS WAS WRITTEN.
	public static final String BLANK = "";
	public static final String DEFAULT_NUMBER_VALUE = "0";
	public static final String SUCCESS_MESSAGE = "Your report has been REPLACETEXT successfully.";
	public static final String DELETE_MESSAGE = "The selected report has been removed.";
	public static final String NON_WHITESPACE = "\\s+";
	public static final String DATE_FORMAT = "MM/dd/yyyy";
	
	String ORIGIN_DOMAIN = "originDomain"; // jvm custom property name for originDomain
	
}
