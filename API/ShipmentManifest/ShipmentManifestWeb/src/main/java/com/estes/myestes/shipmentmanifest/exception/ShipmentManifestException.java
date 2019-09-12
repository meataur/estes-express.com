/**
 *
 */

package com.estes.myestes.shipmentmanifest.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Request info application exception
 */
public class ShipmentManifestException extends ESTESException
{
	private static final long serialVersionUID = 12721061420293410L;

	public ShipmentManifestException(String messageId)
	{
		super(messageId);
	} // Constructor

	public ShipmentManifestException(Exception ex)
	{
		super(ex);
	} // Constructor

	public ShipmentManifestException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}