/**
 * @author: Lakshman K
 *
 * Creation date: 10/10/2018
 */

package com.estes.myestes.recoverpassword.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes recoverPassword DTO
 */
@ApiModel(description="Estes Recover Password")
public class RecoverPassword
{
	@ApiModelProperty(notes="Selection type to search for user – email/userName")
	String selectionType;
	@ApiModelProperty(notes="Search criteria to recover password - My Estes user name or email address")
	String searchCriteria;

	/**
	 * Create new recoverPassword DTO
	 */
	public RecoverPassword()
	{
	} // Constructor

	public RecoverPassword(String selectionType, String searchCriteria) {
		super();
		this.selectionType = selectionType;
		this.searchCriteria = searchCriteria;
	}

	public String getSelectionType() {
		return selectionType;
	}

	public void setSelectionType(String selectionType) {
		this.selectionType = selectionType;
	}

	public String getSearchCriteria() {
		return searchCriteria;
	}

	public void setSearchCriteria(String searchCriteria) {
		this.searchCriteria = searchCriteria;
	}
}
