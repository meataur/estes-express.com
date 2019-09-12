package com.estes.myestes.shipmentmanifest.util;

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
import com.estes.myestes.shipmentmanifest.dto.ManifestRecordDTO;
import com.estes.myestes.shipmentmanifest.service.impl.ShipmentManifestServiceImpl;

public class FileUtils {
	
	static final String[] headersWithCharges = {
		"PRO Number",
		"Bill Of Lading",
		"Purchase Order",
		"Pickup Date",
		"Delivery Date",
		"Origin",
		"Destination",
		"Pieces",
		"Weight",
		"*Charges",
		"Status",
		"Received By",
		"First Delivery Attempt",
		"Delivery Time",
		"Appointment Date",
		"Appointment Time",
		"Shipper",
		"Shippers Address 1",
		"Shippers Address 2",
		"Shippers City",
		"Shippers State",
		"Shippers Zip",
		"Consignee",
		"Consignees Address 1",
		"Consignees Address 2",
		"Consignees City",
		"Consignees State",
		"Consignees Zip",
		"Destination Terminal",
		"Phone Number",
		"Fax Number"
	};
	
	static final String[] headersNoCharges = {
		"PRO Number",
		"Bill Of Lading",
		"Purchase Order",
		"Pickup Date",
		"Delivery Date",
		"Origin",
		"Destination",
		"Pieces",
		"Weight",
		"Status",
		"Received By",
		"First Delivery Attempt",
		"Delivery Time",
		"Appointment Date",
		"Appointment Time",
		"Shipper",
		"Shippers Address 1",
		"Shippers Address 2",
		"Shippers City",
		"Shippers State",
		"Shippers Zip",
		"Consignee",
		"Consignees Address 1",
		"Consignees Address 2",
		"Consignees City",
		"Consignees State",
		"Consignees Zip",
		"Destination Terminal",
		"Phone Number",
		"Fax Number"
	};
	
	public static String createExcelFile(List<ManifestRecordDTO> records, String shipmentTypes, Boolean charges){
		HSSFWorkbook workbook = new HSSFWorkbook();
		
		// set up file name
		String fullPath = " ";
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer filename = new StringBuffer((shipmentTypes+"_"+timeStamp).replaceAll(" ", ""));
		if(filename.length() > 30)
			filename = filename.delete(30, filename.length());
		filename.append(ShipmentManifestConstant.EXCEL_FILE_EXTENSION);
		
		// set up work book
		HSSFCellStyle dateStyle = workbook.createCellStyle();
		dateStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("m/d/yy"));
		
		// set up sheet
		HSSFSheet sheet = workbook.createSheet(shipmentTypes+"_"+timeStamp);
		HSSFRow row = sheet.createRow((short)0);
		// first row is Headers
		createExcelHeaders(workbook, row, sheet, charges);
		
		// fill in rows
		for(int i = 0; i < records.size(); i++) {
			row = sheet.createRow(i+1);
			createExcelDataRow(row, dateStyle, records.get(i), charges);
		}
		
		fullPath = ShipmentManifestConstant.FILE_PATH + filename.toString();
		File file = new File(ShipmentManifestConstant.FILE_PATH);
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
	
	public static String createTabDelimitedFile(List<ManifestRecordDTO> records, String shipmentTypes, Boolean charges){
		
		String fullPath = " ";
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer filename = new StringBuffer((shipmentTypes+"_"+timeStamp).replaceAll(" ", ""));
		if(filename.length() > 30)
			filename = filename.delete(30, filename.length());
		filename.append(ShipmentManifestConstant.TEXT_FILE_EXTENSION);
		
		fullPath = ShipmentManifestConstant.FILE_PATH + filename.toString();
		File file = new File(ShipmentManifestConstant.FILE_PATH);
		file.mkdirs();
		try{
			FileOutputStream fileStream = new FileOutputStream(fullPath);
			
			// first column headings
			String[] headers = null;
			if(charges) {
				headers = headersWithCharges;
			}
			else {
				headers = headersNoCharges;
			}
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
			for(ManifestRecordDTO record: records) {
				String[] data = record.getManifestAsRow(charges);
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
	
	public static String createCSVFile(List<ManifestRecordDTO> records, String shipmentTypes, Boolean charges){
		
		String fullPath = " ";
		String timeStamp = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
		StringBuffer filename = new StringBuffer((shipmentTypes+"_"+timeStamp).replaceAll(" ", ""));
		if(filename.length() > 30)
			filename = filename.delete(30, filename.length());
		filename.append(ShipmentManifestConstant.CSV_FILE_EXTENSION);
		fullPath = ShipmentManifestConstant.FILE_PATH + filename.toString();
		File file = new File(ShipmentManifestConstant.FILE_PATH);
		file.mkdirs();
		try{
			FileOutputStream fileStream = new FileOutputStream(fullPath);
			// first write headers
			String[] headers = null;
			if(charges) {
				headers = headersWithCharges;
			}
			else {
				headers = headersNoCharges;
			}
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
			for(ManifestRecordDTO record: records) {
				String[] data = record.getManifestAsRow(charges);
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
			ESTESLogger.log(ESTESLogger.ERROR, ShipmentManifestServiceImpl.class, "createCSVFile", "\n " + e.getMessage());
		}
		return fullPath;
	}

	public static void cleanUpFile(String fullPath){
		new File(fullPath).delete();
	}
	
	private static short createExcelHeaders(HSSFWorkbook workbook, HSSFRow row, HSSFSheet sheet, Boolean charges) {
		short cellCount = 0;
		HSSFFont headerFont = workbook.createFont();
		headerFont.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
		
		HSSFCellStyle headerStyle = workbook.createCellStyle();
		headerStyle.setFont(headerFont);
		headerStyle.setWrapText(false);
		
		String[] headers = null;
		if(charges) {
			headers = headersWithCharges;
		}
		else {
			headers = headersNoCharges;
		}
		
		for(String header : headers) {
			HSSFCell cell = row.createCell(cellCount);
			cell.setCellStyle(headerStyle);
			cell.setCellValue(header);
			sheet.setColumnWidth(cellCount++, (short)((cell.getStringCellValue().length() + 3) * 256));
		}
		
		return cellCount;
	}
	
	private static void createExcelDataRow(HSSFRow row, HSSFCellStyle dateStyle, ManifestRecordDTO record, Boolean charges) {
		short cellCount = 0;
		
		HSSFCell cell;
		String[] elements = record.getManifestAsRow(charges);
		
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