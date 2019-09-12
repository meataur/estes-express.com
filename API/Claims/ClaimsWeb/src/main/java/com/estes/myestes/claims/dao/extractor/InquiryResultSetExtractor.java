package com.estes.myestes.claims.dao.extractor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.jdbc.core.ResultSetExtractor;

import com.estes.myestes.claims.dto.ClaimDTO;

public class InquiryResultSetExtractor implements ResultSetExtractor<List<ClaimDTO>> {
	String searchBy = null;

	public InquiryResultSetExtractor() {
		super();
		this.searchBy = null;
	}

	public InquiryResultSetExtractor(String searchBy) {
		super();
		this.searchBy = searchBy;
	}

	public List<ClaimDTO> extractData(ResultSet rs) throws SQLException {
		List<ClaimDTO> claimsList = new ArrayList<ClaimDTO>();
		String claimNo = "";
		boolean firstRecord = true;
		boolean showVD = true;
		boolean showClaim = true;
		while(rs.next()) {
			if(!claimNo.equals(rs.getString("AHCLM"))) {
				firstRecord = true;
				showVD = true;
				showClaim = true;
			}
			claimNo = rs.getString("AHCLM");
			if(!showClaim) {
				continue;
			}

			ClaimDTO claim = new ClaimDTO();
			claim.setClaimNumber(rs.getString("AHCLM"));
			claim.setProNumber(rs.getString("PRONUM"));
			claim.setStatus(rs.getString("AHSTAT"));
			claim.setDateFiled(rs.getString("AHDRE8"));
			claim.setRefNumber(rs.getString("AHCREF"));
			claim.setClaimAmount(rs.getString("AHRAMT"));
			claim.setCheckNumber(rs.getString("AHPCHK"));
			claim.setCheckAmount(rs.getString("AHPAMT"));
			claim.setCheckDate(rs.getString("AHDCH8"));
			claim.setClaiment(rs.getString("AHNAME"));
			claim.setRemitTo(rs.getString("AHRNAM"));

			if(searchBy != null && !searchBy.equals("ALL")) {
				if(searchBy.equals("DECLINED") && 
						!(claim.getStatus().equals("V") || claim.getStatus().equals("D"))){
					showClaim = false;
				}
				if(searchBy.equals("PAID") && 
						!claim.getStatus().equals("P")){
					showClaim = false;
				}
				if(searchBy.equals("OPEN") && 
						(claim.getStatus().equals("V") || claim.getStatus().equals("D")
								|| claim.getStatus().equals("P"))){
					showClaim = false;
				}		
			}

			if(firstRecord){
				firstRecord = false;
				if(!claim.getStatus().equals("D") && !claim.getStatus().equals("V")){
					showVD = false;
				}
			}
			else{
				showVD = false;
			}
			if(claim.getStatus().equals("D") || claim.getStatus().equals("V")){
				claim.setStatus("Declined");
			}
			else{
				if(claim.getStatus().equals("P")){
					claim.setStatus("Paid");
				}
				else{
					claim.setStatus("Under Investigation");
				}
			}
			if(claim.getCheckAmount().equals("0.00") || !claim.getStatus().equals("Paid")){
				claim.setCheckAmount("");
			}
			//if it's the first record and its a d, then show all
			//if it's the first record and not a d, then don't show d's or v's
			if(showClaim && (showVD || !claim.getStatus().equals("Declined"))){
				claimsList.add(claim);
			}
		}
		return claimsList;
	}
}
