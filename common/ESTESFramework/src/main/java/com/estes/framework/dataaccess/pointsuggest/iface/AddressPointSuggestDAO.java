package com.estes.framework.dataaccess.pointsuggest.iface;

import java.util.List;

import com.estes.framework.dto.AddressPoint;
/**
 * Interface for point suggest.
 * @author SinghPa
 *
 * @param <E>
 */
public interface AddressPointSuggestDAO {
	/**
	 * Method to get the address based on the given parameters.
	 * 1.get the address from the preference city.
	 * 2.If no points found in the preferred city, then find in the remaining set of city.
	 * @param city
	 * @param state
	 * @param zip
	 * @return
	 */
	List<AddressPoint> getAddress(AddressPoint inputAddressPoint, int recordCnt);
	
}
