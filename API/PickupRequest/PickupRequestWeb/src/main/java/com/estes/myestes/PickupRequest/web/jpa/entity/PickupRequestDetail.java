package com.estes.myestes.PickupRequest.web.jpa.entity;

import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Entity
@Table(name="PICKUP_REQUEST_DETAIL")
@Data
@ApiModel(description="Pickup Request Details")
public class PickupRequestDetail{

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PICKUP_REQUEST_DETAIL_ID")
	private Integer id;

	@Column(name="BOL_NO")
	private String bolNo;

	@Column(name="BOL_SEQ_NO")
	private String bolSeqNo;
	
	/**
	 * @StartPickupInformation
	 */
	
	@Column(name="PICKUP_DT")
	private Date pickupDate;
	
	@Column(name="AVAILABLE_TM")
	private Time startTime;
	
	@Column(name="CLOSE_TM")
	private Time endTime;

	@Column(name="PICKUP_HOOK_DROP_IND")
	private String pickupHookDrop;
	@Column(name="PICKUP_LIFTGATE_REQ_IND")
	private String pickupLiftgateRequired;

	@Column(name="PICKUP_PALLETS_NO_STACK_IND")
	private String pickupPalletsNoStackInd;
	
	@Column(name="PICKUP_INSTRUCT_TXT")
	private String pickupInstruction;

	/**
	 * @EndPickupInformation
	 */
	
	/**
	 * The following properties are for User/Requestor
	 * @StartRequester/User
	 * 
	 */
	@Column(name="REQUESTER_EMAIL_ADDRESS")
	private String requesterEmailAddress;

	@Column(name="REQUESTER_NM")
	private String requesterName;

	@Column(name="REQUESTER_PHONE_EXT")
	private String requesterPhoneExt;

	@Column(name="REQUESTER_PHONE_NO")
	private String requesterPhoneNo;

	@Column(name="REQUESTER_ROLE_CD")
	private String requesterRole;
	/**
	 * @EndRequestor
	 */
	
	@Column(name="SHIPMENT_FOOD_IND")
	private String shipmentFoodInd;

	@Column(name="SHIPMENT_FREEZE_IND")
	private String shipmentFreezeInd;

	@Column(name="SHIPMENT_OVERSIZE_IND")
	private String shipmentOversizeInd;

	@Column(name="SHIPMENT_POISON_IND")
	private String shipmentPoisonInd;

	@Column(name="SHIPPER_ACCOUNT_NO")
	private String shipperAccountNo;

	@Column(name="USER_NM")
	private String username;
	
	@Column(name="ACCOUNT_NO")
	private String accountNo;
		
	@Column(name="TEMPLATE_NM")
	private String templateName;

	@ManyToOne
	@JoinColumn(name="PICKUP_DETAIL_TYPE_ID")
	private PickupDetailType pickupDetailType;
	
	@Column(name="CHANGED_BY")
	private String changedBy;

	@Column(name="CHANGED_ON")
	private Timestamp changedOn;
	
	@Column(name="CREATED_BY")
	private String createdBy;

	@Column(name="CREATED_ON")
	private Timestamp createdOn;
}