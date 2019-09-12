/**
 * @author: Todd Allen
 *
 * Creation date: 11/05/2018
 */

package com.estes.services.myestes.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Shipment tracking exception
 */
public class ServiceException extends ESTESException
{
	private static final long serialVersionUID = 1L;

	public ServiceException(String messageId)
	{
		super(messageId);
	} // Constructor

	public ServiceException(Exception ex)
	{
		super(ex);
	} // Constructor


	public ServiceException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor

	public ServiceException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
