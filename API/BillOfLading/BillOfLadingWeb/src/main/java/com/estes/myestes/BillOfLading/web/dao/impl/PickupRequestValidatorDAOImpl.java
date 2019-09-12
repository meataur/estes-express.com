package com.estes.myestes.BillOfLading.web.dao.impl;

import static com.estes.myestes.BillOfLading.web.dao.sql.PickupRequestValidationQuery.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.exception.BolException;
import com.estes.myestes.BillOfLading.util.EstesUtil;
import com.estes.myestes.BillOfLading.web.dao.iface.PickupRequestValidatorDAO;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.Role;
import com.estes.myestes.BillOfLading.web.dto.common.PickupRequest;


@Repository("pickupRequestValidatorDAO")
public class PickupRequestValidatorDAOImpl implements  PickupRequestValidatorDAO{
	
	@Autowired
	private JdbcTemplate jdbcTemplate; 
	
	@Autowired
	private Environment env;
	
	@Override
	public void validatePickupRequest(PickupRequest pickupRequest, BillOfLading bol, AuthenticationDetails auth){
		
	
		  List<ServiceResponse> errorList = new ArrayList<>();
		
		  /**
		   * Validate Basic PickupDetail Information using annotation
		   */
	      errorList.addAll(getValidationErrors(bol.getPickupDetailInfo(),"pickupDetailInfo"));
	      
	      /**
	       * validate pickupDate using database
	       */
	      if(bol.getPickupDetailInfo().getPickupDate()!=null){
	    	  ESTESLogger.log(getClass(),"Pickup date is not null");
	    	  ServiceResponse error = validatePickupDate(bol.getPickupDetailInfo().getPickupDate());
	    	  if(error!=null){
	    		  ESTESLogger.log(getClass(),"Pickup date has error");
	    		  errorList.add(error);
	    	  }
	      }
	      
	      /**
	       * Validate Shipper Basic Information Information using annotation
	       */
	      
	      errorList.addAll(getValidationErrors(bol.getShipperInfo(),"shipperInfo"));
	      

	      /**
	       * If the pickup requestor role is shipper, validate the shipper account number & shipper information
	       * else does not require to validate.
	       */
	      if(bol.getPickupDetailInfo().getRole().equals(Role.SHIPPER)){
	    	  
	    	  ESTESLogger.log(getClass(),"Pickup requestor role is shipper");
	    	  
	    	  /**
	    	   * If the authenticated account type is Local, set the authenticated account code to shipper
	    	   * 
	    	   */
	    	  
	    	  if( "L".equalsIgnoreCase(auth.getAccountType())){
	    		  
	    		  ESTESLogger.log(getClass(),"User account type is local");
	    		  /**
	    		   * The account type is local, set the authenticated user account code for the shipper
	    		   * In that case, account code from input is not required
	    		   */
		    	  bol.getPickupDetailInfo().setAccountCode(auth.getAccountCode());
		      }else{
		    	  
		    	  ESTESLogger.log(getClass(),"User account type is not local");
		    	  /**
		    	   * For other type, account code is required.
		    	   */
		    	  if(bol.getPickupDetailInfo().getAccountCode()==null || bol.getPickupDetailInfo().getAccountCode().trim().equals("")){
		    		  ESTESLogger.log(getClass(),"Shipper account code is empty or null");
		    		  errorList.add(prepareMessage(
		    				  "pickupDetailInfo.accountCode", 
		    				  null,
		    				  "pickupDetailInfo.accountCode.required"
		    				  ));
		    	  }
		      }


	    	  /**
	    	   * Check if the shipper account is active 
	    	   */
	    	   
	    	  if(isActiveAccount(bol.getPickupDetailInfo().getAccountCode())==false){
	    		  ESTESLogger.log(getClass(),"Shipper account code is not active");
	    		  /**
	    		   * Shipper Account is not active
	    		   */
	    		  errorList.add(prepareMessage(
	    				  "pickupDetailInfo.accountCode", 
	    				  bol.getPickupDetailInfo().getAccountCode(),
	    				  "pickupDetailInfo.accountCode.notActive"
	    				  ));
	    	  } else {
	    		  
	    		  ESTESLogger.log(getClass(),"Shipper account code is active");
	    		  /**
	    		   * Shipper account is active
	    		   */
	    		  
	    		  /**
	    		   * For account type W,G & N, there are subaccounts
	    		   * So validate if the authenticated user owns the subAccount 
	    		   */
	    		  ESTESLogger.log(getClass(),"Shipper account type is "+auth.getAccountType());
	    		  
	    		  if("W".equalsIgnoreCase(auth.getAccountType()) 
	    	  
	    			  || "G".equalsIgnoreCase(auth.getAccountType())
	    			  || "N".equalsIgnoreCase(auth.getAccountType())){
	    		  
	    			  
	    			  ESTESLogger.log(getClass(),"Shipper account type is any of W,G,N");
	    			  
	    			  /***
	    			   * To identify the subaccount, there are multiple query.
	    			   * No single table is there.
	    			   * Different table for different account type
	    			   */
		    		  String query= "";
		    		  
		    		  
		    		  switch(auth.getAccountType()){
		    		  	case "W" :
		    		  		query = QUERY_WEB_GROUP_ACCOUNT;
		    		  		ESTESLogger.log(getClass(),QUERY_WEB_GROUP_ACCOUNT+" for account type W");
		    			    break;
		    		  	case "G":
		    		  		query = QUERY_ACCOUNT_BELONGS_91;
		    		  		ESTESLogger.log(getClass(),QUERY_ACCOUNT_BELONGS_91+" for account type G");
		    		  		break;
		    		  		
		    		  	case "N":
		    		  		query = QUERY_ACCOUNT_BELONGS_NATIONAL_ACCOUNT;
		    		  		ESTESLogger.log(getClass(),QUERY_ACCOUNT_BELONGS_NATIONAL_ACCOUNT+" for account type N");
		    		  		break;
		    		  	default:
		    		  		ESTESLogger.log(getClass(),QUERY_ACCOUNT_BELONGS_NATIONAL_ACCOUNT+" Something wrong in if statement .Account type is "+auth.getAccountCode());
		    		  		break;
		    		  }
		    		  
		    		  ESTESLogger.log(getClass(),"Query to check accoount exists : "+query+" paraent : "+auth.getAccountCode()+" Child : "+bol.getPickupDetailInfo().getAccountCode());
		    		  
		    		  /**
		    		   * run the query to find if authenticated user owns the subaccount
		    		   */
		    		  if(isAccountExists(query, auth.getAccountCode(), bol.getPickupDetailInfo().getAccountCode())){
		    			 
		    			  ESTESLogger.log(getClass(),"Account exists " );
		    			  /**
		    			   * Authenticated user owns the subaccount
		    			   * Account exists, validate the given shipper information with database
		    	    	   * Validate Shipper Information with the given information
		    	    	   */
		    			   
		    			  Map<String,?> accountInfo = getAccounInfoByAccountCode(bol.getPickupDetailInfo().getAccountCode());
		    			  
		    			  if(accountInfo!=null){
		    				  
		    				  ESTESLogger.log(getClass(),"Account Information is not null" );
		    				  //String company = (String) accountInfo.get("COMPANY");
		    				  //String addressLine1 = (String) accountInfo.get("ADDRESS1");
		    				  //String addressLine2 = (String) accountInfo.get("ADDRESS2");
		    				  String city = (String) accountInfo.get("CITY");
		    				  String state = (String) accountInfo.get("STATE");
		    				  String zip = (String) accountInfo.get("ZIP");
		    				 
		    				  
		    				  if(city.trim().equals(bol.getShipperInfo().getCity().trim())==false){
		    					  ESTESLogger.log(getClass(),"Shipper city does not match" );
		    					  errorList.add(prepareMessage(
					    				  "shipperInfo.city", 
					    				  city.trim(),
					    				  "shipperInfo.city.missMatch"
					    				  ));
		    				  }
		    				  
		    				  if(state.trim().equals(bol.getShipperInfo().getState().trim())==false){
		    					  ESTESLogger.log(getClass(),"Shipper state does not match" );
		    					  errorList.add(prepareMessage(
					    				  "shipperInfo.state", 
					    				   state.trim(),
					    				  "shipperInfo.state.missMatch"
					    				  ));	  
				    		  }
		    				  
		    				  if(zip.trim().equals(bol.getShipperInfo().getZip().trim())==false){
		    					  ESTESLogger.log(getClass(),"Shipper zip does not match" );
		    					  errorList.add(prepareMessage(
					    				  "shipperInfo.zip", 
					    				  zip.trim(),
					    				  "shipperInfo.zip.missMatch"
					    				  ));	  
				    		  }
		    				  
		    				  
		    			  }else{
		    				  ESTESLogger.log(getClass(),"Account Information is  null" );
		    			  }
		    			  
		    		  }else{
		    			  /**
		    			   * Authenticated user doesn't own the subAccount
		    			   * Account is not found in sub account
		    			   */
		    			  
		    			  ESTESLogger.log(getClass(),"Account code not found" );
		    			  
		    			  errorList.add(prepareMessage(
			    				  "pickupDetailInfo.accountCode", 
			    				  bol.getPickupDetailInfo().getAccountCode(),
			    				  "pickupDetailInfo.accountCode.notFound"
			    				  ));
		    		  }
		    	  }
	    	  }
	      }
	      
	      /**
	       * Shipper zip, city, state validations are done in FBPGM.EBG10Q310 (BOL Header SPROC)
	       * No need to validate again here
	       */
	      
	      /**
	       * If errors, throw exception
	       */
	      if(errorList.size()>0){
	    	  throw new BolException(errorList);
	      }
	      
	      

	}
	
	@Override
	public Map<String, ?> getAccounInfoByAccountCode(String accountCode) {
		
		Map<String, ?> results = jdbcTemplate.queryForMap(QUERY_SHIPPER_INFO_BY_ACCOUNT_CODE,new Object[]{accountCode});
		return results;
	}


	@Override
	public boolean isAccountExists(String query, String parentAccountCode, String subAccountCode){
		int result = jdbcTemplate.queryForObject(query, new Object[]{parentAccountCode,subAccountCode},Integer.class);
		
		return result>0;
	}
	
	
	
	@Override
	public boolean isActiveAccount(String accountCode){
		int result = jdbcTemplate.queryForObject(QUERY_ACTIVE_ACCOUNT, new Object[]{accountCode},Integer.class);
		
		return result>0;
	}
	
	
	/**
	 * Validate Pickup Date
	 * @param pickupDate
	 * @return
	 */
	public ServiceResponse validatePickupDate(Date pickupDate){
		
		  String messageCode = "pickupDetailInfo.pickupDate.";
		  
		  String code = "invalid";

    	  int date = Integer.parseInt(EstesUtil.formatDate(pickupDate,EstesUtil.DATE_YYYYMMDD));
    	  
		  int today = Integer.parseInt(EstesUtil.formatDate(new Date(),EstesUtil.DATE_YYYYMMDD));
		  
		  Calendar c = new GregorianCalendar();
		  c.add(Calendar.DATE, 30);
		  int after30DaysLaterDate = Integer.parseInt(EstesUtil.formatDate(c.getTime(),EstesUtil.DATE_YYYYMMDD));
		  /**
		   * Check if date is past
		   */
		  if(today > date){
			  /**
			   * Pickup Date is in the past
			   */
			  code = "past";
		  }else if(date > after30DaysLaterDate){
			  code = "future";
		  }else {
			  
			 Map<String, ?> results = jdbcTemplate.queryForMap(VALID_PICKUP_DATE_QUERY, new Object[]{date});

		 	 BigDecimal day = (BigDecimal) results.get("WDDAY");
			 String holiday = (String) results.get("WDEXCP");
			 if(day.intValueExact()>5 || "H".equalsIgnoreCase(holiday)){
				 code="holiday";
			 }else{
				 return null;
			 }
		  }

    	 
	      
	      return prepareMessage(
				  "pickupDetailInfo.pickupDate", 
				  pickupDate,
				  messageCode+code
				  );
	      
	}
	
	private ServiceResponse prepareMessage(String fieldName, Object badData, String messageCode){
		 ServiceResponse serviceResponse = new ServiceResponse();
		 serviceResponse.setErrorCode("error");
		 

		 serviceResponse.setBadData(null);

		 try{
			 serviceResponse.setBadData((String) badData);
		 }catch(Exception ex){
			 
		 }

		 serviceResponse.setFieldName(fieldName);
		 
		 serviceResponse.setMessage(messageCode);
		 if(env.containsProperty(messageCode)){
			 serviceResponse.setMessage(env.getProperty(messageCode));
		 }
		 
		 return serviceResponse;
	}
	
	private <T> List<ServiceResponse> getValidationErrors(T bean, String rootFieldName){
		
		List<ServiceResponse> errorList = new ArrayList<>();
		ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
		
	    Validator validator = factory.getValidator();
	    Set<ConstraintViolation<T>> errors = validator.validate(bean);
	      
		for(ConstraintViolation<T> error : errors){

			 errorList.add(prepareMessage(
					 rootFieldName+"."+error.getPropertyPath().toString(),
					 error.getInvalidValue(),
					 rootFieldName+"."+error.getPropertyPath().toString()+".invalid"));
		}
		
		return errorList;
	}
	
}
