/**
 * @author: Todd Allen
 *
 * Creation date: 01/03/2019
 */

package com.estes.myestes.rating.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.rating.dao.iface.AccessorialDAO;
import com.estes.myestes.rating.dao.iface.ChargesDAO;
import com.estes.myestes.rating.dao.iface.CommentsDAO;
import com.estes.myestes.rating.dao.iface.CommodityDAO;
import com.estes.myestes.rating.dao.iface.CustomerDAO;
import com.estes.myestes.rating.dao.iface.DisclaimerDAO;
import com.estes.myestes.rating.dao.iface.FoodWarehouseDAO;
import com.estes.myestes.rating.dao.iface.LegacyRatingDAO;
import com.estes.myestes.rating.dao.iface.QuoteMessageDAO;
import com.estes.myestes.rating.dao.iface.RatingDAO;
import com.estes.myestes.rating.dto.Accessorial;
import com.estes.myestes.rating.dto.FoodWarehouse;
import com.estes.myestes.rating.dto.QuoteKeys;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.dto.RatingRequest;
import com.estes.myestes.rating.dto.ServiceLevel;
import com.estes.myestes.rating.exception.RatingErrorsException;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.RatingService;
import com.estes.myestes.rating.utils.RatingUtil;

@Service("ratingService")
public class RatingServiceImpl implements RatingService
{
	@Autowired
	private RatingDAO ratingDAO;
	@Autowired
	private LegacyRatingDAO legacyDAO;
	@Autowired
	private CustomerDAO custDAO;
	@Autowired
	private FoodWarehouseDAO fwDAO;
	@Autowired
	private CommodityDAO commodityDAO;
	@Autowired
	private CommentsDAO commentsDAO;
	@Autowired
	private AccessorialDAO accessDAO;
	@Autowired
	private ChargesDAO chargeDAO;
	@Autowired
	private QuoteMessageDAO messageDAO;
	@Autowired
	private DisclaimerDAO discDAO;

	@Override
	public void loadGMSData(String quoteId) throws RatingException
	{
		ratingDAO.loadGMSData(quoteId);
	} // loadGMSData

	@Override
	public List<Accessorial> retrieveAccessorials() throws RatingException
	{
		return accessDAO.getAccessorials();
	} //retrieveAccessorials

	@Override
	public List<FoodWarehouse> retrieveFoodWarehouses() throws RatingException
	{
		return fwDAO.getFoodWarehouses();
	} //retrieveFoodWarehouses

	@Override
	public List<RateQuote> retrieveNewQuotes(List<String> quoteApps, QuoteKeys keys) throws RatingException
	{
		List<RateQuote> allQuotes =  ratingDAO.getNewQuotes(Integer.parseInt(keys.getRefNum()));
		
		Map<Integer, RateQuote> results =  new HashMap<>();
		
		List<String> quoteInfo = new ArrayList<>();
		
		for (RateQuote quote : allQuotes) {
			// Return service levels based on quote app type
			if (RatingUtil.isRateReturned(quoteApps, quote.getService().getId())) {
				quote.setAccountName(custDAO.getName(quote.getAccountCode()));
				quote.setFoodWarehouse(fwDAO.getName(quote.getFoodWarehouseId()));
				quote.setComments(commentsDAO.getComments(quote.getQuoteID(), quote.getApp()));
				quote.setCommodities(commodityDAO.getQuoteCommodities(quote.getQuoteID()));
				quote.setAccessorials(accessDAO.getQuoteAccessorials(quote.getQuoteID()));
				quote.setCharges(chargeDAO.getQuoteCharges(quote.getQuoteID()));

				quoteInfo.addAll(messageDAO.getQuoteMessages(quote.getQuoteID()));
				quote.setInfo(new ArrayList<>(quoteInfo));
				
				quoteInfo.clear();
				
				quote.setDisclaimers(discDAO.getDisclaimers());
				results.put(quote.getService().getId(),quote);
			}else{
				quoteInfo.addAll(messageDAO.getQuoteMessages(quote.getQuoteID()));
			}
		}
		return RatingUtil.sortRateQuote(results);
	} // retrieveNewQuotes

	@Override
	public RateQuote retrieveQuote(String id) throws RatingException
	{
		RateQuote quote =  ratingDAO.getQuoteData(id);
		if (quote != null) {
			quote.setAccountName(custDAO.getName(quote.getAccountCode()));
			quote.setFoodWarehouse(fwDAO.getName(quote.getFoodWarehouseId()));
			quote.setComments(commentsDAO.getComments(quote.getQuoteID(), quote.getApp()));
			quote.setCommodities(commodityDAO.getQuoteCommodities(quote.getQuoteID()));
			quote.setAccessorials(accessDAO.getQuoteAccessorials(quote.getQuoteID()));
			quote.setCharges(chargeDAO.getQuoteCharges(quote.getQuoteID()));
			quote.setInfo(messageDAO.getQuoteMessages(quote.getQuoteID()));
			quote.setDisclaimers(discDAO.getDisclaimers());
		}
		else {
			// Got legacy quote data if ID is numeric
			try {
				quote = legacyDAO.getQuoteData(Integer.parseInt(id));
			} catch (NumberFormatException e) {}
		}

		return quote;
	} // retrieveQuote

	@Override
	public List<ServiceLevel> retrieveServiceLevels() throws RatingException
	{
		return ratingDAO.getServiceLevels();
	} //retrieveServiceLevels

	@Override
	public QuoteKeys processRateRequest(RatingRequest rating) throws RatingException, RatingErrorsException
	{
		// Stage rate quote request data
		String masterId = stageRateRequest(rating);
		// Process quote request
		QuoteKeys keys = ratingDAO.persistData(masterId);
		// If error index is present then check for errors and put those in ServiceResponse objects
		if (!keys.getErrorId().equals("0") || !keys.getErrorId().equals("")) {
			// Get all error messages
			Map<String, String> errorInfo = messageDAO.getErrorMessages(Integer.parseInt(keys.getErrorId()));
			if (errorInfo != null) {
				List<ServiceResponse> errors = new ArrayList<ServiceResponse>();
				for (Map.Entry<String,String> pair : errorInfo.entrySet()){
					ServiceResponse resp = new ServiceResponse(pair.getKey(), pair.getValue());
					errors.add(resp);
			    }
				
				if (errors != null && !errors.isEmpty()) {
					throw new RatingErrorsException(HttpStatus.BAD_REQUEST,errors);
				}
				
				
			}
		}
	
		// No errors
		return keys;
	}

	/**
	 * Stage rate quote request data
	 * 
	 * @param req (@RatingRequest}
	 * @return Master quote ID
	 * @throws RatingException
	 */
	private String stageRateRequest(RatingRequest rating) throws RatingException
	{
		String quoteId = ratingDAO.stageRateRequest(rating);
		commodityDAO.stageCommodities(quoteId, rating.getCommodities());
		if (!StringUtils.isEmpty(rating.getComments())) {
			commentsDAO.stageComments(quoteId, rating);
		}
		if (rating.getAccessorials() != null) {
			accessDAO.stageAccessorials(quoteId, rating.getAccessorials());
		}
		return quoteId;
	} // stageRateRequest
}
