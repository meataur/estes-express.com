package com.estes.myestes.ImageViewing.web.dto;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URL;
import java.util.List;

import javax.imageio.ImageIO;
import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@ApiModel(description="Image Document Email Request")
public class ImageEmail {
	
	@NotNull
	@ApiModelProperty(position=1, required=true, allowEmptyValue=false,notes="Image Url")
	@Size(min=1,message="Please select at least one document.")
	private List<String> imageUrl;
	
	@ApiModelProperty(position=2, required=false, notes="User Email Address. It is optional upon checkbox selection.")
	private String userEmail;
	
	@NotNull
	@ApiModelProperty(position=3, required=true, allowEmptyValue=false, notes="Recipient email addresses. At least one email address is required")
	@Size(min=1,message="At least one email address is required")
	private List<String> recipientEmails;
	
	@AssertTrue(message="Please select at least one document.")
	public boolean isImageUrl(){
		try {
			for(String imgUrl : imageUrl){
				 URL url = new URL(imgUrl);
				 BufferedImage image = ImageIO.read(url);
				 if(image==null){
					 return false;
				 }
			}
            return true;
        } catch (IOException e) {
        	return false;
        }
	}
}
