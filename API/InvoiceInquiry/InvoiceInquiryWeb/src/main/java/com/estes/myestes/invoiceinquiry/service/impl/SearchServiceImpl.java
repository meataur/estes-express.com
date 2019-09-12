/**
 * @author: Todd Allen
 *
 * Creation date: 11/30/2018
 */

package com.estes.myestes.invoiceinquiry.service.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.invoiceinquiry.dao.iface.ErrorDAO;
import com.estes.myestes.invoiceinquiry.dao.iface.SearchDAO;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchRequest;
import com.estes.myestes.invoiceinquiry.dto.InvoiceSearchResult;
import com.estes.myestes.invoiceinquiry.exception.AppException;
import com.estes.myestes.invoiceinquiry.exception.InvoiceException;
import com.estes.myestes.invoiceinquiry.service.iface.SearchService;

@Service("searchService")
@Scope("prototype")
public class SearchServiceImpl implements SearchService
{
	@Autowired
	private SearchDAO searchDAO;
	@Autowired
	private ErrorDAO errDAO;

	
	

	//@Override
	public List<InvoiceSearchResult> searchInvoices(String session, InvoiceSearchRequest search) throws InvoiceException
	{
		List<InvoiceSearchResult> results = null;
		

		// Ensure at least 1 search item entered
		if ((search.getCriteria() == null) || (search.getCriteria().length == 0)) {
			throw new AppException("error","Please enter search criteria.");
		}
		

		Set<String> searchTerms = new HashSet<String>(Arrays.asList(search.getCriteria()));

		// Default to search by PRO
		if (!search.getSearchType().matches(PRO_SEARCH + "|" + STATEMENT_SEARCH + "|" + PO_SEARCH + "|" + BOL_SEARCH + "|")) {
			search.setSearchType(PRO_SEARCH);
		}

		List<ServiceResponse> errorMessages = new ArrayList<>();
		Set<String> allErrorCodes = new HashSet<>();
		
		// Validate statement search criteria
		if (search.getSearchType().equals(STATEMENT_SEARCH)) {
			Map<String, String> statementErrors = searchDAO.validateStatements(session, SearchService.createSearchString(searchTerms));
			
			Set<String> keys = statementErrors.keySet();

			for(Map.Entry<String, String> entry : statementErrors.entrySet()){
				allErrorCodes.add(entry.getValue());
				String errorMessage = errDAO.getErrorMessage(entry.getValue());
				if(entry.getKey()!=null){
					errorMessage =  entry.getKey()+" - " +errorMessage;
				}
				errorMessages.add(new ServiceResponse(entry.getValue(),errorMessage));
			}
			if(keys.containsAll(searchTerms)){
				throw new AppException(errorMessages);
			}else{
				searchTerms.removeAll(keys);
			}
		}else if(search.getSearchType().equals(PRO_SEARCH)) {
			
			Map<String, String> proErrors = new HashMap<>();
			
			for(String searchTerm : searchTerms){
				String errorCode = validatePro(searchTerm);
				if(!errorCode.isEmpty()){
					proErrors.put(searchTerm, errorCode);
				}
			}
			
			Set<String> keys = proErrors.keySet();
			String errorMessage = errDAO.getErrorMessage(INVALID_PRO_LENGTH);
			
			for(Map.Entry<String, String> entry : proErrors.entrySet()){
				allErrorCodes.add(entry.getValue());
				errorMessages.add(new ServiceResponse(entry.getValue(),entry.getKey()+" - "+errorMessage));
			}
			if(keys.containsAll(searchTerms)){
				
				throw new AppException(errorMessages);
			}else{
				searchTerms.removeAll(keys);
			}
		}
		
		if (searchTerms.size() > 0) {
			results = searchDAO.getInvoiceInfo(session, search,searchTerms);
		}

		// Populate error messages for all error codes present
		
		List<InvoiceSearchResult> output = new ArrayList<>();
		
		if (null!=results) {
			Set<String> errorCodes = new HashSet<>();
			for (InvoiceSearchResult res : results) {
				
				if (ServiceResponse.isError(res.getError().getErrorCode())) {
					errorCodes.add(res.getError().getErrorCode());
				}else{
					output.add(res);
				}
		    }
			
			if(output.isEmpty() && !errorCodes.isEmpty()){
				
				for(String errorCode : errorCodes){
					if(!allErrorCodes.contains(errorCode)){
						ServiceResponse error = new ServiceResponse(errorCode,errDAO.getErrorMessage(errorCode));
						errorMessages.add(error);
					}
				}
				throw new AppException(errorMessages);
			}
		}
		
		if(output.isEmpty()){
			errorMessages.add(new ServiceResponse("error","There are no invoice details available at this time."));
			throw new AppException(errorMessages);
		}
		
		return output;
	} // searchInvoices

	

	/**
	 * Validate PRO
	 * 
	 * @param pro PRO being searched for
	 * @return Error code or blank for valid PRO
	 */
	public String validatePro(String pro)
	{
		// PROs must be 10 characters long
		if (pro==null || pro.replaceAll("\\D+","").length() != 10) {
			return INVALID_PRO_LENGTH;
		}

		return "";
	} // validatePro
}
