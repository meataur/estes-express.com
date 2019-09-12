/**
 * 
 */
package com.estes.myestes.edirequest.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.edps.logger.ESTESLogger;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.estes.myestes.edirequest.utils.EdiRequestUtil;
import com.estes.myestes.edirequest.utils.PDFConstants;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;


/**
 * @author Lakshman K
 * 
 */
public class PDFBuilder extends BaseBuilder {

	@Override
	protected void buildHeader(PdfWriter pWriter, Document pDocument, EdiRequest pInput) throws DocumentException,
			MalformedURLException, IOException {
		float grayFill = 1.0f;
		float tableWidth = getTableWidth(pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), 3.0f);
		float[] widths = { tableWidth * 0.2f, tableWidth * 0.8f };
		PdfPTable table = getPdfTable(2, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), widths);
		PdfPCell receiptLabelCell = getUniformTextCell(getParagraph(PDFConstants.EDIT_FORM_REQUEST, titleBoldBlack()), Rectangle.NO_BORDER,
				grayFill, true, 1f, Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE);
		
		PdfPCell referenceCell = getUniformTextCell(getParagraph(PDFConstants.REF_NUMBER + pInput.getReferenceNumber(), titleBoldBlack()), Rectangle.NO_BORDER,
				grayFill, true, 1f, Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE);
		
		table.addCell(receiptLabelCell);
		table.addCell(referenceCell);
		pDocument.add(table);
	}

	
	
	@Override
	protected void buildBody(PdfWriter pWriter, Document pDocument, EdiRequest pEdiRequest) throws DocumentException {
		
		float tableWidth = getTableWidth(pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), 3.0f);
		
		float[] mainTableWidths = { tableWidth };
		
		PdfPTable mainTable = getPdfTable(1, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), mainTableWidths);
			
		
		float[] customerTableWidths = { tableWidth * .125f,tableWidth * .125f,tableWidth * .125f,tableWidth * .125f,tableWidth * .125f,tableWidth * .125f,tableWidth * .125f,tableWidth * .125f };
		PdfPTable customerTable = getPdfTable(8, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), customerTableWidths);
		customerTable.setWidthPercentage(100.0f);		
		addCustomerInformation(customerTable, pEdiRequest);
		//pDocument.add(customerTable);	
		PdfPCell cell = getTableAsCell(customerTable);		
		mainTable.addCell(cell);
		
		float[] primaryContactTableWidths = { tableWidth * .1f, tableWidth * .6f, tableWidth * .15f, tableWidth * .15f};
		PdfPTable primaryContactTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), primaryContactTableWidths);
		addPrimaryContact(primaryContactTable, pEdiRequest);
		primaryContactTable.setWidthPercentage(100.0f);	
		//pDocument.add(primaryContactTable);				
		mainTable.addCell(getTableAsCell(primaryContactTable));
		
		float[] secondaryContactTableWidths = { tableWidth * .1f, tableWidth * .6f, tableWidth * .15f, tableWidth * .15f};
		PdfPTable secondaryContactTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), secondaryContactTableWidths);
		addSecondaryContact(secondaryContactTable, pEdiRequest);
		secondaryContactTable.setWidthPercentage(100.0f);	
		//pDocument.add(secondaryContactTable);
		mainTable.addCell(getTableAsCell(secondaryContactTable));
		
		float[] accTPayableTableWidths = { tableWidth * .1f, tableWidth * .6f, tableWidth * .15f, tableWidth * .15f};
		PdfPTable accTPayableTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), accTPayableTableWidths);
		addActPayable(accTPayableTable, pEdiRequest);
		accTPayableTable.setWidthPercentage(100.0f);
		//pDocument.add(accTPayableTable);
		mainTable.addCell(getTableAsCell(accTPayableTable));
		
		float[] businessContactTableWidths = { tableWidth * .1f, tableWidth * .6f, tableWidth * .15f, tableWidth * .15f};
		PdfPTable businessContactTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), businessContactTableWidths);
		addBusinessContact(businessContactTable, pEdiRequest);
		businessContactTable.setWidthPercentage(100.0f);
		//pDocument.add(businessContactTable);
		mainTable.addCell(getTableAsCell(businessContactTable));
		
		float[] trafficContactTableWidths = { tableWidth * .1f, tableWidth * .6f, tableWidth * .15f, tableWidth * .15f};
		PdfPTable trafficContactTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), trafficContactTableWidths);
		addTrafficContact(trafficContactTable, pEdiRequest);
		trafficContactTable.setWidthPercentage(100.0f);
		//pDocument.add(trafficContactTable);
		mainTable.addCell(getTableAsCell(trafficContactTable));
		
		float[] additionalContactTableWidths = { tableWidth * .1f, tableWidth * .6f, tableWidth * .15f, tableWidth * .15f};
		PdfPTable additionalContactTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), additionalContactTableWidths);
		addAdditionalContact(additionalContactTable, pEdiRequest);
		additionalContactTable.setWidthPercentage(100.0f);
		//pDocument.add(additionalContactTable);
		mainTable.addCell(getTableAsCell(additionalContactTable));
		
		float[] billingTypeTableWidths = { tableWidth * 1.0f};
		PdfPTable billingTypeTable = getPdfTable(1, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), billingTypeTableWidths);
		addEDIBilling(billingTypeTable, pEdiRequest);
		billingTypeTable.setWidthPercentage(100.0f);
		//pDocument.add(billingTypeTable);
		mainTable.addCell(getTableAsCell(billingTypeTable));
		
		float[] addFormTypeTableWidths = { tableWidth * .05f, tableWidth * .3f, tableWidth * .05f, tableWidth * .5f, tableWidth * .05f, tableWidth * .05f, tableWidth * .05f, tableWidth * .05f };
		PdfPTable addFormTypeTable = getPdfTable(8, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addFormTypeTableWidths);
		addFormType(addFormTypeTable, pEdiRequest);
		addFormTypeTable.setWidthPercentage(100.0f);
		//pDocument.add(addFormTypeTable);
		mainTable.addCell(getTableAsCell(addFormTypeTable));
		
		float[] addAccountNumbersTableWidths = { tableWidth * 1.0f};
		PdfPTable addAccountNumbersTable = getPdfTable(1, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addAccountNumbersTableWidths);
		addAccountNumbers(addAccountNumbersTable, pEdiRequest);
		addAccountNumbersTable.setWidthPercentage(100.0f);
		//pDocument.add(addAccountNumbersTable);
		mainTable.addCell(getTableAsCell(addAccountNumbersTable));
		
		float[] addAddressLocationTableWidths = { tableWidth * 1.0f};
		PdfPTable addAddressLocationTable = getPdfTable(1, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addAddressLocationTableWidths);
		addAddressLocation(addAddressLocationTable, pEdiRequest);
		addAddressLocationTable.setWidthPercentage(100.0f);
		//pDocument.add(addAddressLocationTable);
		mainTable.addCell(getTableAsCell(addAddressLocationTable));
		
		float[] addBillUsedTableWidths = { tableWidth * 0.3f, tableWidth * 0.05f, tableWidth * 0.1f, tableWidth * 0.05f, tableWidth * 0.5f};
		PdfPTable addBillUsedTable = getPdfTable(5, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addBillUsedTableWidths);
		addBillUsed(addBillUsedTable, pEdiRequest);
		addBillUsedTable.setWidthPercentage(100.0f);
		//pDocument.add(addBillUsedTable);
		mainTable.addCell(getTableAsCell(addBillUsedTable));
		
		float[] addThirdPartyNetworkWidths = { tableWidth * 0.05f, tableWidth * 0.95f};
		PdfPTable addThirdPartyNetworkTable = getPdfTable(2, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addThirdPartyNetworkWidths);
		addThirdPartyNetwork(addThirdPartyNetworkTable, pEdiRequest);
		addThirdPartyNetworkTable.setWidthPercentage(100.0f);
		//pDocument.add(addThirdPartyNetworkTable);
		mainTable.addCell(getTableAsCell(addThirdPartyNetworkTable));
		
		float[] addHeaderTypeWidths = { tableWidth * 0.5f, tableWidth * 0.5f};
		PdfPTable addHeaderTypeTable = getPdfTable(2, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addHeaderTypeWidths);
		addHeaderType(addHeaderTypeTable, pEdiRequest);
		addHeaderTypeTable.setWidthPercentage(100.0f);
		//pDocument.add(addHeaderTypeTable);
		mainTable.addCell(getTableAsCell(addHeaderTypeTable));
		
		float[] addFtpCommDetailsWidths = { tableWidth * 0.1f, tableWidth * 0.225f, tableWidth * 0.225f, tableWidth * 0.225f, tableWidth * 0.225f};
		PdfPTable addFtpCommDetailsTable = getPdfTable(5, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addFtpCommDetailsWidths);
		addFtpCommDetails(addFtpCommDetailsTable, pEdiRequest);
		addFtpCommDetailsTable.setWidthPercentage(100.0f);
		//pDocument.add(addFtpCommDetailsTable);
		mainTable.addCell(getTableAsCell(addFtpCommDetailsTable));
		
		float[] addHeaderInformationWidths = { tableWidth * 0.2375f, tableWidth * 0.2625f,  tableWidth * 0.1875f, tableWidth * 0.3125f};
		PdfPTable addHeaderInformationTable = getPdfTable(4, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addHeaderInformationWidths);
		addHeaderInformation(addHeaderInformationTable, pEdiRequest);
		addHeaderInformationTable.setWidthPercentage(100.0f);
		//pDocument.add(addHeaderInformationTable);
		mainTable.addCell(getTableAsCell(addHeaderInformationTable));
		
		float[] addAnsiVersionWidths = { tableWidth * 0.3f, tableWidth * 0.7f};
		PdfPTable addAnsiVersionTable = getPdfTable(2, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addAnsiVersionWidths);
		addAnsiVersion(addAnsiVersionTable, pEdiRequest);
		addAnsiVersionTable.setWidthPercentage(100.0f);
		//pDocument.add(addAnsiVersionTable);
		mainTable.addCell(getTableAsCell(addAnsiVersionTable));
		
		float[] addPersonCompletingWidths = { tableWidth * 0.2f, tableWidth * 0.8f};
		PdfPTable addPersonCompletingTable = getPdfTable(2, pDocument.getPageSize().getRight(), pDocument.getPageSize().getLeft(), addPersonCompletingWidths);
		addPersonCompleting(addPersonCompletingTable, pEdiRequest);
		addPersonCompletingTable.setWidthPercentage(100.0f);
		//pDocument.add(addPersonCompletingTable);
		mainTable.addCell(getTableAsCell(addPersonCompletingTable));
		
		pDocument.add(mainTable);
							
	}



	
	private void addCustomerInformation(PdfPTable pTable, EdiRequest pEdiRequest) {
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.CUSTOMER_INFO_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
					Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 8, BaseColor.WHITE));
				
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.IS_L2L_REPORT, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getIsL2L(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.COMPANY_NAME, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 6, BaseColor.WHITE));
						
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ADDRESS_REG, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getAddress(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
						
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.CITY_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getCity(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.STATE_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getState(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ZIP_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getZip(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
						
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph( getFormattedPhoneFax(pEdiRequest.getCustomer().getPhone()) , normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getCustomer().getFax()) , normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
				
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.WEBSITE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getWebsite(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
						
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FREIGNT_PAYMENT_AGENCY, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getFreightPaymentAgency(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
				
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FREIGNT_MGMT_COMP, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getCustomer().getFreightManagementCompany(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 8, BaseColor.WHITE));
	}

	private void addPrimaryContact(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PRIM_EDI_CONTACT_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getPrimaryEdiContact().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getPrimaryEdiContact().getPhone()) , normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TITLE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getPrimaryEdiContact().getTitle(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getPrimaryEdiContact().getFax()) , normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.EMAIL_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getPrimaryEdiContact().getEmail(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
	}
	
	private void addSecondaryContact(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.SEC_EDI_CONTACT_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getSecondaryEdiContact().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph( getFormattedPhoneFax(pEdiRequest.getSecondaryEdiContact().getPhone()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TITLE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getSecondaryEdiContact().getTitle(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getSecondaryEdiContact().getFax()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.EMAIL, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getSecondaryEdiContact().getEmail(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
	}
	
	private void addActPayable(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ACC_PAYABLE_CONTACT_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getAccountsPayableContact().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getAccountsPayableContact().getPhone()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TITLE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getAccountsPayableContact().getTitle(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getAccountsPayableContact().getFax()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.EMAIL, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getAccountsPayableContact().getEmail(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
				
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
	}
	
	private void addBusinessContact(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BUSI_CONTACT_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getBusinessContact().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getBusinessContact().getPhone()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TITLE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getBusinessContact().getTitle(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getBusinessContact().getFax()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.EMAIL, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getBusinessContact().getEmail(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
	}

	private void addAdditionalContact(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ADDITIONAL_CONTACT_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getAdditionalContact().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getAdditionalContact().getPhone()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TITLE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getAdditionalContact().getTitle(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph( getFormattedPhoneFax(pEdiRequest.getAdditionalContact().getFax()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.EMAIL, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getAdditionalContact().getEmail(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
	}
	
	private void addTrafficContact(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TRAFFIC_CONTACT_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTrafficContact().getName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph( getFormattedPhoneFax(pEdiRequest.getTrafficContact().getPhone()) , normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TITLE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTrafficContact().getTitle(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.FAX, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph( getFormattedPhoneFax(pEdiRequest.getTrafficContact().getFax()), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.EMAIL, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTrafficContact().getEmail(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 3, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
	}
	
	private void addEDIBilling(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TYPE_OF_EDI_SECTION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getEdiBillingType(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
	}
	
	private void addFormType(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.DOCUMENT_TYPE_STR, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 8, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm204()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.LOAD_TENDER_204, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.AUTO_ACCEPT_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		renderRadioCheck(pEdiRequest.getAutoAccept(), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.YES_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheckNo(pEdiRequest.getAutoAccept(), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NO_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PICKUP_REQ, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm210()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.INVOICE_201, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm211()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BILL_OF_LADING_211, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.USE_211_PICKUP_REQ, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm211()), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.YES_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheckNo(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm211()), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NO_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ARE_YOU_ABLE_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheck(pEdiRequest.getSendReserveProsInBOL06(), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.YES_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheckNo(pEdiRequest.getSendReserveProsInBOL06(), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NO_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PRO_NUM_IN_BOL_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.COMMENTS, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getFormComments(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm212()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TRAILER_MANIFEST_211, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm214()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.SHIPEMEMT_STATUS_211, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.CAN_YOU_PROVIDE_214_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm214()), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.YES_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheckNo(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm214()), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NO_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));	
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph("to Estes Express?", normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isForm990()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.RESPONSE_TO_A_LOAD_990, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));
		
		renderRadioCheck(EdiRequestUtil.getStringFromBool(pEdiRequest.isFormOther()), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.OTHER, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 7, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 8, BaseColor.WHITE));
	}
	
	private void addAccountNumbers(PdfPTable pTable, EdiRequest pEdiRequest) {		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ESTES_ACCOUNT_NUMBERS, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		if(null!=pEdiRequest && null!=pEdiRequest.getEstesAccountNumber()){
			for(int i=0; i<pEdiRequest.getEstesAccountNumber().size(); i++){
			pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getEstesAccountNumber().get(i), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
					Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
			}
		}		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
	}
	
	private void addAddressLocation(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ADDRESS_FOR_LOCATIONS_REQ, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		if(null!=pEdiRequest && null!=pEdiRequest.getEdiAddressLocation()){
			for(int i=0; i<pEdiRequest.getEdiAddressLocation().size(); i++){
				pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getEdiAddressLocation().get(i), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
						Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));				
			}
		}
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
	}
	
	private void addBillUsed(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.WLL_RESERVERD_BILLS, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		renderRadioCheck(pEdiRequest.getWillReservedBillsUsed(), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.YES_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		renderRadioCheckNo(pEdiRequest.getWillReservedBillsUsed(), pTable);
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NO_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));	
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
	}
	
	private void addThirdPartyNetwork(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.THIRD_PARTY_NETWORKS, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, BaseColor.WHITE));
		
		renderRadioCheck(pEdiRequest.getThirdPartyNetworks(), pTable);		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.KLEINSCHMIDT, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		
		renderRadioCheck(pEdiRequest.getOtherThirdPartyCheck(), pTable);	
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.OTHER + pEdiRequest.getOtherThirdPartyValue(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, BaseColor.WHITE));
	}
	
	private void addHeaderType(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TYPE_OF_EDI_HEADER, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getEdiHeaderType(), normalBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, BaseColor.WHITE));
	}
	
	private void addFtpCommDetails(PdfPTable pTable, EdiRequest pEdiRequest) {
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.IF_FTP_PREFERRED_METHOD, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 5, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.SERVER_ADDRESS, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getFtpServerAddress(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.USER_NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getFtpUsername(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.DIRECTORY_PATH, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getFtpDirectoryPath(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PASSWORD, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_RIGHT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getFtpPassword(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
	}
	
	private void addHeaderInformation(PdfPTable pTable, EdiRequest pEdiRequest) {
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.HEADER_INFO, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ESTES_EXPRESS, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ISA_QUALIFIED, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.ISA_ID_EXLA, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.SCAC_EXLA, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PROD_BG01_PASSWORD, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getProdBg01Password(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TEST_BG01_PASSWORD, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTestBg01Password(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PROD_BG03_RECE_ID, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getProdBg03ReceiverId(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TEST_BG03_RECE_ID, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTestBg03ReceiverId(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PROD_ISA_QUALIFIER, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getProdISAQualifier(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TEST_ISA_QUALIFIER, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTestISAQualifier(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PROD_ISA_RECE_ID, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getProdISAReceiverId(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TEST_ISA_RECE_ID, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTestISAReceiverId(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PROD_GSID, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getProdGSId(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TEST_GSID, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTestGSId(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 0.25f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 4, BaseColor.WHITE));
	}
	
	private void addAnsiVersion(PdfPTable pTable, EdiRequest pEdiRequest) {
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.TDCC_ANSI_VERSION, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getTdccAnsiVersion(), normalBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));		
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PDF_BLANK, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 1f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, BaseColor.WHITE));
	}
	
	private void addPersonCompleting(PdfPTable pTable, EdiRequest pEdiRequest) {
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PERSON_COMPLETING_FORM, normalBoldBlack()), Rectangle.NO_BORDER, 0.8f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 2, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.NAME, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(pEdiRequest.getFillersName(), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.PHONE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(getFormattedPhoneFax(pEdiRequest.getFillersPhone()) , normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		
		DateFormat dateFormat = new SimpleDateFormat(PDFConstants.DATE_FORMAT_MM_DD_YYYY);
		Date date = new Date();
		
		pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.DATE, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		pTable.addCell(getUniformTextCell(getParagraph(dateFormat.format(date), normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
				Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
				
	}
	
	@Override
	protected void buildFooter(PdfWriter pWriter, Document pDocument, EdiRequest pEdiRequest) {
		// TODO Auto-generated method stub
	}

	
	@Override
	public ByteArrayOutputStream generatePDFStream(EdiRequest pInput) throws MalformedURLException, DocumentException, IOException {
		Document document = new Document();
		ByteArrayOutputStream  outputStream = null;
		try {
			outputStream = new ByteArrayOutputStream();
			PdfWriter writer = PdfWriter.getInstance(document, outputStream);
			document.open();
			buildHeader(writer, document, pInput);
			buildBody(writer, document, pInput);
		} catch (DocumentException ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "generatePDFStream", ex.getMessage(), ex);
		} catch (IOException ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "generatePDFStream", ex.getMessage(), ex);
		}
		document.close();
		return outputStream;
	}

	
	
	private void renderRadioCheck(String radioValue, PdfPTable pTable){			
		Image img = null;
		img = getCheckedLogo(img, PDFConstants.CHECKED_PNG);
		if(null!=radioValue && (PDFConstants.Y_STR).equalsIgnoreCase(radioValue.trim())){
			pTable.addCell(getImageCell(img, Rectangle.NO_BORDER, 0.9f, true, 5f));
		}else{
			pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
					Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		}
	}
	
	private void renderRadioCheckNo(String radioValue, PdfPTable pTable){			
		Image img = null;
		img = getCheckedLogo(img, PDFConstants.CHECKED_PNG);
		if(null!=radioValue &&(PDFConstants.N_STR).equalsIgnoreCase(radioValue.trim())){
			pTable.addCell(getImageCell(img, Rectangle.NO_BORDER, 0.9f, true, 5f));
		}else{
			pTable.addCell(getUniformTextCell(getParagraph(PDFConstants.BLANK_STR, normalBlack()), Rectangle.NO_BORDER, 0.9f, true, 5f,
					Element.ALIGN_MIDDLE, Element.ALIGN_LEFT, 1, BaseColor.WHITE));
		}
	}
	
	private String getFormattedPhoneFax(String phone){	
		if(phone != null){
			return trim(EdiRequestUtil.getAreaCode(phone))+PDFConstants.HYPEN_STR+trim(EdiRequestUtil.getExchange(phone))+PDFConstants.HYPEN_STR+trim(EdiRequestUtil.getLast4(phone));			
		}
		return PDFConstants.BLANK_STR;		
	}
	
	private String trim(String pValue){
		String returnVal = pValue;
		if(pValue != null){
			returnVal = pValue.trim();
		}
		return returnVal;
	}
	
	private Image getCheckedLogo(Image img, String imageName) {
		URL url = this.getClass().getClassLoader().getResource(imageName);
			ESTESLogger.log(ESTESLogger.INFO, this.getClass(), "getCheckedLogo: Url ", ""+url);
		if(null!=url){
			try{
				img=img.getInstance(url);
			}catch(Exception ex){
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "getCheckedLogo", ex.getMessage(), ex);
			}
		}
		return img;
	}
	
	
}
