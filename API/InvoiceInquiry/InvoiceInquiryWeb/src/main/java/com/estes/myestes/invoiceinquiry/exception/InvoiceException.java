/**
 * @author: Todd Allen
 *
 * Creation date: 10/01/2018
 */

package com.estes.myestes.invoiceinquiry.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Invoice inquiry application exception
 */
public class InvoiceException extends ESTESException
{
	private static final long serialVersionUID = 6790426377323743133L;

	public InvoiceException(String messageId)
	{
		super(messageId);
	} // Constructor

	public InvoiceException(Exception ex)
	{
		super(ex);
	} // Constructor

	public InvoiceException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor

	public InvoiceException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
