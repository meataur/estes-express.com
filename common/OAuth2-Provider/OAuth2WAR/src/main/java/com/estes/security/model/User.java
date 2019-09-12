package com.estes.security.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

public class User implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String name;
	private String password;;
	private List<SimpleGrantedAuthority> authorities = new ArrayList<SimpleGrantedAuthority>();
	
	private AdditionalInformation info;
	 
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	
	public void addAuthority(SimpleGrantedAuthority role){
		authorities.add(role);
	}
	public List<SimpleGrantedAuthority> getAuthorities() {
		return authorities;
	}
	public void setAuthorities(List<SimpleGrantedAuthority> authorities) {
		this.authorities = authorities;
	}
	public AdditionalInformation getInfo() {
		return info;
	}
	public void setInfo(AdditionalInformation info) {
		this.info = info;
	}
	
}