package com.estes.myestes.PickupRequest.web.jpa.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import io.swagger.annotations.ApiModel;
import lombok.Data;

@Entity
@Table(name="PICKUP_DETAIL_TYPE_DEF")
@ApiModel(description="Pickup Detail Type")
@Data
public class PickupDetailType{
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="PICKUP_DETAIL_TYPE_ID")
	private Integer id;

	@Column(name="PICKUP_DETAIL_TYPE_NM")
	private String name;
	
	@Column(name="PICKUP_DETAIL_TYPE_DSC")
	private String description;

}