/**
 * @author: Todd Allen
 *
 * Creation date: 02/07/2019
 */

package com.estes.myestes.rating.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.dto.Accessorial;
import com.estes.myestes.rating.dto.FoodWarehouse;
import com.estes.myestes.rating.dto.QuoteKeys;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.dto.ServiceLevel;
import com.estes.myestes.rating.exception.RatingErrorsException;
import com.estes.myestes.rating.exception.RatingException;

/**
 * Rate quote services
 */
public interface RatingService
{

	

	/**
	 * Load rating GMS data upon rate quote selection
	 * 
	 * @param quoteId Unique quote ID
	 */
	public void loadGMSData(String quoteId) throws RatingException;

	/**
	 * Process rating request
	 * 
	 * @param {@RatingRequest}
	 * @return List of {@link ServiceResponse} objects
	 * @throws RatingErrorsException 
	 */
	public QuoteKeys processRateRequest(RatingRequest rating) throws RatingException, RatingErrorsException;

	/**
	 * Retrieve all accessorials
	 * 
	 * @return List of {@link Accessorial} objects
	 */
	public List<Accessorial> retrieveAccessorials() throws RatingException;

	/**
	 * Retrieve all food warehouses
	 * 
	 * @return List of {@link FoodWarehouse} objects
	 */
	public List<FoodWarehouse> retrieveFoodWarehouses() throws RatingException;

	/**
	 * Retrieve newly generated quotes
	 * 
	 * @param quoteApps Selected quote types
	 * @return {@link RateQuote} object
	 */
	public List<RateQuote> retrieveNewQuotes(List<String> quoteApps, QuoteKeys keys) throws RatingException;

	/**
	 * Retrieve quote data for given ID
	 * 
	 * @param id Unique quote ID
	 * @return {@link RateQuote} object
	 */
	public RateQuote retrieveQuote(String id) throws RatingException;

	/**
	 * Retrieve all service levels
	 * 
	 * @return List of {@link ServiceLevel} objects
	 */
	public List<ServiceLevel> retrieveServiceLevels() throws RatingException;
}
