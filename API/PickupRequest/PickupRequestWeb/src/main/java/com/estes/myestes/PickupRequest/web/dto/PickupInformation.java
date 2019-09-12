package com.estes.myestes.PickupRequest.web.dto;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

import com.estes.myestes.PickupRequest.util.EstesUtil;
import com.fasterxml.jackson.annotation.JsonFormat;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Pickup Information")
public class PickupInformation {
	
	@ApiModelProperty(position=1, value="Pickup Date - MM/DD/YYYY")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "MM/dd/yyyy",timezone="EST")
	private Date pickupDate;
	
	@ApiModelProperty(position=2, value="Start Time / Available by - hh:mm a (ex. 08:30 AM / 05:00 PM)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	private LocalTime startTime;
	
	@ApiModelProperty(position=3,value="End Time / Close At  - hh:mm a (ex. 08:30 AM / 05:00 PM)")
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "hh:mm a")
	private LocalTime endTime;
	
	@ApiModelProperty(position=4,value="Is Hook/Drop")
	private boolean hookOrDrop;
	
	@ApiModelProperty(position=5,value="Is Liftgate Required At Pickup")
	private boolean liftgateRequired;
	
	@ApiModelProperty(position=6,value="Do not Stack Palletes?")
	private boolean noStackPallet;
	
	
	public String formatPickupDate(){
		return EstesUtil.formatDate(pickupDate, "MM/dd/yyyy");
	}
	
	private String getTimeOnly(LocalTime localTime){
		 try{
			 DateTimeFormatter pattern = DateTimeFormatter.ofPattern("hh:mm a");
				return localTime.format(pattern);
		 }catch(Exception ex){
			 return "";
		 }
		
	}
	
	public String formatStartTime(){
		if(startTime==null){
			return "";
		}
		
		return getTimeOnly(startTime);
		
	}
	public String formatEndTime(){
		
		if(endTime==null){
			return "";
		}
		
		return getTimeOnly(endTime);
		
	}
	
	public String formatPickupInstructions() {
		
		StringBuilder pickupInstructions = new StringBuilder("YYY");
		
		if(hookOrDrop==false){
			pickupInstructions.insert(0, " ");
		}
		
		if(liftgateRequired==false){
			pickupInstructions.insert(1, " ");
		}
		
		if(noStackPallet==false){
			pickupInstructions.insert(2, " ");
		}
		
		return pickupInstructions.toString();
	}
	
}
