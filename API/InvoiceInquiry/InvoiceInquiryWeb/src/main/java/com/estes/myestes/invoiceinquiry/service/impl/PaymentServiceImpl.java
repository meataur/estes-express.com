/**
 * @author: Todd Allen
 *
 * Creation date: 01/03/2019
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.invoiceinquiry.dao.iface.PaymentDAO;
import com.estes.myestes.invoiceinquiry.dto.Payment;
import com.estes.myestes.invoiceinquiry.dto.PaymentLimits;
import com.estes.myestes.invoiceinquiry.dto.PaymentReason;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.PaymentService;
import com.estes.myestes.invoiceinquiry.utils.InvoiceInquiryConstants;

@Service("paymentService")
@Scope("prototype")
public class PaymentServiceImpl implements PaymentService
{
	@Autowired
	private PaymentDAO paymentDAO;

	@Override
	public PaymentLimits getPaymentLimits() throws InvoiceException
	{
		return paymentDAO.getPaymentLimits();
	} // getPaymentLimits

	/**
	 * Build URL with query string for GET request to Western Union web site
	 * 
	 * @return GET request parameters
	 */
	private String build(String url, String user, String acct, double pay, int id) throws InvoiceException
	{
		StringBuilder bld = new StringBuilder(ESTESConfigUtil.getProperty(InvoiceInquiryConstants.PAYMENTS,
				InvoiceInquiryConstants.VERIFY_URL) + "?CREDIT_ACCOUNT=");
		// Add My Estes account code to query
		bld.append(acct);
		// Add total amount to be paid
		bld.append("&AMOUNT_OWED=" + pay);
		// Add payment ID
		bld.append("&ESTES_PAYMENT_ID=" + id);
		// Add apply fee indicator
		bld.append("&APPLY_FEE=" + paymentDAO.applyPaymentFee(user));
		// Add cancel/finish links
		bld.append("&CANCEL_LINK=" + encodeString(url + "/finalize?" +
				PAYMENTID_PARM + "=" + id + "&" + ACTION_PARM + "=" + CANCEL_PAYMENT));
		bld.append("&FINISH_LINK=" + encodeString(url + "/finalize?" +
				PAYMENTID_PARM + "=" + id +
				"&" + ACTION_PARM + "=" + FINISH_PAYMENT +
				"&paid=" + pay));
		
		return bld.toString();
	} // build

	/**
	 * Encode string in URL
	 * 
	 * @param  str String to be encoded
	 * @return Encoded string
	 */
	private static String encodeString(String str) throws InvoiceException
	{
		try {
			return URLEncoder.encode(str, ENCODING);
		}
		catch (UnsupportedEncodingException uee) {
			ESTESLogger.log(ESTESLogger.ERROR, PaymentServiceImpl.class, "encodeString()",
					"** Error occurred buidling payment redirect link.", uee);
			throw new InvoiceException("Error buidling payment redirect link");
		}
	} // encodeString

	@Override
	public String processPayment(int payId, String action) throws InvoiceException
	{
		return paymentDAO.finalizePayment(payId, action);
	} // processPayment

	@Override
	public List<PaymentReason> retrievePaymentReasons() throws InvoiceException
	{
		return paymentDAO.getPayReasonCodes();
	} //retrievePaymentReasons

	/**
	 * Verify payment details on PRO
	 * 
	 * @param payments List of {@link Payment}
	 * @return {@link ServiceResponse} list of results
	 */
	private List<ServiceResponse> validate(List<Payment> payments) throws InvoiceException
	{
		List<ServiceResponse> errors = new ArrayList<ServiceResponse>();

		for (Payment pay : payments) {
			double diff = pay.getOpenAmount() - pay.getPendingPayAmount();
			// Pay amounts cannot be greater than amount available to pay
			if (pay.getPayment() > diff) {
				errors.add(new ServiceResponse("error", PAY_AMT_ERROR));
			}
			// Flag short pay amounts with blank reason code
			if (diff < pay.getPayment() && StringUtils.isBlank(pay.getReasonCode())) {
				errors.add(new ServiceResponse("error", PAY_REASON_ERROR));
			}
		}

		return errors;
	} // validate

	@Override
	public List<ServiceResponse> verify(String usr, String acct, List<Payment> payments) throws InvoiceException
	{
		List<ServiceResponse> responses = validate(payments);
		// Verify payment if no errors present
		if (responses.isEmpty()) {
			int id = paymentDAO.verify(usr, acct, payments);
			double tot = 0;
			// Total all payment amounts
			for (Payment pmt: payments) {			
				tot = tot + pmt.getPayment();
			}
			// Build redirect link to WU payment site
			String url = build(ESTESConfigUtil.getProperty(InvoiceInquiryConstants.PAYMENTS, InvoiceInquiryConstants.FINALIZE), usr, acct, tot, id);
			ServiceResponse success = new ServiceResponse("", "");
			success.setRedirectUrl(url);
			responses.add(success);
		}

		return responses;
	} // verify
}
