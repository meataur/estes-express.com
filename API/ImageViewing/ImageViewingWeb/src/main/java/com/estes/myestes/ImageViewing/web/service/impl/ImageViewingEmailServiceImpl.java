package com.estes.myestes.ImageViewing.web.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URI;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.ImageViewing.web.dto.ImageEmail;
import com.estes.myestes.ImageViewing.web.service.iface.ImageViewingEmailService;

@Service("imageViewingEmailService")
public class ImageViewingEmailServiceImpl implements ImageViewingEmailService{
	
	@Autowired
	private JavaMailSender emailSender;
	
	@Autowired
	public SimpleMailMessage simpleMessage;
	
	@Override
	public boolean processEmailRequest(ImageEmail imageEmail) {
		
		MimeMessage message = emailSender.createMimeMessage();
	      
	    MimeMessageHelper helper;
	    
		try {
			
			helper = new MimeMessageHelper(message, true);
			
			helper.setTo(imageEmail.getRecipientEmails().toArray(new String[0]));
			
			if(imageEmail.getUserEmail()!=null && !"".equals(imageEmail.getUserEmail().trim())){
				ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "processEmailRequest()", "User email has been set");
				helper.setBcc(imageEmail.getUserEmail());
			}
			
			
			helper.setFrom(simpleMessage.getFrom());
			
			helper.setSubject(simpleMessage.getSubject());
		    
			helper.setText(simpleMessage.getText(), true);
			
		    
			
		    for(String url : imageEmail.getImageUrl()){
			    InputStream inputStream = URI.create(url).toURL().openStream();
			    helper.addAttachment(url.substring(url.lastIndexOf("/")), new ByteArrayResource(IOUtils.toByteArray(inputStream)));
			    inputStream.close();
		    }
		    
		    emailSender.send(message);
		    
		    ESTESLogger.log(ESTESLogger.DEBUG, getClass(), "processEmailRequest()", "Email has been sent");
		    
		    return true;
			
		} catch (MessagingException e) {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "processEmailRequest()", "Email has not been sent : "+e.getMessage());
		} catch (MalformedURLException e) {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "processEmailRequest()", "Email has not been sent : "+e.getMessage());
		} catch (IOException e) {
			ESTESLogger.log(ESTESLogger.ERROR, getClass(), "processEmailRequest()", "Email has not been sent : "+e.getMessage());
		}
	     
	  
		return false;
	}

}
