/**
 * @author: James Arthur
 *
 * Creation date: 10/26/2018
 */

package com.estes.dto.common.rest;

import java.util.List;

/**
 * Page DTO
 */
public class Page<T>
{
	// If it is the first page
	private boolean first;
	// If it is the last page
	private boolean last;
	// Current Page number
	private int number;
	// The number of Objects in current page
	private int numberOfElements;
	// The number of results per page
	private int size;
	// The total number of results
	private int totalElements;
	// The total number of pages
	private int totalPages;
	//List of Object
	private List<T> content;
	
	public Page(List<T> content, Pageable pageable, int totalSize){
		if(pageable != null && pageable.getPage() == 1) {
			this.first = true;
		}
		else {
			this.first = false;
		}
		if(pageable != null) {
			this.number = pageable.getPage();
			this.size = pageable.getSize();
		}
		else {
			this.number = 0;
			this.size = 0;
		}
		if(content != null) {
			this.numberOfElements = content.size();
		}
		else {
			this.numberOfElements = 0;
		}
		this.totalElements = totalSize;
		this.content = content;
		
		// total pages are the total pages divided by the number per page, need to round up with ceil
		this.totalPages = (int)Math.ceil((double)this.totalElements/(double)this.size);
		if(this.number >= this.totalPages) {
			this.last = true;
		}
		else {
			this.last = false;
		}
	}
	
	public boolean isFirst() {
		return first;
	}
	public boolean isLast() {
		return last;
	}
	public int getNumber() {
		return number;
	}
	public int getNumberOfElements() {
		return numberOfElements;
	}
	public int getSize() {
		return size;
	}
	public int getTotalElements() {
		return totalElements;
	}
	public int getTotalPages() {
		return totalPages;
	}
	public List<T> getContent() {
		return content;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "Page [first=" + first + ", last=" + last + ", number=" + number + ", numberOfElements="
				+ numberOfElements + ", size=" + size + ", totalElements=" + totalElements + ", totalPages="
				+ totalPages + "]";
	}//toString() 
}
