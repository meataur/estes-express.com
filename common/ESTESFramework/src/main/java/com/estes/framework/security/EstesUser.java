/**
 * 
 */
package com.estes.framework.security;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * @author sinhasu
 * 
 */
public class EstesUser implements UserDetails {

	private static final long serialVersionUID = 1L;

	private String password;

	private String username;

	private boolean accountNonExpired = true;

	private boolean accountNonLocked = true;

	private boolean credentialsNonExpired = true;

	private boolean enabled = true;

	private Integer applicationId;

	private String displayName;

	private String givenName;

	private String userPrincipalName;

	private String cn;

	private Collection<GrantedAuthority> authorities = new ArrayList<GrantedAuthority>();

	private boolean authorizedToAccessCurrentEnvironment = true;

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	/**
	 * @param pAuthorities
	 *            the authorities to set
	 */
	public void setAuthorities(Collection<GrantedAuthority> pAuthorities) {
		List<GrantedAuthority> grantedAuthorities = new ArrayList<GrantedAuthority>();
		grantedAuthorities.addAll(pAuthorities);
		this.authorities = grantedAuthorities;
	}

	/**
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return the accountNonExpired
	 */
	public boolean isAccountNonExpired() {
		return accountNonExpired;
	}

	/**
	 * @param accountNonExpired
	 *            the accountNonExpired to set
	 */
	public void setAccountNonExpired(boolean accountNonExpired) {
		this.accountNonExpired = accountNonExpired;
	}

	/**
	 * @return the accountNonLocked
	 */
	public boolean isAccountNonLocked() {
		return accountNonLocked;
	}

	/**
	 * @param accountNonLocked
	 *            the accountNonLocked to set
	 */
	public void setAccountNonLocked(boolean accountNonLocked) {
		this.accountNonLocked = accountNonLocked;
	}

	/**
	 * @return the credentialsNonExpired
	 */
	public boolean isCredentialsNonExpired() {
		return credentialsNonExpired;
	}

	/**
	 * @param credentialsNonExpired
	 *            the credentialsNonExpired to set
	 */
	public void setCredentialsNonExpired(boolean credentialsNonExpired) {
		this.credentialsNonExpired = credentialsNonExpired;
	}

	/**
	 * @return the enabled
	 */
	public boolean isEnabled() {
		return enabled;
	}

	/**
	 * @param enabled
	 *            the enabled to set
	 */
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	/**
	 * @return the applicationId
	 */
	public Integer getApplicationId() {
		return applicationId;
	}

	/**
	 * @param applicationId
	 *            the applicationId to set
	 */
	public void setApplicationId(Integer applicationId) {
		this.applicationId = applicationId;
	}

	/**
	 * @return the authorizedToAccessCurrentEnvironment
	 */
	public boolean isAuthorizedToAccessCurrentEnvironment() {
		return authorizedToAccessCurrentEnvironment;
	}

	/**
	 * @param authorizedToAccessCurrentEnvironment
	 *            the authorizedToAccessCurrentEnvironment to set
	 */
	public void setAuthorizedToAccessCurrentEnvironment(boolean authorizedToAccessCurrentEnvironment) {
		this.authorizedToAccessCurrentEnvironment = authorizedToAccessCurrentEnvironment;
	}

	/**
	 * @return the givenName
	 */
	public String getGivenName() {
		return givenName;
	}

	/**
	 * @param givenName
	 *            the givenName to set
	 */
	public void setGivenName(String givenName) {
		this.givenName = givenName;
	}

	/**
	 * @return the userPrincipalName
	 */
	public String getUserPrincipalName() {
		return userPrincipalName;
	}

	/**
	 * @param userPrincipalName
	 *            the userPrincipalName to set
	 */
	public void setUserPrincipalName(String userPrincipalName) {
		this.userPrincipalName = userPrincipalName;
	}

	/**
	 * @return the displayName
	 */
	public String getDisplayName() {
		return displayName;
	}

	/**
	 * @param displayName
	 *            the displayName to set
	 */
	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	/**
	 * @return the cn
	 */
	public String getCn() {
		return cn;
	}

	/**
	 * @param cn
	 *            the cn to set
	 */
	public void setCn(String cn) {
		this.cn = cn;
	}

	/**
	 * Adds the authority to the list, unless it is already there, in which case
	 * it is ignored
	 */
	public void addAuthority(GrantedAuthority pAuthority) {
		if (!hasAuthority(pAuthority)) {
			authorities.add(pAuthority);
		}
	}

	private boolean hasAuthority(GrantedAuthority pAuthority) {
		for (GrantedAuthority authority : authorities) {
			if (authority.equals(pAuthority)) {
				return true;
			}
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "EstesUser [password=" + password + ", username=" + username + ", accountNonExpired=" + accountNonExpired
				+ ", accountNonLocked=" + accountNonLocked + ", credentialsNonExpired=" + credentialsNonExpired + ", enabled=" + enabled
				+ ", applicationId=" + applicationId + ", displayName=" + displayName + ", givenName=" + givenName + ", userPrincipalName="
				+ userPrincipalName + ", cn=" + cn + ", authorities=" + authorities + ", authorizedToAccessCurrentEnvironment="
				+ authorizedToAccessCurrentEnvironment + "]";
	}

}
