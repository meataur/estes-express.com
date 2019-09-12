package com.estes.myestes.profile.web.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.estes.myestes.profile.exception.AppException;
import com.estes.myestes.profile.web.dao.iface.ProfileDAO;
import com.estes.myestes.profile.web.dto.BasicProfile;
import com.estes.myestes.profile.web.dto.EmailAddress;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.Username;
import com.estes.myestes.profile.web.service.iface.ProfileService;


@Service("profileService")
public class ProfileServiceImpl implements ProfileService {
	
	@Autowired
	private ProfileDAO profileDAO;
	
	@Override
	public boolean updateUsername(String currentUsername,Username username) {
		return profileDAO.updateUsername(currentUsername, username.getUsername());
	}

	@Override
	public boolean updateEmailAddress(String username, EmailAddress emailAddress) {
		//System.out.println(username+":"+emailAddress);
		return profileDAO.updateEmailAddress(username, emailAddress.getEmail());
	}

	@Override
	public boolean changePassword(String username, Password password) {
		return profileDAO.changePassword(username, password);
	}

	@Override
	public boolean updateEmailPrefence(String username, String flag) {
		return profileDAO.updateEmailPrefence(username, flag);
	}

	@Override
	public Profile getUserProfile(String username) {
		Profile profile = profileDAO.getUserProfile(username);
		return profile;
	}
	
	@Override
	public boolean hasProfileInfo(String username) {
		return profileDAO.hasProfileInfo(username);
	}

	@Override
	public void updateProfile(String username, String accountCode, BasicProfile profile) {
		if(profileDAO.hasProfileInfo(username)){
			profileDAO.updateProfile(username, profile);
		}else{
			profileDAO.addProfile(username, accountCode, profile);
		}
		
	}
	
	
}
