/**
 * @author: Lakshman K
 *
 * Creation date: 12/5/2018
 */

package com.estes.services.common.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Estes package type DTO
 */
@ApiModel(description="Estes package type information")
public class PackageType
{
	@ApiModelProperty(notes="2 letter package type code; e.g. BD")
	String code;
	@ApiModelProperty(notes="Package type abbreviation; e.g. BUND")
	String abbrev;
	@ApiModelProperty(notes="Package type description; e.g. BUNDLE")
	String description;
	@ApiModelProperty(notes="Package type plural; e.g. BUNDLES")
	String plural;


	public String getAbbrev() {
		return abbrev;
	}

	public void setAbbrev(String abbrev) {
		this.abbrev = abbrev;
	}

	/**
	 * @return the code
	 */
	public String getCode() {
		return code;
	}
	/**
	 * @param code the code to set
	 */
	public void setCode(String code) {
		this.code = code;
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

	public String getPlural() {
		return plural;
	}

	public void setPlural(String plural) {
		this.plural = plural;
	}
}
