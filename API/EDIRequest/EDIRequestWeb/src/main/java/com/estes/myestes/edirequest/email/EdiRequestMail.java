package com.estes.myestes.edirequest.email;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import com.edps.logger.ESTESLogger;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.edirequest.utils.EdiRequestConstant;

/**
 * Class that provides Email with PDF Attachment Facility 
 * @author Lakshman K
 *
 */
public class EdiRequestMail {
		
	public EdiRequestMail(){
		
	}
	
	public boolean sendEmail(String requestorsEmail,ByteArrayOutputStream baos,String ediRequestNumber){
		ESTESLogger.log(ESTESLogger.DEBUG, EdiRequestMail.class, "sendEmail()", "EdiRequestMail:sendCopyToEmail Started.");
		boolean sent=false;
		try {
			CustomizedEmailMessage email = new CustomizedEmailMessage();
			
			String fromEmail=ESTESConfigUtil.getProperty(EdiRequestConstant.EMAILS, EdiRequestConstant.EDI_FROM_EMAIL);
			String bccEmail=ESTESConfigUtil.getProperty(EdiRequestConstant.EMAILS, EdiRequestConstant.EDI_BCC_EMAIL);
			String subjectLine=EdiRequestConstant.MAIL_SUBJECT+ediRequestNumber;;			
			String messageContent=EdiRequestConstant.MAIL_BODY;	
			String fileNameForAttachment="EdiRequest_"+ediRequestNumber+".pdf";
			
			email.setFromEmail(fromEmail);			
			if(requestorsEmail!=null&&!requestorsEmail.equals("")){
				email.setToEmail(requestorsEmail);
			}
			email.setBccEmail(bccEmail);
			email.setSubject(subjectLine);
			email.setMessageContent(messageContent);	    		        		
			sent = email.sendPdf(new ByteArrayInputStream(baos.toByteArray()),fileNameForAttachment);			
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EdiRequestMail.class, "sendEmail()", e.getMessage(), e);
		}	
		
		if(sent){
			ESTESLogger.log(ESTESLogger.DEBUG, EdiRequestMail.class, "sendEmail()", "Mail has been sent.");
		}else{
			ESTESLogger.log(ESTESLogger.DEBUG, EdiRequestMail.class, "sendEmail()", "Mail Could not be sent.");
		}
		
		ESTESLogger.log(ESTESLogger.DEBUG, EdiRequestMail.class, "sendEmail()", "EdiRequestMail:sendCopyToEmail Completed.");
		return sent;
	}		
	public boolean sendEmail(ByteArrayOutputStream baos,String ediRequestNumber){
		return sendEmail(null,baos,ediRequestNumber);
	}
}
