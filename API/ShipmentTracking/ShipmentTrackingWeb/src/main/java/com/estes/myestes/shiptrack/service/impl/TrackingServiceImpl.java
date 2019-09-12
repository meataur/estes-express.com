/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.shiptrack.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.shiptrack.dao.iface.ShipTrackDAO;
import com.estes.myestes.shiptrack.dto.Shipment;
import com.estes.myestes.shiptrack.dto.TrackingRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;
import com.estes.myestes.shiptrack.service.iface.TrackingService;
import com.estes.myestes.shiptrack.utils.AppConstants;

@Service("trackingService")
@Scope("prototype")
public class TrackingServiceImpl implements TrackingService
{
	@Autowired
	private ShipTrackDAO trackingDAO;

	/**
	 * Create a new service
	 */
	public TrackingServiceImpl() {
	}

	/**
	 * (non-Javadoc)
	 * @see com.estes.myestes.shiptrack.service.iface.TrackingService#searchShipments()
	 */
	public List<Shipment> searchShipments(TrackingRequest search) throws ShipTrackException
	{
		List<Shipment> shipments = trackingDAO.search(search);
		
		return sortShipments(shipments,search);
	}
	
	private List<Shipment> sortShipments(List<Shipment> shipments, TrackingRequest crit){
		List<Shipment> sortedShipments = new ArrayList<>();
		String[] searchTerms = crit.getSearch().split(AppConstants.DELIMITER);
		
		for(String searchItem: searchTerms){
			
			for(Shipment shipment: shipments) {	
				
				if(crit.getType().equals("PRO")) {
					if(shipment.getError().equals("") && shipment.getPro().replaceAll("-", "").equals(searchItem.replaceAll("-", ""))) {
						sortedShipments.add(shipment);
				    }else if(shipment.getErrorInfo().getErrorCode()!=null && shipment.getErrorInfo().getBadData().replaceAll("-", "").equals(searchItem.replaceAll("-", ""))){
				    	sortedShipments.add(shipment);
				    }
				}else{
					sortedShipments.add(shipment);
				}
			}
		}
		
		return sortedShipments;
		
	}
	
	@Override
	public String getExtractedSearch(String searchCriteria) {
		ArrayList<String> scrubbedCriteria = new ArrayList<String>();	
		for(String criterion: searchCriteria.split(AppConstants.DELIMITER)) {
			criterion = criterion.trim();		
			if(!scrubbedCriteria.contains(criterion)) {
				scrubbedCriteria.add(criterion);
			}
		}
		StringBuilder builder = new StringBuilder();
		for(String criteria: scrubbedCriteria){
			builder.append(criteria + AppConstants.DELIMITER);
		}
		String searchStr = builder.toString();
		return searchStr.substring(0, searchStr.length()-1);
	}
}
