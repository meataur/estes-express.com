package com.estes.myestes.profile.web.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estes.dto.common.rest.Page;
import com.estes.dto.common.rest.Pageable;
import com.estes.myestes.profile.exception.AppException;
import com.estes.myestes.profile.web.dao.iface.ProfileDAO;
import com.estes.myestes.profile.web.dao.iface.UserDAO;
import com.estes.myestes.profile.web.dto.App;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.User;
import com.estes.myestes.profile.web.dto.UserStatus;
import com.estes.myestes.profile.web.service.iface.UserService;

@Service("userService")
public class UserServiceImpl implements UserService {
	@Autowired
	private UserDAO userDAO;
	@Autowired
	private ProfileDAO profileDAO;
	
	@Override
	@Transactional
	public void createUser(User user, String username) {
		userDAO.createUser(user, username);
	}


	
	@Override
	public Page<Profile> getUsers(String username, Pageable pageable, String search, String qFirstName,
			String qLastName, String qUsername) {
		return userDAO.getUsers(username, pageable, search, qFirstName, qLastName, qUsername);
	}

	@Override
	public List<App> getAuthorizedApps(String parentUsername, String childUsername) {
		return userDAO.getAuthorizedApps(parentUsername,childUsername);
	}

	@Override
	public List<App> getBlockedApps(String parentUsername, String childUsername) {
		return userDAO.getBlockedApps(parentUsername,childUsername);
	}
	
	
	private void checkChildUsername(String parentUsername, String childUsername){
		/**
		 * check the username is in child User List of authenticated users.
		 * 
		 */
		
		if(! userDAO.checkChildUser(parentUsername,childUsername)){
			throw new AppException(HttpStatus.NOT_FOUND, childUsername+" is not found in your sub account list!");
		}
		
	}
	
	@Override
	@Transactional
	public void addToBlockedApps(List<String> appNames, String parentUsername, String childUsername) {
		
		/**
		 * check the appNames size
		 */
		int initialSize = appNames.size();
		/**
		 * throw exception if the size is empty
		 */
		if(initialSize==0){
			
			throw new AppException(HttpStatus.BAD_REQUEST,"Invalid list of App Name");
		}
		
		checkChildUsername(parentUsername, childUsername);
		
		
		/**
		 * Get List of Blocked Apps for childUsername
		 */
		List<App> blockedApps = userDAO.getBlockedApps(parentUsername,childUsername);
		
		/**
		 * Extract List of AppNames form the list of blocked apps
		 */
		List<String> blockedAppNames = new ArrayList<>();
		
		for(App app: blockedApps){
			blockedAppNames.add(app.getName());
		}
		
		
		/**
		 * remove the already blocked apps from requested list of app names.
		 */
		
		appNames.removeAll(blockedAppNames);
		
		/**
		 * After removal, if the size is empty , throw exception
		 */
		
		if(appNames.size()==0){
			throw new AppException(HttpStatus.BAD_REQUEST,"You have already added these apps to blocked list");
		}
		
		
		/**
		 * add the remaining appNames to blocked List
		 */
		userDAO.addToBlockedApps(appNames,childUsername);
	}

	@Override
	public void deleteAllBlockedApps(String parentUsername, String childUsername) {
		
		checkChildUsername(parentUsername, childUsername);
		
		userDAO.deleteAllBlockedApps(parentUsername,childUsername);
	}
	

	@Override
	public void deleteBlockedApps(List<String> appNames, String parentUsername, String childUsername) {
		/**
		 * Check Child User is created by Parent User
		 */
		checkChildUsername(parentUsername, childUsername);
		
		/**
		 * Delete appNames from blocked List
		 */
		userDAO.deleteBlockedApps(appNames,parentUsername,childUsername);
		
	}

	@Override
	public void updateUserStatus(String parentUsername, String childUsername, UserStatus status) {
		/**
		 * Check the Child User is created by ParentUser.
		 */
		checkChildUsername(parentUsername, childUsername);
		
		/**
		 * Get current Status
		 */
		
		UserStatus userStatus = userDAO.getUserStatus(childUsername);
		
		if(userStatus==status){
			String message = status.name().toLowerCase();
			throw new AppException(HttpStatus.BAD_REQUEST,"This user is already "+message);
		}
		
		if(status == UserStatus.DISABLED){
			userDAO.disableUser(childUsername, parentUsername);
		}else{
			userDAO.enableUser(childUsername);
		}		
		
	}
	
	

	@Override
	public void changeChildUserPassword(String parentUsername, String childUsername, Password password) {
		/**
		 * Check the child user created by Parent User
		 */
		checkChildUsername(parentUsername, childUsername);
		
		/**
		 * Change the Child User Password
		 */
		profileDAO.changePassword(childUsername, password);
		
	}

}