package com.estes.myestes.shiptrack.service.iface;

import java.util.List;

import com.estes.myestes.shiptrack.dto.Shipment;
import com.estes.myestes.shiptrack.dto.TrackingRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

/**
 * Shipment tracking service
 */
public interface TrackingService
{
	/**
	 * Retrieve shipments for criteria requested
	 * 
	 * @param  search {@link TrackingRequest} from input form
	 * @return  Array of {@link Shipment} objects
	 */
	public List<Shipment> searchShipments(TrackingRequest search) throws ShipTrackException;
	
	/**
	 * Remove the duplicates
	 * @param searchCriteria
	 * @return
	 */
	public String getExtractedSearch(String searchCriteria);
}
