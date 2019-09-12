package com.estes.myestes.PickupRequest.web.jpa.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Entity
@Table(name="NOTIFY_PARTY_TYPE_DEF")
@Data
@ApiModel(description="Notify Party Type")
public class NotifyPartyType {

	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NOTIFY_PARTY_TYPE_ID")
	@ApiModelProperty(position=0,notes="Party Type Id")
	private Integer id;
	
	@Column(name="NOTIFY_PARTY_TYPE_NM")
	@ApiModelProperty(position=1, notes="Party Type Name")
	private String name;
	
	@Column(name="NOTIFY_PARTY_TYPE_DSC")
	@ApiModelProperty(position=2, notes="Party Type Description")
	private String description;
	
}