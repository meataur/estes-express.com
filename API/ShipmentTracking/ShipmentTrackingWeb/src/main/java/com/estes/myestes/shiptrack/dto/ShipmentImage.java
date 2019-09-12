/**
 * @author: Todd Allen
 *
 * Creation date: 07/23/2018
 */

package com.estes.myestes.shiptrack.dto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Shipment tracking images
 */
@ApiModel(description="Scanned image information")
@Data
@NoArgsConstructor
public class ShipmentImage
{
	
	@ApiModelProperty(position=1, notes="Image request number")
	private String requestNumber;
	@ApiModelProperty(position=2, notes="PRO associated with image")
	private String proNumber;
	@ApiModelProperty(position=3, notes="Image path")
	private String imagePath;
	@ApiModelProperty(position=4, notes="Image path")
	private boolean found;
	@ApiModelProperty(position=5, notes="Image path")
	private boolean retry;
	
	public ShipmentImage(String requestNumber, String proNumber) {
		this.requestNumber = requestNumber;
		this.proNumber = proNumber;
	}
}
