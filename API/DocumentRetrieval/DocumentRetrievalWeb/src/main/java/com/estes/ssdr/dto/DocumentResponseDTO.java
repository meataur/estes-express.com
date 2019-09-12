/**
 * @author: Todd Allen
 *
 * Creation date: 03/09/2016
 *
 */

package com.estes.ssdr.dto;

/**
 * Document response DTO
 */
public class DocumentResponseDTO
{
	private String code = "";
	private String message = "";
	private String pro = "";
	private String originTerminal = "";
	private String originTermPhone = "";
	private String destTerminal = "";
	private String destTermPhone = "";

	/**
	 * Create a document retrieval response
	 */
	public DocumentResponseDTO()
	{
		super();
	} // Constructor

	/**
	 * Get the response code.
	 * 
	 * @return Response code
	 */
	public String getCode()
	{
		return this.code;
	} // getCode

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
	 * Get the message
	 * 
	 * @return Return message
	 */
	public String getMessage()
	{
		return this.message;
	} // getMessage

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
	 * Set the response code
	 * 
	 * @param val Response code
	 */
	public void setCode(String val)
	{
		this.code = val;
	} // setCode

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
	 * Set the return message
	 * 
	 * @param val Message
	 */
	public void setMessage(String val)
	{
		this.message = val;
	} // setMessage

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