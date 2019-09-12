/**
 * @author: Todd Allen
 *
 * Creation date: 04/10/2018
 */

package com.estes.myestes.accountrequest.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes point applications exception
 */
public class AccountRequestException extends ESTESException
{
	private static final long serialVersionUID = 4652762508136974484L;

	public AccountRequestException(String messageId)
	{
		super(messageId);
	} // Constructor

	public AccountRequestException(Exception ex)
	{
		super(ex);
	} // Constructor

	public AccountRequestException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor

	public AccountRequestException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
