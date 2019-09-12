package com.estes.myestes.quickLinks.web.service.iface;

import java.util.List;

import com.estes.myestes.quickLinks.web.dto.QuickLink;

public interface QuickLinkService {
	
	
	public List<QuickLink> getDefaultQuickLinks();
	
	public List<QuickLink> getUserQuickLinks(String username);
	
	public List<QuickLink> getAvailableQuickLinks(String username);
	
	public QuickLink addQuickLink(QuickLink quickLink,String username);
	
	public void deleteQuickLink(QuickLink quickLink, String username);
	
	public void setDefaultQuickLinks(String username);
}
