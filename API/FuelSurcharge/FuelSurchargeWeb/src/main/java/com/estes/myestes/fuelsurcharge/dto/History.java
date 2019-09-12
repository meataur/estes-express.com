package com.estes.myestes.fuelsurcharge.dto;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes Fuel Surcharge History DTO
 */
@ApiModel
public class History {

	@ApiModelProperty(notes="Effective Date - YYYYMMDD")
	String effectiveDate;
	@ApiModelProperty(notes="National Average")
	String nationalAverage;
	@ApiModelProperty(notes="LTL Surcharge")
	BigDecimal ltlSurcharge;
	@ApiModelProperty(notes="TL Surcharge")
	BigDecimal tlSurcharge;
	
	public String getEffectiveDate() {
		return effectiveDate;
	}
	
	public void setEffectiveDate(String effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
	
	public String getNationalAverage() {
		return nationalAverage;
	}
	
	public void setNationalAverage(String nationalAverage) {
		this.nationalAverage = nationalAverage;
	}
	
	public BigDecimal getLtlSurcharge() {
		return ltlSurcharge;
	}
	
	public void setLtlSurcharge(BigDecimal ltlSurcharge) {
		this.ltlSurcharge = ltlSurcharge;
	}
	
	public BigDecimal getTlSurcharge() {
		return tlSurcharge;
	}
	
	public void setTlSurcharge(BigDecimal tlSurcharge) {
		this.tlSurcharge = tlSurcharge;
	}

}