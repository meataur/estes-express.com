package com.estes.framework.services.mail.impl;

import java.io.File;
import java.io.UnsupportedEncodingException;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.UrlResource;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.mail.javamail.MimeMessagePreparator;

import com.estes.framework.exception.ESTESException;
import com.estes.framework.logger.ESTESLogger;
import com.estes.framework.services.mail.iface.MailService;

/**
 * This class is used for sending out email notification to suremove
 * customers.This is a super class.
 * 
 * 
 */
public class MailServiceImpl implements MailService {

	@Autowired
	private JavaMailSender mailSender;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.estes.framework.services.mail.iface.MailService#sendEmail(java.lang
	 * .String, java.lang.String, java.lang.String, java.lang.String,
	 * java.lang.String)
	 */
	public boolean sendEmail(final String fromAddress, final String companyName, final String toAddress, final String bccAddress,
			final String subject, final String messageBody, final Map<String, String> imgSrcMap) throws ESTESException {
		// TODO Auto-generated method stub
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
					message.setTo(toAddress);
					if(StringUtils.isNotBlank(companyName)){
						try {
							message.setFrom(fromAddress, companyName);
						} catch (UnsupportedEncodingException e) {
							
						}
					}else{
						message.setFrom(fromAddress);
					}
					message.setSubject(subject);
					if (StringUtils.isNotBlank(bccAddress)) {
						message.setBcc(bccAddress);
					}
					message.setText(messageBody, true);			
					processImageResources(message, imgSrcMap);					
				}
			};
			mailSender.send(preparator);
			return true;
		} catch (MailException me) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "FW:sendEmail", me.getMessage(), me);
		} catch (Exception ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "FW:sendEmail", ex.getMessage(), ex);
		}
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.estes.framework.services.mail.iface.MailService#sendEmail(java.lang
	 * .String, java.util.List, java.lang.String, java.lang.String)
	 */
	public boolean sendBulkEmails(String fromAddress, String companyName, List<String> toAddressList, String bccAddress, String subject,
			String messageBody, Map<String, String> imgSrcMap) throws ESTESException {
		if (null != toAddressList) {
			try {
				for (String toAddress : toAddressList) {
					sendEmail(fromAddress, companyName, toAddress, bccAddress, subject, messageBody, imgSrcMap);
				}
				return true;
			} catch (MailException me) {
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "sendBulkEmails", me.getMessage(), me);
			} catch (Exception ex) {
				ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "sendBulkEmails", ex.getMessage(), ex);
			}
		}
		return false;
	}

	/*
	 * Method is used to send mail with file(s) attached to it to a list of users.
	 * (non-Javadoc)
	 * @see com.estes.framework.services.mail.iface.MailService#sendAttachedMail(java.lang.String, java.lang.String, java.util.List, java.lang.String, java.lang.String, java.util.Map)
	 */
	public boolean sendAttachedMail(final String fromAddress, final String companyName, final List<String> toAddress, 
			final String subject, final String messageBody, final Map<String, File>attachmentFileMap)throws ESTESException{
		
		try {
			MimeMessagePreparator preparator = new MimeMessagePreparator() {
				public void prepare(MimeMessage mimeMessage) throws MessagingException {
					MimeMessageHelper message = new MimeMessageHelper(mimeMessage, true);
					if(null!=toAddress && toAddress.size()>0){
						String[] recipientAddress = toAddress.toArray(new String[toAddress.size()]);
						message.setTo(recipientAddress);
					}
					if(StringUtils.isNotBlank(companyName)){
						try {
							message.setFrom(fromAddress, companyName);
						} catch (UnsupportedEncodingException e) {							
						}
					}else{
						message.setFrom(fromAddress);
					}
					message.setSubject(subject);
					message.setText(messageBody, false);		
					if(attachmentFileMap != null){
						Iterator attachmentIterator = attachmentFileMap.entrySet().iterator();						
						while (attachmentIterator.hasNext()) {
							Map.Entry pairs = (Map.Entry)attachmentIterator.next();														
							message.addAttachment((String)pairs.getKey(), (File)pairs.getValue());
						}						
					}
				}
			};
			mailSender.send(preparator);
			return true;
		} catch (MailException me) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "FW:sendAttachedMail", me.getMessage(), me);
		} catch (Exception ex) {
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "FW:sendAttachedMail", ex.getMessage(), ex);
		}		
		return false;
	}
	
	/**
	 * This method is used to process the image and its location
	 * 
	 * @param message
	 * @param imgSrcMap
	 */
	private void processImageResources(MimeMessageHelper message, Map<String, String> imgSrcMap) {
		try {
			if (imgSrcMap != null && !imgSrcMap.isEmpty()) {
				for (Map.Entry<String, String> entry : imgSrcMap.entrySet()) {
					String key = entry.getKey();
					String value = entry.getValue();
					if (StringUtils.isNotBlank(value)) {
						UrlResource urlRes = new UrlResource(value);
						message.addInline(key, urlRes);
					}
				}
			}
		}	
		catch (MessagingException me) {
			// TODO Auto-generated catch block
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "fw:processImageResources", me.getMessage(), me);
		}catch(Exception e){
			ESTESLogger.log(ESTESLogger.ERROR, this.getClass(), "fw:processImageResources", e.getMessage(), e);
		} 	

	}
}
