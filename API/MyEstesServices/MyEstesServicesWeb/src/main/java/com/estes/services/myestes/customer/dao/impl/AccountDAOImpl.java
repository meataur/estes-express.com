package com.estes.services.myestes.customer.dao.impl;
import static com.estes.services.myestes.customer.dao.sql.QuerySubAccount.QUERY_FOR_SUBACCOUNT_GROUP91;
import static com.estes.services.myestes.customer.dao.sql.QuerySubAccount.QUERY_FOR_SUBACCOUNT_NATIONAL;
import static com.estes.services.myestes.customer.dao.sql.QuerySubAccount.QUERY_FOR_SUBACCOUNT_WEBGROUP;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.Address;
import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.framework.logger.ESTESLogger;
import com.estes.services.myestes.customer.dao.iface.AccountDAO;
import com.estes.services.myestes.customer.dao.iface.PagingAndSortingDAO;
import com.estes.services.myestes.customer.dto.AccountDTO;
import com.estes.services.myestes.exception.ServiceException;

@Repository("accountDAO")
public class AccountDAOImpl implements AccountDAO {
	
	
	@Autowired
	private PagingAndSortingDAO<AccountDTO> pagingAndSortingDAO;
	
	@Override
	public Page<AccountDTO> getSubAccounts(String username, String accountType, String parentAccountCode, Pageable pageable, String searchByFields, String searchTerm) throws ServiceException {
		
		List<Object> params = new ArrayList<>();
		params.add(parentAccountCode);
		
		
		String countQuery = "";
		String query      = "";
		
		switch(accountType){
		
			case "G" :
				query = QUERY_FOR_SUBACCOUNT_GROUP91;
				break;
				
			case "N":
				query = QUERY_FOR_SUBACCOUNT_NATIONAL;
				break;
				
			case "W":
				query = QUERY_FOR_SUBACCOUNT_WEBGROUP;
				break;
				
			default:
				throw new ServiceException("Your account type is "+accountType+". You don't have any sub-accounts!","");
				
		}
		
		
		ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","Checking the searchTerm if it is empty or null");
		
		if(searchTerm!=null && !searchTerm.isEmpty()){
			ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","searchTerm is not null or empty. Splitting the searchBy by comma(,)");
			String[] searchByParams = null;
			
			if(searchByFields!=null && searchByFields.trim().length()>0){
				searchByParams= searchByFields.split(",");
			}
			
			ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()"," checking searchBy params length");
			if(searchByParams!=null && searchByParams.length >0){
				
				List<String> subQuery = new ArrayList<>();
				
				
				ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()"," Iteraring searchBy params to create subquery");
				for(String searchBy : searchByParams){
					
					ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","Checking search by param length is empty or itself null"); 
					if(searchBy!=null && searchBy.trim().length()>0){
						
						searchBy = searchBy.trim();
						switch(searchBy){
							case  "accountNumber": 
								subQuery.add(" UPPER(SUB_ACCOUNT_CODE) LIKE ? ");
								params.add(searchTerm.toUpperCase()+"%");
								break;
							
							case  "name": 
								subQuery.add(" UPPER(COMPANY) LIKE ? ");
								params.add("%"+searchTerm.toUpperCase()+"%");
								break;
								
							case  "contactName": 
								//TODO Not Sure which column is contactName in db table FBFILES.RAP001
								//subQuery.add(" UPPER(COMPANY) LIKE ? ");
								//params.add("%"+searchTerm.toUpperCase()+"%");
								break;	
								
								
							case  "streetAddress":
								subQuery.add(" UPPER(ADDRESS_LINE1) LIKE ? ");
								params.add("%"+searchTerm.toUpperCase()+"%");
								break;
							
							case  "streetAddress2":
								subQuery.add(" UPPER(ADDRESS_LINE2) LIKE ? ");
								params.add("%"+searchTerm.toUpperCase()+"%");
								break;
								
							case  "city": 
								subQuery.add(" UPPER(CITY) LIKE ? ");
								params.add(searchTerm.toUpperCase()+"%");
								break;
								
							case  "state": 
								subQuery.add(" UPPER(STATE) LIKE ? ");
								params.add(searchTerm.toUpperCase()+"%");
								break;
								
							case  "zip": 
								subQuery.add(" UPPER(ZIP) LIKE ? ");
								params.add(searchTerm.toUpperCase()+"%");
								break;
								
							case  "zip4": 
								subQuery.add(" UPPER(ZIP_EXT) LIKE ? ");
								params.add("%"+searchTerm.toUpperCase()+"%");
								break;
								
							case  "country": 
								//TODO Not Sure which column is country in db table FBFILES.RAP001
								//subQuery.add(" UPPER(ZIP_EXT) LIKE ? ");
								//params.add("%"+searchTerm.toUpperCase()+"%");
								break;case  "phone": 
								subQuery.add(" UPPER(PHONE) LIKE ? ");
								params.add("%"+searchTerm.replaceAll("\\D+", "")+"%");
								break;
							default:
								break;
						}
					}
				
				}
				
				if(!subQuery.isEmpty()){
					String subQueryStr = String.join(" OR ", subQuery);
					
					query+= " AND ( "+subQueryStr+" ) ";
				}
				
				
			}else{
				query +=" AND ( UPPER(SUB_ACCOUNT_CODE) LIKE ? "
						+ " OR UPPER(COMPANY) LIKE ? "
						+ " OR UPPER(ADDRESS_LINE1) LIKE ? "
						+ " OR UPPER(ADDRESS_LINE2) LIKE ? "
						+ " OR UPPER(CITY) LIKE ? "
						+ " OR UPPER(STATE) LIKE ? "
						+ " OR UPPER(ZIP) LIKE ? "
						+ " OR UPPER(PHONE) LIKE ? ) ";
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.toUpperCase()+"%");
				params.add("%"+searchTerm.replaceAll("\\D+", "")+"%");
			}
			

			ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","Query: "+query); 
		
		}
		
		countQuery = query.replace("*", "COUNT(*) as total");
		
		ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","Count Query: "+countQuery); 
		
		String sort = pageable.getSort();
		
		ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","Preparing sorting Query: "); 
		
		if (sort != null) {

			String order = pageable.getOrder().name();

			switch (sort) {

			case "accountNumber":
				query += " ORDER BY UPPER(SUB_ACCOUNT_CODE) " + order+"";
				break;

			case "name":
				query += " ORDER BY UPPER(COMPANY) " + order+"";
				break;
			
			case "contactName":
				//TODO Not Sure which column is contactName in db table FBFILES.RAP001
				//query += " ORDER BY UPPER(COMPANY) " + order+"";
				break;	
				
			case "streetAddress":
				query += " ORDER BY UPPER(ADDRESS_LINE1) " + order+"";
				break;
				
			case "streetAddress2":
				query += " ORDER BY UPPER(ADDRESS_LINE2) " + order+"";
				break;
				
			case "city":
				query += " ORDER BY UPPER(CITY) " + order+"";
				break;

			case "state":
				query += " ORDER BY UPPER(STATE) " + order+"";
				break;
			case "zip":
				query += " ORDER BY UPPER(ZIP) " + order+"";
				break;
				
			case "zip4":
				query += " ORDER BY UPPER(ZIP_EXT) " + order+"";
				break;
			case "country":
				//TODO Not Sure which column is contactName in db table FBFILES.RAP001
				//query += " ORDER BY UPPER(ZIP_EXT) " + order+"";
				break;
			
			case "phone":
				query += " ORDER BY UPPER(PHONE) " + order+"";
				break;
				
			default:
				break;
			}
		}
		
		ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "getSubAccounts()","Query after adding sorting order: "+query); 
		
		
		
		RowMapper<AccountDTO> rowMapper = (rs, rowNum) -> {
			AccountDTO account = new AccountDTO();
			account.setAccountNumber(rs.getString("SUB_ACCOUNT_CODE"));
			account.setName(rs.getString("COMPANY"));
			
			Address address = new Address();
			address.setStreetAddress(rs.getString("ADDRESS_LINE1"));
			address.setStreetAddress2(rs.getString("ADDRESS_LINE2"));
			address.setCity(rs.getString("CITY"));
			address.setState(rs.getString("STATE"));
			address.setZip(rs.getString("ZIP"));
			address.setZip4(rs.getString("ZIP_EXT"));
			
			account.setAddress(address);
			account.setPhone(rs.getString("PHONE"));
			
			return account;
		};
		
		
		Page<AccountDTO> results = pagingAndSortingDAO.getResult(countQuery, query, params, pageable, rowMapper);
		return results;
	}
	

}
