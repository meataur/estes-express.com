/**
 * @author: Todd Allen
 *
 * Creation date: 08/21/2018
 */

package com.estes.myestes.addressbook.dao.iface;

import com.estes.dto.common.Address;
import com.estes.myestes.addressbook.exception.AddressBookException;

public interface PointsDAO extends BaseDAO
{
	/**
	 * Validate city/state/zip
	 * 
	 * @param address Physical address
	 */
	public boolean getPoint(Address addr) throws AddressBookException;
}
