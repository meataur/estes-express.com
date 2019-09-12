/**
 * @author: Todd Allen
 *
 * Creation date: 03/09/2016
 *
 */

package com.estes.ssdr.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Document information
 */
@ApiModel(description="Document information")
public class DocumentInfo
{
	@ApiModelProperty(notes="Estes PRO")
	private String pro = "";
	@ApiModelProperty(notes="Estes origin terminal of shipment")
	private String originTerminal = "";
	@ApiModelProperty(notes="Estes origin terminal phone number")
	private String originTermPhone = "";
	@ApiModelProperty(notes="Estes destination terminal of shipment")
	private String destTerminal = "";
	@ApiModelProperty(notes="Estes destination terminal phone number")
	private String destTermPhone = "";

	/**
	 * Create a document information object
	 */
	public DocumentInfo()
	{
		super();
	} // Constructor

	public DocumentInfo(DocumentResponseDTO dto)
	{
		this();
		setPro(dto.getPro());
		setOriginTerminal(dto.getOriginTerminal());
		setOriginTermPhone(dto.getOriginTermPhone());
		setDestTerminal(dto.getDestTerminal());
		setDestTermPhone(dto.getDestTermPhone());
	} // Constructor

	/**
	 * Get the destination terminal.
	 * 
	 * @return Destination terminal
	 */
	public String getDestTerminal()
	{
		return this.destTerminal;
	} // getDestTerminal

	/**
	 * Get the destination terminal phone number.
	 * 
	 * @return Destination terminal phone number
	 */
	public String getDestTermPhone()
	{
		return this.destTermPhone;
	} // getDestTermPhone

	/**
	 * Get the origin terminal.
	 * 
	 * @return Origin terminal
	 */
	public String getOriginTerminal()
	{
		return this.originTerminal;
	} // getOriginTerminal

	/**
	 * Get the origin terminal phone number.
	 * 
	 * @return Origin terminal phone number
	 */
	public String getOriginTermPhone()
	{
		return this.originTermPhone;
	} // getEmail

	/**
	 * Get the PRO.
	 * 
	 * @return PRO
	 */
	public String getPro()
	{
		return this.pro;
	} // getPro

	/**
	 * Set the destination terminal number.
	 * 
	 * @param val Destination terminal number
	 */
	public void setDestTerminal(String val)
	{
		this.destTerminal = val;
	} // setDestTerminal

	/**
	 * Set the destination terminal phone number.
	 * 
	 * @param val Destination terminal phone number
	 */
	public void setDestTermPhone(String val)
	{
		this.destTermPhone = val;
	} // setDestTermPhone

	/**
	 * Set the origin terminal
	 * 
	 * @param val Origin terminal number
	 */
	public void setOriginTerminal(String val)
	{
		this.originTerminal = val;
	} // setOriginTerminal

	/**
	 * Set the origin terminal phone number.
	 * 
	 * @param val Origin terminal phone number
	 */
	public void setOriginTermPhone(String val)
	{
		this.originTermPhone = val;
	} // setOriginTermPhone

	/**
	 * Set the PRO
	 * 
	 * @param val PRO
	 */
	public void setPro(String val)
	{
		this.pro = val;
	} // setPro
}