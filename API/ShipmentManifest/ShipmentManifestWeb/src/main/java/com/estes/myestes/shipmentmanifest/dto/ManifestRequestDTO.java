/**
 *
 */

package com.estes.myestes.shipmentmanifest.dto;

import java.io.Serializable;

import io.swagger.annotations.ApiModelProperty;

/**
 * Info request DTO
 */
public class ManifestRequestDTO implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1701436030006447386L;
	
	@ApiModelProperty(notes="type of shipment - OUTBOUND/INBOUND/THIRDPARTY/ALL")
	private String shipmentTypes;
	@ApiModelProperty(notes="Whether to sort by ascending or descending order - ASCENDING/DESCENDING")
	private String sortBy;
	@ApiModelProperty(notes="What date to search by - PICKUPDATE/DELIVERYDATE")
	private String searchBy;
	@ApiModelProperty(notes="Account Number or ALL")
	private String account;
	@ApiModelProperty(notes="Start Date YYYYMMDD")
	private String startDate;
	@ApiModelProperty(notes="End Date YYYYMMDD")
	private String endDate;
//	@ApiModelProperty(notes="If doing paged request what the index of page is starting with 0. If not paged leave blank.")
//	private Integer pageNum;
//	@ApiModelProperty(notes="If doing paged request what the size of the page is. If not paged leave blank.")
//	private Integer pageSize;
	@ApiModelProperty(notes="If the request should be able to view charges. Gotten from Login projects 'authorize app' service")
	private Boolean viewCharges;

	public String getShipmentTypes() {
		return shipmentTypes;
	}

	public void setShipmentTypes(String shipmentTypes) {
		this.shipmentTypes = shipmentTypes;
	}
	
	public String getSortBy() {
		return sortBy;
	}

	public void setSortBy(String sortBy) {
		this.sortBy = sortBy;
	}
	
	public String getSearchBy() {
		return searchBy;
	}

	public void setSearchBy(String searchBy) {
		this.searchBy = searchBy;
	}
	
	public String getAccount() {
		return account;
	}

	public void setAccount(String account) {
		this.account = account;
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

//	public Integer getPageNum() {
//		return pageNum;
//	}
//
//	public void setPageNum(Integer pageNum) {
//		this.pageNum = pageNum;
//	}
//
//	public Integer getPageSize() {
//		return pageSize;
//	}
//
//	public void setPageSize(Integer pageSize) {
//		this.pageSize = pageSize;
//	}

	public Boolean getViewCharges() {
		return viewCharges;
	}

	public void setViewCharges(Boolean viewCharges) {
		this.viewCharges = viewCharges;
	}

}