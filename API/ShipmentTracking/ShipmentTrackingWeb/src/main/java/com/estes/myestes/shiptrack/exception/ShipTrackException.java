/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.shiptrack.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Shipment tracking exception
 */
public class ShipTrackException extends ESTESException
{
	private static final long serialVersionUID = 5939035938262614667L;

	public ShipTrackException(String messageId)
	{
		super(messageId);
	} // Constructor

	public ShipTrackException(Exception ex)
	{
		super(ex);
	} // Constructor

	public ShipTrackException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
