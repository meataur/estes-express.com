/**
 * @author: Todd Allen
 *
 * Creation date: 02/06/2019
 */

package com.estes.myestes.rating.dto;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonFormat.Shape;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * Delivery information
 */
@ApiModel(description="Delivery information")
@Data
public class Delivery
{
	@ApiModelProperty(notes="Delivery date - MM/DD/YYYY")
	@JsonFormat(shape=Shape.STRING,pattern="MM/dd/yyyy")
	private LocalDate date;
	@ApiModelProperty(notes="Delivery time - hh:mm:ss")
	private String time;

	public Delivery()
	{
	} // Constructor

	public Delivery(LocalDate dat, String tim)
	{
		this();
		this.date = dat;
		this.time = tim;
	} // Constructor

}
