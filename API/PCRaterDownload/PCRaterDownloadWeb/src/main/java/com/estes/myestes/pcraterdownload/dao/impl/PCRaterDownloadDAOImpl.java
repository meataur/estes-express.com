/**
 * @author: Pradeep K
 *
 */

package com.estes.myestes.pcraterdownload.dao.impl;

import java.sql.Types;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.SqlInOutParameter;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.pcraterdownload.dao.iface.ErrorDAO;
import com.estes.myestes.pcraterdownload.dao.iface.PCRaterDownloadDAO;
import com.estes.myestes.pcraterdownload.dto.PCRaterDownloadDTO;
import com.estes.myestes.pcraterdownload.exception.PCRaterDownloadException;
import com.estes.myestes.pcraterdownload.utils.PCRaterDownloadConstants;

@Repository("PCRaterDownloadDAO")
public class PCRaterDownloadDAOImpl implements PCRaterDownloadDAO {
	@Autowired
	private JdbcTemplate jdbcTemplate;

	@Autowired
	private ErrorDAO errorDAO;

	@Override
	public String getPCRaterDownloadLink(PCRaterDownloadDTO dto) throws PCRaterDownloadException {

		SimpleJdbcCall sproc = new SimpleJdbcCall(jdbcTemplate);

		sproc.withSchemaName(PCRaterDownloadConstants.SCHEMA_NAME);
		sproc.withProcedureName(PCRaterDownloadConstants.PROCEDURE_NAME);
		String output = PCRaterDownloadConstants.EMPTY_STRING;

		sproc.declareParameters(new SqlInOutParameter("NAME", Types.CHAR), new SqlInOutParameter("NAME_E", Types.CHAR),
				new SqlInOutParameter("CONAME", Types.CHAR), new SqlInOutParameter("CONAME_E", Types.CHAR),
				new SqlInOutParameter("ACCT_CODE", Types.CHAR), new SqlInOutParameter("ACCT_CODE_E", Types.CHAR),
				new SqlInOutParameter("ADDRESS", Types.CHAR), new SqlInOutParameter("ADDRESS_E", Types.CHAR),
				new SqlInOutParameter("CITY", Types.CHAR), new SqlInOutParameter("CITY_E", Types.CHAR),
				new SqlInOutParameter("STATE", Types.CHAR), new SqlInOutParameter("STATE_E", Types.CHAR),
				new SqlInOutParameter("ZIP", Types.CHAR), new SqlInOutParameter("ZIP_E", Types.CHAR),
				new SqlInOutParameter("Z4", Types.CHAR), new SqlInOutParameter("Z4_E", Types.CHAR),
				new SqlInOutParameter("PHONE", Types.CHAR), new SqlInOutParameter("PHONE_E", Types.CHAR),
				new SqlInOutParameter("EMAIL", Types.CHAR), new SqlInOutParameter("EMAIL_E", Types.CHAR),
				new SqlInOutParameter("DL_TYPE", Types.CHAR), new SqlInOutParameter("DL_TYPE_E", Types.CHAR),
				new SqlInOutParameter("UPDATE", Types.CHAR), new SqlInOutParameter("UPDATE_E", Types.CHAR),
				new SqlInOutParameter("CONTACT", Types.CHAR), new SqlInOutParameter("CONTACT_E", Types.CHAR),
				new SqlInOutParameter("MEDIA", Types.CHAR), new SqlInOutParameter("MEDIA_E", Types.CHAR),
				new SqlInOutParameter("MAJOR_V", Types.CHAR), new SqlInOutParameter("MAJOR_V_E", Types.CHAR),
				new SqlInOutParameter("MINOR_V", Types.CHAR), new SqlInOutParameter("MINOR_V_E", Types.CHAR),
				new SqlInOutParameter("SUB_V", Types.CHAR), new SqlInOutParameter("SUB_V_E", Types.CHAR),
				new SqlInOutParameter("ACCESS", Types.CHAR), new SqlInOutParameter("ACCESS_E", Types.CHAR),
				new SqlInOutParameter("COMPLETE", Types.CHAR), new SqlInOutParameter("COMPLETE_E", Types.CHAR),
				new SqlInOutParameter("DATE_ENT", Types.CHAR), new SqlInOutParameter("DATE_ENT_E", Types.CHAR),
				new SqlInOutParameter("TIME_ENT", Types.CHAR), new SqlInOutParameter("TIME_ENT_E", Types.CHAR),
				new SqlInOutParameter("QUERY_STRING", Types.CHAR), new SqlInOutParameter("SUBMIT_FORM", Types.CHAR),
				new SqlInOutParameter("ERROR", Types.CHAR));

		sproc.withoutProcedureColumnMetaDataAccess();
		sproc.compile();

		LinkedHashMap<String, Object> parameters = new LinkedHashMap<String, Object>();

		parameters.put("NAME", dto.getContactName());
		parameters.put("NAME_E", "");
		parameters.put("CONAME", dto.getCompanyName());
		parameters.put("CONAME_E", "");
		parameters.put("ACCT_CODE", dto.getAccountCode());
		parameters.put("ACCT_CODE_E", "");
		parameters.put("ADDRESS", dto.getCustomerAddress());
		parameters.put("ADDRESS_E", "");
		parameters.put("CITY", dto.getCustomerCity());
		parameters.put("CITY_E", "");
		parameters.put("STATE", dto.getCustomerState());
		parameters.put("STATE_E", "");
		parameters.put("ZIP", dto.getCustomerZip());
		parameters.put("ZIP_E", "");
		parameters.put("Z4", "");
		parameters.put("Z4_E", "");
		parameters.put("PHONE", dto.getCustomerPhone());
		parameters.put("PHONE_E", "");
		parameters.put("EMAIL", dto.getEmail());
		parameters.put("EMAIL_E", "");
		parameters.put("DL_TYPE", "");
		parameters.put("DL_TYPE_E", "");
		parameters.put("UPDATE", "");
		parameters.put("UPDATE_E", "");
		parameters.put("CONTACT", "");
		parameters.put("CONTACT_E", "");
		parameters.put("MEDIA", "");
		parameters.put("MEDIA_E", "");
		parameters.put("MAJOR_V", "");
		parameters.put("MAJOR_V_E", "");
		parameters.put("MINOR_V", "");
		parameters.put("MINOR_V_E", "");
		parameters.put("SUB_V", "");
		parameters.put("SUB_V_E", "");
		parameters.put("ACCESS", "");
		parameters.put("ACCESS_E", "");
		parameters.put("COMPLETE", "");
		parameters.put("COMPLETE_E", "");
		parameters.put("DATE_ENT", "");
		parameters.put("DATE_ENT_E", "");
		parameters.put("TIME_ENT", "");
		parameters.put("TIME_ENT_E", "");
		parameters.put("QUERY_STRING", "");
		parameters.put("SUBMIT_FORM", "");
		parameters.put("ERROR", "");

		Map<String, Object> outParms = sproc.execute(parameters);

		if (null!= outParms ) {
			if (ErrorDAO.isError((String) outParms.get("ERROR"))) {
				List<ServiceResponse> errors = getPCRaterErrorCodes(outParms);
				if (errors.size() > 0) {
					throw new PCRaterDownloadException(errors);
				}
			}

			if ("21".equals(outParms.get("MAJOR_V").toString().trim()) && "00".equals(outParms.get("MINOR_V").toString().trim())) {
				output = PCRaterDownloadConstants.MAJOR_V_21_LINK;
				ESTESLogger.log(ESTESLogger.DEBUG, PCRaterDownloadDAOImpl.class, "getPCRaterDownloadLink()", "PC rater download link :" + output);
			} else if ("2".equals(outParms.get("MAJOR_V").toString().trim()) && "00".equals(outParms.get("MINOR_V").toString().trim())) {
				output = PCRaterDownloadConstants.MAJOR_V_2_LINK;
				ESTESLogger.log(ESTESLogger.DEBUG, PCRaterDownloadDAOImpl.class, "getPCRaterDownloadLink()", "PC rater download link :" + output);
			}

		} else {
			ESTESLogger.log(ESTESLogger.ERROR, PCRaterDownloadDAOImpl.class, "getPCRaterDownloadLink()", "An error occurred retrieving rater download link ");
			throw new PCRaterDownloadException("An error occurred retrieving rater download link .");
		}

		return output;
	}

	private List<ServiceResponse> getPCRaterErrorCodes(Map<String, ?> resultMap) {

		List<ServiceResponse> errors = new ArrayList<>();

		for (Entry result : resultMap.entrySet()) {
			switch (result.getKey().toString()) {
			case "NAME_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("contactName");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));

					errors.add(error);
				}
				break;
			case "CONAME_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("companyName");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "ACCT_CODE_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("accountCode");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "ADDRESS_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("customerAddress");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "CITY_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("customerCity");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "STATE_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("customerState");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "ZIP_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("customerZip");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "Z4_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("z4");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "PHONE_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("customerPhone");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			case "EMAIL_E":
				if (ErrorDAO.isError((String) result.getValue())) {
					ServiceResponse error = new ServiceResponse();
					error.setErrorCode((String) result.getValue());
					error.setFieldName("email");
					error.setMessage(errorDAO.getErrorMessage((String) result.getValue()));
					
					errors.add(error);
				}
				break;
			}
		}

		return errors;

	}
}
