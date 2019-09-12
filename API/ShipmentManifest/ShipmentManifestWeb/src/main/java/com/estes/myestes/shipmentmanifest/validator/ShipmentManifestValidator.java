package com.estes.myestes.shipmentmanifest.validator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.estes.myestes.shipmentmanifest.exception.ShipmentManifestException;
import com.estes.myestes.shipmentmanifest.util.ShipmentManifestConstant;
import com.estes.framework.logger.ESTESLogger;
import com.estes.myestes.shipmentmanifest.dao.iface.ManifestDAO;
import com.estes.myestes.shipmentmanifest.dto.ManifestRequestDTO;


@Component
public class ShipmentManifestValidator {
	
	@Autowired
	private ManifestDAO manifestDAO;
	
	public void performValidation(ManifestRequestDTO objectToValidate, String accountNumber, String accountType) throws ShipmentManifestException {
		// validate account Number as it was typed in
		if(accountType.equalsIgnoreCase(ShipmentManifestConstant.ACCOUNT_TYPE_NATIONAL) && !objectToValidate.getAccount().equalsIgnoreCase(accountNumber) && !objectToValidate.getAccount().equalsIgnoreCase("ALL") && !manifestDAO.accountBelongsToGroup(objectToValidate.getAccount(), accountType, accountNumber)){
			 ESTESLogger.log(ESTESLogger.ERROR, ShipmentManifestValidator.class, "performValidation()", "Not a valid account number.");
			throw new ShipmentManifestException("Invalid Account Number");
		}
	}
	
}