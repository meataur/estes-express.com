/**
 *
 */

package com.estes.myestes.requestinfo.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.edps.email.EMailMessage;
import com.edps.logger.ESTESLogger;
import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.requestinfo.dao.iface.AccountDAO;
import com.estes.myestes.requestinfo.dao.iface.BusinessTypeDAO;
import com.estes.myestes.requestinfo.dao.iface.RequestLogDAO;
import com.estes.myestes.requestinfo.dao.impl.BusinessTypeDAOImpl;
import com.estes.myestes.requestinfo.dto.InfoRequestDTO;
import com.estes.myestes.requestinfo.dto.ProblemTypeRequestDTO;
import com.estes.myestes.requestinfo.exception.RequestInfoException;
import com.estes.myestes.requestinfo.service.iface.RequestInfoService;
import com.estes.myestes.requestinfo.util.BusinessType;
import com.estes.myestes.requestinfo.util.ProblemType;
import com.estes.myestes.requestinfo.util.RequestInfoConstant;

/**
 * Document retrieval service implementation 
 */
@Service("requestInfoService")
@Scope("prototype")
@Transactional
public class RequestInfoServiceImpl implements RequestInfoService, RequestInfoConstant {
	
	@Autowired
	private RequestLogDAO requestLogDAO;
	
	@Autowired
	private BusinessTypeDAO businessTypeDAO;
	
	@Autowired
	private AccountDAO accountDAO;
	
	/**
	 * Create a new service
	 */
	public RequestInfoServiceImpl() {
		super();
	} // Constructor

	/**
	 */
	public ServiceResponse submitRequest(String username, InfoRequestDTO dto) throws RequestInfoException {
		// write to the database first
		requestLogDAO.writeLog(username, dto);
		
		// compose email
		String sender = dto.getEmailAddress();
		String ot = "";
		String pro = "";
		if(dto.getProNumber() != null && !dto.getProNumber().trim().equals("")) {
			ot = dto.getProNumber().split("-")[0];
			pro = dto.getProNumber().split("-")[1];
		}
		String recipient;
		if(businessTypeDAO.isL2LShipment(ot)) { 
			recipient = BusinessType.L2L.getEmailAddress(); 
		} 
		else if(businessTypeDAO.isEFWShipment(ot)) { 
			recipient = BusinessType.EFW.getEmailAddress(); 
		} 
		else { 
			recipient = BusinessType.LTL.getEmailAddress(); 
		}
		
		EMailMessage email = new EMailMessage();
		email.setMailJndi(RequestInfoConstant.APP_MAIL_JNDI);
		email.setFrom(sender);
		email.addTo(recipient);
		email.setSubject("Additional Info Request - "+dto.getProblem().getText());
		email.setMessageBodyHTML(getEmailContent(username, dto));
		email.send();
		
		ServiceResponse response = new ServiceResponse();
		response.setMessage("Successfully processed request.");
		return response;
	} // submitRequest

	/** Returns the new email content with the newly added form fields. */
	private String getEmailContent(String username, InfoRequestDTO requestForm) {
		
		String template = "This message was sent from the Request Additional Information web application.<br />" 
						+ "Please review the issue noted below and respond to the customer as soon as possible."
						+ "<br /><br />"
						+ "<span style=\"font-weight: bold; text-decoration: underline;\">"
						+ "Request Additional Information Summary"
						+ "</span><br />"
						+ "<span style=\"font-weight: bold;\"> Name: </span>{NAME}"
						+ "<br />"
						+ "<span style=\"font-weight: bold;\"> Phone: </span>{PHONE}"
						+ "<span style=\"font-weight: bold;\">{EXTLABEL}</span>{EXT}"
						+ "<br />"
						+ "<span style=\"font-weight: bold;\">{FAXLABEL}</span>{FAX}"
						+ "<span style=\"font-weight: bold;\">{USERLABEL}</span>{USER}"
						+ "<span style=\"font-weight: bold;\">{PROLABEL}</span>{PRO}"
						+ "<span style=\"font-weight: bold;\">Issue: </span>{ISSUE}"
						+ "<br />"
						+ "<span style=\"font-weight: bold;\">{DESLABEL}</span>{DESCRIPTION}"
						+ "<br /><br />Thank you.";
		
		//Replace template variables where appropriate
		template = template.replace("{NAME}", requestForm.getName());
		template = template.replace("{PHONE}", requestForm.getPhoneNumber());
		
			
		if(requestForm.getPhoneNumberExt() != null && !requestForm.getPhoneNumberExt().equals("")) {
			template = template.replace("{EXTLABEL}", " Ext: "); 
			template = template.replace("{EXT}", requestForm.getPhoneNumberExt());
		} else {
			template = template.replace("{EXTLABEL}", "");
			template = template.replace("{EXT}", "");
		}
		
		if(requestForm.getFaxNumber() != null && !requestForm.getFaxNumber().equals("")) {
			template = template.replace("{FAXLABEL}", " Fax: "); 
			template = template.replace("{FAX}", requestForm.getFaxNumber() + "<br />");
		} else {
			template = template.replace("{FAXLABEL}", ""); 
			template = template.replace("{FAX}", "");
		}
		
		if(username != null && !username.equals("")) {
			template = template.replace("{USERLABEL}"," My Estes Username: ");
			template = template.replace("{USER}",username+"<br/>");
		} else {
			template = template.replace("{USERLABEL}", "");
			template = template.replace("{USER}", "");
		}
		
		if(requestForm.getProNumber() != null && !requestForm.getProNumber().equals("")) {
			template = template.replace("{PROLABEL}"," PRO #: ");
			template = template.replace("{PRO}",requestForm.getProNumber()+"<br/>");
		} else {
			template = template.replace("{PROLABEL}", "");
			template = template.replace("{PRO}", "");
		}
		
		ProblemType problemType = requestForm.getProblem();
		
		if(problemType == ProblemType.imageNotAvailable) {
			StringBuilder issues = new StringBuilder();

			// build out the image issue
			if(requestForm.isBOLSelected()) {
				issues.append("Bill of Lading, ");
			}
			if(requestForm.isDRSelected()) {
				issues.append("Delivery Receipt, ");
			}
			if(requestForm.isWRSelected()) {
				issues.append("Weight & Research, ");
			}

			// if image not specified just put problem in otherwise, add that issue in, minus last comma and space
			if("".equals(issues.toString())) {
				template = template.replace("{ISSUE}",problemType.getText());
			} else {
				String issue = " for " + issues.toString().substring(0, issues.toString().length()-2);
				template = template.replace("{ISSUE}", issue);
			}
		}
		else {
			template = template.replace("{ISSUE}",problemType.getText());
		}
		
		if(requestForm.getDescription() != null && !requestForm.getDescription().equals("")) {
			template = template.replace("{DESLABEL}"," Comments/Description: ");
			template = template.replace("{DESCRIPTION}",requestForm.getDescription());
		} else {
			template = template.replace("{DESLABEL}", "");
			template = template.replace("{DESCRIPTION}", "");
		}
		
		return template;
	}

	public List<ProblemType> getProblemTypes(String accountCode, String accountType, ProblemTypeRequestDTO request) {
		ArrayList<ProblemType> types = new ArrayList<ProblemType>();
		types.add(ProblemType.trackingHelp);
		try {
			String[] otpro = request.getOtPro().split("-");
			Integer ot = Integer.parseInt(otpro[0]);
			Integer pro = Integer.parseInt(otpro[1]);
			
			if (accountCode !=null && accountType !=null) {
				if (accountDAO.isPartyToShipment(accountCode, accountType, ot, pro)) {
					types.add(ProblemType.imageNotAvailable);
				}
				if (accountDAO.isPayorOfFreight(accountCode, accountType, ot, pro)) {
					types.add(ProblemType.ratingQuestion);
				} 
			}
			types.add(ProblemType.other);
		} catch(Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR, BusinessTypeDAOImpl.class, "getProblemTypes()", "An error occurred checking account problems");
		}
		
		return types;
	}
}