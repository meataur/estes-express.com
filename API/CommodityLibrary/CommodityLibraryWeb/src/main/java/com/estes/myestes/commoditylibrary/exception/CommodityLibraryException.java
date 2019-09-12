/**
 * @author: Lakshman K
 *
 * Creation date: 10/29/2018
 */

package com.estes.myestes.commoditylibrary.exception;

import com.estes.framework.exception.ESTESException;

/**
 * Estes Commodity Library applications exception
 */
public class CommodityLibraryException extends ESTESException
{
	private static final long serialVersionUID = -2647673053567792077L;

	public CommodityLibraryException(String messageId)
	{
		super(messageId);
	} // Constructor

	public CommodityLibraryException(Exception ex)
	{
		super(ex);
	} // Constructor

	public CommodityLibraryException(String messageId, String message)
	{
		super(messageId, message);
	} // Constructor
}
