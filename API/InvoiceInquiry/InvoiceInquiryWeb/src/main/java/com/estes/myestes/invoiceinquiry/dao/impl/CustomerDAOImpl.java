/**
 * @author: Todd Allen
 *
 * Creation date: 01/29/2019
 */

package com.estes.myestes.invoiceinquiry.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.CustomerDAO;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

@Repository ("customerDAO")
public class CustomerDAOImpl implements CustomerDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public CustomerDAOImpl()
	{
	} // Constructor

	@Override
	public boolean hasSpecialInstructions(String acct) throws InvoiceException
	{
		String sql = "SELECT COUNT(cmsss) " +
				"FROM " + DATA_SCHEMA + ".rap001 " + 
				"WHERE cmcust = ? AND cmsss <> ''";

		try {
			return jdbcMyEstes.queryForObject(sql, Integer.class, new Object[] { new String(acct) }) > 0;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomerDAOImpl.class, "hasSpecialInstructions()",
					"** An error occurred checking invoice special instructions.", e);
			throw new InvoiceException("Error checking invoice special instructions.");
		}
	} // hasSpecialInstructions
}
