/**
 * @author: Todd Allen
 *
 * Creation date: 02/11/2019
 */

package com.estes.myestes.rating.dao.iface;

import java.util.List;

import org.springframework.util.StringUtils;

import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.dto.QuoteKeys;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.dto.ServiceLevel;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Data access for rating services
 */
public interface RatingDAO extends BaseDAO
{
	/**
     * Request origin (WB = web UI)
     */
	public static final String ORIGIN = "WB";

	/**
	 * Book (reserve) quote
	 *
	 * @param id Quote ID
	 * @return Success indicator
	 */
	public boolean bookQuote(BookingRequest bookingRequest) throws RatingException;

	/**
	 * Get newly generated rate quotes
	 *
	 * @param ref Quote reference number
	 * @return List of {@link RateQuote}
	 */
	public List<RateQuote> getNewQuotes(int ref) throws RatingException;

	/**
	 * Get rate quote data for given ID
	 * 
	 * @return {@link RateQuote}
	 */
	public RateQuote getQuoteData(String id) throws RatingException;

	/**
	 * Get service level data
	 * 
	 * @return List of {@link ServiceLevel}
	 */
	public List<ServiceLevel> getServiceLevels() throws RatingException;

	/**
	 * Load GMS data from GSC tables for given quote ID
	 * 
	 * @param quoteId Unique quote ID
	 */
	public void loadGMSData(String quoteId) throws RatingException;

	/**
	 * Process staged rate quote request data
	 * 
	 * @return Master quote ID
	 * @throws RatingException
	 */
	public QuoteKeys persistData(String masterId) throws RatingException;

	/**
	 * Stage rate quote request data
	 * 
	 * @param req (@RatingRequest}
	 * @return Master quote ID
	 * @throws RatingException
	 */
	public String stageRateRequest(RatingRequest req) throws RatingException;
	
	
	/**
	 * Construct phone number
	 * 
	 * @param phone Phone number
	 * @param ext Phone number extension
	 * @return Full phone number
	 */
	public static String constructPhone(String phone, String ext)
	{
		if(phone==null || phone.replaceAll("\\D+","").length() < 10 ){
			return "";
		}
		
		phone = phone.replaceAll("\\D+","");
		
		phone = phone.substring(0, 3)+"-"+ phone.substring(3,6)+"-"+phone.substring(6,10);
		
		StringBuilder phn = new StringBuilder(phone);
		
		if (!StringUtils.isEmpty(ext)) {
			phn.append(" x" + ext);
		}
		
		return phn.toString();
	} // constructPhone

	/**
	 * Extract phone number
	 * 
	 * @param phn Full phone number
	 * @return Phone number
	 */
	public static String extractPhone(String phn)
	{
		if (phn==null || StringUtils.isEmpty(phn) || phn.length() <10) {
			return "";
		}
		
		String[] phoneWithExt = phn.split("x");
		String phone = phoneWithExt[0];
		phone = phone.replaceAll("\\D+", "");
		if(phone.length()>=10){
			return "("+phone.substring(0, 3)+") "+ phone.substring(3,6)+"-"+phone.substring(6,10);
		}
		
		return "";
	} // extractPhone

	/**
	 * Extract phone extension
	 * 
	 * @param phn Full phone number
	 * @return Phone extension
	 */
	public static String extractPhoneExt(String phn)
	{
		int x = phn.indexOf("x");

		// Return blanks if no "x" found or no digits after "x"
		if (x < 0 || x+1 > phn.length()) {
			return "";
		}

		return phn.substring(x+1,phn.length());
	} // constructPhone

}
