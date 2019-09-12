package com.estes.myestes.PickupRequest.web.jpa.entity;


import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonBackReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * The persistent class for the PARAMETER_DEF database table.
 * 
 */
@Entity
@Table(name = "PARAMETER_DEF")
@ApiModel(description="Parameter")
@Data
public class Parameter{
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAMETER_ID")
	@ApiModelProperty(position=0,notes="Parameter Id")
	private Integer id;
	
	@ManyToOne(cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@JoinColumn(name="PARAMETER_TYPE_ID", referencedColumnName="PARAMETER_TYPE_ID")
	@ApiModelProperty(position=1,notes="Parameter Type")
	@JsonBackReference
	private ParameterType type;
	
	@Column(name = "PARAMETER_CD")
	@ApiModelProperty(position=2,notes="Parameter Name")
	private String name;
	
	@Column(name = "PARAMETER_VAL")
	@ApiModelProperty(position=3,notes="Parameter Value")
	private String value;
	
	
	@Column(name = "PARAMETER_DSC")
	@ApiModelProperty(position=4,notes="Parameter Description")
	private String description;
	

	@Column(name = "SORT_ORDER_NO")
	@ApiModelProperty(position=5,notes="Parameter Sorting Order No")
	private Integer order;

}