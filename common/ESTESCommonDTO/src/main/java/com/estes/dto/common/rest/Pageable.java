/**
 * @author: James Arthur
 *
 * Creation date: 10/26/2018
 */

package com.estes.dto.common.rest;


/**
 * Pageable DTO
 */
public class Pageable
{
	public enum Order
	{
		asc,desc
	}
	static final int MAX_SIZE = 100;
	private int page=1;
	private int size=25;
	private String sort;
	private Order order=Order.asc;
	
	/**
	 * Get the page
	 * 
	 * @return page
	 */
	public int getPage() {
		return page;
	}
	/**
	 * Set the page
	 * 
	 * @param page
	 */
	public void setPage(int page) {
		this.page = page;
	}
	/**
	 * Get the size
	 * 
	 * @return size
	 */
	public int getSize() {
		return size;
	}
	/**
	 * Set the size
	 * Limited to a max size of 100 
	 * 
	 * @param size
	 */
	public void setSize(int size) {
		if(size > MAX_SIZE) {
			this.size = MAX_SIZE;
		}
		else {
			this.size = size;
		}
	}
	/**
	 * Get the sort
	 * 
	 * @return sort
	 */
	public String getSort() {
		return sort;
	}
	/**
	 * Set the sort
	 * 
	 * @param sort
	 */
	public void setSort(String sort) {
		this.sort = sort;
	}
	/**
	 * Get the order
	 * 
	 * @return order
	 */
	public Order getOrder() {
		return order;
	}
	/**
	 * Set the order
	 * 
	 * @param order
	 */
	public void setOrder(Order order) {
		this.order = order;
	}
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Pageable [page=" + page + ", size=" + size + ", sort=" + sort + ", order=" + order + "]";
	}//toString() 
	
	
}
