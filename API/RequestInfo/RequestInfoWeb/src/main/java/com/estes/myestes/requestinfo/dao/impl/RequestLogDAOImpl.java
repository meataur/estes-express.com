package com.estes.myestes.requestinfo.dao.impl;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.myestes.requestinfo.dao.iface.BusinessTypeDAO;
import com.estes.myestes.requestinfo.dao.iface.RequestLogDAO;
import com.estes.myestes.requestinfo.dto.InfoRequestDTO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;
import com.estes.myestes.requestinfo.util.BusinessType;

@Repository ("requestLogDAO")
public class RequestLogDAOImpl implements RequestLogDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;

	@Autowired
	private BusinessTypeDAO businessTypeDAO;
	
	@Override
	public void writeLog(String username, InfoRequestDTO request) throws RequestInfoException {
		String sql = "INSERT INTO " + ALT_PGM_SCHEMA + ".QNP00P100 VALUES " + 
			"(DEFAULT, DEFAULT, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";

		try {
			String ot = "";
			int otNum = 0;
			String pro = "";
			int proNum = 0;
			if(request.getProNumber() != null && !request.getProNumber().trim().equals("")) {
				ot = request.getProNumber().split("-")[0];
				otNum = Integer.parseInt(ot);
				pro = request.getProNumber().split("-")[1];
				proNum = Integer.parseInt(pro);
			}
			String businessType = null;
			if(businessTypeDAO.isL2LShipment(ot)) { 
				businessType = BusinessType.L2L.getDescription(); 
			} 
			else if(businessTypeDAO.isEFWShipment(ot)) { 
				businessType = BusinessType.EFW.getDescription(); 
			} 
			else { 
				businessType = BusinessType.LTL.getDescription(); 
			}
			Object[] values = {
				request.getName(),
				request.getPhoneNumber(),
				request.getPhoneNumberExt(),
				request.getFaxNumber(),
				request.getEmailAddress(),
				username,
				otNum,
				proNum,
				request.getProblem().getText(),
				request.isBOLSelected()?"Y":"N",
				request.isDRSelected()?"Y":"N",
				request.isWRSelected()?"Y":"N",
				request.getDescription(),
				businessType
			};
			
			jdbcMyEstes.update(sql,  values);
		}
		catch (Exception e) {
			throw new RequestInfoException("Error writing log for info request");
		}
	}
}