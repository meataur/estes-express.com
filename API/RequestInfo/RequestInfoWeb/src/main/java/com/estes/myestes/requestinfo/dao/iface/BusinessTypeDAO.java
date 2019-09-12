package com.estes.myestes.requestinfo.dao.iface;

import java.util.List;

import com.estes.myestes.requestinfo.exception.RequestInfoException;

public interface BusinessTypeDAO extends BaseDAO
{
	/** 
	 * Returns true if the shipment is L2L.
	 * @param OT 'Origin Terminal' - first 3 digits of the PRO 
	 */
	public boolean isL2LShipment(String OT) throws RequestInfoException;

	/** 
	 * Returns true if the shipment is EFW.
	 * @param OT 'Origin Terminal' - first 3 digits of the PRO 
	 */
	public boolean isEFWShipment(String OT) throws RequestInfoException;

}