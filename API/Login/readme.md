#Login Services for Angular

+ POST /login  - To login
+ GET /logout  - To logout
+ GET /check_token - To check token

### Login
+ Sample Request
  - POST http://__API_DOMAIN__/api/myestes/Login/v1.0/login
  - Request Body
```json
{
  "password": "DEMO",
  "userName": "DEMO"
}
```

+ Sample Response (__Response is same as Oauth2 application returns__)
	- Success Response
```json
{
  "access_token": "9d80821c-e2e3-440d-88a8-60c613738af4",
  "token_type": "bearer",
  "expires_in": 604799,
  "scope": "QUN200 WRVIEWING QUOTEHIST CLAIMSFILE QUN100 ECN300 SHIPMAN SDN015 LTLRATEQOT SHIPTRACK TMN100 OLDBOL EDN426 EBG10O120 CLAIMIN EBG10O101 MEXCHAT BOL100 VTLQUOTE PDN100 FRN100 INVINQUIRY ONL100 DSN10O100 TIMECRIT",
  "accountCode": "0212345",
  "session": "3509201",
  "accountType": "L",
  "hash": "9FB367F03FB211800BBC77C55DCA6B53404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040",
  "authorities": [
    {
      "authority": "ROLE_L"
    }
  ],
  "username": "DEMO"
}
```
	- Error Response (400)
```
{
  "error": "invalid_grant",
  "error_description": "We're sorry, the username and/or password you have entered is invalid.  Please try again.                                                                                                                                                                           "
}
```

### Logout 
+ Sample Request
	- token as Request Parem
	- http://__API_DOMAIN__/api/myestes/Login/v1.0/logout?token=__<token>__
+ Sample Response (__Same Response as Oauth2 service returns__)
	- Success Response (200)
	```json 
	  {
		"success": true
	  }
	```
	- Error Response (400). (__Response format is same for other code__)
	```json
	{
	  "error": "invalid_request",
	  "error_description": "Token was not recognised"
	}
	```
### Check Token

+ Sample Request
	- token as request parameter
	- http://__API_DOMAIN__/api/myestes/Login/v1.0/check_token?token=__<token>__
+ Sample Response (__Response as same as Oauth2 service returns__)
	- Success Response (200)
	```json
	{
	  "accountCode": "0212345",
	  "user_name": "com.estes.security.model.User@b67082b3",
	  "session": "3509202",
	  "scope": [
	    "QUN200",
	    "WRVIEWING",
	    "QUOTEHIST",
	    "CLAIMSFILE",
	    "QUN100",
	    "ECN300",
	    "SHIPMAN",
	    "SDN015",
	    "LTLRATEQOT",
	    "SHIPTRACK",
	    "TMN100",
	    "OLDBOL",
	    "EDN426",
	    "EBG10O120",
	    "CLAIMIN",
	    "EBG10O101",
	    "MEXCHAT",
	    "BOL100",
	    "VTLQUOTE",
	    "PDN100",
	    "FRN100",
	    "INVINQUIRY",
	    "ONL100",
	    "DSN10O100",
	    "TIMECRIT"
	  ],
	  "accountType": "L",
	  "exp": 1558977534,
	  "authorities": [
	    {
	      "authority": "ROLE_L"
	    }
	  ],
	  "hash": "F8028673A84CCF7AAF2FBEAD7670A885404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040404040",
	  "client_id": "MY_ESTES",
	  "username": "DEMO"
	}
	```
	- Error Response (400). ( __For other error code response format is same __)
	```json
	{
  		"error": "invalid_token",
 		 "error_description": "Token was not recognised"
	}
	```
	
### API_DOMAIN
	+ QA - qa.estestinternal.com
	+ UAT - uat.estesinternal.com
	+ PROD - estes-express.com or www.estes-express.com

### Swagger Documentaion
https://qa.estesinternal.com/api/myestes/Login/v1.0/swagger-ui.html

	
	