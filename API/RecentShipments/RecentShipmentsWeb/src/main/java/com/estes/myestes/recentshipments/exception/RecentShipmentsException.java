/**
 *
 */

package com.estes.myestes.recentshipments.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Request info application exception
 */
public class RecentShipmentsException extends ESTESException
{
	private static final long serialVersionUID = 12721061420293410L;

	public RecentShipmentsException(String messageId) {
		super(messageId);
	} // Constructor

	public RecentShipmentsException(Exception ex) {
		super(ex);
	} // Constructor

	public RecentShipmentsException(String messageId, String message) {
		super(messageId, message);
	} // Constructor
}