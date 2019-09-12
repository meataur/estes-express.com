package com.estes.myestes.claims.util;

import java.io.File;
import java.io.FileOutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFDataFormat;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import com.edps.logger.ESTESLogger;
import com.estes.myestes.claims.dto.ClaimDTO;

public class FileUtils {
	
	static final String[] headers = {
		"Claim Number",
		"PRO Number",
		"Status",
		"Date Filed",
		"Ref. Number",
		"Amount Of Claim",
		"Check Number",
		"Check Amount",
		"Check Date",
		"Claiment",
		"Remit To"
	};
	
	public static String createExcelFile(List<ClaimDTO> records){
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// set up file name
		String fullPath = " ";
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer filename = new StringBuffer(("ClaimsResults_"+timeStamp).replaceAll(" ", ""));
		if(filename.length() > 30)
			filename = filename.delete(30, filename.length());
		filename.append(ClaimsConstant.EXCEL_FILE_EXTENSION);
		
		// set up work book
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		
		// set up sheet
		HSSFSheet sheet = workbook.createSheet("ClaimsResults_"+timeStamp);
		HSSFRow row = sheet.createRow((short)0);
		// first row is Headers
		createExcelHeaders(workbook, row, sheet);
		
		// fill in rows
		for(int i = 0; i < records.size(); i++) {
			row = sheet.createRow(i+1);
			createExcelDataRow(row, dateStyle, records.get(i));
		}
		
		fullPath = ClaimsConstant.FILE_PATH + filename.toString();
		File file = new File(ClaimsConstant.FILE_PATH);
		file.mkdirs();
		
		try {
			FileOutputStream fileOut = new FileOutputStream(fullPath);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR,FileUtils.class,"createExcelFile()",e.getMessage(),e);
		}
		
		return fullPath;
	}
	
	public static String createTabDelimitedFile(List<ClaimDTO> records){
		
		String fullPath = " ";
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer filename = new StringBuffer(("ClaimsResults_"+timeStamp).replaceAll(" ", ""));
		if(filename.length() > 30)
			filename = filename.delete(30, filename.length());
		filename.append(ClaimsConstant.TEXT_FILE_EXTENSION);
		
		fullPath = ClaimsConstant.FILE_PATH + filename.toString();
		File file = new File(ClaimsConstant.FILE_PATH);
		file.mkdirs();
		try{
			FileOutputStream fileStream = new FileOutputStream(fullPath);
			
			// first column headings
			for(int columnNumber=0; columnNumber < headers.length; columnNumber++){
				String cell = headers[columnNumber];
				for (int j = 0; j < cell.length(); j++) {
					fileStream.write(cell.charAt(j));
				}	
				fileStream.write('\t');
			}
			fileStream.write('\r');
			fileStream.write('\n');
			
			// write data
			for(ClaimDTO record: records) {
				String[] data = record.generateClaimAsRow();
				for(String cell: data) {
					for (int j = 0; j < cell.length(); j++) {
						fileStream.write(cell.charAt(j));
					}
					fileStream.write('\t');
				}
				fileStream.write('\r');
				fileStream.write('\n');
			}
			
			// close stream
			fileStream.flush();
			fileStream.close();
		}
		catch(Exception e){
			ESTESLogger.log(ESTESLogger.ERROR,FileUtils.class,"createTabDelimitedFile()",e.getMessage(),e);
		}
		return fullPath;
	}
	
	public static String createCSVFile(List<ClaimDTO> records){
		
		String fullPath = " ";
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer filename = new StringBuffer(("ClaimsResults_"+timeStamp).replaceAll(" ", ""));
		if(filename.length() > 30)
			filename = filename.delete(30, filename.length());
		filename.append(ClaimsConstant.CSV_FILE_EXTENSION);
		fullPath = ClaimsConstant.FILE_PATH + filename.toString();
		File file = new File(ClaimsConstant.FILE_PATH);
		file.mkdirs();
		try{
			FileOutputStream fileStream = new FileOutputStream(fullPath);
			// first write headers
			for(int columnNumber=0; columnNumber < headers.length; columnNumber++){
				String cell = headers[columnNumber];
				if (columnNumber > 0 && cell != null) {
					fileStream.write((byte)',');
				}
				for (int j = 0; j < cell.length(); j++) {
					fileStream.write(cell.charAt(j));
				}
			}
			fileStream.write('\r');
			fileStream.write('\n');
			
			// write data
			for(ClaimDTO record: records) {
				String[] data = record.generateClaimAsRow();
				for(int i = 0; i < data.length; i++) {
					String cell = data[i];
					cell = formatCSVCell(cell);
					if (i > 0 && cell != null) {
						fileStream.write((byte)',');
					}
					for (int j = 0; j < cell.length(); j++) {
						fileStream.write(cell.charAt(j));
					}
				}
				fileStream.write('\r');
				fileStream.write('\n');
			}
			// close stream
			fileStream.flush();
			fileStream.close();
		}
		catch(Exception e){
			ESTESLogger.log(ESTESLogger.ERROR, FileUtils.class, "createCSVFile", "\n " + e.getMessage());
		}
		return fullPath;
	}

	public static void cleanUpFile(String fullPath){
		new File(fullPath).delete();
	}
	
	private static short createExcelHeaders(HSSFWorkbook workbook, HSSFRow row, HSSFSheet sheet) {
		short cellCount = 0;
		HSSFFont headerFont = workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(false);
		
		for(String header : headers) {
			HSSFCell cell = row.createCell(cellCount);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);
			sheet.setColumnWidth(cellCount++, (short)((cell.getStringCellValue().length() + 3) * 256));
		}
		
		return cellCount;
	}
	
	private static void createExcelDataRow(HSSFRow row, HSSFCellStyle dateStyle, ClaimDTO record) {
		short cellCount = 0;
		
		HSSFCell cell;
		String[] elements = record.generateClaimAsRow();
		
		for(String element: elements) {
			cell = row.createCell(cellCount++);
			cell.setCellValue(element);
		}
	}
	
	private static String formatCSVCell(String cell) {
		cell = cell.replaceAll("\"", "\"\"");
		if (cell.indexOf('\"') != -1 || cell.indexOf(',') != -1) {
			cell = "\"" + cell + "\"";
		}
		return cell;
	}
	
}