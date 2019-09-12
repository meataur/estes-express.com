/**
 * @author: Todd Allen
 *
 * Creation date: 01/07/2019
 */

package com.estes.myestes.invoiceinquiry.dao.iface;

import java.util.List;

import com.estes.myestes.invoiceinquiry.dto.Payment;
import com.estes.myestes.invoiceinquiry.dto.PaymentLimits;
import com.estes.myestes.invoiceinquiry.dto.PaymentReason;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;

/**
 * Data access for A/R payment services
 */
public interface PaymentDAO extends BaseDAO
{
	/**
	 * Get flag to apply payment fee for My Estes user
	 * 
	 * @param user My Estes user name
	 * @return Y/N flag as to whether to apply payment fee
	 */
	public String applyPaymentFee(String user) throws InvoiceException;

	/**
	 * Finalize customer payment
	 * 
	 * @param id Payment ID
	 * @param action cancel/finish
	 * @return Error code
	 */
	public String finalizePayment(int id, String action) throws InvoiceException;
	
	/**
	 * Get invoice payment amount limits
	 * 
	 * @return {@link PaymentLimits}
	 */
	public PaymentLimits getPaymentLimits() throws InvoiceException;

	/**
	 * Get short payment reason code information.
	 * 
	 * @return List of {@link PaymentReason}
	 */
	public List<PaymentReason> getPayReasonCodes() throws InvoiceException;

	/**
	 * Verify payment information
	 * 
	 * @param usr My Estes user profile name
	 * @param usr Customer account code
	 * @param payments List of {@link Payment} to process
	 * @return Generated payment ID
	 */
	public int verify(String usr, String acct, List<Payment> payments) throws InvoiceException;
}
