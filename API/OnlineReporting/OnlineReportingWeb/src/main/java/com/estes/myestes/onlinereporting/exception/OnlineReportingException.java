/**
 * @author: Lakshman K
 *
 * Creation date: 10/29/2018
 */

package com.estes.myestes.onlinereporting.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes point applications exception
 */
public class OnlineReportingException extends ESTESException
{
	private static final long serialVersionUID = -2647673053567792077L;

	public OnlineReportingException(String messageId)
	{
		super(messageId);
	} // Constructor

	public OnlineReportingException(Exception ex)
	{
		super(ex);
	} // Constructor

	public OnlineReportingException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
