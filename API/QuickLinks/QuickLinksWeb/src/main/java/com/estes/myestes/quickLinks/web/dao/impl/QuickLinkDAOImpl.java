package com.estes.myestes.quickLinks.web.dao.impl;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BatchPreparedStatementSetter;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.estes.myestes.quickLinks.web.dao.iface.QuickLinkDAO;
import com.estes.myestes.quickLinks.web.dto.QuickLink;

@Repository("quickLinkDAO")
public class QuickLinkDAOImpl implements QuickLinkDAO {
	
	@Autowired
	private JdbcTemplate jdbcTemplate;
	
	@Override
	public List<QuickLink> getDefaultQuickLinks() {
		List<QuickLink> quickLinks = new ArrayList<>();

		final SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(DEFAULT_QUICK_LINKS);
		
		while (queryForRowSet.next()) {
			QuickLink quickLink = new QuickLink();
			quickLink.setAppName(queryForRowSet.getString("APPNAME").trim());
			quickLink.setAppCategory(queryForRowSet.getString("APPCATEGORY").trim());
			quickLink.setLinkOrder(queryForRowSet.getInt("LINKORDER"));
			quickLink.setDescription(queryForRowSet.getString("DESCRIPTION").trim());
			quickLinks.add(quickLink);
		}
		
		return quickLinks;
	}

	
	

	@Override
	public List<QuickLink> getUserQuickLinks(String username) {
		List<QuickLink> quickLinks = new ArrayList<>();

		final SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(USER_QUICK_LINKS,new Object[]{username});
		
		while (queryForRowSet.next()) {
			
			QuickLink quickLink = new QuickLink();
			quickLink.setAppName(queryForRowSet.getString("APPNAME").trim());
			quickLink.setAppCategory(queryForRowSet.getString("APPCATEGORY").trim());
			String orderStr = queryForRowSet.getString("LINKORDER");
			
			int order = 0;
			try{
				order = Integer.parseInt(orderStr.trim());
			}catch(Exception ex){
			}
			
			quickLink.setLinkOrder(order);
			quickLink.setDescription(queryForRowSet.getString("DESCRIPTION").trim());
			quickLinks.add(quickLink);
		}
		
		return quickLinks;
	}
	
	private int getNewQuickLinkOrder(String username){
		List<QuickLink> quickLinks = getUserQuickLinks(username);
		return quickLinks.size()+1;
	}
	
	
	@Override
	public boolean checkNewLinkInUserQuickLinks(QuickLink quickLink, String username) {
		final SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(USER_QUICK_LINKS,new Object[]{username});
		
		while (queryForRowSet.next()) {
			
			if(queryForRowSet.getString("APPNAME").trim().equals(quickLink.getAppName().trim())
				&& 	queryForRowSet.getString("APPCATEGORY").trim().equals(quickLink.getAppCategory().trim())
			){
				return true;
			}
	
		}
		return false;
	}
	

	@Override
	public List<QuickLink> getAvailableQuickLinks(String username) {
		List<QuickLink> quickLinks =new ArrayList<>();

		final SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(AVAILABLE_QUICK_LINKS.replace(REPLACE_WITH_USERNAME, username)
				, new Object[]{username,username,username,username});
		int i =1;
		while (queryForRowSet.next()) {
			
			QuickLink quickLink = new QuickLink();
			quickLink.setAppName(queryForRowSet.getString("QWMENU").trim());
			quickLink.setAppCategory(queryForRowSet.getString("QNTYPE").trim());
			quickLink.setLinkOrder(i++);
			quickLink.setDescription(queryForRowSet.getString("QWDSC1").trim());
			quickLinks.add(quickLink);
		}
		
		
		return quickLinks;
	}

	@Override
	public QuickLink getQuickLinkFromAvailableQuickLinks(QuickLink quickLink, String username) {
		final SqlRowSet queryForRowSet = jdbcTemplate.queryForRowSet(AVAILABLE_QUICK_LINKS.replace(REPLACE_WITH_USERNAME, username)
				, new Object[]{username,username,username,username});
		
		while (queryForRowSet.next()) {
			
			if(queryForRowSet.getString("QWMENU").trim().equals(quickLink.getAppName().trim())
				&& 	queryForRowSet.getString("QNTYPE").trim().equals(quickLink.getAppCategory().trim())
			){
				QuickLink ql = new QuickLink();
				ql.setAppName(queryForRowSet.getString("QWMENU").trim());
				ql.setAppCategory(queryForRowSet.getString("QNTYPE").trim());
				//quickLink.setLinkOrder(queryForRowSet.getInt("ORDER_LINK"));
				ql.setDescription(queryForRowSet.getString("QWDSC1").trim());
				return ql;
			}
	
		}
		return null;
	}
	
	
	@Override
	public void addQuickLink(QuickLink quickLink, String username) {
		//System.out.println(quickLink.getAppCategory()+" : "+quickLink.getAppName()+" : "+ quickLink.getLinkOrder());
		
		int linkOrder = getNewQuickLinkOrder(username);
		quickLink.setLinkOrder(linkOrder);
		jdbcTemplate.update(
				ADD_LINK, 
				new Object[]{
						quickLink.getAppName(),
						username,
						quickLink.getLinkOrder(),
						quickLink.getAppCategory(),
						"",
						""
				});
	}

	@Override
	public void deleteQuickLink(QuickLink quickLink, String username) {
		jdbcTemplate.update(DELETE_LINK,
				quickLink.getAppName(),
				username,
				quickLink.getAppCategory()
				);
		
		updateQuickLinkOrder(username);
		
	}
	
	private void updateQuickLinkOrder(String username){
		List<QuickLink> quickLinks = getUserQuickLinks(username);
		
		int i =1;
		for(QuickLink quickLink : quickLinks ){
			
			jdbcTemplate.update(UPDATE_QUICK_LINK_ORDER,
					i++,
					quickLink.getAppName(),
					username,
					quickLink.getAppCategory()
					);
		}
	}
	
	
	@Override
	public void deleteAllQuickLink(String username) {
		jdbcTemplate.update(DELETE_USER_ALL_LINK,username);	
	}

	@Override
	public int getUserQuickLinkTotal(String username) {
		return jdbcTemplate.queryForObject(COUNT_LINKS_FOR_USER, Integer.class, new Object[]{username});
	}




	@Override
	public void addQuickLinks(List<QuickLink> quickLinks, String username) {
		

		jdbcTemplate.batchUpdate(ADD_LINK, new BatchPreparedStatementSetter() {

		    @Override
		    public void setValues(PreparedStatement ps, int i) throws SQLException {
		    	QuickLink quickLink = quickLinks.get(i);
		        ps.setString(1, quickLink.getAppName());
		        ps.setString(2, username);
		        ps.setInt(3, quickLink.getLinkOrder());
		        ps.setString(4, quickLink.getAppCategory());
		        ps.setString(5, "");
		        ps.setString(6, "");
		    }

		    @Override
		    public int getBatchSize() {
		        return quickLinks.size();
		    }
		});
	}



	

}
