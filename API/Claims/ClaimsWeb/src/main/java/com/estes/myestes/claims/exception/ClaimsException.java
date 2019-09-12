/**
 *
 */

package com.estes.myestes.claims.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Request info application exception
 */
public class ClaimsException extends ESTESException {
	private static final long serialVersionUID = 12721061420293410L;

	public ClaimsException(String messageId) {
		super(messageId);
	} // Constructor

	public ClaimsException(Exception ex) {
		super(ex);
	} // Constructor

	public ClaimsException(String messageId, String message) {
		super(messageId, message);
	} // Constructor
}