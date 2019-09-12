
Profile Database Tables/SPROC/SQL:
==================================


* The following tables of DB2 database are being used:
	1.	__ESTESRTGY2.QNP150__
	2.	__ESTESRTGY2.QNP230__
	3.	__ESTESRTGY2.QNP302__
* The following Store Procedures are being used:
	1.	__FBPGMS.SP_UPDUSERNAME__
	2.  __FBPGMS.SP_UPDPASSWORD__
	3.  __FBPGMS.SP_UPDEMAILADDR__
	4.  __FBPGMS.SP_UPDUPDATEPREF__
	5.  __FBPGMS.SP_CREATESUBACCOUNT__

* Usage & Signature of the SPROC/SQL:
	+ To update username, call the following store procedure __FBPGMS.SP_UPDUSERNAME__
	
		```sql
		CREATE PROCEDURE FBPGMS.SP_UPDUSERNAME ( 
			IN USRNAME CHAR(10) , 
			IN NEWUSERNAME CHAR(10) , 
			OUT "ERROR" CHAR(7)
		)
		```
		
	+ To change password, call the following store procedure __FBPGMS.SP_UPDPASSWORD__
	
		```sql
		CREATE PROCEDURE FBPGMS.SP_UPDPASSWORD ( 
			IN USRNAME CHAR(10) , 
			IN NEWPWRD CHAR(10) , 
			IN VERIFYPWRD CHAR(10) , 
			OUT "ERROR" CHAR(7)
		)
		```
		
	+ To update email address, call the following store procedure  __FBPGMS.SP_UPDEMAILADDR__
		
		```sql
		CREATE PROCEDURE FBPGMS.SP_UPDEMAILADDR ( 
			IN USRNAME CHAR(10) , 
			IN EMAIL CHAR(50) , 
			OUT "ERROR" CHAR(7)
		)
		```
		
	+ To update email preference, call the following store procedure __FBPGMS.SP_UPDUPDATEPREF__
		
		```sql
		CREATE PROCEDURE FBPGMS.SP_UPDUPDATEPREF ( 
			IN USRNAME CHAR(10) , 
			IN UPDATE_FLG CHAR(1) , 
			OUT "ERROR" CHAR(7)
		)
		``` 
	
	+ To create sub account, call the following store procedure __FBPGMS.SP_CREATESUBACCOUNT__	
		
		```sql
		CREATE PROCEDURE FBPGMS.SP_CREATESUBACCOUNT ( 
			IN SIGNED_IN_USER CHAR(10) , 
			IN FIRST_NAME CHAR(25) , 
			OUT FIRST_NAME_ERR CHAR(7) , 
			IN LAST_NAME CHAR(25) , 
			OUT LAST_NAME_ERR CHAR(7) , 
			IN CO_NAME CHAR(50) , 
			OUT CO_NAME_ERR CHAR(7) , 
			IN ACCT_CODE CHAR(7) , 
			OUT ACCT_CODE_ERR CHAR(7) , 
			IN ACCT_REP CHAR(60) , 
			OUT ACCT_REP_ERR CHAR(7) , 
			IN EMAIL CHAR(50) , 
			OUT EMAIL_ERR CHAR(7) , 
			IN AREA_CODE CHAR(3) , 
			OUT AREA_CODE_ERR CHAR(7) , 
			IN EXCHANGE CHAR(3) , 
			OUT EXCHANGE_ERR CHAR(7) , 
			IN LAST_FOUR CHAR(4) , 
			OUT LAST_FOUR_ERR CHAR(7) , 
			IN USER_NAME CHAR(10) , 
			OUT USER_NAME_ERR CHAR(7) , 
			IN "PASSWORD" CHAR(10) , 
			OUT PASSWORD_ERR CHAR(7) , 
			IN PW_CONFIRM CHAR(10) , 
			OUT PW_CONFIRM_ERR CHAR(7) , 
			IN NOTIFY CHAR(1) , 
			OUT NOTIFY_ERR CHAR(7) , 
			OUT "ERROR" CHAR(7)
		);
		```	 
		
	+ To get Profile information, the following query is being used.
		* Params:
			- __QSUN__ : signed in user's __username__
			
		```sql
		select qrfnam as firstName,
		qrlnam as lastName,
		QSACT# as accountCode,
		CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,
		QRCNAM as companyName,
		QRUN as username,
		qrem1 as email,
		qrwebu as preference,
		QSRDAT as createdDate,
		CASE WHEN QZREFC IS  NULL THEN 'ENABLED' ELSE 'DISABLED' END AS status
		from estesrtgy2.qnp150
		LEFT  JOIN estesrtgy2.qnp230 ON QSUN=QRUN
		LEFT JOIN ( select QZREFC from estesrtgy2.qnp302 where  QZBLTP = 1 and QZREFT = 1) as t 
		ON QZREFC=QSUN
		WHERE QSUN=?;
		```
		
	+ To retrieve list of Users Created By Signed In User
		* Params:
			- __QSEM2__ : Signen In User's __username__
			
		```sql
		select qrfnam as firstName,
		qrlnam as lastName,
		QSACT# as accountCode,
		CONCAT(CONCAT( CONCAT('(',qrpnac),CONCAT(') ', qrpnfp)), CONCAT('-',qrpnlp)) as phone,
		QRCNAM as companyName,
		QRUN as username,
		qrem1 as email,
		qrwebu as preference,
		QSUDAT as createdDate,
		CASE WHEN QZREFC IS  NULL THEN 'ENABLED' ELSE 'DISABLED' END AS status,
		QSEM2 as createdBy
		from estesrtgy2.qnp150
		LEFT  JOIN estesrtgy2.qnp230 ON QSUN=QRUN
		LEFT JOIN ( select QZREFC from estesrtgy2.qnp302 where  QZBLTP = 1 and QZREFT = 1) as t 
		ON QZREFC=QSUN
		WHERE QSEM2=?	
		```
	+ To get List of Authorized Apps for a user
		* Params:
			- __qnun__ : Child Username
			- __qzrefc__ (first) : Child Username
			- __qzrefc__ (second) : Parent Username (Signed In User's username)
			
		```sql
		select qwmenu, qnun, qntype, qwdsc1 
		from estesrtgy2.qnp200 
		inner join 
		estesrtgy2.qnp220 
		on estesrtgy2.qnp200.qntype=estesrtgy2.qnp220.qwtype 
		where qnun=? 
		and qwmenu not in (select qzapp from estesrtgy2.qnp302 where qzrefc=? and qzbltp=2) 
		and qwmenu not in (select qzapp from estesrtgy2.qnp302 where qzrefc=? and qzbltp=2) 
		and qntype <> 'A12' 
		and qntype<> 'A10' 
		and qntype<> 'A11' 
		and qntype<>'K05' 
		and qntype<>'K01' 
		and qntype <>'F02' 
		and qntype<>'F01' 
		order by qwdsc1
		```
	+ To get List of Blocked Apps for a user
		* Params:
			- __qnun__ : Child Username
			- __qzrefc__ (first) : Child Username
			- __qzrefc__ (second) : Parent Username (Signed In User's username)
		
		```sql
		select qwmenu, qntype, qwdsc1
		from estesrtgy2.qnp200
		inner join estesrtgy2.qnp220
		on estesrtgy2.qnp200.qntype=estesrtgy2.qnp220.qwtype
		where qnun=?
		and qwmenu in (select qzapp from estesrtgy2.qnp302 where qzrefc=? and qzbltp=2)
		and qwmenu not in (select qzapp from estesrtgy2.qnp302 where qzrefc=? and qzbltp=2)
		and qntype <> 'A12'
		and qntype<> 'A10'
		and qntype<> 'A11'
		and qntype<>'K05'
		and qntype<>'K01'
		and qntype <>'F02'
		and qntype<>'F01'
		order by qwdsc1;
		```
	+ To delete all blocked apps for a user
		* Params:
			- __qzrefc__ (first) : Child Username
			- __qzrefc__ (second) : Parent Username (Signed In User's username)
		
		```sql
		delete from estesrtgy2.qnp302
		where qzrefc=?
		AND QZAPP NOT IN (SELECT QZAPP 
								from estesrtgy2.qnp302
								WHERE QZREFC = ?
								AND QZBLTP = 2
							 )
		and qzapp <> 'ADMINSUBS'
		and qzbltp=2;
		```
	+ To delete spcefic apps from User Blocked List
		* Params:
			- __qzrefc__ (first) : Child Username
			- __qzrefc__ (second) : Parent Username (Signed In User's username)
			- __QZAPP__ : App Name
			
		```sql
		delete from estesrtgy2.qnp302
		where qzrefc=?
		AND QZAPP NOT IN (SELECT QZAPP 
									from estesrtgy2.qnp302 
									WHERE QZREFC = ? 
									AND QZBLTP = 2
							 )
		and qzapp <> 'ADMINSUBS'
		and qzbltp=2
		AND QZAPP=?;
		```
	+ To add an app to user blocked list
		* Params:
			- first : Child Username
			- second : App Name
		
		```sql
		insert into estesrtgy2.qnp302
		values(?, 1, 2, ?, '', '', CURRENT_DATE, CURRENT_TIME, '', '', '', '', '', '', 0, 0);
		```
	+ To check if a user is blocked or not
		* Params:
			- __QZREFC__ : Username
			 
		```sql
		select count(*) as IS_BLOCKED from estesrtgy2.qnp302 where QZREFC = ? and QZBLTP = 1 and QZREFT = 1
		```
	+ To disable a user
		* Params:
			- first : Child Username
			- Second : Parent Username (Signed In User's Username)
			
		```sql
		insert into estesrtgy2.qnp302
		values (?, 1, 1, '', '', ?, CURRENT_DATE, CURRENT_TIME, '', '', '', '', '', '', 0, 0)
		```
		
	+ To enable a user
		* Params:
			- __qzrefc__ : Child Username
			
		```sql
		delete from estesrtgy2.qnp302 where qzrefc = ? and qzbltp = 1 and qzreft = 1
		```
	+ To get User Status
		* Params:
			- __QZREFC__ : Username
			
		```sql
		select count(*) as total from estesrtgy2.qnp302 where QZREFC = ? and QZBLTP = 1 and QZREFT = 1
		```
	+ To check if a child user is created by Parent User (Signed in User)
		* Params:
			- __createdBY__ : Parent Username (Signed In User's Username)
			- __username__ : Child Username
			
		```sql
		SELECT count(*) FROM
		( select 
			QRUN as username,
			QSEM2 as createdBY
			from estesrtgy2.qnp150
			LEFT  JOIN estesrtgy2.qnp230
			ON QSUN=QRUN
			LEFT JOIN ( 
				select QZREFC 
				from estesrtgy2.qnp302
				where  QZBLTP = 1 
				and QZREFT = 1
			) as t 
			ON QZREFC=QSUN
		) AS USER_INFO
		WHERE createdBY=? 
		AND username=?;
		
		```	

		
========= THE END ==========
============================
			