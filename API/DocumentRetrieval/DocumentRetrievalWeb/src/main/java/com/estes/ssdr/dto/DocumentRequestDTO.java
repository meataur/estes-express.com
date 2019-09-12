/**
 * @author: Todd Allen
 *
 * Creation date: 02/23/2016
 *
 */

package com.estes.ssdr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Document request DTO
 */
@ApiModel(description="Document request information")
public class DocumentRequestDTO
{
	@ApiModelProperty(notes="Tracking number")
	private String trackingNum = "";
	@ApiModelProperty(notes="Shipment destination US or Canadian zip/postal code")
	private String destZip = "";
	@ApiModelProperty(notes="Document types to check - BOL/DR; comma separated")
	private String documentTypes = "";
	@ApiModelProperty(notes="Delivery method for documents - email/fax")
	private String deliveryMethod = "";
	@ApiModelProperty(notes="E-mail address to receive document images")
	private String email = "";
	@ApiModelProperty(notes="Fax number to receive document images")
	private String fax = "";
	@ApiModelProperty(notes="Fax attention")
	private String faxAttention = "";

	/**
	 * Create a new output queue form
	 */
	public DocumentRequestDTO()
	{
		super();
	} // Constructor

	/**
	 * Create a new output queue form.
	 * 
	 * @param num Tracking number
	 * @param zip Destination zip code
	 * @param types Document type codes
	 * @param method Delivery method
	 */
	public DocumentRequestDTO(String num, String zip, String types, String method)
	{
		this();
		setTrackingNum(num);
		setDestZip(zip);
		setDocumentTypes(types);
		setDeliveryMethod(method);
	} // Constructor

	/**
	 * Get the delivery method
	 * 
	 * @return Delivery method
	 */
	public String getDeliveryMethod()
	{
		return this.deliveryMethod;
	} // getDeliveryMethod

	/**
	 * Get the destination zip code
	 * 
	 * @return Destination zip code
	 */
	public String getDestZip()
	{
		return this.destZip;
	} // getDestZip

	/**
	 * Get the document type codes.
	 * 
	 * @return Document type codes
	 */
	public String getDocumentTypes()
	{
		return this.documentTypes;
	} // getDocumentTypes

	/**
	 * Get the e-mail address.
	 * 
	 * @return E-mail address
	 */
	public String getEmail()
	{
		return this.email;
	} // getEmail

	/**
	 * Get the fax number.
	 * 
	 * @return Fax phone number
	 */
	public String getFax()
	{
		return this.fax;
	} // getFax

	/**
	 * Get the fax attention.
	 * 
	 * @return Fax attention notation
	 */
	public String getFaxAttention()
	{
		return this.faxAttention;
	} // getFaxAttention

	/**
	 * Get the tracking number.
	 * 
	 * @return Tracking number
	 */
	public String getTrackingNum()
	{
		return this.trackingNum;
	} // getTrackingNum

	/**
	 * Determine whether the request has e-mail info
	 * 
	 * @param  docRequest Document request info
	 * @return Boolean indicator for existence of e-mail info 
	 */
	public static boolean hasEmail(DocumentRequestDTO docRequest)
	{
		return docRequest.getEmail().length() > 0;
	} // hasEmail

	/**
	 * Determine whether the request has fax info
	 * 
	 * @param  docRequest Document request info
	 * @return Boolean indicator for existence of fax info 
	 */
	public static boolean hasFax(DocumentRequestDTO docRequest)
	{
		return docRequest.getFax().length() > 0;
	} // hasFax

	/**
	 * Set the delivery method
	 * 
	 * @param val Delivery method
	 */
	public void setDeliveryMethod(String val)
	{
		this.deliveryMethod = val;
	} // setDeliveryMethod

	/**
	 * Set the destination zip code
	 * 
	 * @param val Destination zip code
	 */
	public void setDestZip(String val)
	{
		this.destZip = val;
	} // setDestZip

	/**
	 * Set the document type codes
	 * 
	 * @param val Document type codes
	 */
	public void setDocumentTypes(String vals)
	{
		this.documentTypes = vals;
	} // setDocumentTypes

	/**
	 * Set the e-mail address.
	 * 
	 * @param val E-mail address
	 */
	public void setEmail(String val)
	{
		this.email = val;
	} // setEmail

	/**
	 * Set the fax number.
	 * 
	 * @param val Fax phone number
	 */
	public void setFax(String val)
	{
		this.fax = val;
	} // setFax

	/**
	 * Set the fax attention.
	 * 
	 * @param val Fax attention notation
	 */
	public void setFaxAttention(String val)
	{
		this.faxAttention = val;
	} // setFaxAttention

	/**
	 * Set the tracking number
	 * 
	 * @param val Tracking number
	 */
	public void setTrackingNum(String val)
	{
		this.trackingNum = val;
	} // setTrackingNum
}