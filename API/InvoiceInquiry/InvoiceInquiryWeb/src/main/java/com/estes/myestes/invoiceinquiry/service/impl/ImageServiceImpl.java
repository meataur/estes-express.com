/**
 * @author: Todd Allen
 *
 * Creation date: 12/31/2018
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.myestes.invoiceinquiry.dao.iface.ImageDAO;
import com.estes.myestes.invoiceinquiry.dto.ImageRequest;
import com.estes.myestes.invoiceinquiry.dto.Image;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.ImageService;

@Service("imageService")
@Scope("prototype")
public class ImageServiceImpl implements ImageService
{
	@Autowired
	private ImageDAO imgDAO;

	@Override
	public Image getImageInfo(String acct, String sess, ImageRequest request) throws InvoiceException
	{
		String accountCode = imgDAO.getBillToAccountCode(request.getPro());
		return imgDAO.reprintInvoice(accountCode, sess, request);
	} // getImageInfo
}
