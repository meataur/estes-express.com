package com.estes.myestes.shipmentmanifest.util;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.estes.dto.common.Address;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;

public class CallEDQ380 {
	
	final public static String EXTERNAL_SCHEMA_NAME = "FBPGMS";
	final public static String EXTERNAL_PROCEDURE_NAME = "EDQ380";
	
	final public static String DELAY         = "DELAY";         //6
	final public static String OTPRO         = "OTPRO";         //25
	final public static String OTPRO_E       = "OTPRO_E";       //7
	final public static String RANDOM_NUMBER = "RANDOM_NUMBER"; //7
	final public static String HASH_VALUE    = "HAS_VALUE";    //200
	
	protected SimpleJdbcCall sproc;
	protected Map<String, Object> inParams;
	protected ManifestRecordDTO manifest;
	
	public CallEDQ380(JdbcTemplate jdbcMyEstes) {
		sproc = new SimpleJdbcCall(jdbcMyEstes);
		Mapper mapper = new Mapper();
		inParams = new HashMap<String, Object>();
		
		sproc.withSchemaName(EXTERNAL_SCHEMA_NAME);
		sproc.withProcedureName(EXTERNAL_PROCEDURE_NAME);
		sproc.addDeclaredParameter(new SqlParameter(DELAY, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(OTPRO, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(OTPRO_E, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(RANDOM_NUMBER, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(HASH_VALUE, Types.CHAR));
		sproc.addDeclaredParameter(new SqlReturnResultSet("RESULT_SET", mapper));
	}
	
	// SETTERS /////////////////////////////////////////////////////////////////
	public void setDelay(String value) {
		inParams.put(DELAY, value);
	}
	
	public void setOtPro(String value) {
		inParams.put(OTPRO, value);
	}
	
	public void setOtProE(String value) {
		inParams.put(OTPRO_E, value);
	}
	
	public void setRandomNumber(String value) {
		inParams.put(RANDOM_NUMBER, value);
	}
	
	public void setHashValue(String value) {
		inParams.put(HASH_VALUE, value);
	}
	
	public void setManifest(ManifestRecordDTO manifest) {
		this.manifest = manifest;
	}
	
	// EXECUTE PROGRAM /////////////////////////////////////////////////////////////
	public ManifestRecordDTO execute() throws ShipmentManifestException {
		try {
			Map<String, Object> outParms = sproc.execute(inParams);

	        if (outParms != null) {
	    		List<EDQ380Details> accounts = (List<EDQ380Details>) outParms.get("RESULT_SET");
	    		if(accounts.size() == 1) {
	    			manifest.setStatus(accounts.get(0).getStatus());
	    			manifest.setReceivedBy(accounts.get(0).getReceivedBy());
	    			manifest.setFirstDeliveryAttempt(accounts.get(0).getFirstDeliveryAttempt());
	    			manifest.setDeliveryTime(accounts.get(0).getDeliveryTime());
	    			manifest.setAppointmentDate(accounts.get(0).getAppointmentDate());
	    			manifest.setAppointmentTime(accounts.get(0).getAppointmentTime());
	    			manifest.setShipper(accounts.get(0).getShipper());
	    			manifest.setShipperAddress(accounts.get(0).getShipperAddress());

	    			manifest.setConsignee(accounts.get(0).getConsignee());
	    			manifest.setConsigneeAddress(accounts.get(0).getConsigneeAddress());
	    			manifest.setDestinationTerminal(accounts.get(0).getDestinationTerminal());
	    			manifest.setPhoneNumber(accounts.get(0).getPhoneNumber());
	    			manifest.setFaxNumber(accounts.get(0).getFaxNumber());
	    			//set the delivery date to what comes back from EDR380 instead of what's in FRP001
	    			manifest.setDeliveryDate(accounts.get(0).getDeliveryDate());
	    		}
	    		return manifest;
			}
	        else {
	        	ESTESLogger.log(ESTESLogger.ERROR, CallEDQ380.class, "execute()", "An error occurred executing edq380");
				throw new ShipmentManifestException("Failed to extecute extra details query");
	        }
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, CallEDQ380.class, "execute()", "An error occurred executing edq380");
			throw new ShipmentManifestException("Failed to extecute extra details query");
		}
	}

	private class Mapper implements RowMapper<EDQ380Details> {

		@Override
		public EDQ380Details mapRow(ResultSet rs, int rowNum) throws SQLException {
			EDQ380Details details = new EDQ380Details();
			details.setStatus(rs.getString(54));
			details.setReceivedBy(rs.getString(11));
			details.setFirstDeliveryAttempt(rs.getString(53));
			details.setDeliveryTime(FormatUtils.formatTimeString((rs.getString(10)),ShipmentManifestConstant.APP_DISPLAY_TIME_DATABASE_FORMAT, ShipmentManifestConstant.APP_DISPLAY_TIME_UI_FORMAT));
			details.setAppointmentDate(rs.getString(35));
			details.setAppointmentTime(FormatUtils.formatTimeString((rs.getString(36)), ShipmentManifestConstant.APP_DISPLAY_TIME_DATABASE_FORMAT, ShipmentManifestConstant.APP_DISPLAY_TIME_UI_FORMAT));
			details.setShipper(rs.getString(16));
			Address shippersAddress = new Address();
			shippersAddress.setStreetAddress(rs.getString(17));
			shippersAddress.setStreetAddress2(rs.getString(18));
			shippersAddress.setCity(rs.getString(19));
			shippersAddress.setState(rs.getString(20));
			shippersAddress.setZip(rs.getString(21));
			details.setShipperAddress(shippersAddress);
			details.setConsignee(rs.getString(25));
			Address consigneesAddress = new Address();
			consigneesAddress.setStreetAddress(rs.getString(26));
			consigneesAddress.setStreetAddress2(rs.getString(27));
			consigneesAddress.setCity(rs.getString(28));
			consigneesAddress.setState(rs.getString(29));
			consigneesAddress.setZip(rs.getString(30));
			details.setConsigneeAddress(consigneesAddress);
			details.setDestinationTerminal(rs.getString(38));
			details.setPhoneNumber(rs.getString(45));
			details.setFaxNumber(rs.getString(46));
			details.setEstimatedDeliveryDate(rs.getString(55));
			details.setDeliveryDate(rs.getString(9));
			return details;
		}
		
	}

	private class EDQ380Details {
		String status;
		String receivedBy;
		String firstDeliveryAttempt;
		String deliveryTime;
		String appointmentDate;
		String appointmentTime;
		String shipper;
		Address shipperAddress;
		String consignee;
		Address consigneeAddress;
		String destinationTerminal;
		String phoneNumber;
		String faxNumber;
		String estimatedDeliveryDate;
		String deliveryDate;
		
		public String getStatus() {
			return status;
		}
		public void setStatus(String status) {
			this.status = status;
		}
		public String getReceivedBy() {
			return receivedBy;
		}
		public void setReceivedBy(String receivedBy) {
			this.receivedBy = receivedBy;
		}
		public String getFirstDeliveryAttempt() {
			return firstDeliveryAttempt;
		}
		public void setFirstDeliveryAttempt(String firstDeliveryAttempt) {
			this.firstDeliveryAttempt = firstDeliveryAttempt;
		}
		public String getDeliveryTime() {
			return deliveryTime;
		}
		public void setDeliveryTime(String deliveryTime) {
			this.deliveryTime = deliveryTime;
		}
		public String getAppointmentDate() {
			return appointmentDate;
		}
		public void setAppointmentDate(String appointmentDate) {
			this.appointmentDate = appointmentDate;
		}
		public String getAppointmentTime() {
			return appointmentTime;
		}
		public void setAppointmentTime(String appointmentTime) {
			this.appointmentTime = appointmentTime;
		}
		public String getShipper() {
			return shipper;
		}
		public void setShipper(String shipper) {
			this.shipper = shipper;
		}
		public Address getShipperAddress() {
			return shipperAddress;
		}
		public void setShipperAddress(Address shipperAddress) {
			this.shipperAddress = shipperAddress;
		}
		public String getConsignee() {
			return consignee;
		}
		public void setConsignee(String consignee) {
			this.consignee = consignee;
		}
		public Address getConsigneeAddress() {
			return consigneeAddress;
		}
		public void setConsigneeAddress(Address consigneeAddress) {
			this.consigneeAddress = consigneeAddress;
		}
		public String getDestinationTerminal() {
			return destinationTerminal;
		}
		public void setDestinationTerminal(String destinationTerminal) {
			this.destinationTerminal = destinationTerminal;
		}
		public String getPhoneNumber() {
			return phoneNumber;
		}
		public void setPhoneNumber(String phoneNumber) {
			this.phoneNumber = phoneNumber;
		}
		public String getFaxNumber() {
			return faxNumber;
		}
		public void setFaxNumber(String faxNumber) {
			this.faxNumber = faxNumber;
		}
		public String getEstimatedDeliveryDate() {
			return estimatedDeliveryDate;
		}
		public void setEstimatedDeliveryDate(String estimatedDeliveryDate) {
			this.estimatedDeliveryDate = estimatedDeliveryDate;
		}
		public String getDeliveryDate() {
			return deliveryDate;
		}
		public void setDeliveryDate(String deliveryDate) {
			this.deliveryDate = deliveryDate;
		}
	}
}
