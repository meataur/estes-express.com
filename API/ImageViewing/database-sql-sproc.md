Image Viewing APP:
==================

Database:
---------
    
* __TABLE:__
	+ __FBFILES.XIG10P526__ : Image Retrieval - Image Return Parm File
		- Signature:

			```sql
				CREATE TABLE FBFILES.XIG10P526 ( 
					RPSRCH CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
					RPSTYPE CHAR(1) CCSID 37 NOT NULL DEFAULT '' , 
					RPDOCTY CHAR(6) CCSID 37 NOT NULL DEFAULT '' , 
					RPRAND# CHAR(7) CCSID 37 NOT NULL DEFAULT '' , 
					RPRQSTO CHAR(19) CCSID 37 NOT NULL DEFAULT '' , 
					RPWEBID CHAR(10) CCSID 37 NOT NULL DEFAULT '' , 
					RPRQST# CHAR(19) CCSID 37 NOT NULL DEFAULT '' , 
					RPERROR CHAR(7) CCSID 37 NOT NULL DEFAULT '' , 
					RPSEARCH1 CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
					RPSEARCH2 CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
					RPSEARCH3 CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
					RPSEARCH4 CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
					RPSEARCH5 CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
					RPDATE DATE NOT NULL DEFAULT CURRENT_DATE , 
					RPTIME TIME NOT NULL DEFAULT CURRENT_TIME , 
					RPPGM CHAR(10) CCSID 37 NOT NULL DEFAULT '' , 
					RPJOB CHAR(10) CCSID 37 NOT NULL DEFAULT '' , 
					RPJOBN CHAR(6) CCSID 37 NOT NULL DEFAULT '' 
				)
			``` 

		- Column Level:

			| Column Name  | Column Label        |
			|:------------:|:-------------------:|
			| RPSRCH       |  Search  Data       |
			| RPSTYPE      |  Search Type        |
			| RPDOCTY      |  Document Type      |
			| RPRAND#      |  Random Number      |
			| RPRQSTO      |  Request Origin     |
			| RPWEBID      |  Web Procedure Id   |
			| RPRQST#      |  Request Number     |
			| RPERROR      |  Error Code         |
			| RPSEARCH1    |  Search Key Value 1 |
			| RPSEARCH2    |  Search Key Value 2 |
			| RPSEARCH3    |  Search Key Value 3 |
			| RPSEARCH4    |  Search Key Value 4 |
			| RPSEARCH5    |  Search Key Value 5 |
			| RPDATE       |  Date               |
			| RPTIME       |  Time               |
			| RPPGM        |  Program            |
			| RPJOB        |  Job                |
			| RPJOBN       |  Job#               |

	+ __FBFILES.XIG10P020__ Image Retrieval Initial Request File
		- Signature:

			```sql
			CREATE TABLE FBFILES.XIG10P020 ( 
				SEARCH_KEY_VALUE_1 FOR COLUMN KEYVAL1    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_2 FOR COLUMN KEYVAL2    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_3 FOR COLUMN KEYVAL3    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_4 FOR COLUMN KEYVAL4    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_5 FOR COLUMN KEYVAL5    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				FRIEGHT_BILL_DATE FOR COLUMN FB_DATE    DECIMAL(8, 0) NOT NULL DEFAULT 0 , 
				IMAGE_FOUND_FLAG FOR COLUMN IMGFND     CHAR(1) CCSID 37 NOT NULL DEFAULT '' , 
				DOCUMENT_TYPE FOR COLUMN DOCTYPE    CHAR(8) CCSID 37 NOT NULL DEFAULT '' , 
				OUTPUT_DIRECTORY FOR COLUMN OUTDIR     CHAR(255) CCSID 37 NOT NULL DEFAULT '' , 
				OUTPUT_FILE_NAME FOR COLUMN OUTFILE    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				DOC_ID FOR COLUMN DOCID      CHAR(12) CCSID 37 NOT NULL DEFAULT '' , 
				PROCESS_ID FOR COLUMN PROCID     CHAR(15) CCSID 37 NOT NULL DEFAULT '' , 
				ADD_DATE FOR COLUMN ADDDATE    DATE NOT NULL DEFAULT CURRENT_DATE , 
				DELETE_BY_DATE FOR COLUMN DELDATE    DATE NOT NULL DEFAULT CURRENT_DATE , 
				RANDOM_NUMBER FOR COLUMN RANDOM#    CHAR(7) CCSID 37 NOT NULL DEFAULT '' , 
				REQUEST_NUMBER FOR COLUMN REQUEST#   CHAR(19) CCSID 37 NOT NULL DEFAULT '' , 
				PROGRAM_NAME FOR COLUMN PGMNAME    CHAR(10) CCSID 37 NOT NULL DEFAULT '' , 
				NAS_DIRECT_ACCESS FOR COLUMN NASACC     CHAR(1) CCSID 37 NOT NULL DEFAULT ''
			)
			```   
		- Column Label:

			| Column Name  | Column Label |
			| ------------- | ------------- |
			| SEARCH\_KEY\_VALUE_1 | Search Key Value 1 |
			| SEARCH\_KEY\_VALUE_2 | Search Key Value 2 |
			| SEARCH\_KEY\_VALUE_3 | Search Key Value 3 |
			| SEARCH\_KEY\_VALUE_4 | Search Key Value 4 |
			| SEARCH\_KEY\_VALUE_5 | Search Key Value 5 |
			| FRIEGHT\_BILL\_DATE | Freight Bill Date | 
			| IMAGE\_FOUND\_FLAG | Image Found Flag |
			| DOCUMENT\_TYPE | Document Type |
			| OUTPUT\_DIRECTORY | Output Directory Name | 
			| OUTPUT\_FILE_NAME | Output File Name |
			| DOC\_ID | Document ID |
			| PROCESS\_ID | Process ID |
			| ADD\_DATE | Date Record Added | 
			| DELETE\_BY\_DATE | Delete by Date |
			| RANDOM\_NUMBER | Random Number |
			| REQUEST\_NUMBER | Request Number |
			| PROGRAM\_NAME | Record Creation Program | 
			| NAS_DIRECT_ACCESS | NAS Direct Access |
	+ __FBFILES.XIG10P021__ Image Retrieval Image Location File
		- Signature:
		
			```sql
			CREATE TABLE FBFILES.XIG10P021 ( 
				SEARCH_KEY_VALUE_1 FOR COLUMN KEYVAL1    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_2 FOR COLUMN KEYVAL2    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_3 FOR COLUMN KEYVAL3    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_4 FOR COLUMN KEYVAL4    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				SEARCH_KEY_VALUE_5 FOR COLUMN KEYVAL5    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				FREIGHT_BILL_DATE FOR COLUMN FB_DATE    DECIMAL(8, 0) NOT NULL DEFAULT 0 , 
				IMAGE_FOUND_FLAG FOR COLUMN IMGFND     CHAR(1) CCSID 37 NOT NULL DEFAULT '' , 
				DOC_TYPE FOR COLUMN DOCTYPE    CHAR(8) CCSID 37 NOT NULL DEFAULT '' , 
				OUTPUT_DIRECTORY FOR COLUMN OUTDIR     CHAR(255) CCSID 37 NOT NULL DEFAULT '' , 
				OUTPUT_FILENAME FOR COLUMN OUTFILE    CHAR(30) CCSID 37 NOT NULL DEFAULT '' , 
				DOC_ID FOR COLUMN DOCID      CHAR(12) CCSID 37 NOT NULL DEFAULT '' , 
				SYS_ID FOR COLUMN SYSID      CHAR(6) CCSID 37 NOT NULL DEFAULT '' , 
				DRIVE_LETTER FOR COLUMN DRIVE      CHAR(1) CCSID 37 NOT NULL DEFAULT '' , 
				IMAGE_SYSTEM FOR COLUMN IMGSYS     CHAR(1) CCSID 37 NOT NULL DEFAULT '' , 
				PROCESS_ID FOR COLUMN PROCID     CHAR(15) CCSID 37 NOT NULL DEFAULT '' , 
				PROGRAM_NAME FOR COLUMN PGMNAME    CHAR(10) CCSID 37 NOT NULL DEFAULT '' , 
				PROCESS_COUNTER FOR COLUMN PROCCNT    DECIMAL(5, 0) NOT NULL DEFAULT 0 , 
				ADD_DATE FOR COLUMN ADDDATE    DATE NOT NULL DEFAULT CURRENT_DATE , 
				ADD_TIME FOR COLUMN ADDTIME    TIME NOT NULL DEFAULT CURRENT_TIME , 
				PROC_DATE FOR COLUMN PROCDATE   DATE NOT NULL DEFAULT CURRENT_DATE , 
				PROC_TIME FOR COLUMN PROCTIME   TIME NOT NULL DEFAULT CURRENT_TIME , 
				NAS_DIRECT_ACCESS FOR COLUMN NASACC     CHAR(1) CCSID 37 NOT NULL DEFAULT ''
			)
			```
		- Column Label:

			| Column Name  | Column Label |
			| ------------- | ------------- |
			| SEARCH\_KEY\_VALUE\_1  |  Search Key Value 1 |
			| SEARCH\_KEY\_VALUE\_2 | Search Key Value 2 |
			| SEARCH\_KEY\_VALUE\_3 | Search Key Value 3 |
			| SEARCH\_KEY\_VALUE_4 | Search Key Value 4 |
			| SEARCH\_KEY\_VALUE\_5 | Search Key Value 5 |
			| FREIGHT\_BILL\_DATE | Freight Bill Date |
			| IMAGE\_FOUND\_FLAG | Image Found Flag |
			| DOC\_TYPE | Document Type |
			| OUTPUT\_DIRECTORY | Output Directory Name |
			| OUTPUT\_FILENAME | Output File Name |
			| DOC\_ID | Document ID |
			| SYS\_ID | System ID |
			| DRIVE\_LETTER | Drive Letter |
			| IMAGE\_SYSTEM | Image System |
			| PROCESS\_ID | Process ID |
			| PROGRAM\_NAME | Record Creation Program |
			| PROCESS\_COUNTER | Process Counter |
			| ADD\_DATE | Date Record Added |
			| ADD\_TIME | Time Record Added |
			| PROC\_DATE | Last Process Date |
			| PROC\_TIME | Last Process Time |
			| NAS\_DIRECT\_ACCESS | NAS Direct Access |

* __SPROC:__
	+ __FBPGMS.SP_GETDOCTYPES__: 
		- To get the list of doctypes for a user.
		- Signature

			```sql
			CREATE PROCEDURE FBPGMS.SP_GETDOCTYPES ( 
				IN "USER" CHAR(10) , 
				IN ACCOUNT CHAR(7) , 
				OUT "ERROR" CHAR(7)
			)
			```

	+ __FBPGMS.SP_XIG10C525__:
		- To send image view request
		- Signature
		
			```sql
			CREATE PROCEDURE FBPGMS.SP_XIG10C525 ( 
				IN SEARCH_CRITERIA CHAR(30) , 
				IN SEARCH_TYPE CHAR(1) , 
				IN DOC_TYPE CHAR(6) , 
				IN RANDOM_NUMBER CHAR(7) , 
				IN RQSTORIG CHAR(19) , 
				IN WEBID CHAR(10) , 
				INOUT RQSTNBR CHAR(19) , 
				OUT "ERROR" CHAR(7)
			) 
			```
	+ __FBPGMS.SP_WRITEFAXREQUEST__:
		- To write Fax Request
		- Signature:

			```sql
			CREATE PROCEDURE FBPGMS.SP_WRITEFAXREQUEST ( 
				IN RANDOM_NUMBER CHAR(7) , 
				IN SEARCH_CRITERIA CHAR(25) , 
				IN SEARCH_TYPE CHAR(3) , 
				IN DOC_TYPE CHAR(8) , 
				IN COMPANY CHAR(30) , 
				IN ATTENTION CHAR(40) , 
				IN CUSTOMER CHAR(7) , 
				IN AREA_CODE CHAR(3) , 
				IN EXCHANGE CHAR(3) , 
				IN LAST4 CHAR(4) , 
				OUT "ERROR" CHAR(7)
			) 
			```

* __SQL:__
	+ To get requested image view List:
		- __RPRQST#__ : Request Number
		- __query__:

			```sql
				SELECT DISTINCT RPSRCH, 
					RPSEARCH1,
					RPSEARCH2,
					RPSEARCH3,
					RPSEARCH4,
					RPSEARCH5,
					RPDOCTY AS DOCTYPE,
					TRIM(RPERROR) AS ERROR,
					IMGFND AS IMAGE_FOUND_FLAG,
					RPSRCH AS SEARCHDATA
				FROM FBFILES.XIG10P526
				LEFT OUTER JOIN FBFILES.XIG10P020
					ON RPSEARCH1 = KEYVAL1
						AND RPSEARCH2 = KEYVAL2
						AND RPSEARCH3 = KEYVAL3
						AND RPSEARCH4 = KEYVAL4
						AND RPSEARCH5 = KEYVAL5
						AND RPRQST# = REQUEST#
						AND DOCTYPE = RPDOCTY
				WHERE RPRQST# = ?
			```
	+ To get image info 
		- __query__:

			```sql
			SELECT * FROM FBFILES.XIG10P021 
			WHERE KEYVAL1 = ? 
				AND KEYVAL2 = ?
				AND KEYVAL3 = ?
				AND KEYVAL4 = ?
				AND KEYVAL5 = ?
				AND DOCTYPE = ?
		  ```



Oauth2:
------
__scope__ = __PDN100__

Rest Services:
--------------
* **GET /image/types**
* **POST /image/request**
	+ __request type:__
		- __View__
		- __Fax__
* **GET /image/status**
* **GET /image/view**