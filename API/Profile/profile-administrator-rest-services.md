
My Estes Profile Administrator Rest Services:
=============================================

Table of Contents
-----------------
| SL | Service Info        | Request Method | Services End Point           |
|:-------| ------------- |:-------------|:----------------|
|1| Get Authenticate User Profile Information     | GET | http://qa.estesinternal.com/api/myestes/Profile/v1.0/me|
|2| Profile Information - Change Username      | POST | http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_username |
|3| Profile Information - Change Email Address     |  POST | http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_email |
|4| Profile Information - Change Password     |   POST |  http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_email |
|5| Profile Information - Change Email Preference     |   POST | http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_email |
|6| Sub Account Management Get List of Users     | GET | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users|
|7| Sub Account Management - Create New User      | POST | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users |
|8| Sub Account Management - Enable/Disable User     |   POST | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/change_status |
|9| Sub Account Management - Change User Password     |   POST |  http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/change_password |
|10| Sub Account Management - Get List of User Authorized Apps     |   GET | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/authorized_apps |
|11| Sub Account Management - Get List of Blocked Apps for a User    | GET | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_appps|
|12| Sub Account Management - Add List of Apps to Blocked List for a User    | POST | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps |
|13| Sub Account Management - Delete list of Apps from Blocked List for a user     |   DELETE | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps |
|14| Sub Account Management - Delete/Clear the blocked list for a user     |   DELETE | http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps/all |



Profile Information Retrieve:
-----------------------------
* ** Get Authenticate User Profile Information **
	- User needs to be authenticated to access this service.
 	- **GET http://qa.estesinternal.com/api/myestes/Profile/v1.0/me**
		* It provides the authenticated User Profile Information.
		* Send Authorization in header to get access
		* Sample Successful Response for DEMO user:
			
			```json
			{
			    "firstName": "DEMO",
			    "lastName": "DEMO",
			    "username": "DEMO",
			    "email": "LNASH@ESTES-EXPRESS.COM",
			    "phone": "(804) 353-1900",
			    "companyName": "DEMO",
			    "accountType": "L",
			    "accountCode": "0212345",
			    "createdDate": "20180829",
			    "status": "ENABLED",
			    "preference": "Y"
			}
			```
		* Response DTO
				
			```java
				
			@Data
			@ApiModel(description="User Profile")
			public class Profile
			{
				 @ApiModelProperty(position=1, notes="First Name")
				 private String firstName;
				 
				 @ApiModelProperty(position=2, notes="Last Name")	
				 private String lastName;
			
				 @ApiModelProperty(position=3, notes="Username")
				 private String username;
			
				 @ApiModelProperty(position=4, notes="Email Address")
				 private String email;
			
				 @ApiModelProperty(position=5, notes="Phone Number")
				 private String phone;
			
				 @ApiModelProperty(position=6, notes="Company Name")
				 private String companyName;
			
				 @ApiModelProperty(position=7, notes="Account Type")
				 private String accountType;
			
				 @ApiModelProperty(position=8, notes="Account Code")
				 private String accountCode;
			
				 @ApiModelProperty(position=9, notes="Created Date")
				 private String createdDate;
			
				 @ApiModelProperty(position=10, notes="User Status: ENABLED/DISABLED")
				 private UserStatus status;
				 
				 @ApiModelProperty(position=11, notes="Email Update Preference. Y for Yes, N for No")
				 private String preference;
				 
			}
	 		```
		

Profile Information Update:
---------------------------
> User needs to be authenticated to access these services.
> User should have __EDITPROF__ scope. In another words, user should have permission to access the Edit Profile Information.
> Send Authorization in header to get access these services.

* ** Change Username **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_username**
		* It changes the username of authenticated user.
		* Username should not be null. 
		* Username should have minimum 5 characters & maximum 10 characters
		* Sample Request Body
			
			```json
			{
				"username" : "NEW_USERNAME"
			}
			```
			
		* Sample Successful Response 
			
			```json
			{
				"success" : true,
				"message" : " Successful Message"
			}
			```
			
		* Sample Error Response
			
			```json
			{
				"error" : "BAD_REQUEST",
				"error_description" : "Error Response Message"
			}
			```
			
		* DTO for Username
			
			```java
			@ApiModel(description="This model will be used for updating username")
			@Data
			public class Username {
				@NotNull
				@Size(min=5,max=10,message="Username should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(name="Username",notes="Username should have minimum 5 & maximum 10 characters")
				private String username;
			}
			```
			
			
* ** Change Email Address **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_email**
		* It changes the email address of authenticated user.
		* Email Address should not be null & should be valid email address.
		* The following pattern is used to validate email address: 
			```
            ^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@" + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$
            ```
		* Sample Request Body
			
			```json
			{
				"email" : "new_email@mail.com"
			}
			```
			
		* Sample Successful Response 
			
			```json
			{
				"success" : true,
				"message" : " Successful Message"
			}
			```
			
		* Sample Error Response
			
			```json
			{
				"error" : "BAD_REQUEST",
				"error_description" : "Error Response Message"
			}
			```
			
		* DTO for Email Address
			
			```java
			@Data
			@ApiModel(description="This model will be used to update user email address")
			public class EmailAddress {
				@NotNull
				@ValidEmail(message="Invalid email address")
				@ApiModelProperty(name="Email Address",notes="User new email Address")
				private String email;
			}
			```
			
			
* ** Change Password **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_password**
		* It changes the password for authenticated user.
		* Password should not be null
		* Password should have minimum 5 characters & maximum 10 characters
		* Password & Confirm Password should be identical
		* Sample Request Body
			
			```json
			{
				"password" : "new_password",
				"confirmPassword": "confirmPassword"
			}
			```
		* Sample Successful Response 
			
			```json
			{
				"success" : true,
				"message" : " Successful Message"
			}
			```
		* Sample Error Response
			```json
			{
				"error" : "BAD_REQUEST",
				"error_description" : "Error Response Message"
			}
			```
			
		* DTO for Password
			
			```java
			
			@Data
			@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match!")
			@ApiModel(description="Password model will be used to change password")
			public class Password {
				
				@NotNull
				@Size(min=5, max=10, message = "Password should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=1,notes="Password should have minimum 5 & maximum 10 characters. Password should match the confirm password")
				private String password;
			
				@NotNull
				@Size(min =5, max=10, message = "Confirm Password should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=2,name="Confirm Password",notes="Confirm Password should have minimum 5 & maximum 10 characters. Confirm password should match the Password")
				private String confirmPassword;
				
			}
				
			```
				
				
* ** Change Email Preference **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/change_email_preference**
		* It changes the email preference of authenticated user.
		* Email Preference should not be null & should be either '__Y__' or '__N__'.
		* Sample Request Body
			
			```json
			{
				"preference" : "Y"
			}
			```
			
		* Sample Successful Response 
			
			```json
			{
				"success" : true,
				"message" : " Successful Message"
			}
			```
			
		* Sample Error Response
			
			```json
			{
				"error" : "BAD_REQUEST",
				"error_description" : "Error Response Message"
			}
			```
			
		* DTO for Email Preference
			
			```java
			@Data
			@ApiModel(description="This model will be used for updating email preference")
			@NotNull(message="Email Preference can not be null")
			public class EmailPreference {
				
				@NotNull(message="Preference can not be null")
				@ApiModelProperty(name="Email Preference",notes="Email Preference can have two value 'Y' or 'N'")
				private String preference;
				
				@AssertTrue(message="Preference can be either 'Y' or 'N'")
			    public boolean isPreference() {
			        return preference!=null && ( preference.trim().equalsIgnoreCase("Y") || preference.trim().equalsIgnoreCase("N"));
			    }
			}
			```
	
	
Sub Account Management:
------------------------
> User needs to be authenticated to access these services.
> User should have __EDITPROF__ as well as __ADMINSUBS__ scopes. In another words, user should have permission to access the Sub Account Management.
> Send Authorization in header to get access these services.

* ** Get List of Users **
	- **GET http://qa.estesinternal.com/api/myestes/Profile/v1.0/users**
		* It provides the user list (sub account list) created by the authenticated user
		* The following __request parameters__ can be passed to get advanced results.
			+ __firstName__ - To search by first name (__optional__)
			+ __lastName__ - To search by last name (__optional__)
			+ __username__ - To search by username (__optional__)
			+ __q__ - To search any string that will match to __firstName__,__lastName__,__username__ and __email__ (__optional__)
			+ __rpp__ - Results Per Page. Deafult value is __10__. (__optional__)
			+ __page__ Page no. (__optional__)
				- Deafult value is __1__.
				- You need to pass the page value for next pages.
				- Page value can not be less than __1__  and greater than __totalPages__
			+ __sortBy__ - Results are sorted by this column. (__optional__)
				- Value can be any of the following:
					+ __firstName__,__lastName__,__username__,__email__,__phone__,__companyName__,__accountCode__,__createdDate__,__status__
				- Default value is __createdDate__.
			+ __type__ - Sort type. (__optional__)
				- Value can be either __asc__ or __desc__ .
				- default value is __asc__
		*  Successful Response:
			+	DTO Fields:
				- __page__ - Current Page No
				- __totalResults__ - Total Results without any filtration by above __q__,__firstName__,__lastName__,__username__.
				- __totalPages__ - Total Page No without any filtration by above __q__,__firstName__,__lastName__,__username__.
				- __filtratedTotalResults__ - Total Results after Filtering by above __q__,__firstName__,__lastName__,__username__.
				- __filtratedTotalPages__ Total Page No after Filtering by above __q__,__firstName__,__lastName__,__username__.
				- __rpp__ - Results Per Page
				- __results__ List of Profile
				- __payload__ Search Parameters if you sent.
			+	__DTO__:
				- Profile Data: 
					
					```java
					@Data
					@ApiModel(description="List of User Profile")
					public class ProfileData {
						@ApiModelProperty(position=1, notes="Current Page No")
						private int page;
						@ApiModelProperty(position=2, notes="Total Number of Page")
						private int totalPages;
						@ApiModelProperty(position=3, notes="Total Results")
						private int totalResults;
						@ApiModelProperty(position=4, notes="Total Number of Pages After Filtration")
						private int filtratedTotalPages;
						@ApiModelProperty(position=5, notes="Total Results after Filtration")
						private int filtratedTotalResults;
						@ApiModelProperty(position=6, notes="Results Per Page")
						private int rpp;
						@ApiModelProperty(position=7, notes="List of User Profile")
						private List<Profile> results;
						@ApiModelProperty(position=8, notes="Payload")
						private Map<String, Object> payload;
					}
					```
					
				- Profile:
					
					```java
					@Data
					@ApiModel(description="User Profile")
					public class Profile
					{
						 @ApiModelProperty(position=1, notes="First Name")
						 private String firstName;
						 @ApiModelProperty(position=2, notes="Last Name")	
						 private String lastName;
						 @ApiModelProperty(position=3, notes="Username")
						 private String username;
						 @ApiModelProperty(position=4, notes="Email Address")
						 private String email;
						 @ApiModelProperty(position=5, notes="Phone Number")
						 private String phone;
						 @ApiModelProperty(position=6, notes="Company Name")
						 private String companyName;
						 @ApiModelProperty(position=7, notes="Account Type")
						 private String accountType;
						 @ApiModelProperty(position=8, notes="Account Code")
						 private String accountCode;
						 @ApiModelProperty(position=9, notes="Created Date")
						 private String createdDate;
						 @ApiModelProperty(position=10, notes="User Status: ENABLED/DISABLED")
						 private UserStatus status;
						 @ApiModelProperty(position=11, notes="Email Update Preference. Y for Yes, N for No")
						 private String preference;
					}
					```	
									
			+ Json Response for user TESTADMIN with rpp=2:
				
				```json
				{
				    "page": 1,
				    "totalPages": 7,
				    "totalResults": 14,
				    "filtratedTotalPages": 7,
				    "filtratedTotalResults": 14,
				    "rpp": 2,
				    "results": [
				        {
				            "firstName": "JERRY",
				            "lastName": "HEFNER",
				            "username": "JHEFNER",
				            "email": "JHEFNER@ESTES-EXPRESS.COM",
				            "phone": "(804) 353-1900",
				            "companyName": "DIAMOND CRYSTAL BRANDS",
				            "accountType": null,
				            "accountCode": "5023958",
				            "createdDate": "20110714",
				            "status": "ENABLED",
				            "preference": "N"
				        },
				        {
				            "firstName": "LISA",
				            "lastName": "NASH",
				            "username": "LNASH06",
				            "email": "LNASH@ESTES-EXPRESS.COM",
				            "phone": "(804) 353-1900",
				            "companyName": "DIAMOND CRYSTAL BRANDS",
				            "accountType": null,
				            "accountCode": "5023958",
				            "createdDate": "20110714",
				            "status": "ENABLED",
				            "preference": "Y"
				        }
				    ],
				    "payload": {
				        "q": null,
				        "firstName": null,
				        "lastName": null,
				        "sortBy": "createdDate",
				        "type": "asc",
				        "username": null
				    }
				}
				```
					
* ** Create New User **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/users**
		* It create a new sub account 
		* Fields:
			+ __firstName__:
				- First Name can not be null. Maximum 25 characters are allowed.
			+ __lastName__:
				- Last Name can not be null. Maximum 25 characters are allowed.
			+ __email__:
				- Should be valid email Address. Above Email Pattern is being used to validate email address.
			+ __phone__:
				- Should be valid phone.
				-  The following pattern is used to validate the phone
				    
				    ```
                    ^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$
                 ```
                    
				- Sample Valid phone numbers are (804) 335 83659 , (804)-455-667, (804) 457-678 etc. 
			+ __username__:
				- username can not be null.
				- Mimimum 5 characters are required.
				- Maximum 10 characters are allowed
				- username should be unique
			+ __password__:
				- Password should have minimum 5 characters & maximum 10 characters
			+ __confirmPassword__:
				- Password & Confirm Password should be identical
			+ __notify__: 
				- Value can be either '__Y__' or '__N__'
		* Sample __@RequestBody__:
			
			```json
			{
			  "firstName": "User First Name",
			  "lastName": "User Last Name",
			  "email": "user.email@test.com",
			  "phone": "(804) 336-8359",
			  "username": "username",
			  "password": "password",
			  "confirmPassword": "password",
			  "notify": "Y"
			}
			```
			
		* Request DTO:
			
			```java
			
			@Data
			@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match!")
			@ApiModel(description="To create Sub Account")
			public class User {

				@NotNull
				@Size(min=1, max=25)
				@ApiModelProperty(position=1, name="firstName",notes="First Name")
				private String firstName;
				
				@NotNull
				@Size(min=1,max=25)
				@ApiModelProperty(position=2, name="lastName",notes="First Name")
				private String lastName;
			
				@NotNull
				@ValidEmail
				@ApiModelProperty(position=5, name="email",notes="Email Address")
				private String email;
				
				@NotNull
				@Pattern(regexp="^\\(?(\\d{3})\\)?[- ]?(\\d{3})[- ]?(\\d{4})$", message="Invalid phone number")
				@ApiModelProperty(position=6, name="phone",notes="Phone Number")
				private String phone;
				
				@NotNull
				@Size(min=5,max=10,message="Username should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=7,name="Username",notes="Username should have minimum 5 & maximum 10 characters")
				private String username;
				@NotNull(message = "Password can't be null")
				@Size(min=5, max=10, message = "Password should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=8,notes="Password should have minimum 5 & maximum 10 characters. Password should match the confirm password")
				private String password;
			
				@NotNull
				@Size(min =5, max=10, message = "Confirm Password should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=9,name="Confirm Password",notes="Confirm Password should have minimum 5 & maximum 10 characters. Confirm password should match the Password")
				private String confirmPassword;
				
				@NotNull
				@ApiModelProperty(position=10, name="notify",notes="Notify can be either 'Y' or 'N")
				private String notify;
				
				@AssertTrue(message="Notify can be either 'Y' or 'N'")
			    public boolean isNotify() {
			        return notify!=null && ( notify.trim().equalsIgnoreCase("Y") || notify.trim().equalsIgnoreCase("N"));
			    }
				
				public String getAreaCode(){
					return phone.replaceAll("\\D+", "").substring(0, 3);
				}
				
				public String getExchange(){
					return phone.replaceAll("\\D+", "").substring(3, 6);
				}
				
				public String getLastFour(){
					return phone.replaceAll("\\D+", "").substring(6,10);
				}
			
			}
			```
			
* ** Enable / Disable User **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/change_status**
		* It enables/disables the sub account.
		* __Request Parameters__:
			+ __status__: 
				- It can not be null
				- It is the new status of User
				- Value can be either __ENABLED__ or __DISABLED__
				- __EANBLED__ is used to enable the user & __DISABLED__ is used to disable the user.
			+ __username__:
				- It can not be null
				- The child Username
		* Sample Request With Query String :
			- POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/change_status?status=ENABLED&username=DEMO
		* Sample Successful Response 
			
			```json
			{
				"success" : true,
				"message" : " Successful Message"
			}
			```
			
		* Sample Error Response
			
			```json
			{
				"error" : "BAD_REQUEST",
				"error_description" : "Error Response Message"
			}
			```
			
* ** Change User Password **
	- **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/change_password**
		* It changes the password for authenticated user.
		* Password should not be null
		* Password should have minimum 5 characters & maximum 10 characters
		* Password & Confirm Password should be identical
		* Sample Request Body
			
			```json
			{
				"password" : "new_password",
				"confirmPassword": "confirmPassword"
			}
			```
			
		* Sample Successful Response 
			
			```json
			{
				"success" : true,
				"message" : " Successful Message"
			}
			```
			
		* Sample Error Response
			
			```json
			{
				"error" : "BAD_REQUEST",
				"error_description" : "Error Response Message"
			}
			```
			
		* DTO for Password
			
			```java
			@Data
			@FieldsMatch(field = "password", fieldMatch = "confirmPassword", message = "Passwords do not match!")
			@ApiModel(description="Password model will be used to change password")
			public class Password {
				
				@NotNull
				@Size(min=5, max=10, message = "Password should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=1,notes="Password should have minimum 5 & maximum 10 characters. Password should match the confirm password")
				private String password;
			
				@NotNull
				@Size(min =5, max=10, message = "Confirm Password should have minimum 5 & maximum 10 characters")
				@ApiModelProperty(position=2,name="Confirm Password",notes="Confirm Password should have minimum 5 & maximum 10 characters. Confirm password should match the Password")
				private String confirmPassword;
				
			}
			```
			
* ** Get List of User Authorized Apps **
	- **GET http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/authorized_apps**
		* It provides the list of user authorized apps.
		* Request Parameters:
			+ __username__: username can not be null (__Required__)
		* Sample Request:  GET http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/authorized_apps?username=DEMO
		* Sample Response:
			
			```json
			[
			    {
			        "name": "EBG10O101",
			        "category": "G05",
			        "description": "Address Book"
			    },
			    {
			        "name": "BOL100",
			        "category": "Y01",
			        "description": "Bill of Lading"
			    },
			    {
			        "name": "CLAIMSFILE",
			        "category": "D03",
			        "description": "Claims Filing"
			    },
			    {
			        "name": "CLAIMIN",
			        "category": "D01",
			        "description": "Claims Inquiry"
			    },
			]
			```
		
* ** User Blocked Apps Management**
	> After creation of any sub account, sub account gets all the privileges  the admin user have except __AMINSUBS__
	> Admin can block/unblock a user from any specific applications.
	> The following services does the block/unblock application for a user
	+ Get List of User Blocked Apps
		+ **GET http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps**
			* It provides the list of blocked applications for a user.
			* Request Parameters:
				+ __username__: username can not be null (__Required__)
			* Sample Request: GET http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps?username=DEMO
			* Sample Response:
				
				```json
				[
				    {
				        "name": "EBG10O101",
				        "category": "G05",
				        "description": "Address Book"
				    },
				    {
				        "name": "BOL100",
				        "category": "Y01",
				        "description": "Bill of Lading"
				    },
				    {
				        "name": "CLAIMSFILE",
				        "category": "D03",
				        "description": "Claims Filing"
				    },
				    {
				        "name": "CLAIMIN",
				        "category": "D01",
				        "description": "Claims Inquiry"
				    },
				]
				```
	+ ** Add Apps to the User Blocked List **
		+ **POST http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps**
			* Add list of Apps to the blocked list of Applications for a user.
			* Request Parameters:
				- __username__: username can not be null (__Required__)
			* Request Body:
				- List of __appName__
				- Sample Values :
					
					```json
					    ["EBG10O101","BOL100"]
					```
					
	+ ** Remove Apps from User Blocked List **
		+ **DELETE http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps**
			* Delete list of Apps from the blocked list of Applications for a user.
			* Request Parameters:
				- __username__: username can not be null (__Required__)
			* Request Body:
				- List of __appName__
				- Sample Values :
					
					```json
						["EBG10O101","BOL100"]
					```
					
	+ ** Remove/Clear Blocked List **
		+ **DELETE http://qa.estesinternal.com/api/myestes/Profile/v1.0/users/blocked_apps/all**
			* Delete/Clear the blocked list of Applications for a user.
			* Request Parameters:
				- __username__: username can not be null (__Required__)

			
=========THE END ========
=========================
