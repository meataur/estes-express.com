package com.estes.myestes.commoditylibrary.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.commoditylibrary.dto.Commodity;
import com.estes.myestes.commoditylibrary.exception.CommodityLibraryException;

/**
 * Address book maintenance service
 */
public interface CommodityLibraryService
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
	 * @param user
	 * @return List<String>
	 * @throws OnlineReportingException
	 */
	public List<String> getCommodityErrorCodes(String user) throws CommodityLibraryException;
	
	/**
	 * Get the list of commodities for MyEstes user
	 * @param user
	 * @return List<Commodity>
	 * @throws CommodityLibraryException
	 */
	public List<Commodity> getCommodities(String user) throws CommodityLibraryException;
	
	/**
	 * Add Commodity for My Estes user
	 * 
	 * @param user
	 * @param commodity
	 * @return  Indication of success
	 */
	public boolean addCommodity(Commodity commodity, String user) throws CommodityLibraryException;
	
	/**
	 * Update Commodity for My Estes user
	 * 
	 * @param user
	 * @param commodity
	 * @return  Indication of success
	 */
	public boolean updateCommodity(Commodity commodity, String user) throws CommodityLibraryException;

	/**
	 * Delete a commodity
	 * @param user
	 * @param commodityId
	 * @return
	 * @throws CommodityLibraryException
	 */
	public boolean deleteCommodity(String user, String commodityId) throws CommodityLibraryException;
	
	/**
	 * Get all service errors
	 * @param user
	 * @return Array of errors
	 */
	public ServiceResponse[] getErrors(String user) throws CommodityLibraryException;


	
}
