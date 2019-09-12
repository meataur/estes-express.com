--QUERY_FOR_SUBACCOUNT_NATIONAL 
SELECT * FROM ( SELECT
	TRIM(CMNAME) AS COMPANY,
	TRIM(CMADR1) AS ADDRESS_LINE1,
	TRIM(CMADR2) AS ADDRESS_LINE2,
	TRIM(CMCITY) AS CITY,
	TRIM(CMST) AS STATE,
	TRIM(CMZIP) AS ZIP,
	TRIM(CMZIP4) AS ZIP_EXT,
	TRIM(CMPHON) AS PHONE,
	TRIM(CMFAX) AS FAX,
	CMCUST AS SUB_ACCOUNT_CODE,
	CMNA AS PARENT_ACCOUNT_CODE
FROM FBFILES.RAP001 ) AS T
WHERE UPPER(PARENT_ACCOUNT_CODE) = UPPER(?);
	
--QUERY_FOR_SUBACCOUNT_GROUP91 
SELECT * FROM ( SELECT TRIM(CMNAME) AS COMPANY,
	TRIM(CMADR1) AS ADDRESS_LINE1,
	TRIM(CMADR2) AS ADDRESS_LINE2,
	TRIM(CMCITY) AS CITY,
	TRIM(CMST) AS STATE,
	TRIM(CMZIP) AS ZIP,
	TRIM(CMZIP4) AS ZIP_EXT,
	TRIM(CMPHON) AS PHONE,
	TRIM(CMFAX) AS FAX,
	CMCUST AS SUB_ACCOUNT_CODE,
	CMOCC AS PARENT_ACCOUNT_CODE
FROM FBFILES.RAP001 ) AS T
WHERE UPPER(PARENT_ACCOUNT_CODE) = UPPER(?);
	
--QUERY_FOR_SUBACCOUNT_WEBGROUP 
SELECT * FROM ( SELECT
	TRIM(CMNAME) AS COMPANY,
	TRIM(CMADR1) AS ADDRESS_LINE1,
	TRIM(CMADR2) AS ADDRESS_LINE2,
	TRIM(CMCITY) AS CITY,
	TRIM(CMST) AS STATE,
	TRIM(CMZIP) AS ZIP,
	TRIM(CMZIP4) AS ZIP_EXT,
	TRIM(CMPHON) AS PHONE,
	TRIM(CMFAX) AS FAX,
	CMCUST AS SUB_ACCOUNT_CODE,
	QAGRPC AS PARENT_ACCOUNT_CODE
FROM ESTESRTGY2.QNP245 
INNER JOIN FBFILES.RAP001 
ON QACUSC = CMCUST ) AS T
WHERE UPPER(PARENT_ACCOUNT_CODE) = UPPER(?);