package com.estes.myestes.shipmentmanifest.util;

import java.sql.Types;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;

import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;

public class CallUTQ102 {
	
	final public static String EXTERNAL_SCHEMA_NAME = "FBPGMS";
	final public static String EXTERNAL_PROCEDURE_NAME = "UTQ102";
	
	final public static String DELAY = "delay"; //DECIMAL(5 , 0),
	final public static String LIB  = "lib";//CHAR(10),
	final public static String IOT = "iot";//CHAR(3),
	final public static String IPRO = "ipro"; //CHAR(7),
	final public static String IMOVE = "imove"; //CHAR(3),
	final public static String IERROR = "ierror"; //CHAR(7),
	final public static String BILLTO = "billto"; //CHAR(7),
	final public static String SHIPPER = "shipper"; //CHAR(7),
	final public static String CONSIGNEE = "consignee"; //CHAR(7),
	final public static String CUST = "cust"; //CHAR(7),
	final public static String PAYOR = "payor"; //CHAR(1),
	final public static String TERMS = "terms"; //CHAR(1),
	final public static String CHARGES = "charges"; //CHAR(12),
	final public static String DELDATE = "deldate"; //CHAR(10),
	final public static String DELTIME = "deltime"; //CHAR(8),
	final public static String POD = "pod"; //CHAR(20),
	final public static String PICKDATE = "pickdate"; //CHAR(8),
	final public static String PICKTIME = "picktime"; //CHAR(4),
	final public static String WEIGHT = "weight"; //CHAR(8),
	final public static String PIECES = "pieces"; //CHAR(6),
	final public static String SNAME = "sname"; //CHAR(30),
	final public static String SADR1 = "sadr1"; //CHAR(30),
	final public static String SADR2 = "sadr2"; //CHAR(30),
	final public static String SCITY = "scity"; //CHAR(25),	
	final public static String SST = "sst"; //CHAR(2),
	final public static String SZIP = "szip"; //CHAR(10),
	final public static String SCNTY = "scnty"; //CHAR(2),
	final public static String SREF = "sref"; //CHAR(35),
	final public static String CNAME = "cname"; //CHAR(30),
	final public static String CADR1 = "cadr1"; //CHAR(30),
	final public static String CADR2 = "cadr2"; //CHAR(30),
	final public static String CCITY = "ccity"; //CHAR(25),
	final public static String CST = "cst"; //CHAR(2),
	final public static String CZIP = "czip"; //CHAR(10),
	final public static String CCNTY = "ccnty"; //CHAR(2),
	final public static String CREF = "cref"; //CHAR(35),
	final public static String SRVTYP = "srvtyp"; //CHAR(2),
	final public static String SDELDATE = "sdeldate"; //CHAR(8),
	final public static String SDELTIME = "sdeltime"; //CHAR(4),
	final public static String DIMWEIGHT = "dimweight"; //CHAR(8)
	
	protected SimpleJdbcCall sproc;
	protected Map<String, Object> inParams;
	protected Map<String, Object> outParms;
	protected ManifestRecordDTO manifest;
	
	public CallUTQ102(JdbcTemplate jdbcMyEstes) {
		sproc = new SimpleJdbcCall(jdbcMyEstes);
		inParams = new HashMap<String, Object>();
		
		sproc.withSchemaName(EXTERNAL_SCHEMA_NAME);
		sproc.withProcedureName(EXTERNAL_PROCEDURE_NAME);
		sproc.addDeclaredParameter(new SqlParameter(DELAY, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(LIB, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(IOT, Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter(IPRO, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(IMOVE, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(IERROR, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(BILLTO, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SHIPPER, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CONSIGNEE, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CUST, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(PAYOR, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(TERMS, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CHARGES, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(DELDATE, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(DELTIME, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(POD, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(PICKDATE, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(PICKTIME, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(WEIGHT, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(PIECES, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SNAME, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SADR1, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SADR2, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SCITY, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SST, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SZIP, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SCNTY, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SREF, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CNAME, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CADR1, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CADR2, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CCITY, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CST, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CZIP, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CCNTY, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(CREF, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SRVTYP, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SDELDATE, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(SDELTIME, Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter(DIMWEIGHT, Types.CHAR));
		
	}
	
	// SETTERS /////////////////////////////////////////////////////////////////
	public void setDelay(int value) {
		inParams.put(DELAY, value);
	}
	
	public void setLib(String value) {
		inParams.put(LIB, value);
	}
	
	public void setIot(String value) {
		inParams.put(IOT, value);
	}
	
	public void setIpro(String value) {
		inParams.put(IPRO, value);
	}
	
	//GETTERS //////////////////////////////////////////////////////////////////
	public String getImove() {
		return (String) outParms.get(IMOVE);
	}
	
	public String getIerror() {
		return (String) outParms.get(IERROR);
	}
	public String getBillTo() {
		return (String) outParms.get(BILLTO);
	}
	public String getShipper() {
		return (String) outParms.get(SHIPPER);
	}
	public String getConsignee() {
		return (String) outParms.get(CONSIGNEE);
	}
	public String getCust() {
		return (String) outParms.get(CUST);
	}
	public String getPayor() {
		return (String) outParms.get(PAYOR);
	}
	public String getTerms() {
		return (String) outParms.get(TERMS);
	}
	public String getCharges() {
		return (String) outParms.get(CHARGES);
	}
	public String getDelDate() {
		return (String) outParms.get(DELDATE);
	}
	public String getDelTime() {
		return (String) outParms.get(DELTIME);
	}
	public String getPod() {
		return (String) outParms.get(POD);
	}
	public String getPickDate() {
		return (String) outParms.get(PICKDATE);
	}
	public String getPickTime() {
		return (String) outParms.get(PICKTIME);
	}
	public String getWeight() {
		return (String) outParms.get(WEIGHT);
	}
	public String getPieces() {
		return (String) outParms.get(PIECES);
	}
	public String getSname() {
		return (String) outParms.get(SNAME);
	}
	public String getSadr1() {
		return (String) outParms.get(SADR1);
	}
	public String getSadr2() {
		return (String) outParms.get(SADR2);
	}
	public String getScity() {
		return (String) outParms.get(SCITY);
	}
	public String getSst() {
		return (String) outParms.get(SST);
	}
	public String getSzip() {
		return (String) outParms.get(SZIP);
	}
	public String getScnty() {
		return (String) outParms.get(SCNTY);
	}
	public String getSref() {
		return (String) outParms.get(SREF);
	}
	public String getCname() {
		return (String) outParms.get(CNAME);
	}
	public String getCadr1() {
		return (String) outParms.get(CADR1);
	}
	public String getCadr2() {
		return (String) outParms.get(CADR2);
	}
	public String getCcity() {
		return (String) outParms.get(CCITY);
	}
	public String getCst() {
		return (String) outParms.get(CST);
	}
	public String getCzip() {
		return (String) outParms.get(CZIP);
	}
	public String getCcnty() {
		return (String) outParms.get(CCNTY);
	}
	public String getCref() {
		return (String) outParms.get(CREF);
	}
	public String getSrvTyp() {
		return (String) outParms.get(SRVTYP);
	}
	public String getSDelDate() {
		return (String) outParms.get(SDELDATE);
	}
	public String getSDelTime() {
		return (String) outParms.get(SDELTIME);
	}
	public String getDimWeight() {
		return (String) outParms.get(DIMWEIGHT);
	}
	
	
	// EXECUTE PROGRAM /////////////////////////////////////////////////////////////
	public void execute() {
		outParms = sproc.execute(inParams);
	}


}