/**
 * @author: Todd Allen
 *
 * Creation date: 12/14/2017
 *
 */

package com.estesexpress.currency.convert.service.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Currency conversion application exception
 */
public class CurrencyConversionException extends ESTESException
{
	private static final long serialVersionUID = 4128490458782784748L;

	public CurrencyConversionException(String messageId)
	{
		super(messageId);
	} // Constructor

	public CurrencyConversionException(Exception ex)
	{
		super(ex);
	} // Constructor

	public CurrencyConversionException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}