/**
 * @author: Todd Allen
 *
 * Creation date: 06/25/2018
 */

package com.estes.myestes.login.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Shipment tracking exception
 */
public class LoginException extends ESTESException
{
	private static final long serialVersionUID = 2708245827570843988L;

	public LoginException(String messageId)
	{
		super(messageId);
	} // Constructor

	public LoginException(Exception ex)
	{
		super(ex);
	} // Constructor

	public LoginException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
