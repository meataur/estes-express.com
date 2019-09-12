package com.estes.framework.dataaccess.audit.iface;

public interface AuditRequestDAO {
	
	void logRequest(String userName, String appName);

}