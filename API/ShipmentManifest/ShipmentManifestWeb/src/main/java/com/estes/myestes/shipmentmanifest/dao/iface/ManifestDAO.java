package com.estes.myestes.shipmentmanifest.dao.iface;

import java.util.List;

import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRequestDTO;
import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;

public interface ManifestDAO extends BaseDAO
{
	/**
	 * Get the data associated with the request
	 * 
	 * @param  request with information on what report to generate
	 * @param  account code of requester
	 * @param account type of requester
	 * @param hash of user logged in (requester)
	 * @param random number of user logged in (requester)
	 * @param whether requester is allowed to see charges
	 * @return  table with all of the information
	 */
	public List<ManifestRecordDTO> gatherData(Pageable pageable, ManifestRequestDTO request, String accountCode, String accountType, String hash, String session) throws ShipmentManifestException;
	
	/**
	 * Get the total number of records associated with the request
	 * 
	 * @param  request with information on what report to generate
	 * @param  account code of requester
	 * @param account type of requester
	 * @param hash of user logged in (requester)
	 * @param random number of user logged in (requester)
	 * @param whether requester is allowed to see charges
	 * @return  table with the subset of the information
	 */
	public int getTotalNumberRecords(ManifestRequestDTO request, String accountCode, String accountType, String hash, String session) throws ShipmentManifestException;

	/**
	 * Get whether the passed account is in group
	 * 
	 * @param the account requesting access to
	 * @param the account type of account (should be group)
	 * @param the account number of group account
	 * @return boolean of if accountRequest is in accountNumber group
	 */
	public boolean accountBelongsToGroup(String accountRequest, String accountType, String accountNumber);
}