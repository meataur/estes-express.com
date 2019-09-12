/**
 * @author: Todd Allen
 *
 * Creation date: 02/19/2019
 */

package com.estes.myestes.rating.dao.impl;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.rating.dao.iface.ChargesDAO;
import com.estes.myestes.rating.dto.Charge;

@Repository ("chargeDAO")
public class ChargesDAOImpl implements ChargesDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	/**
	 * Constructor
	 */
	public ChargesDAOImpl()
	{
	} // Constructor

	@Override
	public List<Charge> getQuoteCharges(String id)
	{
		String sql = "SELECT * FROM " + DATA_SCHEMA + ".gsc00p145 " +
				"WHERE gsdid = ? " +
				"ORDER BY gsdseq";

		try {
			List<Map<String,Object>> dataList = jdbcMyEstes.queryForList(sql, new Object[] { id });
			ArrayList<Charge> charges = new ArrayList<Charge>();
			for (Map<String,Object> data : dataList) {
				Charge chg = new Charge();
				chg.setShow(((String) data.get("gsdshw")).equals("Y"));
				chg.setAmount((String) data.get("gsdchg"));
				chg.setServiceLevel(((BigDecimal) data.get("gsdsvc")).intValue());
				chg.setDescription((String) data.get("gsdtxt"));
				/**
				 * TODO Remove the following hard coded text replacement when the SPROC is updated.
				 */
				if(chg.getDescription()!=null && chg.getDescription().contains("Extra liability coverage")){
					chg.setDescription(chg.getDescription().replaceAll("Extra liability coverage", "Full Value Coverage (FVC)"));
					chg.setDescription(chg.getDescription()+" (Requested amount of FVC must be written on your BOL to obtain coverage.)");
				}
				
				charges.add(chg);
			}
			return charges;
		}
		catch (Exception e) {
			return new ArrayList<Charge>();
		}
	} // getQuoteCharges
}
