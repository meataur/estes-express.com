package com.estes.myestes.PickupRequest.web.dao.impl;

import static com.estes.myestes.PickupRequest.web.dao.sql.PickupRequestQuery.PICKUP_SUMMARY_QUERY;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.dto.common.rest.Pageable.Order;
import com.estes.myestes.PickupRequest.config.AuthenticationDetails;
import com.estes.myestes.PickupRequest.web.dao.iface.PagingAndSortingDAO;
import com.estes.myestes.PickupRequest.web.dao.iface.PickupHistoryDAO;
import com.estes.myestes.PickupRequest.web.dao.mapper.PickupMapper;
import com.estes.myestes.PickupRequest.web.dto.Pickup;

@Repository("pickupHistoryDAO")
public class PickupHistoryDAOImpl implements PickupHistoryDAO {
	
	@Autowired
	private PagingAndSortingDAO<Pickup> pagingAndSortingDAO;
	
	
	@Override
	public Page<Pickup> getPickup(Pageable pageable) {
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		
		List<Object> params = new ArrayList<>();
		params.add(auth.getUsername());
		String query = PICKUP_SUMMARY_QUERY;
		String countQuery = query.replace("*", "count(*) as total");
		String sort = pageable.getSort();
	
		if(sort!=null){
			
			String order = pageable.getOrder().name();
			
			switch(sort){
		
				case "requestNumber":
					query +=" ORDER BY requestNumber "+order;
					break;
					
				case "proNumber":
					query +=" ORDER BY proNumber "+order;
					break;
					
				case "shipperCompany":
					query +=" ORDER BY LOWER(shipperCompany) "+order;
					break;
					
					
				case "shipperCity":
					query +=" ORDER BY LOWER(shipperCity) "+order;
					break;
					
				case "shipperState":
					query +=" ORDER BY LOWER(shipperState) "+order;
					break;
					
				case "submittedDate":
					query +=" ORDER BY submittedDate "+order;
					break;
				case "pickupDate":
					query +=" ORDER BY pickupDate "+order+", requestNumber "+order;
					break;
				
					
				case "totalPieces":
					query +=" ORDER BY totalPieces "+order;
					break;
					
				case "totalWeight":
					query +=" ORDER BY totalWeight "+order;
					break;
					
				case "terminalName":
					query +=" ORDER BY LOWER(terminalName) "+order;
					break;
					
				case "terminalPhone":
					query +=" ORDER BY terminalPhone "+order;
					break;
					
				case "terminalFax":
					query +=" ORDER BY terminalFax "+order;
					break;
					
				case "status":
					query +=" ORDER BY status "+order;
					break;
					
				default:
					pageable.setSort("requestNumber");
					pageable.setOrder(Order.desc);
					query +=" ORDER BY requestNumber desc";
					break;
			}
		}else{
			pageable.setSort("requestNumber");
			pageable.setOrder(Order.desc);
			query +=" ORDER BY requestNumber desc";
		}
		
		//ESTESLogger.log(getClass(), "Count Query = "+countQuery);
		//ESTESLogger.log(getClass(), "Query = "+query);
		
		Page<Pickup> results = pagingAndSortingDAO.getResult(countQuery, query, params, pageable, new PickupMapper());
		return results;
	}

}
