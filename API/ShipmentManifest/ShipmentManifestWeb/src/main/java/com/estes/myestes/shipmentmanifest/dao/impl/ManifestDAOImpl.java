package com.estes.myestes.shipmentmanifest.dao.impl;


import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.rest.Pageable;
import com.estes.dto.common.rest.Pageable.Order;
import com.estes.myestes.shipmentmanifest.dao.iface.ManifestDAO;
import com.estes.myestes.shipmentmanifest.dao.mapper.ManifestRowMapper;
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRequestDTO;
import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;
import com.estes.myestes.shipmentmanifest.util.CallEDQ380;
import com.estes.myestes.shipmentmanifest.util.CallUTQ102;
import com.estes.myestes.shipmentmanifest.util.ShipmentManifestConstant;

@Repository ("manifestDAO")
public class ManifestDAOImpl implements ManifestDAO
{
	@Autowired
	private JdbcTemplate jdbcMyEstes;
	
	// gather all data
	@Override
	public List<ManifestRecordDTO> gatherData(Pageable pageable, ManifestRequestDTO request, String accountCode, String accountType, String hash, String session) throws ShipmentManifestException{
		String query = createQuery(pageable, request, accountCode, accountType);
		// if limiting to subset
		if(pageable != null) {
			// page starts at 1 so offest by 1
			int offset = (pageable.getPage() - 1)  * pageable.getSize();
			query = query + " LIMIT "+offset+", "+pageable.getSize();
		}
		List<ManifestRecordDTO> manifests = jdbcMyEstes.query(query, new ManifestRowMapper());
		manifests = addMoreDetails(manifests, accountCode, accountType, hash, session, request.getViewCharges());
		return manifests;
	}
	
	// get number of data
	@Override
	public int getTotalNumberRecords(ManifestRequestDTO request, String accountCode, String accountType, String hash, String session) throws ShipmentManifestException{
		String query = createQuery(null, request, accountCode, accountType);
		query = "SELECT COUNT(*) FROM ( "+query+" )";
		int count = jdbcMyEstes.queryForObject(query, Integer.class);
		return count;
	}
	
	@Override
	public boolean accountBelongsToGroup(String accountRequest, String accountType, String accountNumber){
		String sql = null;
		
		if(accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_WEB)){
			sql = "SELECT count(estesrtgy2.qnp245.qacusc) "
					+ "FROM estesrtgy2.qnp245 "
					+ "WHERE estesrtgy2.qnp245.qagrpc = '"+accountNumber+"' "
					+ "AND estesrtgy2.qnp245.qacusc = '"+accountRequest+"'";
		}
		if(accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_GROUP)){
			sql = "SELECT count(fbfiles.ral001gp.cmocc) "
					+ "FROM fbfiles.ral001gp "
					+ "WHERE fbfiles.ral001gp.cmocc = '"+accountNumber+"' "
					+ "AND fbfiles.ral001gp.cmcust = '"+accountRequest+"'";
		}
		if(accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_NATIONAL)){
			sql = "SELECT count(fbfiles.rap001.cmocc) "
					+ "FROM fbfiles.rap001 "
					+ "WHERE fbfiles.rap001.cmcust = '"+accountRequest+"' "
					+ "AND fbfiles.rap001.cmna = '"+accountNumber+"'";
		}
		Integer count = jdbcMyEstes.queryForObject(sql, new Object[] { }, Integer.class);
		
		if(count > 0) return true;
		return false;
		
	}
	
	// build out query based on old my estes application
	private String createQuery(Pageable pageable, ManifestRequestDTO request, String accountCode, String accountType) {
		String query = "";

		// what kind of shipment
		String shipperCode = "fhscd";
		if(request.getShipmentTypes().equalsIgnoreCase("OUTBOUND")) shipperCode = "fhscd";
		if(request.getShipmentTypes().equalsIgnoreCase("INBOUND")) shipperCode = "fhccd";
		if(request.getShipmentTypes().equalsIgnoreCase("THIRDPARTY")) shipperCode = "fhbtc";

		// how to sort
		String sortOrder = " ";
		if((pageable != null && pageable.getOrder() == Order.desc) || (request.getSortBy() != null && request.getSortBy().equals("DESCENDING"))){
			sortOrder = "DESC";
		}

		//set up pickup date or delivery date
		String orderBy;
		String whereDateRange;
		if ((pageable != null && pageable.getSort() != null && pageable.getSort().equalsIgnoreCase("PICKUPDATE")) || (request.getSearchBy() != null && request.getSearchBy().equals("PICKUPDATE"))){
			orderBy = "FHPUD8 " + sortOrder;
			whereDateRange = "fhpud8 between '" + request.getStartDate() + "' and '" + request.getEndDate() + "' ";
		}
		else{
			orderBy = "FHDDA8 " + sortOrder;
			whereDateRange = "fhdda8 between '" + request.getStartDate() + "' and '" + request.getEndDate() + "' ";
		}

		// set account number
		String accountNumber = accountCode;
		// if national or group or web account
		if(accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_NATIONAL) || accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_GROUP) || accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_WEB)) {
			if(!request.getAccount().equalsIgnoreCase("ALL")) {
				accountNumber = request.getAccount();
			}
		}

		//if it is an ALL shipmentTypes and ((a national account and all accounts)OR (the account number they type in is a national acct.))
		if(request.getShipmentTypes().equalsIgnoreCase("ALL") && 
				((accountType.equalsIgnoreCase("N") && request.getAccount().equalsIgnoreCase("ALL"))
						|| isNationalAccount(accountNumber))){
			String sql = createNAQueryString("FHSCD",whereDateRange,accountNumber) + " UNION " + createNAQueryString("FHCCD",whereDateRange,accountNumber) + 
	                " UNION " + createNAQueryString("FHBTC",whereDateRange,accountNumber);  
			query = sql + "order by "+orderBy+", fhot, fhpro ";
		}
		else{
			query = createRegularQuery(accountType, request.getAccount(), shipperCode, orderBy, whereDateRange, 
					request.getStartDate(), request.getEndDate(), accountNumber,
					request.getShipmentTypes());
		}

		return query;
	}
		
	/**
	 * Add the 'status' and 'receivedBy' columns and data
	 * to the table.  Also format some of the fields.
	 * @throws ShipmentManifestException 
	 */
	private List<ManifestRecordDTO> addMoreDetails(List<ManifestRecordDTO> manifests, String accountNumber, String accountType, String hash, String session, boolean allowedToSeeCharges) throws ShipmentManifestException{
		CallEDQ380 shipmentInfo = new CallEDQ380(jdbcMyEstes);
		CallUTQ102 airShipmentInfo = new CallUTQ102(jdbcMyEstes);
		
		for (ManifestRecordDTO manifest : manifests){
			
			if(allowedToSeeCharges){
				//Even if they can see charges, hide them if the user is not the payer of the shipment
				if(hideCharges(manifest.getOriginTerminalId(),manifest.getPro(),manifest.getBillToCode(),accountNumber, accountType)){
					manifest.setCharges(" ");
				}
				//Do not display $0.00 charges
				if(manifest.getCharges().equals("0.00")){
					manifest.setCharges(" ");
				}
			}
			else{
				manifest.setCharges(" ");
			}
			if(!manifest.getCharges().trim().equals("")){
				manifest.setCharges("$"+manifest.getCharges());
			}
			if(!isEstesAirShipment(manifest.getOriginTerminalId())){
				shipmentInfo.setDelay("0");
				shipmentInfo.setHashValue(hash);
				shipmentInfo.setOtPro(manifest.getProNumNoDash());
				shipmentInfo.setOtProE(" ");
				shipmentInfo.setRandomNumber(session);
				shipmentInfo.setManifest(manifest);
				manifest = shipmentInfo.execute();
			}
			else{	//call utq102 to get shipment info for estes air shipment
				airShipmentInfo.setDelay(0);
				airShipmentInfo.setIot(manifest.getOriginTerminalId());
				airShipmentInfo.setIpro(manifest.getPro());
				airShipmentInfo.setLib(" ");
				airShipmentInfo.execute();
				manifest.setPickupDate(airShipmentInfo.getPickDate());
				manifest.setDeliveryDate(airShipmentInfo.getDelDate());
				manifest.setShipper(airShipmentInfo.getSname());
				manifest.setConsignee(airShipmentInfo.getCname());
				manifest.setPieces(airShipmentInfo.getPieces());
				manifest.setWeight(airShipmentInfo.getWeight());
				manifest.setCharges(airShipmentInfo.getCharges());
				
			}
		}
		return manifests;
	}
	
	
	private boolean isNationalAccount(String account) {
		String sql = "Select CMCUST from FBFILES.RAL00110 where CMNA = ? ";
		
		List<String> results = jdbcMyEstes.queryForList(sql,  new Object[] {account}, String.class);
		
		if(results.size() > 0) {
			return true;
		}
		return false;
	}
	
	private String createNAQueryString(String shipperCode, String whereDateRange, String account){
        String sql = " ";
        String select = "SELECT DISTINCT concat(concat(right(concat('000',CAST(fhot AS varchar(3))),3),'-'), right(concat('0000000',CAST(fhpro AS varchar(7))),7)) as PRONUM, " + 
        "FHBL#, FHPO, FHPUD8, FHDDA8, FHSNM, FHCNM, FHTOTP, FHSWGT, FHPROD, FHOT, FHPRO, FHPUDT, FHSCD, FHCCD, FHBTC, " +
        "concat(right(concat('000',CAST(fhot AS varchar(3))),3), right(concat('0000000',CAST(fhpro AS varchar(7))),7)) as PRONUMNODASH " +
        "FROM fbfiles.frp001 ";
        
        sql = select + "inner join fbfiles.rap001 " +
        "on " + shipperCode + " = cmcust " +
        "where " + whereDateRange +
        "and cmna = '"+account+"' ";
        
        return sql;
    }	
	
	//create local account query
    private String createRegularQuery(String accountType, String requestAccount, String shipperCode, String orderBy, String whereDateRange,
                                             String startDate, String endDate, String accountNumber, String shipmentType){
        
    	String sql = "SELECT DISTINCT " + 
    			"concat(concat(right(concat('000',CAST(fhot AS varchar(3))),3),'-'), right(concat('0000000',CAST(fhpro AS varchar(7))),7)) as PRONUM, " + 
    			"fhbl#, fhpo, fhpud8, fhdda8, fhsnm, fhcnm, fhtotp, fhswgt, fhprod, fhot, fhpro, fhpudt, fhscd, fhccd, fhbtc, " + 
    			"concat(right(concat('000',CAST(fhot AS varchar(3))),3), right(concat('0000000',CAST(fhpro AS varchar(7))),7)) as PRONUMNODASH " + 
    			"FROM fbfiles.frp001 ";
    	
    	if((accountType.equalsIgnoreCase("N") && requestAccount.equalsIgnoreCase("ALL"))|| isNationalAccount(accountNumber)){
    		//or the number they type in is a national
    		sql = sql + "INNER JOIN fbfiles.rap001 ON "+shipperCode+" = cmcust " + 
				"WHERE " + whereDateRange + 
				"and cmna='"+accountNumber+"'";
    	}
    	else{
    		String whereAccounts = getWhereAccounts(accountNumber, shipmentType, shipperCode);
    		sql = sql + "WHERE "+whereDateRange+whereAccounts;
    	}
    	sql = sql + " ORDER BY "+orderBy+", fhot, fhpro";
    	
    	return sql;
    }
    
    private String getWhereAccounts(String accountNumber, String shipmentType, String shipperCode){
        
    	//build an account list for who they are signed in as
    	String accountList;
    	
    	accountList = convertVectorToString(buildAccountList(accountNumber));
    	String whereAccounts = "";
    	if (shipmentType.equals("ALL")){
    		whereAccounts = " and (FHSCD IN (" + accountList + ") or FHCCD IN (" + accountList;
    		whereAccounts = whereAccounts + ") or FHBTC IN (" + accountList + "))";
    	}
        else{
            whereAccounts = " and " + shipperCode + " IN (" + accountList + ")";
        }
	
        return whereAccounts;
    }

	
	private Vector<String> buildAccountList(String account){
		Vector<String> victor = new Vector<String>();

		//look in web groups
		String sql = "Select QACUSC from ESTESRTGY2.QNP245 where QAGRPC = ?";
		List<String> results = jdbcMyEstes.queryForList(sql,  new Object[] {account}, String.class);
		for(String result : results){
			victor.addElement(result);
		}
		
		//then look in group file RAl001gp
		if(victor.size() == 0){
			sql = "Select CMCUST from FBFILES.RAL001gp where CMOCC = ?";
			results = jdbcMyEstes.queryForList(sql,  new Object[] {account}, String.class);
			for(String result : results){
				victor.addElement(result);
			}
		}
		//then look in regular rap001 file
		sql = "Select CMCUST from FBFILES.RAP001 where CMCUST = ?";
		results = jdbcMyEstes.queryForList(sql,  new Object[] {account}, String.class);
		for(String result : results){
			victor.addElement(result);
		}
		return victor;
	}
	
	private String convertVectorToString(Vector<?> victor){
		String results = " ";
		int count = 0;
		for (Enumeration<?> list = victor.elements(); list.hasMoreElements();)
		{
			if(count > 0) results = results + ", ";
			String account = (String)list.nextElement();
			results = results + "'" + account + "'";
			count++;
		}
		return results;
	}
	
	private boolean hideCharges(String ot, String pro, String billToAccount, String accountCode, String accountType){
		String sql;
		if (isEstesAirShipment(ot)){
			if(billToAccount.equals(accountCode)){
				return false;
			}
			else{
				return true;
			}
		}
		else{

			if(accountType.equals("W")){
				sql = "select count(*) from fbfiles.freight_bill_payer " + 
						"inner join estesrtgy2.qnp245 on qacusc = payer " +
						"where terminal_number = ? and pro_number = ? " + 
						"and qagrpc = ?";
			}
			else{
				if(accountType.equals("G")){
					sql = "select count(*) from fbfiles.freight_bill_payer " + 
							"inner join fbfiles.ral001gp on cmcust = payer " +
							"where terminal_number = ? and pro_number = ? " + 
							"and cmocc = ?";
				}
				else{
					if(accountType.equals("N")){
						sql = "select count(*) from fbfiles.freight_bill_payer " + 
								"inner join fbfiles.ral00110 on cmcust = payer " +
								"where terminal_number = ? and pro_number = ? " + 
								"and cmna = ?";
					}
					else{
						sql = "select count(*) from fbfiles.freight_bill_payer " + 
								"where terminal_number = ? and pro_number = ? " + 
								"and payer = ?";
					}
				}
			}

			Integer count = jdbcMyEstes.queryForObject(sql, new Object[] { ot, pro, accountCode }, Integer.class);

			if(count > 0) {
				return false;
			}
			return true;
		}
	}
	
	private boolean isEstesAirShipment(String ot){
		if(ot.equals("250")) {
			return true;
		}
		return false;
	}
}