/**
 * 
 */
package com.estes.myestes.edirequest.pdf;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import com.edps.logger.ESTESLogger;
import com.estes.myestes.edirequest.dto.EdiRequest;

/**
 * This class will generate Pdf Attachment file
 * @author Lakshman K
 */
public class HTML2PDF {
	
	/**
	 * 
	 * @param input
	 * @param ediNewRequestNumber
	 * @return
	 */
	public ByteArrayOutputStream getAttachmentFileWithHeader(EdiRequest input, String ediNewRequestNumber) {
		ByteArrayOutputStream baos = null;				       
		try {
			PDFBuilder pdfBuilder = new PDFBuilder();
			baos = pdfBuilder.generatePDFStream(input);
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, HTML2PDF.class, "getAttachmentFileWithHeader()", e.getMessage(), e);
		} finally {
			try {
				baos.close();
			} catch (IOException e) {
				ESTESLogger.log(ESTESLogger.ERROR, HTML2PDF.class, "getAttachmentFileWithHeader()", e.getMessage(), e);
			}
		}
		return baos;
	}		
}
