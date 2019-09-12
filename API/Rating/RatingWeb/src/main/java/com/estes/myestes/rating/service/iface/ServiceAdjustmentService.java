/**
 * @author: Todd Allen
 *
 * Creation date: 03/30/2019
 */

package com.estes.myestes.rating.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.dto.ContactRequest;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Rate quote service level adjustment services
 */
public interface ServiceAdjustmentService
{
	/**
	 * Adjust service level of quote
	 * 
	 * @param {@link ContactRequest}
	 * @return {@link ServiceReponse}
	 */
	public ServiceResponse sendAdjustedQuoteInfo(ContactRequest info) throws RatingException;
}
