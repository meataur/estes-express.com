package com.estes.myestes.terminallist.utils;

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

import com.estes.framework.config.ESTESConfigUtil;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.terminallist.dto.Terminal;

public class FileUtils {
	
	static short[] EXCEL_CELL_WIDTHS = { 12, 11, 16, 25, 15, 6, 7, 14, 14 };
	
	static String[] headers = {"Alpha Code", "Terminal #", "Terminal Name", "Street Address", "City", "State", "Zip", "Phone", "Fax", "Country"};
	
	// creates temporary file with passed filename (which should have .xls extension)
	public static String createExcelFile(List<Terminal> records, String filename){
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// set up work book
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		
		// set up sheet
		HSSFSheet sheet = workbook.createSheet(filename);
		for (int i = 0; i < EXCEL_CELL_WIDTHS.length; ++i)
			sheet.setColumnWidth((short) i, (short)(EXCEL_CELL_WIDTHS[i]*256));
		HSSFRow row = sheet.createRow((short)0);
		// first row is Headers
		createExcelHeaders(workbook, row);
		
		// fill in rows
		for(int i = 0; i < records.size(); i++) {
			row = sheet.createRow(i+1);
			createExcelDataRow(row, dateStyle, records.get(i));
		}
		
		String fullPath = ESTESConfigUtil.getProperty(AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR, AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR) + filename.toString();
		File file = new File(ESTESConfigUtil.getProperty(AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR, AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR));
		file.mkdirs();
		
		try {
			FileOutputStream fileOut = new FileOutputStream(fullPath);
			workbook.write(fileOut);
			fileOut.flush();
			fileOut.close();
		}
		catch (Exception e) {
			ESTESLogger.log(ESTESLogger.ERROR,FileUtils.class,"outputToFile()",e.getMessage(),e);
		}
		
		return fullPath;
	}
	
	// creates temporary file with passed filename (which should have .csv extension)
	public static String createCSVFile(List<Terminal> records, String filename){
		String fullPath = ESTESConfigUtil.getProperty(AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR, AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR) + filename.toString();
		File file = new File(ESTESConfigUtil.getProperty(AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR, AppConstants.APP_EXT_PROP_TERMINAL_TEMP_DIR));
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
			for(Terminal record: records) {
				String[] data = {record.getAlphaCode(), 
						record.getTerminal(), 
						record.getTerminalName(), 
						record.getStreetaddress(),
						record.getCity(),
						record.getState(),
						record.getZip(),
						record.getPhone(),
						record.getFax(),
						record.getCountry()};
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
	
	private static short createExcelHeaders(HSSFWorkbook workbook, HSSFRow row) {
		short cellCount = 0;
		HSSFFont headerFont = workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(false);
		
		for(String header : headers) {
			HSSFCell cell = row.createCell(cellCount++);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);
//			sheet.setColumnWidth(cellCount++, (short)((cell.getStringCellValue().length() + 3) * 256));
		}
		
		return cellCount;
	}
	
	private static void createExcelDataRow(HSSFRow row, HSSFCellStyle dateStyle, Terminal record) {
		short cellCount = 0;
		
		HSSFCell cell;
		String[] elements = {record.getAlphaCode(), 
				record.getTerminal(), 
				record.getTerminalName(), 
				record.getStreetaddress(),
				record.getCity(),
				record.getState(),
				record.getZip(),
				record.getPhone(),
				record.getFax(),
				record.getCountry()};
		
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