package com.estes.myestes.edirequest.email;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.activation.DataHandler;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import com.edps.email.MailSessionManager;
import com.edps.logger.ESTESLogger;
import com.estes.myestes.edirequest.utils.EdiRequestConstant;

/**
 * Customized to accommodate 'bcc' facility and a pdf attachment from buffer, rather than file location.
 * @author Lakshman K
 *
 */
public class CustomizedEmailMessage{
	
	private String subject="";
	private String messageContent="";
	private String toEmail="";
	private String fromEmail="";	
	private String bccEmail="";	
	
	public String getSubject() {
		return subject;
	}

	public void setSubject(String subject) {
		this.subject = subject;
	}

	public String getMessageContent() {
		return messageContent;
	}

	public void setMessageContent(String messageContent) {
		this.messageContent = messageContent;
	}

	public String getToEmail() {
		return toEmail;
	}

	public void setToEmail(String toEmail) {
		this.toEmail = toEmail;
	}

	public String getFromEmail() {
		return fromEmail;
	}

	public void setFromEmail(String fromEmail) {
		this.fromEmail = fromEmail;
	}

	public String getBccEmail() {
		return bccEmail;
	}

	public void setBccEmail(String bccEmail) {
		this.bccEmail = bccEmail;
	}	
	
    public boolean sendPdf(ByteArrayInputStream baos,String attachmentFileName) {
    	
    	ESTESLogger.log(ESTESLogger.DEBUG, CustomizedEmailMessage.class, "sendPdf()", "EmailCustomizedMessage:sendPdf Started.");
    	boolean sent=false;
    	try{
	        MimeMessage message = initMessage();
	        message.setFrom(new InternetAddress(getFromEmail()));
			if(getToEmail()!=null&&!getToEmail().equals("")){
				message.addRecipient(Message.RecipientType.TO, new InternetAddress(getToEmail()));
			}
			message.addRecipient(Message.RecipientType.BCC, new InternetAddress(getBccEmail()));
			message.setSubject(getSubject());		
	        message.setContent(getMessageContent(baos,attachmentFileName));
	        Transport.send(message);
	        sent=true;
    	}catch(Throwable t){
    		ESTESLogger.log(ESTESLogger.ERROR, CustomizedEmailMessage.class, "sendPdf()", "Error while sending mail.", t);
    	}
    	ESTESLogger.log(ESTESLogger.DEBUG, CustomizedEmailMessage.class, "sendPdf()", "EmailCustomizedMessage:sendPdf Completed.");
    	return sent;
    }
 
    private MimeMessage initMessage() {
    	ESTESLogger.log(ESTESLogger.DEBUG, CustomizedEmailMessage.class, "initMessage()", "EmailCustomizedMessage:initMessage Started.");
		Session session = MailSessionManager.getSession(EdiRequestConstant.APP_MAIL_JNDI);
		MimeMessage mimeMessage = new MimeMessage(session);
    	ESTESLogger.log(ESTESLogger.DEBUG, CustomizedEmailMessage.class, "initMessage()", "EmailCustomizedMessage:initMessage Completed.");
        return mimeMessage;
    }
 
    private Multipart getMessageContent(ByteArrayInputStream baos,String attachmentFileName) throws MessagingException {
    	ESTESLogger.log(ESTESLogger.DEBUG, CustomizedEmailMessage.class, "getMessageContent()", "EmailCustomizedMessage:getMessageContent Started.");
        Multipart mp = new MimeMultipart();
        BodyPart messageBodyPart = new MimeBodyPart();
        messageBodyPart.setText(getMessageContent());
        mp.addBodyPart(messageBodyPart);
        
		DataSourceWithOutFile source=null;
		try {
			String contentType="application/pdf";				
			source=new DataSourceWithOutFile(attachmentFileName,contentType,baos);
		} catch (IOException e) {
			ESTESLogger.log(ESTESLogger.ERROR, CustomizedEmailMessage.class, "getMessageContent()", e.getMessage() , e);
		}	        
		BodyPart attachmentPart = new MimeBodyPart();
		
		attachmentPart.setDataHandler(new DataHandler(source));
		attachmentPart.setFileName(attachmentFileName);
		mp.addBodyPart(attachmentPart);
    	ESTESLogger.log(ESTESLogger.DEBUG, CustomizedEmailMessage.class, "getMessageContent()", "EmailCustomizedMessage:getMessageContent Completed.");
        return mp;
    }	
}
