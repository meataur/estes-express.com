/**
 * @author: Todd Allen
 *
 * Creation date: 10/11/2018
 */

package com.estes.myestes.terminallist.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Shipment tracking exception
 */
public class TerminalException extends ESTESException
{
	private static final long serialVersionUID = -3585924852550027544L;

	public TerminalException(String messageId)
	{
		super(messageId);
	} // Constructor

	public TerminalException(Exception ex)
	{
		super(ex);
	} // Constructor

	public TerminalException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
