package com.estes.myestes.PickupRequest.web.dao.impl;

import static com.estes.myestes.PickupRequest.web.dao.sql.PickupRequestQuery.UPDATE_PICKUP_NOTIFICATION;
import static com.estes.myestes.PickupRequest.web.dao.sql.Schema.EDIHIST;
import static com.estes.myestes.PickupRequest.web.dao.sql.Schema.FBPGMS;

import java.sql.Types;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.SqlOutParameter;
import org.springframework.jdbc.core.SqlParameter;
import org.springframework.jdbc.core.SqlReturnResultSet;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.PickupRequest.config.AuthenticationDetails;
import com.estes.myestes.PickupRequest.exception.AppException;
import com.estes.myestes.PickupRequest.exception.PickupRequestErrorException;
import com.estes.myestes.PickupRequest.web.dao.iface.ErrorDAO;
import com.estes.myestes.PickupRequest.web.dao.iface.PickupRequestDAO;
import com.estes.myestes.PickupRequest.web.dao.mapper.PickupRequestErrorMapper;
import com.estes.myestes.PickupRequest.web.dto.PartyNotification;
import com.estes.myestes.PickupRequest.web.dto.PickupRequest;
import com.estes.myestes.PickupRequest.web.dto.PickupRequestError;
import com.estes.myestes.PickupRequest.web.dto.Role;
import com.estes.myestes.PickupRequest.web.dto.Shipment;
import com.estes.myestes.PickupRequest.web.dto.ShipmentService;

@Repository("pickupRequestDAO")
public class PickupRequestDAOImpl implements PickupRequestDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Autowired
	private ErrorDAO errorDAO;
	
	@Override
	public void savePickupRequest(PickupRequest pickupRequest){
		
		AuthenticationDetails auth = AuthenticationDetails.getAuthenticationDetails();
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);
		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("SP_CREATEWEBPICKUPREQUEST");
		sproc.addDeclaredParameter(new SqlParameter("USERNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USERPHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USERPHONEXT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USEREMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("USERROLE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPACCOUNT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPCONTACT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPADDR1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPADDR2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPCITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPSTATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPZIP4", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPPHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPPHONEXT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPEMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PICKUPDATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("STARTTIME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("STARTAMPM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ENDTIME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ENDAMPM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PICKUPDETAILS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSCONTACT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSNAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSADDR1", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSADDR2", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSCITY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSSTATE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSZIP", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSZIP4", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSPHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSPHONEXT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSFAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("CONSEMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TOTPIECES", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TOTWEIGHT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TOTSKIDS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("HAZMAT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SPECINSTR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REFNUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SHIPMENTDETAILS", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EXPEDITED", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("RECEIPT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ACCEPT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("REJECT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("BEGIN", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COMPLETE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("WEBUSER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("WEBACCOUNT", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("COUNTER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("BOLSEQNUM", Types.CHAR));
		sproc.addDeclaredParameter(new SqlInOutParameter("REQUEST", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERRORFLAG", Types.CHAR));
		
		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new PickupRequestErrorMapper()));
		
		
		LinkedHashMap<String, Object> params = new LinkedHashMap<String, Object>();
		
		if(pickupRequest.getUser()==null){
			throw new AppException("User Inforation is required!");
		}
		
		params.put("USERNAME", pickupRequest.getUser().getName());
		params.put("USERPHONE",pickupRequest.getUser().formatPhoneForDb());
		params.put("USERPHONEXT",pickupRequest.getUser().getPhoneExt());
		params.put("USEREMAIL",pickupRequest.getUser().getEmail());
		
		if(pickupRequest.getUser().getRole()!=null){
			params.put("USERROLE",pickupRequest.getUser().getRole().getPickupRole());
		}else{
			params.put("USERROLE","");
		}
		
		
		
		if(pickupRequest.getShipper()==null){
			throw new AppException("Shipper Inforation is required!");
		}
		
		
		/**
		 * If the User role is shipper & get
		 */
		if(Role.SHIPPER.equals(pickupRequest.getUser().getRole()) 
				&& "L".equalsIgnoreCase(auth.getAccountType())){
			pickupRequest.getShipper().setAccountCode(auth.getAccountCode());
		}
		params.put("SHIPACCOUNT","");
		if(Role.SHIPPER.equals(pickupRequest.getUser().getRole())){
			params.put("SHIPACCOUNT",pickupRequest.getShipper().getAccountCode());
		}
		params.put("SHIPCONTACT",pickupRequest.getShipper().getName());
		params.put("SHIPNAME",pickupRequest.getShipper().getCompany());
		params.put("SHIPADDR1",pickupRequest.getShipper().getAddressLine1());
		params.put("SHIPADDR2",pickupRequest.getShipper().getAddressLine2());
		params.put("SHIPCITY",pickupRequest.getShipper().getCity());
		params.put("SHIPSTATE",pickupRequest.getShipper().getState());
		params.put("SHIPZIP",pickupRequest.getShipper().getZip());
		params.put("SHIPZIP4",pickupRequest.getShipper().getZipExt());
		params.put("SHIPPHONE",pickupRequest.getShipper().formatPhoneForDb());
		params.put("SHIPPHONEXT",pickupRequest.getShipper().getPhoneExt());
		params.put("SHIPEMAIL",pickupRequest.getShipper().getEmail());
		
		
		if(pickupRequest.getPickupInfo()==null){
			throw new AppException("Pickup details are required!");
		}
		
		params.put("PICKUPDATE",pickupRequest.getPickupInfo().formatPickupDate());
		
		String startTime = pickupRequest.getPickupInfo().formatStartTime();
		
		params.put("STARTTIME",startTime.replaceAll("\\D+",""));
		
		params.put("STARTAMPM","");
		if(startTime.length()==8){
			params.put("STARTAMPM",startTime.substring(6));
		}
		
		String endTime = pickupRequest.getPickupInfo().formatEndTime();
		
		params.put("ENDTIME",endTime.replaceAll("\\D+",""));
		params.put("ENDAMPM","");
		if(endTime.length()==8){
			params.put("ENDAMPM",endTime.substring(6));
		}
		
		
		
		params.put("PICKUPDETAILS",pickupRequest.getPickupInfo().formatPickupInstructions());
		
		
		
		params.put("CONSCONTACT",pickupRequest.getConsignee().getName());
		params.put("CONSNAME",pickupRequest.getConsignee().getCompany());
		params.put("CONSADDR1",pickupRequest.getConsignee().getAddressLine1());
		params.put("CONSADDR2",pickupRequest.getConsignee().getAddressLine2());
		params.put("CONSCITY",pickupRequest.getConsignee().getCity());
		params.put("CONSSTATE",pickupRequest.getConsignee().getState());

		params.put("CONSPHONE",pickupRequest.getConsignee().formatPhoneForDb());
		params.put("CONSPHONEXT",pickupRequest.getConsignee().getPhoneExt());
		params.put("CONSFAX","");
		params.put("CONSEMAIL",pickupRequest.getConsignee().getEmail());

		params.put("WEBUSER",auth.getUsername());
		params.put("WEBACCOUNT",auth.getAccountCode());

		
		params.put("BOLSEQNUM", pickupRequest.getBolId());
		params.put("REQUEST","");
		params.put("ERRORFLAG","");
		
		if(pickupRequest.getShipmentInfo()!=null && ! pickupRequest.getShipmentInfo().isEmpty()){
			int index=0;
			for(Shipment shipment : pickupRequest.getShipmentInfo()){
				
				params.put("CONSZIP",shipment.getCommodity().getDestinationZipCode());
				params.put("CONSZIP4",shipment.getCommodity().getDestinationZipCodeExt());
				params.put("TOTPIECES",shipment.getCommodity().getTotalPieces());
				params.put("TOTWEIGHT",shipment.getCommodity().getTotalWeight());
				params.put("TOTSKIDS",shipment.getCommodity().getTotalSkids());
				params.put("HAZMAT",shipment.getCommodity().isHazmat()?"Y" : "N");
				params.put("SPECINSTR",shipment.getCommodity().getSpecialInstructions());
				params.put("REFNUMBER",shipment.getCommodity().getReferenceNumber());
				params.put("SHIPMENTDETAILS",shipment.getShipmentOption().formatOptions());
				
				
			    params.put("EXPEDITED", ShipmentService.getValue(shipment.getShipmentService()));
				
				
				params.put("RECEIPT","");
				params.put("ACCEPT",shipment.getNotification().isAccepted()? "Y" : "N");
				params.put("REJECT","Y");
				params.put("BEGIN","");
				params.put("COMPLETE",shipment.getNotification().isCompleted()? "Y": "N");
				params.put("COUNTER",index);
				
				for (Map.Entry<String, Object> entry : params.entrySet()) {
			       if(entry.getValue()==null){
			    	   entry.setValue("");
			       }
			    }
	
				Map<String, ?> outParms = sproc.execute(params);
				

				if (outParms != null) {
					
					String errorFlag = (String) outParms.get("ERRORFLAG");
					
					if ("Y".equalsIgnoreCase(errorFlag)) {
						if (outParms.get("DATA") != null) {
							@SuppressWarnings("unchecked")
							List<PickupRequestError> errors = (List<PickupRequestError>) outParms.get("DATA");
							if(errors.size()>0){
								throw new PickupRequestErrorException(errors);
							}
							
						}
						throw new AppException(errorDAO.getErrorMessage((String) outParms.get("ERROR")));
					}else{
						/**
						 * Add Audit Log
						 */
						try{
							addAuditRequestInformation(params);
						}catch(Exception ex){
							ESTESLogger.log(ESTESLogger.ERROR, getClass(), "addAuditRequestInformation()","Could not add audit for pickup request for the exception. "+ex.getMessage());
						}
						

						String requestNumber = ((String) outParms.get("REQUEST")).trim();
						shipment.setRequestNumber(requestNumber);
						
						/**
						 * Process Party Notification
						 */
						
						for(PartyNotification pickupNotification : pickupRequest.getPartyNotificationList()){
							
							try{
								if(pickupNotification.getRole().equals(Role.THIRD_PARTY) 
										|| pickupNotification.getRole().equals(Role.OTHER)){
									
									addPickupNotification(pickupNotification, requestNumber, pickupNotification.getRole().getPickupRole());
									
								}
								if(pickupNotification.getRole().equals(Role.SHIPPER) 
										|| pickupNotification.getRole().equals(Role.CONSIGNEE)){
									updatePickupNotification(requestNumber, pickupNotification.getRole().getPickupRole(), pickupNotification.getEmail());
								}
							}catch(Exception ex){
								ESTESLogger.log(ESTESLogger.ERROR, 
										getClass(),
										"addPickupNotification() or updatePickupNotification()",
										"Pickup is created successfully but could not update pickup notification for"
										+ " pickup Request number "+requestNumber +
										pickupNotification);
							}
						}
						
					}
				} else {
					throw new AppException("An unexpected error occurs");
				}
			}
			
		}
	}
	
	private void updatePickupNotification(String requestNumber,String type, String email) {
		jdbcTemplate.update(UPDATE_PICKUP_NOTIFICATION, new Object[]{requestNumber, type, email});
	}

	private void addPickupNotification(
			PartyNotification partyNotification,
			String requestNumber,
			String type){
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);
		sproc.withSchemaName(FBPGMS);
		sproc.withProcedureName("SP_MAINTAIN_PICKUPCONTACTINFO");
		
		sproc.addDeclaredParameter(new SqlParameter("REQUEST_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("SEQUENCE_NUMBER", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("ACTION", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("TYPE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NAME", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EMAIL", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("PHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("EXTENSION", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("FAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NPHONE", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NEXTENSION", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NFAX", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("NOTIFY", Types.CHAR));
		sproc.addDeclaredParameter(new SqlParameter("METHOD", Types.CHAR));
		sproc.addDeclaredParameter(new SqlOutParameter("ERROR", Types.CHAR));
		sproc.addDeclaredParameter(new SqlReturnResultSet("DATA", new PickupRequestErrorMapper()));
		sproc.withoutProcedureColumnMetaDataAccess();
		sproc.compile();
		Map<String, Object> params = new HashMap<>();
		
		params.put("REQUEST_NUMBER", requestNumber);
		
		params.put("SEQUENCE_NUMBER", "");
		
		params.put("ACTION", "A"); /*A for Add*/
		params.put("TYPE", type);
		
		params.put("NAME", partyNotification.getName());
		params.put("EMAIL", partyNotification.getEmail());
		params.put("PHONE", partyNotification.formatPhoneForDb());
		params.put("EXTENSION", "");
		params.put("FAX","");
		params.put("NPHONE","");
		params.put("NEXTENSION", "");
		params.put("NFAX", "");
		params.put("NOTIFY","Y");
		params.put("METHOD", "E"); /** E for Email**/
		params.put("ERROR", "");
		
		Map<String, Object> results = sproc.execute(params);
		
		
		if (results != null) {
			
			if(results.get("ERROR")!=null && ! "N".equalsIgnoreCase((String) results.get("ERROR")) && ! "0000000".equals((String)results.get("ERROR"))){
				
				if (results.get("DATA") != null) {
					@SuppressWarnings("unchecked")
					List<PickupRequestError> errors = (List<PickupRequestError>) results.get("DATA");
					
					if(errors.size() >0){
						throw new PickupRequestErrorException(errors);
					}
					 
				}
				throw new AppException(errorDAO.getErrorMessage((String) results.get("ERROR")));
			}
			
		} else {

			throw new AppException("An unexpected error occurs");
		}
		
		
		
	}
	
	private void addAuditRequestInformation(Map<String, Object> params){
		
		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);
		sproc.withSchemaName(EDIHIST);
		sproc.withProcedureName("PICKUP_add_WEBLOG");
		sproc.withoutProcedureColumnMetaDataAccess();
		sproc.compile();
	
		sproc.declareParameters(
				new SqlParameter("P_USERNAME", Types.CHAR), 
				new SqlParameter("P_USERPHONE", Types.CHAR), 
				new SqlParameter("P_USERPHONEXT", Types.CHAR), 
				new SqlParameter("P_USEREMAIL", Types.CHAR), 
				new SqlParameter("P_USERROLE", Types.CHAR), 
				new SqlParameter("P_SHIPACCT", Types.CHAR), 
				new SqlParameter("P_SHIPCONTACT", Types.CHAR), 
				new SqlParameter("P_SHIPNAME", Types.CHAR), 
				new SqlParameter("P_SHIPADDR1", Types.CHAR), 
				new SqlParameter("P_SHIPADDR2", Types.CHAR), 
				new SqlParameter("P_SHIPCITY", Types.CHAR), 
				new SqlParameter("P_SHIPSTATE", Types.CHAR), 
				new SqlParameter("P_SHIPZIP", Types.CHAR), 
				new SqlParameter("P_SHIPZIP4", Types.CHAR), 
				new SqlParameter("P_SHIPPHONE", Types.CHAR), 
				new SqlParameter("P_SHIPPHONEXT", Types.CHAR), 
				new SqlParameter("P_SHIPEMAIL", Types.CHAR), 
				new SqlParameter("P_PICKUPDATE", Types.CHAR), 
				new SqlParameter("P_STARTTIME", Types.CHAR), 
				new SqlParameter("P_STARTAMPM", Types.CHAR), 
				new SqlParameter("P_ENDTIME", Types.CHAR), 
				new SqlParameter("P_ENDAMPM", Types.CHAR), 
				new SqlParameter("P_PICKUPDETAILS", Types.CHAR), 
				new SqlParameter("P_CONSCONTACT", Types.CHAR), 
				new SqlParameter("P_CONSNAME", Types.CHAR), 
				new SqlParameter("P_CONSADDR1", Types.CHAR), 
				new SqlParameter("P_CONSADDR2", Types.CHAR), 
				new SqlParameter("P_CONSCITY", Types.CHAR), 
				new SqlParameter("P_CONSSTATE", Types.CHAR), 
				new SqlParameter("P_CONSZIP", Types.CHAR), 
				new SqlParameter("P_CONSZIP4", Types.CHAR), 
				new SqlParameter("P_CONSPHONE", Types.CHAR), 
				new SqlParameter("P_CONSPHONEXT", Types.CHAR), 
				new SqlParameter("P_CONSFAX", Types.CHAR), 
				new SqlParameter("P_CONSEMAIL", Types.CHAR), 
				new SqlParameter("P_TOTPIECES", Types.CHAR), 
				new SqlParameter("P_TOTWEIGHT", Types.CHAR), 
				new SqlParameter("P_TOTSKIDS", Types.CHAR), 
				new SqlParameter("P_HAZMAT", Types.CHAR), 
				new SqlParameter("P_SPECINST", Types.CHAR), 
				new SqlParameter("P_REFNUMBER", Types.CHAR), 
				new SqlParameter("P_SHIPDETAILS", Types.CHAR), 
				new SqlParameter("P_EXPEDITED", Types.CHAR), 
				new SqlParameter("P_WEBUSER", Types.CHAR), 
				new SqlParameter("P_WEBACCT", Types.CHAR), 
				new SqlParameter("P_COUNTER", Types.CHAR), 
				new SqlOutParameter("P_KEY" , Types.DECIMAL)
			);
		
		Map<String, Object> inputParams = new LinkedHashMap<>();
		
		inputParams.put("P_USERNAME",params.get("USERNAME"));
		inputParams.put("P_USERPHONE",params.get("USERPHONE"));
		inputParams.put("P_USERPHONEXT",params.get("USERPHONEXT"));
		inputParams.put("P_USEREMAIL",params.get("USEREMAIL"));
		inputParams.put("P_USERROLE",params.get("USERROLE"));
		inputParams.put("P_SHIPACCT",params.get("SHIPACCT"));
		inputParams.put("P_SHIPCONTACT",params.get("SHIPCONTACT"));
		inputParams.put("P_SHIPNAME",params.get("SHIPNAME"));
		inputParams.put("P_SHIPADDR1",params.get("SHIPADDR1"));
		inputParams.put("P_SHIPADDR2",params.get("SHIPADDR2"));
		inputParams.put("P_SHIPCITY",params.get("SHIPCITY"));
		inputParams.put("P_SHIPSTATE",params.get("SHIPSTATE"));
		inputParams.put("P_SHIPZIP",params.get("SHIPZIP"));
		inputParams.put("P_SHIPZIP4",params.get("SHIPZIP4"));
		inputParams.put("P_SHIPPHONE",params.get("SHIPPHONE"));
		inputParams.put("P_SHIPPHONEXT",params.get("SHIPPHONEXT"));
		inputParams.put("P_SHIPEMAIL",params.get("SHIPEMAIL"));
		inputParams.put("P_PICKUPDATE",params.get("PICKUPDATE"));
		inputParams.put("P_STARTTIME",params.get("STARTTIME"));
		inputParams.put("P_STARTAMPM",params.get("STARTAMPM"));
		inputParams.put("P_ENDTIME",params.get("ENDTIME"));
		inputParams.put("P_ENDAMPM",params.get("ENDAMPM"));
		inputParams.put("P_PICKUPDETAILS",params.get("PICKUPDETAILS"));
		inputParams.put("P_CONSCONTACT",params.get("CONSCONTACT"));
		inputParams.put("P_CONSNAME",params.get("CONSNAME"));
		inputParams.put("P_CONSADDR1",params.get("CONSADDR1"));
		inputParams.put("P_CONSADDR2",params.get("CONSADDR2"));
		inputParams.put("P_CONSCITY",params.get("CONSCITY"));
		inputParams.put("P_CONSSTATE",params.get("CONSSTATE"));
		inputParams.put("P_CONSZIP",params.get("CONSZIP"));
		inputParams.put("P_CONSZIP4",params.get("CONSZIP4"));
		inputParams.put("P_CONSPHONE",params.get("CONSPHONE"));
		inputParams.put("P_CONSPHONEXT",params.get("CONSPHONEXT"));
		inputParams.put("P_CONSFAX",params.get("CONSFAX"));
		inputParams.put("P_CONSEMAIL",params.get("CONSEMAIL"));
		inputParams.put("P_TOTPIECES",params.get("TOTPIECES"));
		inputParams.put("P_TOTWEIGHT",params.get("TOTWEIGHT"));
		inputParams.put("P_TOTSKIDS",params.get("TOTSKIDS"));
		inputParams.put("P_HAZMAT",params.get("HAZMAT"));
		inputParams.put("P_SPECINST",params.get("SPECINST"));
		inputParams.put("P_REFNUMBER",params.get("REFNUMBER"));
		inputParams.put("P_SHIPDETAILS",params.get("SHIPDETAILS"));
		inputParams.put("P_EXPEDITED",params.get("EXPEDITED"));
		inputParams.put("P_WEBUSER",params.get("WEBUSER"));
		inputParams.put("P_WEBACCT",params.get("WEBACCOUNT"));
		inputParams.put("P_COUNTER",params.get("COUNTER"));
		inputParams.put("P_KEY",""); 
		sproc.execute(inputParams);
	}
	

	
	
}
