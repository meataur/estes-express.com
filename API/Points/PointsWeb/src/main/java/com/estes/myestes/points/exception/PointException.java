/**
 * @author: Todd Allen
 *
 * Creation date: 01/31/2018
 */

package com.estes.myestes.points.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes point applications exception
 */
public class PointException extends ESTESException
{
	private static final long serialVersionUID = 3745651527396323728L;

	public PointException(String messageId)
	{
		super(messageId);
	} // Constructor

	public PointException(Exception ex)
	{
		super(ex);
	} // Constructor

	public PointException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor
}
