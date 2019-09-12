package com.estes.framework;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.estes.framework.dataaccess.pointsuggest.iface.AddressPointSuggestDAO;
import com.estes.framework.dataaccess.pointsuggest.impl.AddressPointSuggestDAOImpl;
import com.estes.framework.dto.AddressPoint;
import com.estes.framework.exception.ESTESException;
import com.estes.framework.services.pointsuggest.iface.AddressPointSuggestService;
import com.estes.framework.services.pointsuggest.impl.AddressPointSuggestServiceImpl;
import com.estes.framework.util.FrameworkConstant;

/**
 * Test address point lookup.
 */
public class AddressPointSuggestServiceTest
{
	/**
	 * Main method
	 * 
	 * @param args String of arguments
	 */
	public static void main(String[] args)
	{
		AddressPointSuggestService addressPointSuggestService = new AddressPointSuggestServiceImpl();
		AddressPointSuggestDAO dao = new AddressPointSuggestDAOImpl();

		for (int i=0; i<args.length; i++) {
			if (args[i].equalsIgnoreCase("null")) {
				args[i] = "";
			}
		}

		try {
			List<String> points = addressPointSuggestService.getAddressPoint(args[0] + ", " + args[1] + " " + args[2], args[3], 10);
			List<AddressPoint> pountsFound = new ArrayList<AddressPoint>();
			for (String pt : points) {
				AddressPoint apt = addressPointSuggestService.parsePointStr(pt);
				pountsFound.add(apt);
			}
		} catch (ESTESException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	} // main
}
