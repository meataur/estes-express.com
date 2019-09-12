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
@Table(name="NOTIFY_STATUS_TYPE_DEF")
@Data
@ApiModel(description="Notify Status Type")
public class NotifyStatusType{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="NOTIFY_STATUS_TYPE_ID")
	@ApiModelProperty(position=0,notes="Status Type Id")
	private Integer id;

	@Column(name="NOTIFY_STATUS_TYPE_NM")
	@ApiModelProperty(position=1,notes="Status Type Name")
	private String name;
	
	@Column(name="NOTIFY_STATUS_TYPE_DSC")
	@ApiModelProperty(position=2,notes="Status Type Description")
	private String description;

}