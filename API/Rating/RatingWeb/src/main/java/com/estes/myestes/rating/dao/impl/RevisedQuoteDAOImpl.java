package com.estes.myestes.rating.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.rating.dao.iface.RatingDAO;
import com.estes.myestes.rating.dao.iface.RevisedQuoteDAO;
import com.estes.myestes.rating.dto.CommodityPricing;
import com.estes.myestes.rating.dto.ContactRequest;

@Repository ("revisedQuoteDAO")
public class RevisedQuoteDAOImpl  implements RevisedQuoteDAO{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Override
	public void saveRevisedQuoteInfo(ContactRequest contactRequest) {
		
		jdbcMyEstes.update(RevisedQuoteDAO.SAVE_REVISED_QUOTE_INFO,
				contactRequest.getQuoteId(), "C", RatingDAO.constructPhone(contactRequest.getContactPhone(),contactRequest.getContactPhoneExt()) ,
				contactRequest.getContactEmail(), contactRequest.getContactName(),
				contactRequest.getComments());
	}

	@Override
	public void updateRevisedCommodityInfo(List<CommodityPricing> commodityList, String quoteId) {
		int seq = 1;
		for (CommodityPricing com : commodityList) {
			if (com.getCommodity().getWeight()>0) {
				jdbcMyEstes.update(RevisedQuoteDAO.UPDATE_REVISED_COMMODITY,
						com.getCommodity().getPieces(),
						com.getCommodity().getPieceType(), 
						com.getCommodity().getWeight(), 
						com.getCommodity().getDimensions().getLength(), 
						com.getCommodity().getDimensions().getWidth(), 
						com.getCommodity().getDimensions().getHeight(),
						com.getCommodity().getDescription(), 
						quoteId,
						seq);
			}
			seq++;
		}
	}

	@Override
	public void updateRevisedSchedulingAndStackableInfo(ContactRequest contactRequest, String quoteId) {
		jdbcMyEstes.update(RevisedQuoteDAO.UPDATE_REVISED_SCHEDULING_AND_STACKABLE,
				contactRequest.getPickupDate()==null? "" : java.sql.Date.valueOf(contactRequest.getPickupDate()),
				contactRequest.getPickupAvail(),
				contactRequest.getPickupClose(),
				contactRequest.isStackable()? "Y" : "N",
				quoteId);
	}

}
