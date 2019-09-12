package com.estes.myestes.claims.util;

import java.io.IOException;

import org.springframework.web.multipart.MultipartFile;

import com.edps.format.DateFormat;
import com.edps.format.StringFormat;
import com.edps.logger.ESTESLogger;
import com.estes.framework.config.ESTESConfigUtil;
import com.estes.myestes.claims.exception.ClaimsException;

public class UploadedFile {
	
	private String path;
	private String fileName;
	private String pathOnly;
	private MultipartFile file;
	private String tag;
	
	public UploadedFile(MultipartFile file, String tag) throws ClaimsException {
		if(file != null) {
			String exten = getExtension(file);
			if(!(exten.equalsIgnoreCase(".doc") 
					|| exten.equalsIgnoreCase(".docx") 
					|| exten.equalsIgnoreCase(".jpg") 
					|| exten.equalsIgnoreCase(".jpeg") 
					|| exten.equalsIgnoreCase(".pdf") 
					|| exten.equalsIgnoreCase(".png") 
					|| exten.equalsIgnoreCase(".tiff") 
					|| exten.equalsIgnoreCase(".tif") 
					|| exten.equalsIgnoreCase(".rtf") 
					|| exten.equalsIgnoreCase(".txt") 
					|| exten.equalsIgnoreCase(".xls") 
					|| exten.equalsIgnoreCase(".xlsx"))) {
				throw new ClaimsException("Invalid file type to be uploaded");
			}
		}
		this.file = file;
		this.path = uploadFile();
		this.tag = tag;
	}
	
	private String uploadFile() {
		String newPath = "";
		
		if(file != null && file.getName().length() > 0 && file.getSize() > 0) {
			fileName = getTimeStamp() + getExtension(file);
			pathOnly = "" + DateFormat.getCurrentDateYYYYMMDD() + "/";
			newPath = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_URL, ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_URL) + pathOnly + fileName;
			Web4FTPConnection ftp = new Web4FTPConnection();
			
			try {
				String toDirectory = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_PATH, ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_PATH) + pathOnly;
				ftp.sendFile(toDirectory, fileName, file.getInputStream());
			    Web4FilePermissionChange.execute(toDirectory);
			} catch(IOException e) {
				ESTESLogger.log(ESTESLogger.ERROR, UploadedFile.class, "uploadFile()", e.getMessage(), e);
			}
		} 
		
		return newPath;
	}
	
	public void changeDirectory(String claimNumber) throws IOException {
		if(file != null && file.getSize() > 0) {
			String oldPath = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_PATH, ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_PATH) + pathOnly;
			pathOnly += claimNumber + "/";
			String newPath = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_PATH, ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_PATH) + pathOnly;
			path = ESTESConfigUtil.getProperty(ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_URL, ClaimsConstant.APP_EXT_PROP_IMAGE_SERVER_URL) + pathOnly + fileName;
			Web4FTPConnection ftp = new Web4FTPConnection();
			ftp.renameFile(oldPath, fileName, newPath, fileName);
			Web4FilePermissionChange.execute(newPath);
		}
	}
	
	public void setPath(String path) {
		this.path = path;
	}
	
	public void setPathOnly(String pathOnly) {
		this.pathOnly = pathOnly;
	}
	
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	
	public String getPath() { return path; }
	public String getTag() { return tag; }
	
	public String getPathOnly() {
		return pathOnly == null? "" : pathOnly;
	}
	
	public String getFileName() {
		return fileName == null? "" : fileName;
	}
	
	private String getTimeStamp(){
		return System.currentTimeMillis() + "." + 
			StringFormat.frontPadWithZeroes(5, 
				String.valueOf((int)(Math.random()*10000)));
	}
	
	private String getExtension(MultipartFile item) {
		String name = item.getOriginalFilename();
		int decimalIndex = name.lastIndexOf('.');
		return decimalIndex == -1? "": name.substring(decimalIndex);
	}
}