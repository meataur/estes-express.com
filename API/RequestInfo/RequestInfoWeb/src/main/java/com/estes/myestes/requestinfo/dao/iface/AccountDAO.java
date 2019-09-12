package com.estes.myestes.requestinfo.dao.iface;

import java.util.List;

import com.estes.myestes.requestinfo.exception.RequestInfoException;

public interface AccountDAO extends BaseDAO
{
	/**
	 * Get whether account is payor of freight
	 * 
	 * @param  account code
	 * @param  account type
	 * @param  ot
	 * @param  pro
	 * @return  boolean if payor
	 */
	public boolean isPayorOfFreight(String accountCode, String accountType, Integer OT, Integer PRO) throws RequestInfoException;

	/**
	 * Get whether account is party to shipment
	 * 
	 * @param  account code
	 * @param  account type
	 * @param  ot
	 * @param  pro
	 * @return  boolean if party
	 */
	public boolean isPartyToShipment(String accountCode, String accountType, Integer OT, Integer PRO) throws RequestInfoException;

}