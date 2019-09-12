/**
 * @author: Todd Allen
 *
 * Creation date: 03/01/2019
 */

package com.estes.myestes.rating.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.rating.dao.iface.RateHistoryDAO;
import com.estes.myestes.rating.dto.RateSearch;
import com.estes.myestes.rating.dto.RateSummary;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.RatingHistoryService;
import com.estes.myestes.rating.utils.RatingUtil;

@Service("ratingHistoryService")
@Scope("prototype")
public class RatingHistoryServiceImpl implements RatingHistoryService
{
	@Autowired
	private RateHistoryDAO historyDAO;

	@Override
	public List<RateSummary> retrieveQuoteSummaryData(int ref) throws RatingException
	{

		Map<Integer, RateSummary> results =  new HashMap<>();
		
		List<RateSummary> data =  historyDAO.getAllServiceLevels(ref);
		List<String> quoteApps = historyDAO.getAppsByRef(ref);
		for(RateSummary rs : data){
			if(RatingUtil.isRateReturned(quoteApps, rs.getServiceLevelId())){
				results.put(rs.getServiceLevelId(), rs);
			}
		}
		
		return RatingUtil.sortRateQuote(results);
		
	} // retrieveUserQuotes

	@Override
	public Page<RateSummary> searchUserQuotes(Pageable pageable, RateSearch search) throws RatingException
	{
		List<RateSummary> quotes = historyDAO.searchQuoteHistory(pageable, search);
		int rows = 0;

		if (quotes != null && !quotes.isEmpty()) {
			rows = historyDAO.getSearchTotal(search);
		}

		return new Page<RateSummary>(quotes, pageable, rows);
	} // searchUserQuotes
}
