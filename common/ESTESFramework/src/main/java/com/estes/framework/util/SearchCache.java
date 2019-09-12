package com.estes.framework.util;

import com.estes.framework.dto.SearchCriteria;


/**
 * Search Cache for storing the search related information
 * 
 * @author Data Concepts
 * 
 */
public class SearchCache {
	/**
	 * Total results found in the database for the give criteria
	 */
	private int totalSearchResults;
	/**
	 * Search Criteria used in the search
	 */
	private SearchCriteria searchCriteria;

	public int getTotalSearchResults() {
		return totalSearchResults;
	}

	public void setTotalSearchResults(int totalSearchResults) {
		this.totalSearchResults = totalSearchResults;
	}

	public SearchCriteria getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(SearchCriteria searchCriteria) {
		this.searchCriteria = searchCriteria;
	}

}
