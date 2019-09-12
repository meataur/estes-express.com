#QuickLinks Services



QuickLinks Database Tables
-----------------------------------------------------------------------------------

**The following tables of DB2 database are being used in the quickLink rest services:**

1.	**ESTESRTGY2.QNP220**
2.	**FBFILES.QUICK_LINKS_FOR_MY_ESTES**
3.	**ESTESRTGY2.QNP200**
4.	**ESTESRTGY2.QNP302**
5.	**FBFILES.QCK00P100**

QuickLinks Database Tables

1.	To get the default list of 5 applications
			
			SELECT QCKAPP, QCKORD, QCKCAT,QWMEN1,QWMEN2,QWMEN6 
					FROM FBFILES.QCK00P100 
						INNER JOIN ESTESRTGY2.QNP220 
						ON QCKAPP=QWMENU 
						ORDER BY QCKORD ASC 
						LIMIT 0,5
				 
2. To get the list of 5 apps for authenticated user
			SELECT QWDSC1, QWMENU,QWTYPE,QWMEN1,QWMEN2,QWMEN3,'' AS REMOVE 
					FROM FBFILES.QUICK_LINKS_FOR_MY_ESTES 
						INNER JOIN ESTESRTGY2.QNP220 
						ON QWMENU=APPLI00001
						WHERE USER_NAME=? 
						ORDER BY ORDER_LINK ASC
						LIMIT 0,5
3.	The following query to retrieve the  list of available apps for a user. 
		('REPLACE_WITH_USERNAME' should be replaced with username.)
		
			SELECT QWMENU,QNUN,QNTYPE,QWDSC1,'' AS ADD 
					FROM ESTESRTGY2.QNP200 
               		INNER JOIN ESTESRTGY2.QNP220
               		ON ESTESRTGY2.QNP200.QNTYPE = ESTESRTGY2.QNP200.QWTYPE
               		WHERE QNUN=? 
                              AND QWMENU NOT IN (
                                            SELECT QZAPP FROM ESTESRTGY2.QNP302
                                                            WHERE QZREFC=? 
                                                            AND QZBLTP=2
                              )
                              AND QNTYPE NOT IN 
                              ( SELECT APP_CAT 
                                             FROM FBFILES.QUICK_LINKS_FOR_MY_ESTES
                                             WHERE USER_NAME=?
                              )
                              AND QNTYPE<>'G04'
                              AND QNTYPE<>'A12'
                              AND QNTYPE<>'A10'
                              AND QNTYPE<>'A11' 
                              AND QNTYPE<>'K05'
                              AND QNTYPE<>'F02'
                              AND QNTYPE<>'F01'
                              AND QNTYPE<>'D04' 
                              AND QNTYPE<>'K01'
                              AND QNTYPE<>'C01'
                              AND QNTYPE<>'C06'
                              AND QNTYPE<>'B03'
                              AND QNTYPE<>'Y02'
                              UNION SELECT QWMENU,'REPLACE_WITH_USERNAME' AS QNUN,QWTYPE,QWDSC1, '' AS ADD
                                             FROM ESTESRTGY2.QNP200
                                             WHERE QWTYPE IN (
                                                            SELECT QCKCAT 
                                                            FROM FBFILES.QCK00P100
                                             ) 
                                             AND QWTYPE NOT IN (
                                                            SELECT APP_CAT 
                                                            FROM FBFILES.QUICK_LINKS_FOR_MY_ESTES
                                                            WHERE USER_NAME=?
                                             ) AND QWTYPE NOT IN ('D04','G04','A12') ORDER BY QWDSC1 ASC;





JNDI Name:
---------------------------------
* jdbc/msquicklinksds


#Oauth2 Client Information

* clientId=MY_ESTES
* clientSecret=


JNDI Name Space Binding
------------------------------------------------------------------------------

* **For Check Token Url**
* **Binding Type:** String
* **Binding Identifier:** Oauth2CheckTokenUrl
* **JNDI Name:** oauth2/check_token
* **String Value:** http://appsec.qa.estesinternal.com/security/oauth/check_token
 
 **_String value will be different for each application Server. This url is for QA Server only._**

 
 AppServer 8.5
--------------------------
* **QA:** wasqaappa02.estes.us.dom:9087 & http://wasqaappa01.estes.us.dom:9087

* **Cluster:** Cluster07
* **Context root:** /api/QuickLinks/v1.0

Swagger UI & API Docs
--------------------------
* http://qa.estesinternal.com/api/QuickLinks/v1.0/swagger-ui.html
* http://qa.estesinternal.com/api/QuickLinks/v1.0/v2/api-docs


API Info
--------------------------
* **Unauthenticated Access:**

1. The default list of 5 Apps when a user is not authenticated - 
	GET http://qa.estesinternal.com/api/QuickLinks/v1.0

* **Authenticated Access: **

1. GET http://qa.estesinternal.com/api/QuickLinks/v1.0/links
		* returns the list of 5 apps when a user is authenticated
		
2. GET http://qa.estesinternal.com/api/QuickLinks/v1.0/links/list
		* return the list of available links for a authenticated user 
		
3. GET http://qa.estesinternal.com/api/QuickLinks/v1.0/links/reset
		* The authenticated user can reset his list of customized quick links to default 
		
4. POST http://qa.estesinternal.com/api/QuickLinks/v1.0/links?appName=appName&appCategory=appCategory
		* The authenticated user can add a quick link from available quick links
		
5. DELETE http://qa.estesinternal.com/api/QuickLinks/v1.0/links?appName=appName&appCategory=appCategory
		* The authenticated user can remove a quick link from his quick links 
		


** To get authenticated access , client app needs to send the oauth token in header.**

* **Key**: **Authorization**
* **Value**: **Bearer 3f45be3a-0762-4863-8663-40a9ca5c1c24** (Bearer is token bearer & the rest part is token)


** To know how to get the token, you can browse the following documentation located at SVN repository**

* https://svn-it-r001.estes.us.dom/!/#estes/view/head/Documentation/projects/common/oauth2



