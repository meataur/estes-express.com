/**
 * @author: Todd Allen
 *
 * Creation date: 03/28/2019
 */

package com.estes.myestes.rating.service.iface;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Rate quote services
 */
public interface BookingService
{
	/**
	 * Book shipment from rate quote
	 * 
	 * @param {@link BookingRequest}
	 * @return {@link ServiceReponse}
	 */
	public ServiceResponse bookShipment(BookingRequest bookingRequest) throws RatingException;
}
