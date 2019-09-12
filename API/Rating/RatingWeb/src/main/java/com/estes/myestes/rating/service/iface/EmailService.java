/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
 */

package com.estes.myestes.rating.service.iface;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.dto.EmailRequest;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.exception.RatingException;
import com.lowagie.text.DocumentException;

import freemarker.template.TemplateException;

/**
 * Rating e-mail services
 */
public interface EmailService
{
	public static final String CONTACT_ME = "C";

	public static boolean isValidEmail(String email)
	{
		String EMAIL_REGEX = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
		Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX, Pattern.CASE_INSENSITIVE);

		if (email == null) {
			return false;
		}

		// Check the whole e-mail address structure
		Matcher emailMatcher = EMAIL_PATTERN.matcher(email.trim());
		if (!emailMatcher.matches()) {
			return false;
		}

		return true;
	} // isValidEmail

	public static boolean isValidEmails(List<String> emails)
	{
		// Validate e-mail addresses; Return error if 1 is invalid
		for (String email : emails) {
			if (!isValidEmail(email)) {
				return false;
			}
		}

		return true;
	} // isValidEmails

	/**
	 * Send e-mail of quote details
	 * 
	 * @param req {@link BookingRequest}
	 * @return {@link ServiceReponse}
	 */
	public boolean sendBookingMessage(BookingRequest req) throws RatingException;

	/**
	 * Send e-mail of quote details
	 * 
	 * @param req {@link EmailRequest}
	 * @param quote {@link RateQuote} or null to retrieve existing quote
	 * @return {@link ServiceReponse}
	 */
	public ServiceResponse sendQuoteDetails(EmailRequest req, RateQuote quote) throws RatingException;

	public ByteArrayOutputStream getPdfOutPut(String quoteId)
			throws RatingException, IOException, TemplateException, DocumentException;

}
