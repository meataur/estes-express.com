/**
 * @author: Todd Allen
 *
 * Creation date: 05/24/2018
 */

package com.estes.myestes.addressbook.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes point applications exception
 */
public class AddressBookException extends ESTESException
{
	private static final long serialVersionUID = -2647673053567792077L;

	public AddressBookException(String messageId)
	{
		super(messageId);
	} // Constructor

	public AddressBookException(Exception ex)
	{
		super(ex);
	} // Constructor

	public AddressBookException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor

	public AddressBookException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
