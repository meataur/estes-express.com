package com.estes.framework.dataaccess.search.iface;

import java.util.List;

import com.estes.framework.dto.SearchCriteria;
import com.estes.framework.dto.SearchResults;

/**
 * This interface is used in searching data for a given criteria
 * 
 * @author Data Concepts
 *
 * @param <T> Represents the Criteria type and should be of type SearchCriteria
 * @param <V> Represents the Result type should be of any result object
 */
public interface SearchDao<T extends SearchCriteria, V extends Object> {
	/**
	 * Loads the search results from the database for the given searchcriteria
	 * @param searchCriteria
	 * @return
	 */
	public SearchResults<V> getSearchResults(T searchCriteria);

	/**
	 * Gets the count of search results for the given search criteria
	 * @param searchCriteria
	 * @return
	 */
	 public int getCount(T searchCriteria);
	
	/**
	 * This is a generic method which executes the search for any given search criteria. 
	 * @param query
	 * @param searchCriteria
	 * @param resultClass
	 * @param currentIndexNumber
	 * @param maxRowsToFetch
	 * @return
	 */
	public List<?> executeSearch(String query, SearchCriteria searchCriteria, Class<?> resultClass, int maxRowsToFetch);
	
	/**
	 * 
	 * @param query
	 * @param searchCriteria
	 * @param resultClass
	 * @return
	 */
	public List<?> executeFullSearch(String query, SearchCriteria searchCriteria, Class<?> resultClass);
	
}
