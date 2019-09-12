package com.estes.myestes.fuelsurcharge.dto;

import java.math.BigDecimal;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes Fuel Surcharge Header DTO
 */
@ApiModel
public class Header
{
	@ApiModelProperty(notes="National Average")
	BigDecimal nationalAverage;
	@ApiModelProperty(notes="Effective Date - YYYYMMDD")
	BigDecimal effectiveDate;
	
	public BigDecimal getNationalAverage() {
		return nationalAverage;
	}

	public void setNationalAverage(BigDecimal nationalAverage) {
		this.nationalAverage = nationalAverage;
	}

	public BigDecimal getEffectiveDate() {
		return effectiveDate;
	}

	public void setEffectiveDate(BigDecimal effectiveDate) {
		this.effectiveDate = effectiveDate;
	}
}