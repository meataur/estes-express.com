package com.estes.security.service;


public class AuthenticationServiceFactory {
	
		public static IAuthenticationService getAuthenticationService(String ClientId){
			IAuthenticationService authService = null;
			if("MY_ESTES".equals(ClientId)) {
				authService = new MyEstesAuthenticationService();
			}
			else if("PND_APP".equals(ClientId)) {
				authService = new PNDAuthenticationService();
			}
			else if("CENTRAL_DISPATCH_APP".equals(ClientId)) {
				authService = new CentralDispatchAuthenticationService();
			}
			
			return authService;
		}
}
