package com.estes.myestes.terminallist.dto;

import java.util.List;

import com.estes.dto.common.ServiceResponse;

import io.swagger.annotations.ApiModelProperty;

public class StateCoverage
{
	@ApiModelProperty(notes="Error code/message")
	ServiceResponse error;
	@ApiModelProperty(notes = "State/province")
	String state;
	@ApiModelProperty(notes = "List of Estes servicing terminals")
	List<Terminal> terminals;

	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public List<Terminal> getTerminals() {
		return terminals;
	}
	public void setTerminals(List<Terminal> terminals) {
		this.terminals = terminals;
	}
	public ServiceResponse getError() {
		return error;
	}
	public void setError(ServiceResponse error) {
		this.error = error;
	}
}
