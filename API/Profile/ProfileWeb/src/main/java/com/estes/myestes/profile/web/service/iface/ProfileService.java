package com.estes.myestes.profile.web.service.iface;

import com.estes.myestes.profile.web.dto.BasicProfile;
import com.estes.myestes.profile.web.dto.EmailAddress;
import com.estes.myestes.profile.web.dto.Password;
import com.estes.myestes.profile.web.dto.Profile;
import com.estes.myestes.profile.web.dto.Username;

public interface ProfileService {
	
	public boolean updateUsername(String currentUsername,Username username);
	
	public boolean updateEmailAddress(String username,EmailAddress emailAddress);
	
	public boolean changePassword(String username, Password password);
	
	public boolean updateEmailPrefence(String username,String flag);
	
	public Profile getUserProfile(String username);

	public boolean hasProfileInfo(String username);
	
	public void updateProfile(String username, String accountCode, BasicProfile profile);
}
