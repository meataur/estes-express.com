/**
 * @author: Todd Allen
 *
 * Creation date: 01/03/2019
 */

package com.estes.myestes.invoiceinquiry.service.iface;

import java.util.List;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.invoiceinquiry.dto.Payment;
import com.estes.myestes.invoiceinquiry.dto.PaymentLimits;
import com.estes.myestes.invoiceinquiry.dto.PaymentReason;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Service to verify customer payments
 */
public interface PaymentService
{
	/**
	 * Error message when pay amount exceeds amount available to pay
	 */
	public static final String PAY_AMT_ERROR = "Payment amount greater than the balance due.";
	/**
	 * Error message when short pay reason not provided
	 */
	public static final String PAY_REASON_ERROR = "Short pay reason is required when payment amount is different from open amount.";

	/**
	 * Payment ID parameter
	 */
	public static final String PAYMENTID_PARM = "id";
	/**
	 * Action parameter
	 */
	public static final String ACTION_PARM = "action";
	/**
	 * Action parameter
	 */
	public static final String CANCEL_PAYMENT = "C";
	/**
	 * Action parameter
	 */
	public static final String FINISH_PAYMENT = "F";

	/**
	 * Character encoding
	 */
	public static final String ENCODING = "UTF-8";

	/**
	 * Get maximum invoice payment amount allowed
	 * 
	 * @return Maximum payment amount
	 */
	public PaymentLimits getPaymentLimits() throws InvoiceException;

	/**
	 * Retrieve all payment reasons
	 * 
	 * @return List of {@link PaymentReason} objects
	 */
	public List<PaymentReason> retrievePaymentReasons() throws InvoiceException;

	/**
	 * Process payment action
	 * 
	 * @param id Payment ID
	 * @param action Payment action - C(ancel)/F(inish) 
	 * @return Error code
	 */
	public String processPayment(int payId, String action) throws InvoiceException;

	/**
	 * Verify payment information
	 * 
	 * @param usr My Estes user profile name
	 * @param usr Customer account code
	 * @param payments List of {@link Payment} to process
	 * @return List of {@link ServiceResponse}
	 */
	public List<ServiceResponse> verify(String usr, String acct, List<Payment> payments) throws InvoiceException;
}
