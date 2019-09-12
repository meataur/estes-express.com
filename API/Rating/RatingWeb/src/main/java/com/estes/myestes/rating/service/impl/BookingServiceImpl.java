/**
 * @author: Todd Allen
 *
 * Creation date: 03/28/2019
 */

package com.estes.myestes.rating.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.rating.dao.iface.RatingDAO;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.BookingService;
import com.estes.myestes.rating.service.iface.EmailService;
import com.estes.myestes.rating.utils.RatingConstants;

@Service("bookingService")
@Scope("prototype")
public class BookingServiceImpl implements BookingService
{
	@Autowired
	private EmailService emailService;
	@Autowired
	private RatingDAO ratingDAO;

	@Override
	public ServiceResponse bookShipment(BookingRequest request) throws RatingException
	{
		//if (request.getApp().equals(RatingConstants.LTL_QUOTE) ) {
			// Validate e-mail addresses
			if (!EmailService.isValidEmails(request.getAddresses())) {
				return new ServiceResponse("error", "1 or more e-mail addresses entered is invalid.");
			}
		//}

		if (!ratingDAO.bookQuote(request)) {
			return new ServiceResponse("error", "An unexpected error occurred booking the shipment");
		}

		//if (!request.getApp().equals(RatingConstants.LTL_QUOTE) ) {
			emailService.sendBookingMessage(request);
		//}

		// Set redirection based on target
		ServiceResponse resp = new ServiceResponse("", "");
		if (request.getTarget().equals(BookingRequest.BOL_TARGET)) {
			resp.setRedirectUrl(
					ESTESConfigUtil.getProperty(RatingConstants.REDIRECT, RatingConstants.BILL_OF_LADING_APP) + "?quote="
							+ request.getQuoteId());
		}
		else if (request.getTarget().equals(BookingRequest.PICKUP_TARGET)) {
			resp.setRedirectUrl(
					ESTESConfigUtil.getProperty(RatingConstants.REDIRECT, RatingConstants.PICKUP_REQUEST_APP) + "?quote="
							+ request.getQuoteId());
		}

		return resp;
	} // bookShipment
}
