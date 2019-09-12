/**
 * @author: Lakshman Kandaswamy
 *
 * Creation date: 10/10/2018
 */

package com.estes.myestes.recoverpassword.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes point applications exception
 */
public class RecoverPasswordException extends ESTESException
{
	private static final long serialVersionUID = 3745651527396323728L;

	public RecoverPasswordException(String messageId)
	{
		super(messageId);
	} // Constructor

	public RecoverPasswordException(Exception ex)
	{
		super(ex);
	} // Constructor

	public RecoverPasswordException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor
}
