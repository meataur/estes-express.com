/**
 * @author: Todd Allen
 *
 * Creation date: 12/14/2017
 *
 */

package com.estesexpress.currency.convert.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estesexpress.currency.convert.dao.iface.RatesDAO;
import com.estesexpress.currency.convert.dto.RateDTO;
import com.estesexpress.currency.convert.service.exception.CurrencyConversionException;
import com.estesexpress.currency.convert.service.iface.RateService;

/**
 * Currency rate retrieval service implementation 
 */
@Service("rateService")
@Scope("prototype")
public class RateServiceImpl implements RateService
{
	@Autowired
	private RatesDAO ratesDAO;

	/**
	 * Create a new service
	 */
	public RateServiceImpl()
	{
		super();
	} // Constructor

	/**
	 * (non-Javadoc)
	 * @see com.estesexpress.currency.convert.service.iface.RateService#getRates()
	 */
	public RateDTO[] getRates(String country) throws CurrencyConversionException
	{
		return ratesDAO.getRates(country);
	} // getRates
}
