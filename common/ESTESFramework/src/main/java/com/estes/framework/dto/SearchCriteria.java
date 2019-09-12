package com.estes.framework.dto;


/**
 * Search Criteria for external app.
 * 
 * @author singhpa
 * 
 */
public class SearchCriteria  {

	private Integer searchType;
	private int searchKey;
	private int pageSize;
	private String order;
	private String orderBy;
	private int pageNumber;
	private int maxRecordsToFetch;
	private boolean fullSearch;

	public boolean isFullSearch() {
		return fullSearch;
	}

	public void setFullSearch(boolean fullSearch) {
		this.fullSearch = fullSearch;
	}

	public int getPageSize() {
		return pageSize;
	}

	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
	}

	public String getOrder() {
		return order;
	}

	public void setOrder(String order) {
		this.order = order;
	}

	public String getOrderBy() {
		return orderBy;
	}

	public void setOrderBy(String orderBy) {
		this.orderBy = orderBy;
	}

	public int getPageNumber() {
		return pageNumber;
	}

	public void setPageNumber(int pageNumber) {
		this.pageNumber = pageNumber;
	}

	public Integer getSearchType() {
		return searchType;
	}

	public void setSearchType(Integer searchType) {
		this.searchType = searchType;
	}

	public int getSearchNameKey() {
		return 0;
	}

	public int getSearchKey() {
		return searchKey;
	}

	public void setSearchKey(int searchKey) {
		this.searchKey = searchKey;
	}

	public int getMaxRecordsToFetch() {
		return maxRecordsToFetch;
	}

	public void setMaxRecordsToFetch(int maxRecordsToFetch) {
		this.maxRecordsToFetch = maxRecordsToFetch;
	}	
}
