package com.estes.myestes.BillOfLading.web.dto.common;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Pickup Email Notification")
public class Notification {
	
	@ApiModelProperty(position=1,value= "Send Email When Accepted?")
	private boolean accepted;
	
	@ApiModelProperty(position=2,value="Send Email When Completed?")
	private boolean completed;
	
	@ApiModelProperty(position=3,value="Send Email When Rejected? This option should always be true")
	private boolean rejected=true;
	
}
