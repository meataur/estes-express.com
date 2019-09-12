/**
 *
 */

package com.estes.myestes.claims.dto;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.edps.format.StringFormat;

import io.swagger.annotations.ApiModelProperty;
import springfox.documentation.annotations.ApiIgnore;

/**
 * Info request DTO
 */
public class ClaimDTO implements Serializable {
	private static final long serialVersionUID = 1259914759468533241L;

	@ApiModelProperty(notes="Claim number")
	String claimNumber;
	@ApiModelProperty(notes="Pro Number – xxx-xxxxxxx")
	String proNumber; 
	@ApiModelProperty(notes="Status")
	String status;
	@ApiModelProperty(notes="Date Filed – yyyymmdd ")
	String dateFiled;
	@ApiModelProperty(notes="Reference Number")
	String refNumber;
	@ApiModelProperty(notes="Amount of claim")
	String claimAmount;
	@ApiModelProperty(notes="Check number")
	String checkNumber;
	@ApiModelProperty(notes="Check Amount")
	String checkAmount;
	@ApiModelProperty(notes="Check Date – yyyymmdd ")
	String checkDate;
	@ApiModelProperty(notes="Claiment")
	String claiment;
	@ApiModelProperty(notes="Remit To")
	String remitTo;
	
	@ApiIgnore
	public String[] generateClaimAsRow() {
		String dateFiled;
		if (StringFormat.containsData(this.getDateFiled()) && this.getDateFiled().length() == 8) {
			SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
			Date dateField = new Date();
			try {
				dateField = ymd.parse(this.getDateFiled());
				dateFiled = mdy.format(dateField);
			} catch (Exception e) {
				dateFiled = "";
			}
		}
		else {
			dateFiled = "";
		}
		String checkDate;
		if (StringFormat.containsData(this.getCheckDate()) && this.getCheckDate().length() == 8) {
			SimpleDateFormat mdy = new SimpleDateFormat("MM/dd/yyyy");
			SimpleDateFormat ymd = new SimpleDateFormat("yyyyMMdd");
			Date dateField = new Date();
			try {
				dateField = ymd.parse(this.getCheckDate());
				checkDate = mdy.format(dateField);
			} catch (Exception e) {
				checkDate = "";
			}
		}
		else {
			checkDate = "";
		}
		
		String[] array = {
			this.claimNumber,
			this.proNumber,
			this.status,
			dateFiled,
			this.refNumber,
			this.claimAmount,
			this.checkNumber,
			this.checkAmount,
			checkDate,
			this.claiment,
			this.remitTo
		};
		return array;
	}
	
	public String getClaimNumber() {
		return claimNumber == null ? null : claimNumber.trim();
	}
	public void setClaimNumber(String claimNumber) {
		this.claimNumber = claimNumber;
	}
	public String getProNumber() {
		return proNumber == null ? null : proNumber.trim();
	}
	public void setProNumber(String proNumber) {
		this.proNumber = proNumber;
	}
	public String getStatus() {
		return status == null ? null : status.trim();
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getDateFiled() {
		return dateFiled;
	}
	public void setDateFiled(String dateFiled) {
		this.dateFiled = dateFiled;
	}
	public String getRefNumber() {
		return refNumber == null ? null : refNumber.trim();
	}
	public void setRefNumber(String refNumber) {
		this.refNumber = refNumber;
	}
	public String getClaimAmount() {
		return claimAmount == null ? null : claimAmount.trim();
	}
	public void setClaimAmount(String claimAmount) {
		this.claimAmount = claimAmount;
	}
	public String getCheckNumber() {
		return checkNumber == null ? null : checkNumber.trim();
	}
	public void setCheckNumber(String checkNumber) {
		this.checkNumber = checkNumber;
	}
	public String getCheckAmount() {
		return checkAmount == null ? null : checkAmount.trim();
	}
	public void setCheckAmount(String checkAmount) {
		this.checkAmount = checkAmount;
	}
	public String getCheckDate() {
		return checkDate;
	}
	public void setCheckDate(String checkDate) {
		this.checkDate = checkDate;
	}
	public String getClaiment() {
		return claiment == null ? null : claiment.trim();
	}
	public void setClaiment(String claiment) {
		this.claiment = claiment;
	}
	public String getRemitTo() {
		return remitTo == null ? null : remitTo.trim();
	}
	public void setRemitTo(String remitTo) {
		this.remitTo = remitTo;
	}

}