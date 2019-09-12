package com.estes.myestes.PickupRequest.web.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import lombok.Data;

@Entity
@Table(name="PICKUP_DETAIL_NOTIFY_XREF")
@Data
public class PickupDetailNotify{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PICKUP_DETAIL_NOTIFY_ID")
	private Integer id;

	@ManyToOne
	@JoinColumn(name="NOTIFY_PARTY_TYPE_ID")
	private NotifyPartyType notifyPartyType;

	@ManyToOne
	@JoinColumn(name="NOTIFY_STATUS_TYPE_ID")
	private NotifyStatusType notifyStatusType;
	
	@ManyToOne
	@JoinColumn(name="PICKUP_REQUEST_DETAIL_ID", nullable=false)
	private PickupRequestDetail pickupRequestDetail;
}