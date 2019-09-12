/**
 * @author: chandye
 *
 * Creation date: 12/19/2018
 */

package com.estes.myestes.transittimecalculator.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes Transit Time Calculator applications exception
 */
public class TransitTimeCalculatorException extends ESTESException
{
	private static final long serialVersionUID = 3745651527396323728L;

	public TransitTimeCalculatorException(String messageId)
	{
		super(messageId);
	} // Constructor

	public TransitTimeCalculatorException(Exception ex)
	{
		super(ex);
	} // Constructor

	public TransitTimeCalculatorException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor
}
