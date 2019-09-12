/**
 * 
 */

package com.estes.myestes.transittimecalculator.dao.impl;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.Address;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.transittimecalculator.dao.iface.TerminalDAO;
import com.estes.myestes.transittimecalculator.dto.DestinationPoint;
import com.estes.myestes.transittimecalculator.dto.DestinationTerminal;
import com.estes.myestes.transittimecalculator.dto.Terminal;
import com.estes.myestes.transittimecalculator.dto.TransitTime;
import com.estes.myestes.transittimecalculator.dto.TransitTimeResponse;
import com.estes.myestes.transittimecalculator.exception.TransitTimeCalculatorException;
import com.estes.myestes.transittimecalculator.utils.TransitTimeCalculatorConstants;

@Repository("terminalDAO")
public class TerminalDAOImpl implements TerminalDAO {

	@Autowired
	private JdbcTemplate jdbcTemplateTransitTimeCalculator;

	/**
	 * 
	 */
	
	@Override
	public TransitTimeResponse calculateTransitTime(TransitTime transitTime)
			throws TransitTimeCalculatorException {
		List<DestinationTerminal> destinationTerminals = new ArrayList<>();	
		TransitTimeResponse transitTimeResponse= new TransitTimeResponse();
		SimpleDateFormat formatter = new SimpleDateFormat("MM/DD/YYYY");
		Terminal originTerminal = new Terminal();
		String message =  null;
		for (DestinationPoint destinationPoint : transitTime.getDestinationPoints()) {
			Date shipmentDate = null;
			try {
				if(destinationPoint.getShipmentDate()!=null && StringUtils.isNotEmpty(destinationPoint.getShipmentDate()))
				{
				shipmentDate = formatter.parse(destinationPoint.getShipmentDate());
				}
			} catch (ParseException exception) {
				ESTESLogger.log(ESTESLogger.ERROR, TerminalDAOImpl.class, "calculateTransitTime()",
						"** Error occurred while formating shipmentDate.");
				throw new TransitTimeCalculatorException("Error occurred while formating shipmentDate.", exception);
			}			
			if(transitTime.getOriginpoint().getCountry().equals("MX")) {
				transitTime.getOriginpoint().setCity(formatMexicanCity(transitTime.getOriginpoint().getCity(), transitTime.getOriginpoint().getState()));
				transitTime.getOriginpoint().setState(formatMexicanState(transitTime.getOriginpoint().getState()));
			}			
			if(destinationPoint.getPoint().getCountry().equals("MX")) {
				destinationPoint.getPoint().setCity(formatMexicanCity(destinationPoint.getPoint().getCity(), destinationPoint.getPoint().getState()));
				destinationPoint.getPoint().setState(formatMexicanState(destinationPoint.getPoint().getState()));
			}				
			Map<String, Object> inParamMap = new HashMap<String, Object>();
			
			inParamMap.put(TransitTimeCalculatorConstants.OZIP, transitTime.getOriginpoint().getZip());
			inParamMap.put(TransitTimeCalculatorConstants.OCITY, transitTime.getOriginpoint().getCity());
			inParamMap.put(TransitTimeCalculatorConstants.OSTATE, transitTime.getOriginpoint().getState());
			inParamMap.put(TransitTimeCalculatorConstants.OCOUNTRY, transitTime.getOriginpoint().getCountry());
			
			inParamMap.put(TransitTimeCalculatorConstants.DZIP, destinationPoint.getPoint().getZip());
			inParamMap.put(TransitTimeCalculatorConstants.DCITY, destinationPoint.getPoint().getCity());
			inParamMap.put(TransitTimeCalculatorConstants.DSTATE, destinationPoint.getPoint().getState());
			inParamMap.put(TransitTimeCalculatorConstants.DCOUNTRY, destinationPoint.getPoint().getCountry());
			
			inParamMap.put(TransitTimeCalculatorConstants.ERROR, "");
			inParamMap.put(TransitTimeCalculatorConstants.OZIP_E, "");
			inParamMap.put(TransitTimeCalculatorConstants.DZIP_E, "");
			inParamMap.put(TransitTimeCalculatorConstants.SHIPDATE_E, "");
			inParamMap.put(TransitTimeCalculatorConstants.STD_E, "");
			if(destinationPoint.getShipmentDate()!=null && StringUtils.isNotEmpty(destinationPoint.getShipmentDate())) {
				inParamMap.put(TransitTimeCalculatorConstants.SHIPDATE, shipmentDate);
			}
			else {
				inParamMap.put(TransitTimeCalculatorConstants.SHIPDATE, "");
			}		
			SqlParameterSource inParams = new MapSqlParameterSource(inParamMap);
			SimpleJdbcCall transiTimeProcedure = new SimpleJdbcCall(jdbcTemplateTransitTimeCalculator.getDataSource())
					.withSchemaName("FBPGMS").withProcedureName("SDQ017MS");
			Map result = transiTimeProcedure.execute(inParams);
			List<Map<String, Object>> orginAndDestinationResultMapList = (List<Map<String, Object>>) result
					.get(("#result-set-1"));
			Map<String, Object> orginAndDestinationResultMap = orginAndDestinationResultMapList.get(0);
			Address originAddress = new Address();
			originAddress.setCity((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTCITY));
			originAddress.setStreetAddress((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTADDRESS1));
			originAddress.setStreetAddress2((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTADDRESS2));
			originAddress.setCountry((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTCOUNTRY));
			originAddress.setState((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTSTATE));
			originAddress.setZip((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTZIP));
			BigDecimal phoneEac = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTPHONEAC);
			BigDecimal phoneNumber = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTPHONENUM);
			String fullPhoneNumber = phoneEac.toString() + phoneNumber.toString();
			BigDecimal faxEac = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTFAXAC);
			BigDecimal faxNumber = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTFAXNUM);
			String fullFaxNumber = faxEac.toString() + faxNumber.toString();
			originTerminal.setId((String)orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OT));
			originTerminal.setName((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.OTNAME));
			originTerminal.setFax(fullFaxNumber);
			originTerminal.setAddress(originAddress);
			originTerminal.setPhone(fullPhoneNumber);

			Terminal destinationTerminal = new Terminal();

			Address destinationAddress = new Address();
			destinationAddress.setCity((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTCITY));
			destinationAddress.setStreetAddress((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTADDRESS1));
			destinationAddress.setStreetAddress2((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTADDRESS2));
			destinationAddress.setCountry((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTCOUNTRY));
			destinationAddress.setState((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTSTATE));
			destinationAddress.setZip((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTZIP));
			BigDecimal destinationphoneEac = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTPHONEAC);
			BigDecimal destinationphoneNumber = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTPHONENUM);
			String destinationfullPhoneNumber = destinationphoneEac.toString() + destinationphoneNumber.toString();
			BigDecimal destinationfaxEac = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTFAXAC);
			BigDecimal destinationfaxNumber = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTFAXNUM);
			BigDecimal serviceDays = (BigDecimal) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DAYS);
			String destinationfullFaxNumber = destinationfaxEac.toString() + destinationfaxNumber.toString();
			destinationTerminal.setId((String)orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DT));
			destinationTerminal.setName((String) orginAndDestinationResultMap.get(TransitTimeCalculatorConstants.DTNAME));
			destinationTerminal.setPhone(destinationfullPhoneNumber);
			destinationTerminal.setFax(destinationfullFaxNumber);
			destinationTerminal.setAddress(destinationAddress);				
			message = (String)orginAndDestinationResultMap.get("MESSAGE");
			if(null == message || (null != message && message.trim().equals(""))) {
				if(!originTerminal.getAddress().getCountry().equalsIgnoreCase(destinationTerminal.getAddress().getCountry())) {
					message = "Note:  Transit times may be impacted by events at the border beyond Estes' control.";		
				}
			}
			DestinationTerminal destination = new DestinationTerminal();
			destination.setDestinationTerminal(destinationTerminal);
			destination.setServiceDays(serviceDays.toString());
			destination.setMessage(message);
			destinationTerminals.add(destination);
		}
		transitTimeResponse.setOriginTerminal(originTerminal);
		transitTimeResponse.setDestinationTerminals(destinationTerminals);
		return transitTimeResponse;
	}
	private static String formatMexicanCity(String city, String state) {
		if(state.length() > 2) {
			StringBuffer sb = new StringBuffer(city);
			int cityLength = sb.toString().length();
			
			for(int x = cityLength; x < 17 - state.length() + 2; x ++)
				sb.append(" ");
			
			return sb.append(state.substring(0, state.length() - 2)).toString();
		}
		
		return city;
	}
	
	private static String formatMexicanState(String state) {
		return state.length() > 2? state.substring(state.length() - 2): state;
	}
}
