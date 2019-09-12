package com.estes.services.common.service.iface;

public interface ValidationService {

	boolean validateProNumber(String proNumber);

	boolean validateCityStateZip(String city, String state, String zip);
}
