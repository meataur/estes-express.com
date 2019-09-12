package com.estes.services.common.dao.iface;

public interface ValidationDAO {
	boolean validateProNumber(String proNumber);
	boolean validateCityStateZip(String city, String state, String zip);
}
