/**
 * @author SinghPa
 */
package com.estes.framework.services.pointsuggest.iface;

import java.util.List;

import com.estes.framework.dto.AddressPoint;
import com.estes.framework.exception.ESTESException;

/**
 * Interface to return the address point suggest.
 */
public interface AddressPointSuggestService
{
	/**
	 * Method to return list of address points based on the city state zip.
	 * 
	 * @param addressPoint
	 * @return
	 * @throws ESTESException
	 */
	List<AddressPoint> getAddressPoint(AddressPoint addressPoint, Integer maxRecordsToFectch) throws ESTESException;

	/**
	 * Method to return the address list based on the city state zip.
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 * @throws ESTESException
	 */
	List<String> getAddressPoint(String addressPoint, String country, Integer maxRecordsToFectch) throws ESTESException;

	/**
	 * Method to parse user entered string to  <code>AddressPoint</code>
	 * 
	 * @param addressPoint
	 * @return
	 */
	public AddressPoint parsePointStr(String addressPoint);
}
