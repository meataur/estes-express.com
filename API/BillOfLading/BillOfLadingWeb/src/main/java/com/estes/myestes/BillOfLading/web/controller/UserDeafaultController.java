package com.estes.myestes.BillOfLading.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.BillOfLading.config.AuthenticationDetails;
import com.estes.myestes.BillOfLading.web.dto.Accessorial;
import com.estes.myestes.BillOfLading.web.dto.AddressInformation;
import com.estes.myestes.BillOfLading.web.dto.BillOfLading;
import com.estes.myestes.BillOfLading.web.dto.BillTo;
import com.estes.myestes.BillOfLading.web.dto.BillingInformation;
import com.estes.myestes.BillOfLading.web.dto.CommodityInformation;
import com.estes.myestes.BillOfLading.web.dto.EmailAndFaxNotification;
import com.estes.myestes.BillOfLading.web.dto.PickupDetailInfo;
import com.estes.myestes.BillOfLading.web.dto.ShippingLabel;
import com.estes.myestes.BillOfLading.web.dto.common.Notification;
import com.estes.myestes.BillOfLading.web.service.iface.UserDeafultService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.Authorization;
import springfox.documentation.annotations.ApiIgnore;

@Api(description="## - To retrieve and update User Default Information when creating & editing BOL/Draft/Template")
@RestController
public class UserDeafaultController {
	
	@Autowired
	private UserDeafultService userDeafultService;
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get a new BOL with User default Information",notes="This service returns a new BOL with user default information ")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BillOfLading.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/user/bol")
	public ResponseEntity<?> getNewDefaultBol(@ApiIgnore AuthenticationDetails auth){
		BillOfLading bol = userDeafultService.getUserDefaultBol(auth);
		return new ResponseEntity<>(bol,HttpStatus.OK);
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default pickup request information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=PickupDetailInfo.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/user/pickup_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultPickupRequestInformation(@ApiIgnore AuthenticationDetails auth){
		String accessToken = auth.getAccessToken();
		PickupDetailInfo pickupDetailInfo = userDeafultService.getUserPickupRequestInformation(accessToken);
		
		return new ResponseEntity<>(pickupDetailInfo,HttpStatus.OK);
		
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default pickup request information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/user/pickup_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultPickupRequestInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody PickupDetailInfo pickupDetailInfo){
		String accessToken = auth.getAccessToken();
		userDeafultService.updateUserPickupRequestInformation(accessToken, pickupDetailInfo);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default shipper information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=AddressInformation.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@GetMapping(value="/user/shipper_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultShipperInformation(@ApiIgnore AuthenticationDetails auth){
		
		String username = auth.getUsername();
		AddressInformation shipper = userDeafultService.getUserShipperInformation(username);
		
		return new ResponseEntity<>(shipper,HttpStatus.OK);
		
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default shipper information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@PostMapping(value="/user/shipper_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultShipperInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody AddressInformation shipper){
		String username = auth.getUsername();
		userDeafultService.updateUserShipperInformation(username,shipper);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default consignee information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=AddressInformation.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/user/consignee_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultConsigneeInformation(@ApiIgnore AuthenticationDetails auth){
		
		String username = auth.getUsername();
		AddressInformation shipper = userDeafultService.getUserConsigneeInformation(username);
		
		return new ResponseEntity<>(shipper,HttpStatus.OK);
		
	}
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default consignee information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/user/consignee_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultConsigneeInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody AddressInformation consignee){
		String username = auth.getUsername();
		userDeafultService.updateUserConsigneeInformation(username,consignee);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default commodity information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=CommodityInformation.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@GetMapping(value="/user/commodity_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultCommodityInformation(@ApiIgnore AuthenticationDetails auth){
		
		String username = auth.getUsername();
		CommodityInformation commodityInformation = userDeafultService.getUserCommodityInformation(username);
		
		return new ResponseEntity<>(commodityInformation,HttpStatus.OK);
		
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default commodity information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@PostMapping(value="/user/commodity_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultCommodityInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody CommodityInformation commodityInformation){
		String username = auth.getUsername();
		userDeafultService.updateUserCommodityInformation(username,commodityInformation);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default billing information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=BillingInformation.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@GetMapping(value="/user/billing_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultBillingInformation(@ApiIgnore AuthenticationDetails auth){
		
		String username = auth.getUsername();
		BillTo billingInformation = userDeafultService.getUserBillingInformation(username);
		
		return new ResponseEntity<>(billingInformation,HttpStatus.OK);
		
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default billing information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/user/billing_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultBillingInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody BillTo billingInformation){
		String username = auth.getUsername();
		userDeafultService.updateUserBillingInformation(username,billingInformation);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default Accessorial information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=Accessorial.class, responseContainer="List",message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@GetMapping(value="/user/accessorial_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultAccessorialInformation(@ApiIgnore AuthenticationDetails auth){
		
		String username = auth.getUsername();
		List<Accessorial> accessorials = userDeafultService.getUserAccessorialInformation(username);
		
		return new ResponseEntity<>(accessorials,HttpStatus.OK);
		
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default accessorial information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/user/accessorial_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultAccessorialInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody List<Accessorial> accessorials){
		String username = auth.getUsername();
		userDeafultService.updateUserAccessorialInformation(username,accessorials);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default shipping label information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ShippingLabel.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@GetMapping(value="/user/shipping_label_information",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultShippingLabelInformation(@ApiIgnore AuthenticationDetails auth){
		
		String username = auth.getUsername();
		ShippingLabel shippingLabel = userDeafultService.getUserShippingLabelInformation(username);
		
		return new ResponseEntity<>(shippingLabel,HttpStatus.OK);
		
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default shipping label information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@PostMapping(value="/user/shipping_label_information",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultShippingLabelInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody ShippingLabel shippingLabel){
		String username = auth.getUsername();
		userDeafultService.updateUserShippingLabelInformation(username,shippingLabel);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	

	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value = "To get user default email & fax notification information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=EmailAndFaxNotification.class, message = "Successful Response"),
			@ApiResponse(code = 400, response=ServiceResponse.class, responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500,  message = "Internal Server Error"),
	})
	@GetMapping(value="/user/notifications",produces={"application/json"})
	public ResponseEntity<?> getUserDefaultEmailAndFaxNotificationInformation(@ApiIgnore AuthenticationDetails auth){
		EmailAndFaxNotification emailAndFaxNotification = userDeafultService.getUserNotificationInformation(auth);
		
		return new ResponseEntity<>(emailAndFaxNotification,HttpStatus.OK);
		
	}
	
	
	@ApiOperation(authorizations = {@Authorization(value = "Bearer")}, value="To update user default email & fax notification information")
	@ApiResponses(value = { 
			@ApiResponse(code = 200, response=ServiceResponse.class, message = "Successful Response with empty message & code"),
			@ApiResponse(code = 400, response=ServiceResponse.class,responseContainer="List", message = "Error Response"),
			@ApiResponse(code = 500, message = "Internal Server Error"),
	})
	@PostMapping(value="/user/notifications",consumes={"application/json"},produces={"application/json"})
	public ResponseEntity<?> updateUserDefaultEmailAndFaxNotificationInformation(@ApiIgnore AuthenticationDetails auth,
			@Valid @RequestBody EmailAndFaxNotification emailAndFaxNotification, Notification notification){
		userDeafultService.updateUserNotificationInformation(auth,emailAndFaxNotification, notification);
		ServiceResponse response = new ServiceResponse("","");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
}
