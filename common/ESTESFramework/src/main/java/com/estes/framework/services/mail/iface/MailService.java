package com.estes.framework.services.mail.iface;

import java.io.File;
import java.util.List;
import java.util.Map;

import com.estes.framework.exception.ESTESException;
/**
 * 
 * @author thiruar
 *
 */
public interface MailService {

	/**
	 * 
	 * @param fromAddress
	 * @param toAddress
	 * @param bccAddress
	 * @param subject
	 * @param messageBody
	 * @return
	 * @throws ESTESException
	 */
	public boolean sendEmail(String fromAddress, String companyName, String toAddress, String bccAddress, String subject, String messageBody, Map<String, String> imgSrcMap)throws ESTESException;
	
	/**
	 * Method to send one mail to multiple receipients.
	 * @param fromAddress
	 * @param toAddress
	 * @param subject
	 * @param messageBody
	 * @return
	 * @throws ESTESException
	 */
	public boolean sendBulkEmails(String fromAddress, String companyName, List<String> toAddress, String bccAddress, String subject, String messageBody, Map<String, String> imgSrcMap)throws ESTESException;
	
	
	/**
	 * 
	 * @param fromAddress
	 * @param companyName
	 * @param toAddress
	 * @param bccAddress
	 * @param subject
	 * @param messageBody
	 * @return
	 */
	public boolean sendAttachedMail(String fromAddress, String companyName, List<String> toAddress, String subject, String messageBody, Map<String, File>attachmentFileMap)throws ESTESException; 
}
