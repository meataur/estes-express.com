/**
 * 
 */
package com.estes.framework.security;

import java.util.Collection;

import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextOperations;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.ldap.userdetails.UserDetailsContextMapper;

import com.estes.framework.logger.ESTESLogger;

/**
 * @author sinhasu
 * 
 */

public class EstesUserDetailsMapper implements UserDetailsContextMapper {

	private String rolePrefix;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.security.ldap.userdetails.UserDetailsContextMapper
	 * #mapUserFromContext(org.springframework.ldap.core.DirContextOperations,
	 * java.lang.String, java.util.Collection)
	 */
	@Override
	public UserDetails mapUserFromContext(DirContextOperations ctx, String username, Collection<? extends GrantedAuthority> authorities) {

		EstesUser user = new EstesUser();
		if (authorities != null && !authorities.isEmpty()) {
			for (GrantedAuthority grantedAuthority : authorities) {
				if(grantedAuthority != null && grantedAuthority.getAuthority() != null && grantedAuthority.getAuthority().toUpperCase().startsWith(getRolePrefix())){
					user.addAuthority(grantedAuthority);
				}
			}
		}
		user.setDisplayName(ctx.getStringAttribute("displayName"));
		user.setGivenName(ctx.getStringAttribute("givenName"));
		user.setUserPrincipalName(ctx.getStringAttribute("userPrincipalName"));
		//user.setUsername(ctx.getStringAttribute("sn"));
		user.setUsername(username);
		user.setCn(ctx.getStringAttribute("cn"));
		user.setPassword(null);
		ESTESLogger.log(ESTESLogger.INFO, getClass(), "mapUserFromContext()", "Successfully created EstesUser for user "+username);
		return user;
	}

	@Override
	public void mapUserToContext(UserDetails user, DirContextAdapter ctx) {
		// TODO Auto-generated method stub

	}

	/**
	 * @return the rolePrefix
	 */
	public String getRolePrefix() {
		return rolePrefix;
	}

	/**
	 * @param rolePrefix
	 *            the rolePrefix to set
	 */
	public void setRolePrefix(String rolePrefix) {
		this.rolePrefix = rolePrefix;
	}

}
