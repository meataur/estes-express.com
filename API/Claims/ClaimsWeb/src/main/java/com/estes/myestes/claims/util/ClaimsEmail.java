package com.estes.myestes.claims.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;

import com.edps.email.EMailMessage;
import com.edps.email.EmailConstants;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.claims.dao.impl.FilingDAOImpl;
import com.estes.myestes.claims.dto.ProInfoResponseDTO;
import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;

public class ClaimsEmail {
	private final ProInfoResponseDTO proInfo;
	private final SubmitClaimRequestDTO claim;
	private final String companyName;
	private final ClaimsFormFileItems fileItems;
	private final String claimNumber;
	private final String ot;
	private final String pro;
	
	public ClaimsEmail(SubmitClaimRequestDTO claim, ProInfoResponseDTO proInfo, String companyName, 
	ClaimsFormFileItems fileItems, String claimNumber) throws ClaimsException {
		this.proInfo = proInfo;
		this.claim = claim;
		this.companyName = companyName;
		this.fileItems = fileItems;
		this.claimNumber = claimNumber;
		String otAndPro[] = claim.getOtPro().split("-");
		if(otAndPro.length != 2) {
			throw new ClaimsException("Invalid OT PRO");
		}
		else {
			ot = otAndPro[0];
			pro = otAndPro[1];
		}
	}
	
	/**
	 * Sends an email with the claim information to the claims department.
	 * @throws ClaimsException 
	 */
	public boolean sendClaim() throws ClaimsException {

		EMailMessage eMailMessage = new EMailMessage();
		eMailMessage.setMailJndi(ClaimsConstant.APP_MAIL_JNDI);
		eMailMessage.setFrom(claim.getEmail());
		// Note: In testing, change claimsEmail in the properties file to your email. It is only for testing 
		// purposes to make sure the claims dept does not receive errant e-mails from testing.
		// production will use Claims@estes-express.com
		eMailMessage.addTo(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_CLAIMS, EmailConstants.APP_EXT_PROP_MAIL_CLAIMS));
		eMailMessage.setSubject(getSubject());
		eMailMessage.setMessageBodyHTML(getMessage());
		return eMailMessage.send();
	}

	/**
	 * Sends a confirmation email to the customer regarding the claim.
	 */
	public boolean sendConfirmation() {
		EMailMessage eMailMessage = new EMailMessage();
		eMailMessage.setMailJndi(ClaimsConstant.APP_MAIL_JNDI);
		eMailMessage.setFrom(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_CLAIMS, EmailConstants.APP_EXT_PROP_MAIL_CLAIMS));
		
		// Note: testCustomerEmail is only for testing purpose. It is to make sure the customer does not receive the claims email while testing.
		// Make sure for PROD environment it is blank in the properties file(claimsFiling.properties)
		String testCustomerEmail = ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_TEST_CUSTOMER, EmailConstants.APP_EXT_PROP_MAIL_TEST_CUSTOMER);
		if(testCustomerEmail != null && !(testCustomerEmail.trim().equalsIgnoreCase(""))){
			eMailMessage.addTo(testCustomerEmail);	
		}else{
			String customerEmail = claim.getEmail();
			eMailMessage.addTo(customerEmail);
		}
		
		eMailMessage.setSubject(getSubject());
		
		String emailMessage = 
			"<p>We have received your claim #" + claimNumber + 
			" on PRO #" + ot + '-' + pro + 
			" for the total amount of $" + claim.getAutoTotal() + 
			" and will send you an acknowledgement via U.S. Mail. " + 
			"You will be able to track your claim's status in the " +
			"<a href=\""+ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_CLAIMS_INQUIRY_LINK, ClaimsConstant.APP_EXT_PROP_CLAIMS_INQUIRY_LINK)+"\" " +
			"target=\"_blank\">My Estes Claims Inquiry application</a>" +
			" within 48 hours.</p>" +
			"<p>If you have any questions, please contact our Claims Department " +
			"at " + ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_CLAIMS_DEPT_PHONE, ClaimsConstant.APP_EXT_PROP_CLAIMS_DEPT_PHONE) + ".</p>" +
			"<p>Thank you for choosing Estes.</p>";
		eMailMessage.setMessageBodyHTML(emailMessage);
		return eMailMessage.send();
	}
	
	private String getSubject() {
		String subject = "Estes - Claims Information for PRO # " +
			ot + '-' + pro;
		
		if(claim.getReferenceNumber().trim().length() != 0) {
			subject += " - REF# " + claim.getReferenceNumber();
		}
		
		return subject;
	}
		
	private String getMessage() throws ClaimsException{
		StringBuilder sb = new StringBuilder();
		
		sb.append("<style>td{vertical-align:top;width:30px;white-space:nowrap}</style>");
		
		sb.append("<table><tr><td>");
			sb.append("<table>");
			sb.append("<tr><td>Company Name:</td><td>"+companyName+"</td></tr>");
			sb.append("<tr><td>Pro:</td><td>"+ot+"-"+pro+"</td></tr>");
			sb.append("<tr><td>Total Amount:</td><td>$" + claim.getAutoTotal()+"</td></tr>");
			sb.append("<tr><td>Claim Type:</td><td>"+claim.getClaimType()+"</td></tr>");
			sb.append("<tr><td>Freight Type:</td><td>"+claim.getFreightType()+"</td></tr>");
			sb.append("<tr><td>Pro Date:</td><td>"+formatDate(claim.getProDate())+"</td></tr>");
			sb.append("<tr><td>BOL #:</td><td>"+claim.getBol()+"</td></tr>");
			sb.append("<tr><td>BOL Date:</td><td>"+formatDate(claim.getBolDate())+"</td></tr>");
			sb.append("<tr><td>Reference Number:</td><td>"+claim.getReferenceNumber()+"</td></tr>");
			sb.append("</table>");
		sb.append("</td><td>");
			sb.append("<table>");
			sb.append("<tr><td colspan=\"2\"><b>Claimant Information:</b></td></tr>");
			sb.append("<tr><td>Name:</td><td>"+claim.getName()+"</td></tr>");
			sb.append("<tr><td>Address:</td><td>"+claim.getStreetAddress1()+"</td></tr>");
			sb.append("<tr><td></td><td>"+claim.getStreetAddress2()+"</td></tr>");
			sb.append("<tr><td></td><td>"+claim.getCity()+", " + claim.getState() + " " + claim.getZip() + "</td></tr>");
			sb.append("<tr><td>Email:</td><td>"+claim.getEmail()+"</td></tr>");
			sb.append("<tr><td>Phone: </td><td>"+claim.getPhone()+"</td></tr>");
			sb.append("<tr><td>Fax: </td><td>"+claim.getFax()+"</td></tr>");
			sb.append("</table>");
		sb.append("</td><td>");
			sb.append("<table>");
			sb.append("<tr><td colspan=\"2\"><b>Remit To Information:</b></td></tr>");
			sb.append("<tr><td>Name:</td><td>"+claim.getRemitName()+"</td></tr>");
			sb.append("<tr><td>Address:</td><td>"+claim.getRemitStreetAddress1()+"</td></tr>");
			sb.append("<tr><td></td><td>"+claim.getRemitStreetAddress2()+"</td></tr>");
			sb.append("<tr><td></td><td>"+claim.getRemitCity()+", " + claim.getRemitState()+ " " + claim.getRemitZip()+ "</td></tr>");
			sb.append("<tr><td>Phone: </td><td>"+claim.getRemitPhone()+"</td></tr>");
			sb.append("</table>");
		sb.append("</td></tr><tr><td>");
			sb.append(getShipperInfoTable(proInfo));
		sb.append("</td><td>");
			sb.append(getConsigneeInfoTable(proInfo));
		sb.append("</td><td>");
		sb.append("</td></tr></table>");
		
		sb.append("<p>&nbsp;</p>");
		
		sb.append("<table>");
		sb.append("<tr><td>Invoice:</td>			<td><a href=\""+fileItems.getInvoice().getPath()+"\" taget=\"_blank\" >"+fileItems.getInvoice().getPath()+"</a></td></tr>");
		sb.append("<tr><td>BOL:</td>				<td><a href=\""+fileItems.getBOL().getPath()+"\" taget=\"_blank\" >"+fileItems.getBOL().getPath()+"</td></tr>");
		sb.append("<tr><td>Paid Freight Bill:</td>	<td><a href=\""+fileItems.getFreightBill().getPath()+"\" taget=\"_blank\" >"+fileItems.getFreightBill().getPath()+"</td></tr>");
		sb.append("<tr><td>Other:</td>				<td><a href=\""+fileItems.getOther().getPath()+"\" taget=\"_blank\" >"+fileItems.getOther().getPath()+"</td></tr>");
		sb.append("</table>");
		
		sb.append("<p>&nbsp;</p>");
		
		sb.append("<table>");
		sb.append("<tr><td nowrap><b>Description</b></td><td nowrap><b>QTY</b></td><td><b>Total Cost</b></td><td><b>File</b></td></tr>");
		for(int x = 0; x < 10; x++){
			sb.append("<tr>");
			sb.append("<td>"+fileItems.getDetailsDescription(x)+"</td>");
			sb.append("<td>"+fileItems.getDetailsQuantity(x)+"</td>");
			sb.append("<td>"+fileItems.getDetailsCost(x)+"</td>");
			sb.append("<td><a href=\""+fileItems.getDetail(x).getPath()+"\" taget=\"_blank\" >"+fileItems.getDetail(x).getPath()+"</a></td>");
			sb.append("</tr>");
		}
		sb.append("</table>");
		sb.append("<p>&nbsp;</p>");
		sb.append("<table><tr><td><b>Additional Comments:</td></tr><tr><td>"+claim.getAdditionalComments()+"</td></tr></table>");
		return sb.toString();
	}
	
	private String getShipperInfoTable(ProInfoResponseDTO proInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table><tr><td>");
		sb.append("<b>Shipper Information: </b>");
		sb.append("</td></tr><tr><td>");
		sb.append(proInfo.getShipperName());
		sb.append("</td></tr><tr><td>");
		sb.append(proInfo.getShipperAddress().getStreetAddress());
		sb.append("</td></tr><tr><td>");
		if(!proInfo.getShipperAddress().getStreetAddress2().trim().equals("")){
			sb.append(proInfo.getShipperAddress().getStreetAddress2());
			sb.append("</td></tr><tr><td>");
		}
		sb.append(proInfo.getShipperAddress().getCity() + ", " + 
			proInfo.getShipperAddress().getState() + " " +
			proInfo.getShipperAddress().getZip());
		sb.append("</td></tr></table>");
		return sb.toString();
	}
	
	private String getConsigneeInfoTable(ProInfoResponseDTO proInfo) {
		StringBuilder sb = new StringBuilder();
		sb.append("<table><tr><td>");
		sb.append("<b>Consignee Information:</b>");
		sb.append("</td></tr><tr><td>");
		sb.append(proInfo.getConsigneeName());
		sb.append("</td></tr><tr><td>");
		sb.append(proInfo.getConsigneeAddress().getStreetAddress());
		sb.append("</td></tr><tr><td>");
		if(!proInfo.getConsigneeAddress().getStreetAddress2().trim().equals("")){
			sb.append(proInfo.getConsigneeAddress().getStreetAddress2());
			sb.append("</td></tr><tr><td>");
		}
		sb.append(proInfo.getConsigneeAddress().getCity() + ", " + 
			proInfo.getConsigneeAddress().getState() + " " +
			proInfo.getConsigneeAddress().getZip());
		sb.append("</td></tr></table>");
		return sb.toString();
	}
	
	private String formatDate(String inDate){
		SimpleDateFormat dateFormat = new SimpleDateFormat("MMddyyyy");
		SimpleDateFormat dateFormatInto = new SimpleDateFormat("MM/dd/yyyy");
		
		try {
		    return dateFormatInto.format(dateFormat.parse(inDate.trim()));
		} catch(ParseException e) {
			return "";
		}
	}
}