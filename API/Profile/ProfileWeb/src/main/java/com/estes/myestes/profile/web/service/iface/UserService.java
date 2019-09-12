package com.estes.myestes.profile.web.service.iface;

import java.util.List;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.profile.web.dto.App;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.User;
import com.estes.myestes.profile.web.dto.UserStatus;
/**
 * 	
 * @author rahmaat<Ataur Rahman>
 *
 */
public interface UserService {
	
	/**
	 * Create Sub Account
	 * @param user
	 * @param username
	 */
	public void createUser(User user, String username);
	
	/**
	 * Get List of Sub Account
	 * @param username
	 * @param pageable
	 * @param search
	 * @param qFirstName
	 * @param qLastName
	 * @param qUsername
	 * @return
	 */
	
	public Page<Profile> getUsers(String username, Pageable pageable, String search,String qFirstName, String qLastName,String qUsername);
	
	/**
	 * Get Authoized Apps 
	 * @param parentUsername
	 * @param childUsername
	 * @return
	 */
	public List<App> getAuthorizedApps(String parentUsername, String childUsername);
	
	
	/**
	 * Get List of Blocked Apps
	 * @param parentUsername
	 * @param childUsername
	 * @return
	 */
	public List<App> getBlockedApps(String parentUsername, String childUsername);
	
	
	/**
	 * List of Apps added to 
	 * @param appNames
	 * @param parentUsername
	 * @param childUsername
	 */
	public void addToBlockedApps(List<String> appNames, String parentUsername, String childUsername);
	
	
	/**
	 * 
	 * @param parentUsername
	 * @param childUsername
	 */
	public void deleteAllBlockedApps(String parentUsername, String childUsername);
	
	
	/**
	 * 
	 * @param appNames
	 * @param parentUsername
	 * @param childUsername
	 */
	public void deleteBlockedApps(List<String> appNames,String parentUsername, String childUsername);
	

	/**
	 * 
	 * @param parentUsername
	 * @param childUsername
	 * @param status
	 */
	public void updateUserStatus(String parentUsername, String childUsername, UserStatus status);
	
	/**
	 * 
	 * @param parentUsername
	 * @param childUsername
	 * @param password
	 */
	public void changeChildUserPassword(String parentUsername, String childUsername, Password password);
	
}
