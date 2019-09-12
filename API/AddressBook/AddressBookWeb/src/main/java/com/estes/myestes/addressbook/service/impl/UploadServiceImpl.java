package com.estes.myestes.addressbook.service.impl;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.io.FilenameUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.estes.dto.common.ServiceResponse;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.addressbook.dao.iface.AddressBookDAO;
import com.estes.myestes.addressbook.dao.iface.ErrorDAO;
import com.estes.myestes.addressbook.exception.AddressBookException;
import com.estes.myestes.addressbook.service.iface.UploadService;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvParser;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;

@Service("uploadService")
@Scope("prototype")
public class UploadServiceImpl implements UploadService
{
	@Autowired
	private AddressBookDAO addressDAO;
	@Autowired
	private ErrorDAO errDAO;

	private List<ServiceResponse> errors;
	private int uploads = 0;

	public UploadServiceImpl()
	{
		errors = new ArrayList<ServiceResponse>();
	}

	/**
	 * Check column value against restrictions.
	 * 
	 * @param  Column string value
	 * @param  col Column index
	 * @return Boolean value indicating validity of column value
	 */
	private static boolean checkColumn(String val, int col)
	{
		// Check column value against maximum value.
		if (getStrippedValue(val, col).length() > MAXLENGTH[col]) {
			return false;
		}

		// Verify non-blank numeric value
		if ((val.length() > 0) && (NUMERIC[col].equalsIgnoreCase("Y"))) {
			try {
				Integer.parseInt(val);
			}
			catch (NumberFormatException ne) {
				return false;			
			}			
		}

		return true;
	} // checkColumn

	/**
	 * Check column value lengths of current table row.
	 * 
	 * @param  row Array of column values
	 * @return Boolean value indicating validity of current row
	 */
	private static boolean checkColumns(String[] row)
	{
		int cols = row.length;
		for (int i=0; i < cols; i++) {
			// Check each column value in current row
			if (!checkColumn(row[i], i)) {
				return false;
			}
		}

		return true;
	} // checkColumns

	/**
	 * Check column value lengths on all rows of uploaded file.
	 * 
	 * @param tbl Parsed values from CSV file
	 * @return Boolean value indicating validity of all table rows
	 */
	private void checkRows(List<String[]> tbl)
	{
        /*
         * Read all rows of CSV file and check column values.
         * Start count at 2 to skip heading row
         */
		int rowCount = 2;
		for (String[] row : tbl) {
			if (!checkColumns(row)) {
				errors.add(new ServiceResponse("error", INVALID_COLUMN_ERROR_MESSAGE +
						rowCount + "."));
			}
			rowCount++;
        }
	} // checkRows

	/**
	 * Get string value with inner spaces stripped.
	 * 
	 * @param  text String value
	 * @param  col Column index
	 * @return string with spaces removed
	 */
	private static String getStrippedValue(String text, int col)
	{
		/*
		 * Remove inner spaces from column value for postal/zip codes
		 * since Canadian postal codes contain a space between the 2 sets
		 * of 3 characters.
		 */
		if (col == 19) {
			return text.replaceAll("\\s", "");
		}
		else {
			return text.trim();
		}
	} // getStrippedValue

	@Override
	public boolean isError()
	{
		return !((errors == null) || (errors.isEmpty()));
	} // isError()

	/**
	 * Parse uploaded address data
	 * 
	 * @param upload {@link MultipartFile} Uploaded file
	 * @return List of parsed CSV data
	 */
	private List<String[]> parseFile(MultipartFile upload)
	{
		if (!FilenameUtils.getExtension(upload.getOriginalFilename()).equalsIgnoreCase(CSV_FILE_EXTENSION)) {
			errors.add(new ServiceResponse("error", INVALID_FILE_TYPE_ERROR));
			return Collections.emptyList();
		}

		try {
	        CsvMapper mapper = new CsvMapper();
	        CsvSchema bootstrapSchema = CsvSchema.emptySchema().withSkipFirstDataRow(true);
	        mapper.enable(CsvParser.Feature.WRAP_AS_ARRAY);
	        File csvFile = new File( upload.getOriginalFilename());
			upload.transferTo(csvFile);
			MappingIterator<String[]> readValues = mapper.reader(String[].class).with(bootstrapSchema).readValues(csvFile);
	        return readValues.readAll();
		} catch (Exception e) {
   	    	ESTESLogger.log(ESTESLogger.ERROR, UploadServiceImpl.class, "parseFile()", "** Error occurred parsing CSV file.", e);
			errors.add(new ServiceResponse("error", PARSING_ERROR));
			return Collections.emptyList();
	    }
	} // parseFile

	@Override
	public List<ServiceResponse> processFile(MultipartFile upload, String user, String oper) throws AddressBookException
	{
		List<String[]> rows = parseFile(upload);
		List<ServiceResponse> processErrors = new ArrayList<ServiceResponse>();

		/*
         * Delete all table rows for the user for overwrite operation.
         * If this fails then return.
         */
		if ((oper.equalsIgnoreCase(OVERWRITE))
				&& (!addressDAO.deleteAddresses(user))) {
			processErrors.add(new ServiceResponse("error", DELETE_FAILURE_MESSAGE));
			return processErrors;
		}

		/*
         * Process all rows of CSV data.
         */
		int line = 2;
		for (String[] row : rows) {
			try {
				List<String> errCodes = addressDAO.addAddress(user, row);
				if (!errCodes.isEmpty()) {
					String errMessage = PROCESS_FAILURE_MESSAGE +
							line + ".";
							processErrors.add(new ServiceResponse("error", errMessage));
					// Get all error codes
					for (String code : errCodes) {
						processErrors.add(new ServiceResponse(code, errDAO.getErrorMessage(code)));
					}
				}
				else {
					uploads++;
				}
			}
			catch (Exception e) {
				String errMessage = PROCESS_FAILURE_MESSAGE +
				line + ".";
				processErrors.add(new ServiceResponse("error", errMessage));           			
			}

			// Add message for successful uploads
			processErrors.add(new ServiceResponse("", uploads + UPLOADED_MESSAGE));

			line++;
        }

		return processErrors;
	} // processFile

	@Override
	public List<ServiceResponse> validateAddresses(MultipartFile upload) throws AddressBookException
	{
		errors = new ArrayList<ServiceResponse>();

		List<String[]> rows = parseFile(upload);
		if (isError()) {
			return errors;
		}

		if ((rows == null) || (rows.size() == 0)) {
			errors.add(new ServiceResponse("error", EMPTY_ERROR_MESSAGE));
		}
		else {
			checkRows(rows);
		}

		return errors;
	} // validateAddresses
}
