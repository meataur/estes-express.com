/**
 *  * @author: Lakshman K
 *
 * Creation date: 1/3/2019
 */
package com.estes.myestes.edirequest.dto;

import io.swagger.annotations.ApiModelProperty;

/**
 * 
 *  Billing / Header Types
 */
public class BillingHeaderType {

	@ApiModelProperty(notes=" Billing/Header type id ")
	String id;
	@ApiModelProperty(notes=" Billing/Header type description ")
	String description;

	/**
	 * Create a new BillingType DTO.
	 */
	public BillingHeaderType()
	{
		super();
	} // Constructor

	public BillingHeaderType(String id, String description) {
		super();
		this.id = id;
		this.description = description;
	}

	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}

	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}	
}
