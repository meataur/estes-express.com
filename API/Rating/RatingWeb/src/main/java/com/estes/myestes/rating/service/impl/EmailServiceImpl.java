/**
 * @author: Todd Allen
 *
 * Creation date: 03/21/2019
 */

package com.estes.myestes.rating.service.impl;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.StringReader;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.edps.email.EMailMessage;
import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.rating.dao.iface.EmailDAO;
import com.estes.myestes.rating.dao.impl.EmailDAOImpl;
import com.estes.myestes.rating.dao.impl.RatingDAOImpl;
import com.estes.myestes.rating.dto.BookingRequest;
import com.estes.myestes.rating.dto.EmailRequest;
import com.estes.myestes.rating.dto.Point;
import com.estes.myestes.rating.dto.QuoteTemplate;
import com.estes.myestes.rating.dto.RateQuote;
import com.estes.myestes.rating.exception.RatingException;
import com.estes.myestes.rating.service.iface.EmailService;
import com.estes.myestes.rating.service.iface.RatingService;
import com.estes.myestes.rating.service.iface.TerminalService;
import com.estes.myestes.rating.template.FreeMakerConfiguration;
import com.estes.myestes.rating.utils.RatingConstants;
import com.estes.myestes.rating.utils.RatingUtil;
import com.estes.myestes.rating.utils.TextUtil;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.html.simpleparser.HTMLWorker;
import com.lowagie.text.pdf.PdfWriter;

import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;

@Service("emailService")
@Scope("prototype")
public class EmailServiceImpl implements EmailService
{
	@Autowired
	RatingService rateService;
	
	@Autowired
	EmailDAO mailDAO;
	
	@Autowired
	TerminalService termService;

	private String blend(QuoteTemplate qTemplate) throws RatingException
	{
		String blended = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(baos);

		// Set up FreeMarker template
		Template fmTemplate = null;
		try {
			Configuration cfg = FreeMakerConfiguration.getInstance().getConfiguration();
			fmTemplate = cfg.getTemplate("pdf_template.html");
			// Create the root hash as a Map; It could be a JavaBean too.
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("app", qTemplate.getApp());
			root.put("operation", qTemplate.getOperation());
			root.put("quote", qTemplate.getQuote());
			root.put("origin", qTemplate.getOrigin());
			root.put("dest", qTemplate.getDest());
			
			if(qTemplate.getEstesLogoPath4Print()!=null){
				root.put("estesLogoPath4Print", qTemplate.getEstesLogoPath4Print());
			}

			root.put("disclaimerFooter", qTemplate.getFooterDisclaimer());
			fmTemplate.process(root, out);
			blended = baos.toString("UTF-8");
			return blended;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "sendQuoteDetails()", "** Error creating e-mail template for ID " + qTemplate.getQuote().getQuoteID(), e);
			throw new RatingException("Error creating e-mail template.");
		}
	} // blend

	private String blend(String app, String id, Map<String, String> mailMap) throws RatingException
	{
		String blended = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Writer out = new OutputStreamWriter(baos);

		// Set up FreeMarker template
		Template fmTemplate = null;
		QuoteTemplate qTemplate;
		try {
			Configuration cfg = FreeMakerConfiguration.getInstance().getConfiguration();
			fmTemplate = cfg.getTemplate("pdf_template.html");
			qTemplate = buildTemplate(id, mailMap, app, QuoteTemplate.EMAIL_QUOTE);
			// Create the root hash as a Map; It could be a JavaBean too.
			Map<String, Object> root = new HashMap<String, Object>();
			root.put("app", qTemplate.getApp());
			root.put("operation", qTemplate.getOperation());
			root.put("quote", qTemplate.getQuote());
			root.put("origin", qTemplate.getOrigin());
			root.put("dest", qTemplate.getDest());
			root.put("disclaimerFooter", qTemplate.getFooterDisclaimer());
			fmTemplate.process(root, out);
			blended = baos.toString("UTF-8");
			return blended;
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, RatingDAOImpl.class, "sendQuoteDetails()", "** Error creating e-mail template for ID " + id, e);
			throw new RatingException("Error creating e-mail template.");
		}
	} // blend


	
	private QuoteTemplate buildTemplate(String quoteId, String opr) throws RatingException, IOException, TemplateException
	{
		Map<String, String> props = mailDAO.getMailProps();
		RateQuote quote = rateService.retrieveQuote(quoteId);
		return buildTemplate(quote, props, quote.getApp(), opr);
	
	}
	
	private QuoteTemplate buildTemplate(RateQuote quote, Map<String, String> mailProps, String pageName, String opr)
	{
		QuoteTemplate template = new QuoteTemplate();
		template.setApp(pageName);
		template.setOperation(opr);
		template.setQuote(quote);

		// Get origin and destination terminals with Points terminal service
		try {
			template.setOrigin(termService.getTerminal(new Point(quote.getOrigin().getCountry(), quote.getOrigin().getCity(), quote.getOrigin().getState(), quote.getOrigin().getZip())));
			template.setDest(termService.getTerminal(new Point(quote.getDest().getCountry(), quote.getDest().getCity(), quote.getDest().getState(), quote.getDest().getZip())));
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EmailDAOImpl.class, "sendQuoteDetails()", "** Error getting terminal info.", e);
		}

		// Get stored disclaimer text
		String discText = mailProps.get("LTLDIS");
		// NOTE: DB has the the font-size as 100% which causes issues with PDF render
		String disc = StringUtils.replace(discText, "font-size: 100%", "font-size:8pt");
		String splitString[] = disc.split("<p");
		if (splitString != null && splitString.length > 1) {
			disc = "";
			for (int i = 0; i < splitString.length; i++) {
				disc = disc + " <p " + splitString[i].trim();
			}
		}
		template.setFooterDisclaimer(disc);
		template.setWelcomeMessage(mailProps.get("TCBOD"));

		return template;
	} // buildTemplate

	private QuoteTemplate buildTemplate(String id, Map<String, String> mailProps, String pageName, String opr)
			throws IOException, RatingException, TemplateException
	{
		// Get quote details
		RateQuote quote = rateService.retrieveQuote(id);
		
		return buildTemplate(quote,mailProps,pageName,opr);
	} // buildTemplate

	private static String getReturnMessage(boolean success, String act, String app, Map<String, String> mapper)
	{
		if (act.equalsIgnoreCase(CONTACT_ME)) {
			
			if (app.equals(RatingConstants.TIME_CRITICAL_QUOTE)) {
				return TextUtil.scrubHtml(mapper.get("TCCON")).replaceFirst("Success:", "");
			}
			else if (app.equals(RatingConstants.VTL_QUOTE)) {
				return TextUtil.scrubHtml(mapper.get("VTLCON")).replaceFirst("Success:", "");
			}
		}

		if (success) {
			return TextUtil.scrubHtml(mapper.get("EMLSUC")).replaceFirst("Success:", "");
		}
		else {
			return TextUtil.scrubHtml(mapper.get("EMLFAL"));
		}
	} // getReturnMessage

	/**
	 * Send e-mail message
	 */
	private boolean sendEmail(String from, List<String> to, List<String> cc, String subject, String intro, String htmlContent) throws RatingException
	{
		boolean sent = false;

		try {
			EMailMessage email = new EMailMessage();
			// TODO: Add mail JNDI to config file or make constant
			email.setMailJndi("mail/msratingemail");
			email.setFrom(from);
			email.setSubject(subject);
			for (String addr : to) {
				email.addTo(addr.trim());
			}
			email.setMessageBodyHTML(intro + "<br /><br />" + htmlContent);
			sent = email.send();
		} catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EmailDAOImpl.class, "sendEmail()", "** Send e-mail failed.", e);
			throw new RatingException("Send e-mail failed.");
		}

		return sent;
	} // sendEmail

	@Override
	public boolean sendBookingMessage(BookingRequest req) throws RatingException
	{
		String from = null;
		List<String> to = req.getAddresses();
		String subject = null;
		String initialBody = null;

		// Get e-mail properties
		Map<String, String> props = mailDAO.getMailProps();

		//if (! req.getApp().equals(RatingConstants.LTL_QUOTE) ) {
			if (RatingUtil.isGuaranteed(req.getLevel())) {
				from = props.get("BKFGTD");
				to.add("solutions@estes-express.com");
				subject = props.get("BKSGTD");
				initialBody = props.get("BKBGTD");
			}
			else {
				from = props.get("BKFNON");
				subject = props.get("BKSNON");
				initialBody = props.get("BKBNON");
			}
		//}

		// Get formatted HTML with quote info
		String blendedHTML = blend(req.getApp(), req.getQuoteId(), props);

		return sendEmail(from, to, null, subject, initialBody, blendedHTML);
	} // sendBookingMessage

	@Override
	public ServiceResponse sendQuoteDetails(EmailRequest req, RateQuote quot) throws RatingException
	{
		String from = null;
		List<String> to = req.getAddresses();
		String subject = null;
		String initialBody = null;
		
		// Validate e-mail addresses; Return error if 1 is invalid
		for (String email : req.getAddresses()) {
			if (!EmailService.isValidEmail(email)) {
				return new ServiceResponse("error", "1 or more e-mail addresses entered is invalid.");
			}
		}
		
		if (quot == null) {
			quot = rateService.retrieveQuote(req.getQuoteId());
		}

		// Get e-mail properties
		Map<String, String> mailProps = mailDAO.getMailProps();

		if (RatingUtil.getAppIdByServiceLevel(quot.getService().getId()).equals(RatingConstants.LTL_QUOTE)) {
			from = mailProps.get("LTLFRM");
			subject = mailProps.get("LTLSUB");
			initialBody = mailProps.get("LTLBOD");
		}
		else if (RatingUtil.getAppIdByServiceLevel(quot.getService().getId()).equals(RatingConstants.TIME_CRITICAL_QUOTE)) {
			from = mailProps.get("TCFRM");
			
			subject = mailProps.get("TCSUB");
			initialBody = mailProps.get("TCBOD");
		}
		else if (RatingUtil.getAppIdByServiceLevel(quot.getService().getId()).equals(RatingConstants.VTL_QUOTE)) {
			from = mailProps.get("VTLFRM");
			subject = mailProps.get("VTLSUB");
			initialBody = mailProps.get("VTLBOD");
		}

		
//		if(quot.getContactEmail()==null || quot.getContactEmail().trim().equals("")){
//			quot.setContactEmail(String.join(",",req.getAddresses()));
//		}

		// Get formatted HTML with quote info
		//String blendedHTML = blend(req.getApp(), req.getQuoteId(), mailProps);
		String blendedHTML;
		try {
			QuoteTemplate qTemplate = buildTemplate(quot, mailProps, req.getApp(), QuoteTemplate.EMAIL_QUOTE);
			blendedHTML = blend(qTemplate);
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, EmailDAOImpl.class, "sendQuoteDetails()", "** An error uccurred building e-mail template.", e);
			throw new RatingException("Error building e-mail template.");
		}

		// Contact Me requested
		if (req.getAction().equalsIgnoreCase(CONTACT_ME)) {
			/**
			 * Call load GM AS 400 SPROC when cliecked CONTACT ME
			 */
			try{
				rateService.loadGMSData(req.getQuoteId());
			}catch(Exception ex){
				ESTESLogger.log(ESTESLogger.ERROR, getClass(),"Could not load GM Data when clicked contact me","");
			}
			// Send e-mail immediately for TC/VTL guaranteed quotes
			if (req.getApp().equals(RatingConstants.TIME_CRITICAL_QUOTE) || 
					(req.getApp().equals(RatingConstants.VTL_QUOTE))
					&& RatingUtil.isGuaranteed(req.getLevel())) {
				to = new ArrayList<String>();
				to.add("solutions@estes-express.com");
				// Return response based on action and app
				if (sendEmail(from, to, null, subject, initialBody, blendedHTML)) {
					return new ServiceResponse("", getReturnMessage(true, req.getAction(), RatingUtil.getAppIdByServiceLevel(req.getLevel()), mailProps));
				}
				else {
					return new ServiceResponse("error", getReturnMessage(false, req.getAction(),RatingUtil.getAppIdByServiceLevel(req.getLevel()), mailProps));
				}
			}
			// Otherwise, set flag to send e-mail later
			else {
				mailDAO.updateContact(req.getQuoteId());
				return new ServiceResponse("", TextUtil.scrubHtml(mailProps.get("VTLCON")).replaceFirst("Success:", ""));
			}
		}

		// E-mail quote requested (default)
		if (sendEmail(from, to, null, subject, initialBody, blendedHTML)) {
			return new ServiceResponse("", getReturnMessage(true, RatingUtil.getAppIdByServiceLevel(req.getLevel()), req.getApp(), mailProps));
		}
		else {
			return new ServiceResponse("error", getReturnMessage(false, RatingUtil.getAppIdByServiceLevel(req.getLevel()), req.getApp(), mailProps));
		}
	} // sendQuoteDetails
	
	@Override
	public ByteArrayOutputStream getPdfOutPut(String quoteId) throws RatingException, IOException, TemplateException, DocumentException{
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		Document document = new Document(PageSize.A4);
		PdfWriter.getInstance(document, baos);
		document.open();
		QuoteTemplate quoteTemplate = buildTemplate(quoteId,QuoteTemplate.PRINT_QUOTE);
		quoteTemplate.setEstesLogoPath4Print(getClass().getResource("/fm_templates/Logo-Estes-Black.gif").getPath());
		String blendedHTML = blend(quoteTemplate);
		HTMLWorker htmlWorker = new HTMLWorker(document);
		htmlWorker.parse(new StringReader(blendedHTML));
		document.close();
		return baos;

	}
	
	
}
