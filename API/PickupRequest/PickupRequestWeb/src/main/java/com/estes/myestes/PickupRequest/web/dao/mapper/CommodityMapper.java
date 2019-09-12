package com.estes.myestes.PickupRequest.web.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.PickupRequest.web.dto.Commodity;

public class CommodityMapper  implements RowMapper<Commodity> {

	@Override
	public Commodity mapRow(ResultSet rs, int rowNum) throws SQLException {
		String destinationZipCode = rs.getString("DESTINATIONZIPCODE");
		String destinationZipCodeExt = rs.getString("DESTINATIONZIPCODEEXT");
		int totalPieces = rs.getInt("TOTALPIECES");
		int totalWeight = rs.getInt("TOTALWEIGHT");
		int totalSkids = rs.getInt("TOTALSKIDS");
		String hazmat = rs.getString("HAZMAT");
		String specialInstructions = rs.getString("SPECIALINSTRUCTIONS");
		String referenceNumber = rs.getString("REFERENCENUMBER");
		
		Commodity commodity = new Commodity();
		
		commodity.setDestinationZipCode(destinationZipCode);
		commodity.setDestinationZipCodeExt(destinationZipCodeExt);
		commodity.setTotalPieces(totalPieces);
		commodity.setTotalWeight(totalWeight);
		commodity.setTotalSkids(totalSkids);
		commodity.setHazmat("Y".equalsIgnoreCase(hazmat));
		commodity.setSpecialInstructions(specialInstructions);
		commodity.setReferenceNumber(referenceNumber);
		return commodity;
	}
}