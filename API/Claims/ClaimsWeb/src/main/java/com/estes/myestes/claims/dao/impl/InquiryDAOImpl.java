package com.estes.myestes.claims.dao.impl;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;

import com.estes.myestes.claims.dao.extractor.InquiryResultSetExtractor;
import com.estes.myestes.claims.dao.iface.InquiryDAO;
import com.estes.myestes.claims.dto.ClaimDTO;
import com.estes.myestes.claims.dto.ClaimsRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;
import com.estes.myestes.claims.util.ClaimsConstant;

import org.springframework.stereotype.Repository;

@Repository ("inquiryDAO")
public class InquiryDAOImpl implements InquiryDAO {
	
	@Autowired
	private JdbcTemplate jdbcMyEstes;
	
	@Override
	public List<ClaimDTO> getClaimsByDate(ClaimsRequestDTO request) {
		String sql = "select AHCLM, AHAMND, AHOT, AHPRO, "
				+ "concat(concat(right(concat('000',CAST(ahot AS varchar(3))),3),'-'), right(concat('0000000',CAST(ahpro AS varchar(7))),7)) as PRONUM, "  
				+ "AHSTAT, AHDRE8, AHCREF, AHRAMT, AHPCHK, AHPAMT, AHDCH8, AHNAME, AHRNAM " 
				+ "from fbfiles.clp001 " 
				+ "where ahdre8 between '"+request.getStartDate()+"' "
				+ "and '"+request.getEndDate()+"' "
				+ "and ahcust = '"+request.getAccountNumber()+"' ";
		if(!request.getStatus().equalsIgnoreCase("ALL")) {
			if(request.getStatus().equalsIgnoreCase("DECLINED")) {
				sql += "and AHSTAT = 'D' ";
			}
			if(request.getStatus().equalsIgnoreCase("PAID")) {
				sql += "and AHSTAT = 'P' ";
			}
			if(request.getStatus().equalsIgnoreCase("OPEN")) {
				sql += "and AHSTAT NOT IN ('D','V','P') ";
			}
		}
		
		sql += "order by AHCLM DESC, AHAMND DESC";

		InquiryResultSetExtractor extractor = new InquiryResultSetExtractor(request.getSearchBy());

		List<ClaimDTO> claims = jdbcMyEstes.query(sql, extractor);
		return claims;
	}
	
	@Override
	public List<ClaimDTO> getClaimsByProNumber(String proNumber, String account) throws ClaimsException {
		
		proNumber = proNumber.replaceAll("-","");

		String sql = "select AHCLM, AHAMND, AHOT, AHPRO, "
				+ "concat(concat(right(concat('000',CAST(ahot AS varchar(3))),3),'-'), right(concat('0000000',CAST(ahpro AS varchar(7))),7)) as PRONUM, "  
				+ "AHSTAT, AHDRE8, AHCREF, AHRAMT, AHPCHK, AHPAMT, AHDCH8, AHNAME, AHRNAM " 
				+ "from fbfiles.clp001 " 
				+ "where CONCAT(DIGITS(AHOT),AHPRO)='"+proNumber+"' "
				+ "and ahcust = '"+account+"' ";

		sql += "ORDER BY AHCLM DESC, AHAMND DESC";
		
		InquiryResultSetExtractor extractor = new InquiryResultSetExtractor();
		
		List<ClaimDTO> claims = jdbcMyEstes.query(sql, extractor);
		return claims;
	}
	
	@Override
	public List<ClaimDTO> getClaimsByRefNumber(String refNumber, String account) throws ClaimsException {
		String sql = "select AHCLM, AHAMND, AHOT, AHPRO, "
				+ "concat(concat(right(concat('000',CAST(ahot AS varchar(3))),3),'-'), right(concat('0000000',CAST(ahpro AS varchar(7))),7)) as PRONUM, "  
				+ "AHSTAT, AHDRE8, AHCREF, AHRAMT, AHPCHK, AHPAMT, AHDCH8, AHNAME, AHRNAM " 
				+ "from fbfiles.clp001 " 
				+ "where AHCREF = '"+refNumber+"' "
				+ "and ahcust = '"+account+"' ";

		sql += "order by AHCLM DESC, AHAMND DESC";
		
		InquiryResultSetExtractor extractor = new InquiryResultSetExtractor();
		
		List<ClaimDTO> claims = jdbcMyEstes.query(sql, extractor);
		return claims;
	}
	
	@Override
	public List<ClaimDTO> getClaimsByClaimNumber(String claimNumber, String account) throws ClaimsException {
		String sql = "select AHCLM, AHAMND, AHOT, AHPRO, "
				+ "concat(concat(right(concat('000',CAST(ahot AS varchar(3))),3),'-'), right(concat('0000000',CAST(ahpro AS varchar(7))),7)) as PRONUM, "  
				+ "AHSTAT, AHDRE8, AHCREF, AHRAMT, AHPCHK, AHPAMT, AHDCH8, AHNAME, AHRNAM " 
				+ "from fbfiles.clp001 " 
				+ "where AHCLM = "+claimNumber+" "
				+ "and ahcust = '"+account+"' ";

		sql += "order by AHCLM DESC, AHAMND DESC";
		
		InquiryResultSetExtractor extractor = new InquiryResultSetExtractor();
		
		List<ClaimDTO> claims = jdbcMyEstes.query(sql, extractor);
		return claims;
	}
}