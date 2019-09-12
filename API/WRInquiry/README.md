#Weight & Research Inquiry Services



Profile Database Tables
-----------------------------------------------------------------------------------
[Please check the document](database-sql-sproc.md)

JNDI Name:
---------------------------------
* __jdbc/myestes__


###Oauth2 Client Information

* clientId: __MY_ESTES__
* clientSecret:
* scope:
	+ __PDN100__
		* Request Image Viewing
		* Request Image Faxing

###Oauth2 Integration:
[Here is the documentation to integrate with Oauth2](https://svn-it-r001.estes.us.dom/!/#estes/view/head/SourceCode/projects/myestes/api/README.md)

JNDI Name Space Binding
------------------------------------------------------------------------------

**For Check Token Url**

* **Binding Type:** String
* **Binding Identifier:** Oauth2CheckTokenUrl
* **JNDI Name:** oauth2/check_token
* **String Value:** http://appsec.qa.estesinternal.com/security/oauth/check_token
 
> String value will be different for each application Server. This url is for QA Server only.

 
 AppServer 8.5
--------------------------
* **QA:** http://wasqaappa02.estes.us.dom:9087 & http://wasqaappa01.estes.us.dom:9087

* **Cluster:** Cluster07
* **Context root:** /api/WRInquiry/v1.0

Swagger UI & API Docs
--------------------------
* http://qa.estesinternal.com/api/WRInquiry/v1.0/swagger-ui.html
* http://qa.estesinternal.com/api/WRInquiry/v1.0/v2/api-docs



Athenticated/Authorized Access:
--------------------------------

** To get authenticated access , client app needs to send the oauth token in header.**

* **Key**: **Authorization**
* **Value**: **Bearer 3f45be3a-0762-4863-8663-40a9ca5c1c24** (Bearer is token bearer & the rest part is token)


** [To know how to get the token, click here ](https://svn-it-r001.estes.us.dom/!/#estes/view/head/Documentation/projects/common/oauth2)**





====== THE END =====
====================
