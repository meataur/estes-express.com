/**
 * @author: Todd Allen
 *
 * Creation date: 05/09/2016
 *
 */

package com.estes.ssdr.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Document retrieval (SSDR) application exception
 */
public class DocumentRetrievalException extends ESTESException
{
	private static final long serialVersionUID = 12721061420293410L;

	public DocumentRetrievalException(String messageId)
	{
		super(messageId);
	} // Constructor

	public DocumentRetrievalException(Exception ex)
	{
		super(ex);
	} // Constructor

	public DocumentRetrievalException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}