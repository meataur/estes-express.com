/**
 * 
 */
package com.estes.myestes.edirequest.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import com.edps.logger.ESTESLogger;
import com.estes.myestes.edirequest.dto.EdiRequest;
import com.itextpdf.text.BadElementException;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.Barcode39;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

/**
 * @author Lakshman K
 * 
 */
public abstract class BaseBuilder {

	protected abstract void buildHeader(PdfWriter pWriter, Document pDocument, EdiRequest pInput) throws DocumentException,
			MalformedURLException, IOException;

	protected abstract void buildBody(PdfWriter pWriter, Document pDocument, EdiRequest pInput) throws DocumentException,
			MalformedURLException, IOException;

	protected abstract void buildFooter(PdfWriter pWriter, Document pDocument, EdiRequest pInput) throws DocumentException,
			MalformedURLException, IOException;

	public abstract ByteArrayOutputStream generatePDFStream(EdiRequest pInput) throws MalformedURLException, DocumentException, IOException;

	protected float getTableWidth(float pRight, float pLeft, float pPadding) {
		return (pRight - pLeft - pPadding);
	}

	protected Paragraph getParagraph(String pText, Font pFont) {
		Paragraph paragraph = new Paragraph(pText, pFont);
		return paragraph;
	}

	protected PdfPCell getImageCell(Image pImage, int pBorder, float pGrayFill, boolean pNoWrap, float pPadding) {
		PdfPCell cell = new PdfPCell(pImage, true);
		cell.setBorder(pBorder);
		cell.setGrayFill(pGrayFill);
		cell.setNoWrap(pNoWrap);
		cell.setPadding(pPadding);
		return cell;
	}

	protected PdfPCell getUniformTextCell(Paragraph pText, int pBorder, float pGrayFill, boolean pNoWrap, float pPadding,
			int pVerticalAlignment, int pHorizontalAlignment, int pColspan, BaseColor pBackgroundColor) {
		PdfPCell cell = new PdfPCell(pText);
		cell.setBorder(pBorder);
		cell.setBackgroundColor(pBackgroundColor);
		cell.setGrayFill(pGrayFill);
		cell.setNoWrap(pNoWrap);
		cell.setPadding(pPadding);
		cell.setVerticalAlignment(pVerticalAlignment);
		cell.setHorizontalAlignment(pHorizontalAlignment);
		cell.setColspan(pColspan);
		return cell;
	}

	/**
	 * @param pTable
	 * @return
	 */
	protected PdfPCell getTableAsCell(PdfPTable pTable) {
		PdfPCell cell = new PdfPCell(pTable);
		cell.setBorder(Rectangle.NO_BORDER);
		return cell;
	}
	
	protected PdfPCell getUniformTextCell(Paragraph pText, int pBorder, float pGrayFill, boolean pNoWrap, float pPadding,
			int pVerticalAlignment, int pHorizontalAlignment, int pColspan, BaseColor pBackgroundColor, boolean pEnableWrap) {
		PdfPCell cell = new PdfPCell(pText);
		cell.setBorder(pBorder);
		cell.setBackgroundColor(pBackgroundColor);
		cell.setGrayFill(pGrayFill);
		cell.setNoWrap(pNoWrap);
		cell.setPadding(pPadding);
		cell.setVerticalAlignment(pVerticalAlignment);
		cell.setHorizontalAlignment(pHorizontalAlignment);
		cell.setColspan(pColspan);
		if(pEnableWrap){
			cell.setNoWrap(false);
		}
		return cell;
	}
	
	protected PdfPTable getPdfTable(int pNoOfColumns, float pRightXCoordinate, float pLeftXCoordinate, float[] pWidths) {
		PdfPTable table = new PdfPTable(pWidths);
		table.setSpacingBefore(0f);
		table.setSpacingAfter(0f);
		table.setTotalWidth(pRightXCoordinate - pLeftXCoordinate);		
		return table;
	}
	
	protected PdfPTable getMainPdfTable(int pNoOfColumns, float pRightXCoordinate, float pLeftXCoordinate, float[] pWidths) {
		PdfPTable table = new PdfPTable(pWidths);
		table.setSpacingBefore(0f);
		table.setSpacingAfter(0f);
		table.setTotalWidth(pRightXCoordinate - pLeftXCoordinate);
		return table;
	}

	protected void addBlankLines(Document pDocument, int pBlankLineCount) throws DocumentException {
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < pBlankLineCount; i++) {
			sb.append("\n");
		}
		pDocument.add(new Phrase(sb.toString()));
	}

	protected Image getBarCodeImage(PdfWriter pWriter, String pBarCode) {
		Barcode39 code39 = new Barcode39();
		code39.setCode(pBarCode);
		code39.setAltText("");
		return code39.createImageWithBarcode(pWriter.getDirectContent(), null, null);
	}

	protected Image getEstesLogo() {
		Image img = null;
		URL url = this.getClass().getClassLoader().getResource("estesLogo.png");
		if (url != null) {
			try {
				img = Image.getInstance(url);
			} catch (BadElementException ex) {
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "getEstesLogo", ex.getMessage(), ex);
			} catch (MalformedURLException ex) {
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "getEstesLogo", ex.getMessage(), ex);
			} catch (IOException ex) {
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "getEstesLogo", ex.getMessage(), ex);
			}
		}
		return img;
	}

	protected String getTimeStamp() {
		return null;
	}

	protected Font h1Black() {
		return FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, BaseColor.BLACK);
	}

	protected Font h1ABlack() {
		return FontFactory.getFont(FontFactory.HELVETICA, 14, Font.BOLD, BaseColor.BLACK);
	}

	protected Font h1White() {
		return FontFactory.getFont(FontFactory.HELVETICA, 16, Font.BOLD, BaseColor.WHITE);
	}

	protected Font h2Black() {
		return FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	}

	protected Font h2White() {
		return FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	}

	protected Font titleBlack() {
		return FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.BLACK);
	}

	protected Font titleWhite() {
		return FontFactory.getFont(FontFactory.HELVETICA, 12, Font.BOLD, BaseColor.WHITE);
	}

	protected Font subtitleBlack() {
		return FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.BLACK);
	}

	protected Font subtitleWhite() {
		return FontFactory.getFont(FontFactory.HELVETICA, 11, Font.BOLD, BaseColor.WHITE);
	}

	protected Font normalBlack() {
		return FontFactory.getFont(FontFactory.HELVETICA, 7, Font.NORMAL, BaseColor.BLACK);
	}

	protected Font normalBoldBlack() {
		return FontFactory.getFont(FontFactory.HELVETICA, 7, Font.BOLD, BaseColor.BLACK);
	}
	
	protected Font titleBoldBlack() {
		return FontFactory.getFont(FontFactory.HELVETICA, 10, Font.BOLD, BaseColor.BLACK);
	}

	protected Font normalWhite() {
		return FontFactory.getFont(FontFactory.HELVETICA, 10, Font.NORMAL, BaseColor.WHITE);
	}
	
}
