/**
 * @author: Todd Allen
 *
 * Creation date: 03/30/2019
 */

package com.estes.myestes.rating.service.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.RevisedQuoteDAO;
import com.estes.myestes.rating.dto.CommodityPricing;
import com.estes.myestes.rating.dto.ContactRequest;
import com.estes.myestes.rating.dto.EmailRequest;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.EmailService;
import com.estes.myestes.rating.service.iface.RatingService;
import com.estes.myestes.rating.service.iface.ServiceAdjustmentService;
import com.estes.myestes.rating.utils.RatingConstants;

@Service("adjustmentService")
@Scope("prototype")
public class ServiceAdjustmentServiceImpl implements ServiceAdjustmentService
{
	@Autowired
	private EmailService emailService;
	@Autowired
	private RatingService ratingService;

	@Autowired
	private RevisedQuoteDAO revisedQuoteDAO;
	
	@Override
	public ServiceResponse sendAdjustedQuoteInfo(ContactRequest info) throws RatingException
	{
		/**
		 * Try to update revise quote information
		 */
		try{
			saveRevisedQuote(info);
		}catch(Exception ex){
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "sendAdjustedQuoteInfo(ContactRequest)","Failed to save revised quote");
		}
		
		RateQuote quote = ratingService.retrieveQuote(info.getQuoteId());
		quote.setContactName(info.getContactName());
		quote.setContactPhone(info.getContactPhone());
		quote.setContactPhoneExt(info.getContactPhoneExt());
		quote.setContactEmail(info.getContactEmail());
		if (quote.getApp().equals(RatingConstants.LTL_QUOTE)) {
			quote.setComments(info.getComments());
			quote.setPickupDate(info.getPickupDate());
			
			quote.setPickupAvail(formatTime(info.getPickupAvail()));
			quote.setPickupClose(formatTime(info.getPickupClose()));
			
			quote.setStackable(info.isStackable());
			quote.setCommodities(info.getCommodities());
		}
	
		
		EmailRequest email = new EmailRequest();
		email.setAction("");
		email.setApp(quote.getApp());
		email.setQuoteId(info.getQuoteId());
		email.setQuoteRefNum(info.getQuoteRefNum());
		email.setLevel(quote.getService().getId());
		ArrayList<String> sendTo = new ArrayList<String>();
		sendTo.add(info.getContactEmail());
		sendTo.add("solutions@estes-express.com");
		email.setAddresses(sendTo);

		return emailService.sendQuoteDetails(email, quote);
	} // sendAdjustedQuoteInfo
	
	
	@Transactional
	private void saveRevisedQuote(ContactRequest contactRequest) {
	
		//Create Contact Info
		revisedQuoteDAO.saveRevisedQuoteInfo(contactRequest);
		
		//Create Commodity List
		List<CommodityPricing> commodityList = contactRequest.getCommodities();
		String quoteId = contactRequest.getQuoteId();
		revisedQuoteDAO.updateRevisedCommodityInfo(commodityList, quoteId);
		//update scheduling and stacakble information
		revisedQuoteDAO.updateRevisedSchedulingAndStackableInfo(contactRequest, quoteId);
		
	}
	private String formatTime(String time){
		
		SimpleDateFormat inputFormat = new SimpleDateFormat("hh:mm a");
		SimpleDateFormat outputFormat = new SimpleDateFormat("HH:mm:ss");
		Date date;
		String timeOut="00:00:00";
		try {
			date = inputFormat.parse(time);
			timeOut = outputFormat.format(date);
		} catch (ParseException e) {
			ESTESLogger.log(getClass(),"Invalid Time Format Passed from contact me click modal dialog form");
		}
		
		return timeOut;
	}
}
