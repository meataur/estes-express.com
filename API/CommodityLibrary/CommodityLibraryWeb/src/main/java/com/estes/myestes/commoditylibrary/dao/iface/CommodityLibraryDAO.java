/**
 * @author: Lakshman K
 *
 * Creation date: 11/9/2018
 */

package com.estes.myestes.commoditylibrary.dao.iface;

import java.util.List;

import com.estes.myestes.commoditylibrary.dto.Commodity;
import com.estes.myestes.commoditylibrary.exception.CommodityLibraryException;

public interface CommodityLibraryDAO
{
	/**
	 * Get the Commodity for My Estes user by Id and user
	 * 
	 * @param commodityId, user
	 * @return Commodity
	 * @throws OnlineReportingException
	 */
	public Commodity getCommodityById(String commodityId, String user) throws CommodityLibraryException;
	
	
	/**
	 * Get the Commodity by user and timestamp
	 * 
	 * @param user, timestamp
	 * @return List<String>
	 * @throws CommodityLibraryException
	 */
	public List<String> getCommodityErrorCodes(String user, String timestamp) throws CommodityLibraryException;
	
	/**
	 * Get the list of commodities for MyEstes user
	 * @param user
	 * @return List<Commodity>
	 * @throws CommodityLibraryException
	 */
	public List<Commodity> getCommodities(String user) throws CommodityLibraryException;
	
	/**
	 * Save Commodity for My Estes user
	 * 
	 * @param user, timestamp
	 * @param commodity
	 * @return  Indication of success
	 */
	public boolean saveCommodity(Commodity commodity, String user, String timestamp) throws CommodityLibraryException;

	/**
	 * Delete a commodity
	 * @param user
	 * @param commodityId
	 * @return
	 * @throws CommodityLibraryException
	 */
	public boolean deleteCommodity(String user, String commodityId) throws CommodityLibraryException;

	/**
	 * Add the commodity details to the temporary work table
	 * @param commodity, user, timestamp
	 * @return
	 * @throws CommodityLibraryException
	 */
//	public boolean addTempCommodity(Commodity commodity, String user, String timestamp) throws CommodityLibraryException;
	
}
