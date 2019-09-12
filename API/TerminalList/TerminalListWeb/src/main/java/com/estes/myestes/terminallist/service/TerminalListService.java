/**
 * @author: shelender singh
 *
 * Creation date: 09/12/2018
 */

package com.estes.myestes.terminallist.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import org.apache.commons.lang.StringEscapeUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import com.edps.email.EMailMessage;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.terminallist.dao.iface.TerminalListDAO;
import com.estes.myestes.terminallist.dto.EmailRequestDTO;
import com.estes.myestes.terminallist.dto.StateCoverage;
import com.estes.myestes.terminallist.dto.Terminal;
import com.estes.myestes.terminallist.exception.TerminalException;
import com.estes.myestes.terminallist.utils.AppConstants;
import com.estes.myestes.terminallist.utils.FileUtils;
import com.edps.email.EmailConstants;

/**
 * Terminal search service
 */
@Service("terminalListService")
@Scope("prototype")
public class TerminalListService
{
	@Autowired
	private TerminalListDAO termListDAO;

	private boolean error;

	public String emailTerminalList(EmailRequestDTO dto) throws TerminalException
	{
		error = false;
		String tempFile = null;

		if (!dto.getFileType().equals(AppConstants.EXCEL_FILE_EXTENSION) 
				&& !dto.getFileType().equals(AppConstants.CSV_FILE_EXTENSION)) {
			error = true;
			return "Invalid file type.";
		}

		try {
			String fileName;
			if (dto.getCountry().equals(AppConstants.ALL_COUNTRIES)) {
				fileName = "ALL terminals";
			}
			else {
				fileName = dto.getCountry() + " " + dto.getState() + " terminal";
			}
			fileName += " list" + dto.getFileType();

			EMailMessage email = new EMailMessage();
			email.setMailJndi(AppConstants.APP_MAIL_JNDI);
			email.setFrom(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_FROM, EmailConstants.APP_EXT_PROP_MAIL_FROM));
			email.setSubject("Estes - Terminal List");
			email.setMessageBody(StringEscapeUtils.unescapeJava(ESTESConfigUtil.getProperty(EmailConstants.APP_EXT_PROP_MAIL_BODY, EmailConstants.APP_EXT_PROP_MAIL_BODY)));

			StringBuffer message = new StringBuffer("Your request has been processed. ")
					.append("Serving terminal list for " + dto.getState())
					.append(" sent as (*").append(dto.getFileType()).append(") file to: ");

			ArrayList<String> emailList = getValidEmails(dto.getEmails());

			if (emailList.size() == 0) {
				error = true;
				return "Please enter a valid e-mail address.";
			}

			for (int i = 0; i < emailList.size(); ++i) {
				email.addTo(emailList.get(i));
				message.append(emailList.get(i)).append(i == emailList.size() - 1 ? "" : ", ");
			}
			List<Terminal> terminals = termListDAO.searchTerminals(dto);
			if(dto.getFileType().equals(AppConstants.EXCEL_FILE_EXTENSION)) {
				tempFile = FileUtils.createExcelFile(terminals, fileName);
			}
			if(dto.getFileType().equals(AppConstants.CSV_FILE_EXTENSION)) {
				tempFile = FileUtils.createCSVFile(terminals, fileName);
			}
			email.addAttachment(tempFile, fileName);
			if (email.send())
				return message.toString();

			ESTESLogger.log(ESTESLogger.ERROR, TerminalListService.class, "emailTerminalList()", "** Error occurred sending terminal list e-mail.");
			throw new TerminalException("Error sending terminal listing e-mail.");
		}
		catch (Exception e) {
			error = true;
			ESTESLogger.log(ESTESLogger.ERROR, TerminalListService.class, "emailTerminalList()", e.getMessage(), e);
			return "Please accept our apologies. An error occurred and the e-mail could not be sent.";
		}
		finally {
			if (tempFile != null)
				FileUtils.cleanUpFile(tempFile);
		}
	} // emailTerminalList

	/**
	 * Returns the valid email addresses in the email text area.
	 * 
	 * @param emailTextArea
	 *            text entered into the email form by the user
	 * @return an ArrayList of valid email addresses
	 */
	protected ArrayList<String> getValidEmails(String emailTextArea)
	{
		Pattern emailSeparator = Pattern.compile("[\\s|,]+");
		Pattern emailPattern = Pattern.compile(".+@.+\\.[a-zA-Z]++");
		String[] emails = emailSeparator.split(emailTextArea);
		ArrayList<String> goodEmails = new ArrayList<String>(emails.length);

		for (String email : emails)
			if (emailPattern.matcher(email).matches())
				goodEmails.add(email);

		return goodEmails;
	} // getValidEmails

	/**
	 * Get the error indicator
	 * 
	 * @return Error indicator
	 */
	public boolean isError()
	{
		return this.error;
	} // isError

	public List<StateCoverage> searchTerminals(EmailRequestDTO dto)
	{
		List<Terminal> terminals = termListDAO.searchTerminals(dto);
		Map<Object, List<Terminal>> terminalListGrp = terminals.stream()
				.collect(Collectors.groupingBy(a -> a.stateName));
		List<StateCoverage> stateCoverage = new ArrayList<>();
		terminalListGrp.forEach((k, v) -> {
			StateCoverage coverage = new StateCoverage();
			coverage.setState(k + "");
			coverage.setTerminals(v);
			stateCoverage.add(coverage);
		});
		// Sort by state
		stateCoverage.sort((StateCoverage o1, StateCoverage o2)->o1.getState().compareTo(o2.getState()));
		// Additional sort by country if all countries requested
		if (dto.getCountry().equals(AppConstants.ALL_COUNTRIES) && dto.getState().equals("")) {
			stateCoverage.sort((StateCoverage o1, StateCoverage o2)->o2.getTerminals().get(0).getCountry().compareTo(o1.getTerminals().get(0).getCountry()));
		}
		return stateCoverage;
	} // searchTerminals
}
