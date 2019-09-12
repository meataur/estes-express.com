package com.estes.myestes.claims.util;

import java.io.IOException;

import com.estes.myestes.claims.dto.SubmitClaimRequestDTO;
import com.estes.myestes.claims.exception.ClaimsException;

public class ClaimsFormFileItems {
	private final UploadedFile[] detailsFiles;
	private final UploadedFile invoice;
	private final UploadedFile bol;
	private final UploadedFile freightBill;
	private final UploadedFile other;
	
	private final String[] detailsCost;
	private final String[] detailsQuantity;
	private final String[] detailsDescription;

	public ClaimsFormFileItems(SubmitClaimRequestDTO claim) throws ClaimsException {
		detailsFiles = new UploadedFile[10];
		detailsCost = new String[10];
		detailsQuantity = new String[10];
		detailsDescription = new String[10];

		bol = new UploadedFile(claim.getBolFile(), "BOL");
		invoice = new UploadedFile(claim.getInvoiceFile(), "INVOICE");
		freightBill = new UploadedFile(claim.getFbFile(), "FREIGHT BILL");
		other = new UploadedFile(claim.getOtherFile(), "OTHER");
		
		Integer index = 0;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails1(), index.toString());
		detailsCost[index] = claim.getCostDetails1();
		detailsQuantity[index] = claim.getQtyDetails1();
		detailsDescription[index] = claim.getDescriptionDetails1();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails2(), index.toString());
		detailsCost[index] = claim.getCostDetails2();
		detailsQuantity[index] = claim.getQtyDetails2();
		detailsDescription[index] = claim.getDescriptionDetails2();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails3(), index.toString());
		detailsCost[index] = claim.getCostDetails3();
		detailsQuantity[index] = claim.getQtyDetails3();
		detailsDescription[index] = claim.getDescriptionDetails3();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails4(), index.toString());
		detailsCost[index] = claim.getCostDetails4();
		detailsQuantity[index] = claim.getQtyDetails4();
		detailsDescription[index] = claim.getDescriptionDetails4();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails5(), index.toString());
		detailsCost[index] = claim.getCostDetails5();
		detailsQuantity[index] = claim.getQtyDetails5();
		detailsDescription[index] = claim.getDescriptionDetails5();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails6(), index.toString());
		detailsCost[index] = claim.getCostDetails6();
		detailsQuantity[index] = claim.getQtyDetails6();
		detailsDescription[index] = claim.getDescriptionDetails6();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails7(), index.toString());
		detailsCost[index] = claim.getCostDetails7();
		detailsQuantity[index] = claim.getQtyDetails7();
		detailsDescription[index] = claim.getDescriptionDetails7();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails8(), index.toString());
		detailsCost[index] = claim.getCostDetails8();
		detailsQuantity[index] = claim.getQtyDetails8();
		detailsDescription[index] = claim.getDescriptionDetails8();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails9(), index.toString());
		detailsCost[index] = claim.getCostDetails9();
		detailsQuantity[index] = claim.getQtyDetails9();
		detailsDescription[index] = claim.getDescriptionDetails9();
		index++;
		detailsFiles[index] = new UploadedFile(claim.getFileDetails10(), index.toString());
		detailsCost[index] = claim.getCostDetails10();
		detailsQuantity[index] = claim.getQtyDetails10();
		detailsDescription[index] = claim.getDescriptionDetails10();
		index++;
	}

	public UploadedFile getInvoice() { return invoice; }
	public UploadedFile getBOL() { return bol; }
	public UploadedFile getFreightBill() { return freightBill; }
	public UploadedFile getOther() { return other; }
	public UploadedFile getDetail(int i) { return detailsFiles[i]; }
	public String getDetailsCost(int i) { return detailsCost[i]==null?"":detailsCost[i]; }
	public String getDetailsQuantity(int i) { return detailsQuantity[i]==null?"":detailsQuantity[i]; }
	public String getDetailsDescription(int i) { return detailsDescription[i]==null?"":detailsDescription[i]; }

	public void changeDirectories(String claimNumber) throws IOException {
		invoice.changeDirectory(claimNumber);
		bol.changeDirectory(claimNumber);
		freightBill.changeDirectory(claimNumber);
		other.changeDirectory(claimNumber);

		for(UploadedFile file: detailsFiles) {
			file.changeDirectory(claimNumber);
		}
	}	
}
