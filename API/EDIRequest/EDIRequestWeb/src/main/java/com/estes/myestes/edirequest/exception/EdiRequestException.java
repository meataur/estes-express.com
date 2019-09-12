/**
 * @author: Lakshman K
 *
 * Creation date: 1/3/2019
 */

package com.estes.myestes.edirequest.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes EDI Request applications exception
 */
public class EdiRequestException extends ESTESException
{
	private static final long serialVersionUID = -2647673053567792077L;

	public EdiRequestException(String messageId)
	{
		super(messageId);
	} // Constructor

	public EdiRequestException(Exception ex)
	{
		super(ex);
	} // Constructor

	public EdiRequestException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
