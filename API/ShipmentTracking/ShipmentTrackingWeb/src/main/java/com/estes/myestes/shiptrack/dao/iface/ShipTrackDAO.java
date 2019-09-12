/**
 * @author: Todd Allen
 *
 * Creation date: 06/19/2018
 */

package com.estes.myestes.shiptrack.dao.iface;

import java.util.List;

import com.estes.myestes.shiptrack.dto.Shipment;
import com.estes.myestes.shiptrack.dto.TrackingRequest;
import com.estes.myestes.shiptrack.exception.ShipTrackException;

public interface ShipTrackDAO extends BaseDAO
{
	/**
	 * Search for shipments
	 * @param  criteria {@link TrackingRequest} data
	 * @return  Array of shipments
	 */
	public List<Shipment> search(TrackingRequest criteria) throws ShipTrackException;
}
