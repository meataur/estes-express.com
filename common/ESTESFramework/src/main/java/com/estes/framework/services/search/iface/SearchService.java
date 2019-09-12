package com.estes.framework.services.search.iface;

import com.estes.framework.dto.SearchCriteria;
import com.estes.framework.dto.SearchResults;

/**
 * This interface is used in searching data
 * 
 * @author Data Concepts
 * 
 * @param <T> represents the Search Criteria 
 * @param <V> represents the Search Result
 */
public interface SearchService<T extends SearchCriteria, V extends Object> {

	/**
	 * For a given search criteria, this method will return the matching results
	 * @param criteria
	 * @return
	 */
	public SearchResults<V> getSearchResults(T criteria);

	/**
	 * For a given search criteria, this method will return the count of matching results
	 * @param criteria
	 * @return
	 */
	public int getCount(T criteria);
}
