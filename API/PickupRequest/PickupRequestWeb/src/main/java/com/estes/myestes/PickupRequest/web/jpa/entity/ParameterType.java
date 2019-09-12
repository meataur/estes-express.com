package com.estes.myestes.PickupRequest.web.jpa.entity;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * The persistent class for the PARAMETER_TYPE_DEF database table.
 * 
 */
@Entity
@Table(name = "PARAMETER_TYPE_DEF")
@Data
@ApiModel(description="Parameter Type")
public class ParameterType { 
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "PARAMETER_TYPE_ID")
	@ApiModelProperty(position=0,notes="Parameter Type Id")
	private Integer id;

	@Column(name = "PARAMETER_TYPE_NM")
	@ApiModelProperty(position=1,notes="Parameter Type Name")
	private String name;
	
	@Column(name = "PARAMETER_TYPE_DSC")
	@ApiModelProperty(position=2,notes="Parameter Type Description")
	private String description;
	
	@OneToMany(mappedBy="type",cascade={CascadeType.ALL},fetch=FetchType.EAGER)
	@ApiModelProperty(position=1,notes="Parameter Type")
	@JsonManagedReference
	private List<Parameter> parameters;
}