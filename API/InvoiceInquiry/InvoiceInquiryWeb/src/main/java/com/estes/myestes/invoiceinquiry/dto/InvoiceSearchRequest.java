/**
 * @author: Todd Allen
 *
 * Creation date: 10/05/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer invoice search request
 */
@ApiModel(description="Estes customer invoice search request")
@Data
public class InvoiceSearchRequest
{
	@ApiModelProperty(notes="Search type - F=PRO/S=statement number/P=PO number/B=BOL number")
	String searchType;
	@ApiModelProperty(notes="Array of search criteria - max 100")
	String[] criteria;
	@ApiModelProperty(notes="Image types to include with invoices - bol/dr")
	String[] imageTypes;

	public InvoiceSearchRequest()
	{
	} // constructor

	/**
	 * Get the search criteria
	 * 
	 * @return Search criteria
	 */
	public String[] getCriteria()
	{
		return this.criteria;
	} // getCriteria

	/**
	 * Get the image type codes
	 * 
	 * @return Image types
	 */
	public String[] getImageTypes()
	{
		return this.imageTypes;
	} // getImageTypes

	/**
	 * Get the search type
	 * 
	 * @return Search type
	 */
	public String getSearchType()
	{
		return this.searchType;
	} // getSearchType

	/**
	 * Set the search criteria
	 * 
	 * @param val Search criteria
	 */
	public void setCriteria(String[] vals)
	{
		this.criteria = vals;
	} // setCriteria

	/**
	 * Set the image types
	 * 
	 * @param vals Image type codes
	 */
	public void setImageTypes(String[] vals)
	{
		this.imageTypes = vals;
	} // setImageTypes

	/**
	 * Set the search type
	 * 
	 * @param val Search type
	 */
	public void setSearchType(String val)
	{
		this.searchType = val;
	} // setSearchType
}
