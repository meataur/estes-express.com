package com.estes.myestes.PickupRequest.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.PickupRequest.web.dto.Pickup;

public class PickupMapper implements RowMapper<Pickup>{

	@Override
	public Pickup mapRow(ResultSet resultSet, int rowNum) throws SQLException {
		
		Pickup pickup = new Pickup();
		
		String requestNumber  = resultSet.getString("REQUESTNUMBER");
		String proNumber      = resultSet.getString("PRONUMBER");
		String shipperCompany = resultSet.getString("SHIPPERCOMPANY");
		String shipperCity    = resultSet.getString("SHIPPERCITY");
		String shipperState   = resultSet.getString("SHIPPERSTATE");
		Date submittedDate    = resultSet.getDate("SUBMITTEDDATE");
		Date pickupDate       = resultSet.getDate("PICKUPDATE");
		String status         = resultSet.getString("STATUS");
		int totalPieces       = resultSet.getInt("TOTALPIECES");
		int totalWeight       = resultSet.getInt("TOTALWEIGHT");
		String terminalName   = resultSet.getString("TERMINALNAME");
		String terminalPhone  = resultSet.getString("TERMINALPHONE");
		String terminalFax    = resultSet.getString("TERMINALFAX");
		
		if(terminalName!=null){
			terminalName = terminalName.trim();
		}
		pickup.setRequestNumber(requestNumber);
		pickup.setProNumber(proNumber);
		pickup.setShipperCompany(shipperCompany);
		pickup.setShipperCity(shipperCity);
		pickup.setShipperState(shipperState);

		pickup.setPickupDate(pickupDate);
		
		pickup.setTotalPieces(totalPieces);
		pickup.setTotalWeight(totalWeight);
		pickup.setTerminalName(terminalName);
		pickup.setTerminalPhone(terminalPhone);
		pickup.setTerminalFax(terminalFax);

		pickup.setSubmittedDate(submittedDate);
		pickup.setStatus(status);
		return pickup;
	}
	
}