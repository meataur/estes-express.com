package com.estes.myestes.shipmentmanifest.dao.mapper;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;

public class ManifestRowMapper implements RowMapper {
	public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
		ManifestRecordDTO manifest = new ManifestRecordDTO();
		manifest.setProNumber(rs.getString("PRONUM"));
		manifest.setBol(rs.getString("FHBL#"));
		manifest.setPurchaseOrder(rs.getString("FHPO"));
		manifest.setPickupDate(rs.getString("FHPUD8"));
		manifest.setDeliveryDate(rs.getString("FHDDA8"));
		manifest.setOrigin(rs.getString("FHSNM"));
		manifest.setDestination(rs.getString("FHCNM"));
		manifest.setPieces(rs.getString("FHTOTP"));
		manifest.setWeight(rs.getString("FHSWGT"));
		manifest.setCharges(rs.getString("FHPROD"));
		manifest.setOriginTerminalId(rs.getString("FHOT"));
		manifest.setPro(rs.getString("FHPRO"));
		manifest.setBillToCode(rs.getString("FHBTC"));
		manifest.setProNumNoDash(rs.getString("PRONUMNODASH"));
		return manifest;
	}
	
}