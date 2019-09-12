package com.estes.myestes.addressbook.service.iface;

import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.estes.dto.common.ServiceResponse;
import com.estes.myestes.addressbook.exception.AddressBookException;

/**
 * Address book CSV file upload service
 */
public interface UploadService
{
	/**
	 * Overwrite upload
	 */
	public static final String OVERWRITE = "overwrite";
    /**
     * Error message for a blank file
     */
	public static final String NO_FILE_ERROR_MESSAGE = "A file was not received.";
	/**
	 * File extension for CSV files
	 */
	public static final String CSV_FILE_EXTENSION = "csv";
    /**
     * Error message for an invalid file type
     */
	public static final String INVALID_FILE_TYPE_ERROR = "Invalid file type - must be a comma separated file with .csv extension.";
    /**
     * Parsing rror message
     */
	public static final String PARSING_ERROR = "Unable to parse file. Please ensure all values are comma-separated.";
    /**
     * Error message for failure to delete existing user address book entries
     */
	public static final String DELETE_FAILURE_MESSAGE = "Existing address book entries could not be deleted.";
    /**
     * Error message for failure processing uploaded address book entries
     */
	public static final String PROCESS_FAILURE_MESSAGE = "An error occurred processing address in row ";
    /**
     * Message of successful uploaded addresses
     */
	public static final String UPLOADED_MESSAGE = " addresses were successfully uploaded.";

	/**
	 * Array containing maximum length of all columns
	 */
	public static final int[] MAXLENGTH = {1, 1, 1, 1, 30, 25, 25, 10, 3, 3, 4, 5, 3, 3, 4, 30, 30, 20, 2, 6, 4, 2, 50};
	/**
	 * Array containing numeric indicator for all columns
	 */
	public static final String[] NUMERIC = {"N", "N", "N", "N", "N", "N", "N", "N", "Y", "Y", "Y", "N", "Y", "Y", "Y", "N", "N", "N", "N", "N", "N", "N", "N"};
    /**
     * Empty/Null file validation error message 
     */
	public static final String EMPTY_ERROR_MESSAGE = "No address book data received.";
    /**
     * File column value validation error message 
     */
	public static final String INVALID_COLUMN_ERROR_MESSAGE = "At least one column contains invalid data. See row ";

	/**
	 * Default error code for ReST service calls
	 */
	public static String DEFAULT_ERROR_CODE = "GEN0005";
	/**
	 * Default error message for ReST service calls
	 */
	public static String DEFAULT_ERROR_MSG = "An unexpected error occurred.";
    /**
     * Successful upload message 
     */
	public static final String SUCCESS_MESSAGE = "Your file has been uploaded successfully.";
    /**
     * Successful append upload message 
     */
	public static final String SUCCESS_APPEND_MESSAGE = "The information has been added to your existing address book.";
    /**
     * Successful overwrite upload message 
     */
	public static final String SUCCESS_OVERWRITE_MESSAGE = "Your address book was deleted and replaced with the new information.";

	/**
	 * Determine whether errors are present
	 * 
	 * @return Indicator of error
	 */
	public boolean isError();

	/**
	 * Process uploaded address data
	 * 
	 * @param upload {@link MultipartFile} Uploaded file
	 * @param user User profile name
	 * @param oper Append or overwrite
	 * @return List of error information
	 */
	public List<ServiceResponse> processFile(MultipartFile upload, String user, String oper) throws AddressBookException;

	/**
	 * Validate uploaded address data
	 * 
	 * @param upload {@link MultipartFile} Uploaded file
	 * @return List of error information
	 */
	public List<ServiceResponse> validateAddresses(MultipartFile upload) throws AddressBookException;
}
