/**
 * @author: Todd Allen
 *
 * Creation date: 08/06/2018
 */

package com.estes.myestes.signup.exception;

import com.estes.framework.exception.ESTESException;

/**
 * My Estes profile signup exception
 */
public class SignupException extends ESTESException
{
	private static final long serialVersionUID = 3081359791550424340L;

	public SignupException(String messageId)
	{
		super(messageId);
	} // Constructor

	public SignupException(Exception ex)
	{
		super(ex);
	} // Constructor

	public SignupException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor

	public SignupException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
