/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 */

package com.estes.dto.common;

/**
 * ReST service response DTO
 */
public class ServiceResponse
{
	
	/*
	 * Default error code
	 */
	String DEFAULT_ERROR_CODE = "GEN9998";

	String errorCode;
	String message;
	String fieldName;
	/**
	 * Error severity - info/warning/fatal
	 */
	String severity;
	String badData;
	String redirectUrl;

	/**
	 * Create new ReST service response
	 */
	public ServiceResponse()
	{
		super();
	} // Constructor

	/**
	 * Create new ReST service response
	 * 
	 * @param err Error code
	 * @param msg Error message
	 */
	public ServiceResponse(String err, String msg)
	{
		super();
		this.errorCode = err;
		this.message = msg;
	} // Constructor

	/**
	 * Create new ReST service response
	 * 
	 * @param err Error code
	 * @param msg Error message
	 * @param field Error field
	 */
	public ServiceResponse(String err, String msg, String field)
	{
		this(err, msg);
		this.fieldName = field;
	} // Constructor

	/**
	 * Get the data causing this error
	 * 
	 * @return Error data
	 */
	public String getBadData()
	{
		return this.badData;
	} // getBadData

	/**
	 * Get the error code
	 * 
	 * @return Error code
	 */
	public String getErrorCode()
	{
		return this.errorCode;
	} // getErrorCode

	/**
	 * Get the field name in error
	 * 
	 * @return Field name
	 */
	public String getFieldName()
	{
		return this.fieldName;
	} // getFieldName

	/**
	 * Get the error message
	 * 
	 * @return Error message
	 */
	public String getMessage()
	{
		return this.message == null ? null : this.message.trim();
	} // getMessage

	/**
	 * Get the redirection URL
	 * 
	 * @return Redirection URL
	 */
	public String getRedirectUrl()
	{
		return this.redirectUrl;
	} // getRedirectUrl

	/**
	 * Get the error severity
	 * 
	 * @return Error severity
	 */
	public String getSeverity()
	{
		return severity;
	}

	/**
	 * Check for error
	 * 
	 * @return Boolean error indicator
	 */
	public static boolean isError(String code)
	{
		return code != null && code.trim().length() > 0;
	} // isError

	/**
	 * Set the data causing this error
	 * 
	 * @param val Error data
	 */
	public void setBadData(String val)
	{
		this.badData = val;
	} // setBadData

	/**
	 * Set the error code
	 * 
	 * @param val Error code
	 */
	public void setErrorCode(String val)
	{
		this.errorCode = val;
	} // setErrorCode

	/**
	 * Set the error field name
	 * 
	 * @param val Field name
	 */
	public void setFieldName(String val)
	{
		this.fieldName = val;
	} // setFieldName

	/**
	 * Set the error message
	 * 
	 * @param val Error message
	 */
	public void setMessage(String val)
	{
		this.message = val;
	} // setMessage

	/**
	 * Set the redirection URL
	 * 
	 * @param url Redirection URL
	 */
	public void setRedirectUrl(String url)
	{
		this.redirectUrl = url;
	} // setRedirectUrl

	/**
	 * Set the error severity
	 * 
	 * @param val Error severity
	 */
	public void setSeverity(String val)
	{
		this.severity = val;
	} // setSeverity
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "ServiceResponse [errorCode=" + errorCode + ", message=" + message + ", fieldName=" + fieldName
				+ ", severity=" + severity + ", badData=" + badData + ", redirectUrl=" + redirectUrl + "]";
	}//toString() 

}
