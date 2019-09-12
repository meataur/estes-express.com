/**
 * @author: Todd Allen
 *
 * Creation date: 02/06/2019
 */

package com.estes.myestes.rating.exception;

import org.springframework.http.HttpStatus;

import com.estes.framework.exception.ESTESException;

import lombok.Getter;
import lombok.Setter;

/**
 * Estes point applications exception
 */
public class RatingException extends ESTESException
{
	private static final long serialVersionUID = -746033977472184367L;
	
	@Getter	
	@Setter
	private HttpStatus httpStatus = HttpStatus.BAD_REQUEST;

	public RatingException(String messageId)
	{
		super(messageId);
	} // Constructor

	public RatingException(Exception ex)
	{
		super(ex);
	} // Constructor

	public RatingException(String message, Exception ex)
	{
		super(message, ex);
	} // Constructor
}
