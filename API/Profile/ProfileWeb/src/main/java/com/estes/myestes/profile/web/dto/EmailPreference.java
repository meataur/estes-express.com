package com.estes.myestes.profile.web.dto;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="This model will be used for updating email preference")
@NotNull(message="Email Preference can not be null")
public class EmailPreference {
	
	@NotNull(message="Preference can not be null")
	@ApiModelProperty(name="Email Preference",notes="Email Preference can have two value 'Y' or 'N'")
	private String preference;
	
	@AssertTrue(message="Preference can be either 'Y' or 'N'")
    public boolean isPreference() {
        return preference!=null && ( preference.trim().equalsIgnoreCase("Y") || preference.trim().equalsIgnoreCase("N"));
    }
}
