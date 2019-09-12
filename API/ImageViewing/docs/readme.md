## ImageViewing Service

### Get Image Types
+ Request: GET /image/types
+ Response:

```java
	@ApiModel(description="Document Type: name value pair for Dropdown")
	public class DocumentType {
		@ApiModelProperty(notes="Document Type Name")
		private String name;
		@ApiModelProperty(notes="Document Type Description")
		private String description;
		@ApiModelProperty(notes="Is Faxable")
		private boolean faxable;
	}

```
```json
	[
	    {
	        "name": "DR",
	        "description": "Delivery Receipt",
	        "faxable": true
	    },
	    {
	        "name": "BOL",
	        "description": "Bill of Lading",
	        "faxable": true
	    },
	    {
	        "name": "WR",
	        "description": "Weights and Research",
	        "faxable": false
	    }
	]
```

### Image Searching
> This is the new service in replace of __POST /image/request__. Faxing functionality is removed from the old one. Faxing is another standalone service. 

+ Request: POST /image/search
+ Request Body:

```java
	@ApiModel(description="Image Request")
	public class ImageSearch {
		@NotNull
		@ApiModelProperty(notes="Image Type/Document Type – BOL/DR/WR",required=true)
		private String documentType;
		
		@NotNull
		@ApiModelProperty(notes="Search Type : F for PRO Number, B for Bill of Lading Number (LTL shipments only), P for Purchase Order Number (LTL shipments only), F for Interline PRO Number")
		private String searchType;
		
		@NotNull
		@ApiModelProperty(notes="Search Criteria. (Note - Mutpliple should be space or new line separated.)")
		private String searchTerm;
		
		public List<String> getSearchCriteria(){
			return Arrays.asList(searchTerm.trim().split("\\s+"));
		}
	}

```
```json
{
    "documentType": "DR",
    "searchType": "F",
    "searchTerm": "171-9053070 171-9053069 171-9053068 171-9053067 000-0000000 171-9053066 171-9053064 8500274746 8500254474 FRH1O76497371 4500649234"
}
```
+ Response

```java
@ApiModel(description="Image Result")
public class ImageResult {
	@ApiModelProperty(notes="Request Number")
	private String requestNumber;
	
	@ApiModelProperty(notes="Search Type : F for PRO Number, B for Bill of Lading Number (LTL shipments only), P for Purchase Order Number (LTL shipments only), F for Interline PRO Number")
	private String searchType;
	
	@ApiModelProperty(notes="Search Data: Formatted  PRO Number/Bill of Lading Number/Purchase Order Number/Interline PRO Number")
	private String searchData;
	
	@ApiModelProperty(notes="Search Key Value 1")
	private String key1;
	
	@ApiModelProperty(notes="Search Key Value 2")
	private String key2;
	
	@ApiModelProperty(notes="Search Key Value 3")
	private String key3;
	
	@ApiModelProperty(notes="Search Key Value 4")
	private String key4;
	
	@ApiModelProperty(notes="Search Key Value 5")
	private String key5;
	
	@ApiModelProperty(notes="Document type – BOL/DR/WR")
	private String docType;
	
	@ApiModelProperty(notes="Image Status: ERROR/WORKING/NOT_AVAILABLE/AVAILABLE")
	private ImageRequestStatus status;
	
	@ApiModelProperty(notes="Image Error Message When status is ERROR, otherwise null.")
	private String errorMessage;
	
}
```

```json
	[
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "8500274746",
	        "key1": "096",
	        "key2": "0073246",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "AVAILABLE",
	        "errorMessage": null
	    },
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "8500274746",
	        "key1": "202",
	        "key2": "0138461",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "ERROR",
	        "errorMessage": "This document has not yet been imaged."
	    },
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "8500274746",
	        "key1": "223",
	        "key2": "0792363",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "WORKING",
	        "errorMessage": null
	    },
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "8500254474",
	        "key1": "223",
	        "key2": "0792357",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "WORKING",
	        "errorMessage": null
	    },
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "4500649234",
	        "key1": "212",
	        "key2": "0670965",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "AVAILABLE",
	        "errorMessage": null
	    },
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "8500254474",
	        "key1": "096",
	        "key2": "0073225",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "AVAILABLE",
	        "errorMessage": null
	    },
	    {
	        "requestNumber": "8907621201903251111",
	        "searchData": "FRH1O76497371",
	        "key1": "213",
	        "key2": "0497397",
	        "key3": "",
	        "key4": "",
	        "key5": "",
	        "docType": "BOL",
	        "status": "AVAILABLE",
	        "errorMessage": null
	    }
	]
```


### Get Image Status
> This service is same.
+ Request 
```
	GET /image/status?requestNumber=7885850201902151431&searchData=0401254310&docType=DR
```
+ Response:

```java
	public class ImageStatusResponse {
		@ApiModelProperty(notes="Request Number")
		private String requestNumber;
		
		@ApiModelProperty(notes="Search Data")
		private String searchData;
		
		@ApiModelProperty(notes="Document type – BOL/DR/WR")
		private String docType;
		
		@ApiModelProperty(notes="Image Status")
		private ImageRequestStatus status;
		
	}
```
```json
{
    "requestNumber": "7885850201902151431",
    "searchData": "0401254310",
    "docType": "DR",
    "status": "AVAILABLE"
}
```

### View Image
+ Request: 
```
	GET /image/view?docType=DR&key1=040&key2=1254310&key3=&key4=&key5=
```

+ Response:
```java
	@ApiModel(description="Image")
	public class Image {
		@ApiModelProperty(notes="document Id")
		private String documentId;
		
		@ApiModelProperty(notes="Image Output Directory")
		private String outputDirectory;
		
		@ApiModelProperty(notes="Image Output File Name")
		private String outputFileName;
		@ApiModelProperty(notes="NAS Direct Access")
		private String nasDirectAccess;
		
		@ApiModelProperty(notes="Image Location")
		private String imageLocation;
	}
```

```json
	[
	    {
	        "documentId": "172190043189",
	        "outputDirectory": "/mnt/exlaprod_docview/",
	        "outputFileName": "i0401254310DR1.gif",
	        "nasDirectAccess": "",
	        "imageLocation": "http://qa.estesinternal.com/docview/i0401254310DR1.gif"
	    },
	    {
	        "documentId": "172190043190",
	        "outputDirectory": "/mnt/exlaprod_docview/",
	        "outputFileName": "i0401254310DR_21.gif",
	        "nasDirectAccess": "",
	        "imageLocation": "http://qa.estesinternal.com/docview/i0401254310DR_21.gif"
	    }
	]
```
### Image Faxing
+ Request: 
```
	POST /image/fax
```
+ Request Body:

```java
	@ApiModel(description="Image Request")
	public class ImageFax {
		
		@NotNull
		@ApiModelProperty(position=1, example="DR", notes="Image Type/Document Type – BOL/DR/WR",required=true)
		private String documentType;
		
		@NotNull
		@ApiModelProperty(position=2, required=true, example="F", notes="Search Type : F for PRO Number, B for Bill of Lading Number (LTL shipments only), P for Purchase Order Number (LTL shipments only), F for Interline PRO Number")
		private String searchType;

		@NotNull
		@ApiModelProperty(position=3, example="037-7688929",notes="Pro Number is composed of key1 & key2 by hyphen/dash(-) separated.")
		private List<String> proNumbers;
		
		@ApiModelProperty(notes="Fax Information")
		@NotNull
		@Valid
		private FaxInfo faxInfo;
	}

```

```json
	{
	  "faxInfo": {
	    "companyName": "Estes Express Lines",
	    "attention": "Director",
	    "faxNumber": "(804) 345-9876"
	  },
	  "documentType": "BOL",
	  "searchType": "B",
	  "proNumbers": ["096-0073246"]
	}
```
+ Response:

```java
public class ServiceResponse
{
	String errorCode;
	String message;
	String fieldName;
	String severity;
	String badData;
	String redirectUrl;
}
```

```json
{
    "errorCode": "",
    "message": "Successfully sent to your given fax number",
    "fieldName": null,
    "severity": null,
    "badData": null,
    "redirectUrl": null
}
```
### Image Emailing
+ Request: POST /image/email
+ RequestBody:

```java
@ApiModel(description="Image Document Email Request")
public class ImageEmail {
	
	@NotNull
	@ApiModelProperty(position=1, required=true, allowEmptyValue=false,notes="Image Url")
	@Size(min=1,max=10,message="Number of documents should be 1 to 10")
	private List<String> imageUrl;
	
	@ApiModelProperty(position=2, required=false, notes="User Email Address. It is optional upon checkbox selection.")
	private String userEmail;
	
	@NotNull
	@ApiModelProperty(position=3, required=true, allowEmptyValue=false, notes="Recipient email addresses. At least one email address is required")
	@Size(min=1,message="At least one email address is required")
	private List<String> recipientEmails;
	
}

```

```json
{
  "imageUrl": [
    "http://qa.estesinternal.com/docview/i2120670965BOL1.gif",
    "http://qa.estesinternal.com/docview/i0960073225BOL1.gif"
  ],
  "userEmail": "Ataur.Rahman@extes-express.com",
  "recipientEmails": [
    "Pradeep.Karuturi@estes-express.com",
    "Lakshman.Kandaswamy@estes-express.com"
  ]
}
```

+ Response:

```java
public class ServiceResponse
{
	String errorCode;
	String message;
	String fieldName;
	String severity;
	String badData;
	String redirectUrl;
}
```

```json
{
    "errorCode": "",
    "message": "",
    "fieldName": null,
    "severity": null,
    "badData": null,
    "redirectUrl": null
}
```