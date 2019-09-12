package com.estes.myestes.quickLinks.web.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.estes.myestes.quickLinks.exception.AppException;
import com.estes.myestes.quickLinks.web.dao.iface.QuickLinkDAO;
import com.estes.myestes.quickLinks.web.dto.QuickLink;
import com.estes.myestes.quickLinks.web.service.iface.QuickLinkService;


@Service("quickLinkService")
public class QuickLinkServiceImpl implements QuickLinkService {
	
	@Autowired
	QuickLinkDAO quickLinkDAO;
	
	@Override
	public List<QuickLink> getDefaultQuickLinks() {
		return quickLinkDAO.getDefaultQuickLinks();
	}

	@Override
	public List<QuickLink> getUserQuickLinks(String username) {
		return quickLinkDAO.getUserQuickLinks(username);
	}

	@Override
	public List<QuickLink> getAvailableQuickLinks(String username) {
		return quickLinkDAO.getAvailableQuickLinks(username);
	}
	
	@Transactional
	@Override
	public QuickLink addQuickLink(QuickLink quickLink, String username) {
		/**
		 * Check if the user has exceeded the number of quick links
		 * the default number quick links he can add is 5
		 */
		if(quickLinkDAO.getUserQuickLinkTotal(username)>=5){
			throw new AppException("You must remove a Quick Link before you can add a new one.");
		}
		
		/**
		 * Check if the new one is already added to the quick link or not
		 */
		
		if(quickLinkDAO.checkNewLinkInUserQuickLinks(quickLink, username)){
			throw new AppException("You have already added this quick link");
		}
		
		
		/**
		 * Check the new one is on his available list & find from the actual quickLink 
		 * from the available list to be persisted on User Quick Link
		 */
		QuickLink myQuickLink = quickLinkDAO.getQuickLinkFromAvailableQuickLinks(quickLink, username);
				
		if(myQuickLink==null){
			throw new AppException("Sorry! quick link is not found.");
		}
		
		/**
		 * Persist the my quickLink to User quick link
		 */
		quickLinkDAO.addQuickLink(myQuickLink, username);
		return myQuickLink;
	}
	
	
	@Transactional
	@Override
	public void deleteQuickLink(QuickLink quickLink, String username) {
		
		/**
		 * Check if this one is in user quick link or not
		 */
		
		if(! quickLinkDAO.checkNewLinkInUserQuickLinks(quickLink, username)){
			throw new AppException("Sorry! quickLink is not found.");
		}
		
		quickLinkDAO.deleteQuickLink(quickLink, username);
	}
	
	
	@Override
	public void setDefaultQuickLinks(String username) {
		/**
		 * Remove all the existing User quick links
		 */
		
		quickLinkDAO.deleteAllQuickLink(username);
		
		/**
		 * Retrieve the Default Quick Links 
		 */
		
		List<QuickLink> quickLinks = quickLinkDAO.getDefaultQuickLinks();
		
		
		/**
		 * Persist the default QuickLinks to User Quick Link
		 */
		quickLinkDAO.addQuickLinks(quickLinks, username);
		
	}

}
