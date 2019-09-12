/**
 *
 */

package com.estes.myestes.claims.dto;

import java.io.Serializable;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.estes.dto.common.Address;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Info request DTO
 */
@ApiModel(value="SubmitClaimRequestDTO", description="Sample model for the getting files")
public class SubmitClaimRequestDTO implements Serializable {
	@ApiModelProperty(notes="If logged in as grouped account have to specify sub account from sub account service")
	String accountNumber;
	@ApiModelProperty(notes="The OT PRO number - XXX-XXXXXXX")
	String otPro;
	@ApiModelProperty(notes="The claim type - Damage/Loss")
	String claimType;
	@ApiModelProperty(notes="The freight type, passed value/display value - Bg,Bags/Bl,Bales/Br,Barrels/Bx,Boxes/Bk,Buckets/Bd,Bundles/Cn,Cans/Ct,Cartons/Cs,Cases/Cr,Crates/Cy,Cylinders/Dr,Drums/Jc,Jerrican/Kt,Kits/Pk,Packages/Pl,Pails/Pt,Pallets/Pc,Pieces/Re,Reels/Rl,Rolls/Sk,Skids/To,Totes/Tl,Truck Load") 
	String freightType;
	@ApiModelProperty(notes="The pro date – mmddyyyy ")
	String proDate;
	@ApiModelProperty(notes="Reference Number")
	String referenceNumber;
	@ApiModelProperty(notes="The bol number")
	String bol;
	@ApiModelProperty(notes="The bol date – mmddyyyy ")
	String bolDate;
	@ApiModelProperty(notes="The claiments name")
	String name;
	@ApiModelProperty(notes="The claiments address line 1")
	String streetAddress1;
	@ApiModelProperty(notes="The claiments address line 2")
	String streetAddress2;
	@ApiModelProperty(notes="The claiments city")
	String city;
	@ApiModelProperty(notes="The claiments state")
	String state;
	@ApiModelProperty(notes="The claiments zip")
	String zip;
	@ApiModelProperty(notes="The claiments email")
	String email;
	@ApiModelProperty(notes="The claiments phone number - (xxx) xxx-xxxx")
	String phone;
	@ApiModelProperty(notes="The claiments fax number - (xxx) xxx-xxxx")
	String fax;
	@ApiModelProperty(notes="The remits name")
	String remitName;
	@ApiModelProperty(notes="The remits address line 1")
	String remitStreetAddress1;
	@ApiModelProperty(notes="The remits address line 2")
	String remitStreetAddress2;
	@ApiModelProperty(notes="The remits city")
	String remitCity;
	@ApiModelProperty(notes="The remits state")
	String remitState;
	@ApiModelProperty(notes="The remits zip")
	String remitZip;
	@ApiModelProperty(notes="The remits email")
	String remitEmail;
	@ApiModelProperty(notes="The remits phone number - (xxx) xxx-xxxx")
	String remitPhone;
	@ApiModelProperty(notes="The auto total of the detail costs")
	String autoTotal;
	@ApiModelProperty(notes="Additional Comments")
	String additionalComments;
	@ApiModelProperty(notes="BOL File")
	MultipartFile bolFile;
	@ApiModelProperty(notes="FB")
	MultipartFile fbFile;
	@ApiModelProperty(notes="Invoice File")
	MultipartFile invoiceFile;
	@ApiModelProperty(notes="Other File")
	MultipartFile otherFile;
	
	@ApiModelProperty(notes="The description for details 1")
	String descriptionDetails1;
	@ApiModelProperty(notes="The quantity for details 1")
	String qtyDetails1;
	@ApiModelProperty(notes="The total cost for details 1")
	String costDetails1;
	@ApiModelProperty(notes="The file for details 1")
	MultipartFile fileDetails1;
	@ApiModelProperty(notes="The description for details 2")
	String descriptionDetails2;
	@ApiModelProperty(notes="The quantity for details 2")
	String qtyDetails2;
	@ApiModelProperty(notes="The total cost for details 2")
	String costDetails2;
	@ApiModelProperty(notes="The file for details 2")
	MultipartFile fileDetails2;
	@ApiModelProperty(notes="The description for details 3")
	String descriptionDetails3;
	@ApiModelProperty(notes="The quantity for details 3")
	String qtyDetails3;
	@ApiModelProperty(notes="The total cost for details 3")
	String costDetails3;
	@ApiModelProperty(notes="The file for details 3")
	MultipartFile fileDetails3;
	@ApiModelProperty(notes="The description for details 4")
	String descriptionDetails4;
	@ApiModelProperty(notes="The quantity for details 4")
	String qtyDetails4;
	@ApiModelProperty(notes="The total cost for details 4")
	String costDetails4;
	@ApiModelProperty(notes="The file for details 4")
	MultipartFile fileDetails4;
	@ApiModelProperty(notes="The description for details 5")
	String descriptionDetails5;
	@ApiModelProperty(notes="The quantity for details 5")
	String qtyDetails5;
	@ApiModelProperty(notes="The total cost for details 5")
	String costDetails5;
	@ApiModelProperty(notes="The file for details 5")
	MultipartFile fileDetails5;
	@ApiModelProperty(notes="The description for details 6")
	String descriptionDetails6;
	@ApiModelProperty(notes="The quantity for details 6")
	String qtyDetails6;
	@ApiModelProperty(notes="The total cost for details 6")
	String costDetails6;
	@ApiModelProperty(notes="The file for details 6")
	MultipartFile fileDetails6;
	@ApiModelProperty(notes="The description for details 7")
	String descriptionDetails7;
	@ApiModelProperty(notes="The quantity for details 7")
	String qtyDetails7;
	@ApiModelProperty(notes="The total cost for details 7")
	String costDetails7;
	@ApiModelProperty(notes="The file for details 7")
	MultipartFile fileDetails7;
	@ApiModelProperty(notes="The description for details 8")
	String descriptionDetails8;
	@ApiModelProperty(notes="The quantity for details 8")
	String qtyDetails8;
	@ApiModelProperty(notes="The total cost for details 8")
	String costDetails8;
	@ApiModelProperty(notes="The file for details 8")
	MultipartFile fileDetails8;
	@ApiModelProperty(notes="The description for details 9")
	String descriptionDetails9;
	@ApiModelProperty(notes="The quantity for details 9")
	String qtyDetails9;
	@ApiModelProperty(notes="The total cost for details 9")
	String costDetails9;
	@ApiModelProperty(notes="The file for details 9")
	MultipartFile fileDetails9;
	@ApiModelProperty(notes="The description for details 10")
	String descriptionDetails10;
	@ApiModelProperty(notes="The quantity for details 10")
	String qtyDetails10;
	@ApiModelProperty(notes="The total cost for details 10")
	String costDetails10;
	@ApiModelProperty(notes="The file for details 10")
	MultipartFile fileDetails10;
	
	public String getAccountNumber() {
		return accountNumber;
	}
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	public String getOtPro() {
		
		otPro = otPro.replaceAll("\\D+", "");
		
		if(otPro.length()<4){
			otPro = String.format("%0"+(4 - otPro.length())+"d%s",0, otPro);
		}
		otPro = otPro.substring(0, 3)+"-"+otPro.substring(3,10);
		return otPro;
	}
	public void setOtPro(String otPro) {
		this.otPro = otPro;
	}
	public String getClaimType() {
		return claimType;
	}
	public void setClaimType(String claimType) {
		this.claimType = claimType;
	}
	public String getFreightType() {
		return freightType;
	}
	public void setFreightType(String freightType) {
		this.freightType = freightType;
	}
	public String getProDate() {
		return proDate;
	}
	public void setProDate(String proDate) {
		this.proDate = proDate;
	}
	public String getReferenceNumber() {
		return referenceNumber;
	}
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}
	public String getBol() {
		return bol;
	}
	public void setBol(String bol) {
		this.bol = bol;
	}
	public String getBolDate() {
		return bolDate;
	}
	public void setBolDate(String bolDate) {
		this.bolDate = bolDate;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getFax() {
		return fax;
	}
	public void setFax(String fax) {
		this.fax = fax;
	}
	public String getRemitName() {
		return remitName;
	}
	public void setRemitName(String remitName) {
		this.remitName = remitName;
	}
	public String getRemitEmail() {
		return remitEmail;
	}
	public void setRemitEmail(String remitEmail) {
		this.remitEmail = remitEmail;
	}
	public String getRemitPhone() {
		return remitPhone;
	}
	public void setRemitPhone(String remitPhone) {
		this.remitPhone = remitPhone;
	}
	public String getAutoTotal() {
		return autoTotal;
	}
	public void setAutoTotal(String autoTotal) {
		this.autoTotal = autoTotal;
	}
	public String getAdditionalComments() {
		return additionalComments;
	}
	public void setAdditionalComments(String additionalComments) {
		this.additionalComments = additionalComments;
	}
	public MultipartFile getBolFile() {
		return bolFile;
	}
	public void setBolFile(MultipartFile bolFile) {
		this.bolFile = bolFile;
	}
	public MultipartFile getFbFile() {
		return fbFile;
	}
	public void setFbFile(MultipartFile fbFile) {
		this.fbFile = fbFile;
	}
	public MultipartFile getInvoiceFile() {
		return invoiceFile;
	}
	public void setInvoiceFile(MultipartFile invoiceFile) {
		this.invoiceFile = invoiceFile;
	}
	public MultipartFile getOtherFile() {
		return otherFile;
	}
	public void setOtherFile(MultipartFile otherFile) {
		this.otherFile = otherFile;
	}
	public String getStreetAddress1() {
		return streetAddress1;
	}
	public void setStreetAddress1(String streetAddress1) {
		this.streetAddress1 = streetAddress1;
	}
	public String getStreetAddress2() {
		return streetAddress2;
	}
	public void setStreetAddress2(String streetAddress2) {
		this.streetAddress2 = streetAddress2;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getZip() {
		return zip;
	}
	public void setZip(String zip) {
		this.zip = zip;
	}
	public String getRemitStreetAddress1() {
		return remitStreetAddress1;
	}
	public void setRemitStreetAddress1(String remitStreetAddress1) {
		this.remitStreetAddress1 = remitStreetAddress1;
	}
	public String getRemitStreetAddress2() {
		return remitStreetAddress2;
	}
	public void setRemitStreetAddress2(String remitStreetAddress2) {
		this.remitStreetAddress2 = remitStreetAddress2;
	}
	public String getRemitCity() {
		return remitCity;
	}
	public void setRemitCity(String remitCity) {
		this.remitCity = remitCity;
	}
	public String getRemitState() {
		return remitState;
	}
	public void setRemitState(String remitState) {
		this.remitState = remitState;
	}
	public String getRemitZip() {
		return remitZip;
	}
	public void setRemitZip(String remitZip) {
		this.remitZip = remitZip;
	}
	public String getDescriptionDetails1() {
		return descriptionDetails1;
	}
	public void setDescriptionDetails1(String descriptionDetails1) {
		this.descriptionDetails1 = descriptionDetails1;
	}
	public String getQtyDetails1() {
		return qtyDetails1;
	}
	public void setQtyDetails1(String qtyDetails1) {
		this.qtyDetails1 = qtyDetails1;
	}
	public String getCostDetails1() {
		return costDetails1;
	}
	public void setCostDetails1(String costDetails1) {
		this.costDetails1 = costDetails1;
	}
	public MultipartFile getFileDetails1() {
		return fileDetails1;
	}
	public void setFileDetails1(MultipartFile fileDetails1) {
		this.fileDetails1 = fileDetails1;
	}
	public String getDescriptionDetails2() {
		return descriptionDetails2;
	}
	public void setDescriptionDetails2(String descriptionDetails2) {
		this.descriptionDetails2 = descriptionDetails2;
	}
	public String getQtyDetails2() {
		return qtyDetails2;
	}
	public void setQtyDetails2(String qtyDetails2) {
		this.qtyDetails2 = qtyDetails2;
	}
	public String getCostDetails2() {
		return costDetails2;
	}
	public void setCostDetails2(String costDetails2) {
		this.costDetails2 = costDetails2;
	}
	public MultipartFile getFileDetails2() {
		return fileDetails2;
	}
	public void setFileDetails2(MultipartFile fileDetails2) {
		this.fileDetails2 = fileDetails2;
	}
	public String getDescriptionDetails3() {
		return descriptionDetails3;
	}
	public void setDescriptionDetails3(String descriptionDetails3) {
		this.descriptionDetails3 = descriptionDetails3;
	}
	public String getQtyDetails3() {
		return qtyDetails3;
	}
	public void setQtyDetails3(String qtyDetails3) {
		this.qtyDetails3 = qtyDetails3;
	}
	public String getCostDetails3() {
		return costDetails3;
	}
	public void setCostDetails3(String costDetails3) {
		this.costDetails3 = costDetails3;
	}
	public MultipartFile getFileDetails3() {
		return fileDetails3;
	}
	public void setFileDetails3(MultipartFile fileDetails3) {
		this.fileDetails3 = fileDetails3;
	}
	public String getDescriptionDetails4() {
		return descriptionDetails4;
	}
	public void setDescriptionDetails4(String descriptionDetails4) {
		this.descriptionDetails4 = descriptionDetails4;
	}
	public String getQtyDetails4() {
		return qtyDetails4;
	}
	public void setQtyDetails4(String qtyDetails4) {
		this.qtyDetails4 = qtyDetails4;
	}
	public String getCostDetails4() {
		return costDetails4;
	}
	public void setCostDetails4(String costDetails4) {
		this.costDetails4 = costDetails4;
	}
	public MultipartFile getFileDetails4() {
		return fileDetails4;
	}
	public void setFileDetails4(MultipartFile fileDetails4) {
		this.fileDetails4 = fileDetails4;
	}
	public String getDescriptionDetails5() {
		return descriptionDetails5;
	}
	public void setDescriptionDetails5(String descriptionDetails5) {
		this.descriptionDetails5 = descriptionDetails5;
	}
	public String getQtyDetails5() {
		return qtyDetails5;
	}
	public void setQtyDetails5(String qtyDetails5) {
		this.qtyDetails5 = qtyDetails5;
	}
	public String getCostDetails5() {
		return costDetails5;
	}
	public void setCostDetails5(String costDetails5) {
		this.costDetails5 = costDetails5;
	}
	public MultipartFile getFileDetails5() {
		return fileDetails5;
	}
	public void setFileDetails5(MultipartFile fileDetails5) {
		this.fileDetails5 = fileDetails5;
	}
	public String getDescriptionDetails6() {
		return descriptionDetails6;
	}
	public void setDescriptionDetails6(String descriptionDetails6) {
		this.descriptionDetails6 = descriptionDetails6;
	}
	public String getQtyDetails6() {
		return qtyDetails6;
	}
	public void setQtyDetails6(String qtyDetails6) {
		this.qtyDetails6 = qtyDetails6;
	}
	public String getCostDetails6() {
		return costDetails6;
	}
	public void setCostDetails6(String costDetails6) {
		this.costDetails6 = costDetails6;
	}
	public MultipartFile getFileDetails6() {
		return fileDetails6;
	}
	public void setFileDetails6(MultipartFile fileDetails6) {
		this.fileDetails6 = fileDetails6;
	}
	public String getDescriptionDetails7() {
		return descriptionDetails7;
	}
	public void setDescriptionDetails7(String descriptionDetails7) {
		this.descriptionDetails7 = descriptionDetails7;
	}
	public String getQtyDetails7() {
		return qtyDetails7;
	}
	public void setQtyDetails7(String qtyDetails7) {
		this.qtyDetails7 = qtyDetails7;
	}
	public String getCostDetails7() {
		return costDetails7;
	}
	public void setCostDetails7(String costDetails7) {
		this.costDetails7 = costDetails7;
	}
	public MultipartFile getFileDetails7() {
		return fileDetails7;
	}
	public void setFileDetails7(MultipartFile fileDetails7) {
		this.fileDetails7 = fileDetails7;
	}
	public String getDescriptionDetails8() {
		return descriptionDetails8;
	}
	public void setDescriptionDetails8(String descriptionDetails8) {
		this.descriptionDetails8 = descriptionDetails8;
	}
	public String getQtyDetails8() {
		return qtyDetails8;
	}
	public void setQtyDetails8(String qtyDetails8) {
		this.qtyDetails8 = qtyDetails8;
	}
	public String getCostDetails8() {
		return costDetails8;
	}
	public void setCostDetails8(String costDetails8) {
		this.costDetails8 = costDetails8;
	}
	public MultipartFile getFileDetails8() {
		return fileDetails8;
	}
	public void setFileDetails8(MultipartFile fileDetails8) {
		this.fileDetails8 = fileDetails8;
	}
	public String getDescriptionDetails9() {
		return descriptionDetails9;
	}
	public void setDescriptionDetails9(String descriptionDetails9) {
		this.descriptionDetails9 = descriptionDetails9;
	}
	public String getQtyDetails9() {
		return qtyDetails9;
	}
	public void setQtyDetails9(String qtyDetails9) {
		this.qtyDetails9 = qtyDetails9;
	}
	public String getCostDetails9() {
		return costDetails9;
	}
	public void setCostDetails9(String costDetails9) {
		this.costDetails9 = costDetails9;
	}
	public MultipartFile getFileDetails9() {
		return fileDetails9;
	}
	public void setFileDetails9(MultipartFile fileDetails9) {
		this.fileDetails9 = fileDetails9;
	}
	public String getDescriptionDetails10() {
		return descriptionDetails10;
	}
	public void setDescriptionDetails10(String descriptionDetails10) {
		this.descriptionDetails10 = descriptionDetails10;
	}
	public String getQtyDetails10() {
		return qtyDetails10;
	}
	public void setQtyDetails10(String qtyDetails10) {
		this.qtyDetails10 = qtyDetails10;
	}
	public String getCostDetails10() {
		return costDetails10;
	}
	public void setCostDetails10(String costDetails10) {
		this.costDetails10 = costDetails10;
	}
	public MultipartFile getFileDetails10() {
		return fileDetails10;
	}
	public void setFileDetails10(MultipartFile fileDetails10) {
		this.fileDetails10 = fileDetails10;
	}
	
	public long getMultipartFilesTotalSize() {
		long totalSize = 0;
		if (getBolFile() != null) {
			totalSize += getBolFile().getSize();
		}
		if (getFbFile() != null) {
			totalSize += getFbFile().getSize();
		}
		if (getInvoiceFile() != null) {
			totalSize += getInvoiceFile().getSize();
		}
		if (getOtherFile() != null) {
			totalSize += getOtherFile().getSize();
		}
		if (getFileDetails1() != null) {
			totalSize += getFileDetails1().getSize();
		}
		if (getFileDetails2() != null) {
			totalSize += getFileDetails2().getSize();
		}
		if (getFileDetails3() != null) {
			totalSize += getFileDetails3().getSize();
		}
		if (getFileDetails4() != null) {
			totalSize += getFileDetails4().getSize();
		}
		if (getFileDetails5() != null) {
			totalSize += getFileDetails5().getSize();
		}
		if (getFileDetails6() != null) {
			totalSize += getFileDetails6().getSize();
		}
		if (getFileDetails7() != null) {
			totalSize += getFileDetails7().getSize();
		}
		if (getFileDetails8() != null) {
			totalSize += getFileDetails8().getSize();
		}
		if (getFileDetails9() != null) {
			totalSize += getFileDetails9().getSize();
		}
		if (getFileDetails10() != null) {
			totalSize += getFileDetails10().getSize();
		}
		return totalSize;
	}

}