/**
 * @author: Todd Allen
 *
 * Creation date: 10/05/2018
 */

package com.estes.myestes.invoiceinquiry.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Customer aging e-mail request
 */
@ApiModel(description="Estes customer A/R aging e-mail request")
@Data
public class AgingEmailRequest
{
	@ApiModelProperty(notes="List of e-mail addresses to receive A/R aging information")
	List<String> emailAddresses;
	@ApiModelProperty(notes="File format - xls/csv/txt")
	String fileFormat;
	@ApiModelProperty(notes="Aging bucket : 1-8; 0 when PROs selected")
	String bucket;
	@ApiModelProperty(notes="List of PROs for which to receive A/R aging information XXX-XXXXXXX")
	List<String> pros;

	public AgingEmailRequest()
	{
	} // constructor

	/**
	 * Get the aging bucket#
	 * 
	 * @return Aging bucket
	 */
	public String getBucket()
	{
		return this.bucket;
	} // getBucket

	/**
	 * Get the list of e-mail addresses
	 * 
	 * @return E-mail address list
	 */
	public List<String> getEmailAddresses()
	{
		return this.emailAddresses;
	} // getEmailAddresses

	/**
	 * Get the file format
	 * 
	 * @return File format
	 */
	public String getFileFormat()
	{
		return this.fileFormat;
	} // getFileFormat

	/**
	 * Get the selected PROs
	 * 
	 * @return List of PROs
	 */
	public List<String> getPros()
	{
		return this.pros;
	} // getPros

	/**
	 * Set the aging bucket#
	 * 
	 * @param val Aging bucket
	 */
	public void setBucket(String val)
	{
		this.bucket = val;
	} // setBucket

	/**
	 * Set the list of e-mail address
	 * 
	 * @param val E-mail address list
	 */
	public void setEmailAddresses(List<String> vals)
	{
		this.emailAddresses = vals;
	} // setEmailAddresses

	/**
	 * Set the file format
	 * 
	 * @param val Format of file
	 */
	public void setFileFormat(String val)
	{
		this.fileFormat = val;
	} // setFileFormat

	/**
	 * Set the list of selected PROs
	 * 
	 * @param vals Selected PROs
	 */
	public void setPros(List<String> vals)
	{
		this.pros = vals;
	} // setPros
}
