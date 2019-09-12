package com.estes.myestes.fuelsurcharge.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Fuel Surcharge exception
 */
public class FuelSurchargeException extends ESTESException
{
	private static final long serialVersionUID = 5939035938262614667L;

	public FuelSurchargeException(String messageId)	
	{
		super(messageId);
	} // Constructor

	public FuelSurchargeException(Exception ex)
	{
		super(ex);
	} // Constructor

	public FuelSurchargeException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor

	public FuelSurchargeException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
