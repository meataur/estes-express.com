/**
 *
 */

package com.estes.myestes.requestinfo.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Request info application exception
 */
public class RequestInfoException extends ESTESException
{
	private static final long serialVersionUID = 12721061420293410L;

	public RequestInfoException(String messageId)
	{
		super(messageId);
	} // Constructor

	public RequestInfoException(Exception ex)
	{
		super(ex);
	} // Constructor

	public RequestInfoException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}