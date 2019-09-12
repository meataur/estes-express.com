/**
 *
 */

package com.estes.myestes.claims.dto;

import java.io.Serializable;
import java.util.List;

import io.swagger.annotations.ApiModelProperty;

/**
 * Info request DTO
 */
public class ClaimsRequestDTO implements Serializable {
	private static final long serialVersionUID = 5359892133749186252L;
	@ApiModelProperty(notes="If logged in as grouped account have to specify sub account from sub account service")
	String accountNumber;
	@ApiModelProperty(notes="Search with field – Date Range/PRO Number/Your Reference Number/Estes Claim Number")
	String searchBy;
	@ApiModelProperty(notes="If searching with Date Range have to specify status–All/Open/Paid/Declined")
	String status;
	@ApiModelProperty(notes="If searching by Date the start of the range – yyyymmdd ")
	String startDate;
	@ApiModelProperty(notes=" If searching by Date the end of the range – yyyymmdd ")
	String endDate;
	@ApiModelProperty(notes="If not searching by Date the numbers searching for")
	List<String> numbers;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getSearchBy() {
		return searchBy;
	}
	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getStartDate() {
		return startDate;
	}
	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}
	public String getEndDate() {
		return endDate;
	}
	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}
	public List<String> getNumbers() {
		return numbers;
	}
	public void setNumbers(List<String> numbers) {
		this.numbers = numbers;
	}

}