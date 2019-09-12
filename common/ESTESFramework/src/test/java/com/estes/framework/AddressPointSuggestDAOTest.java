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
public class AddressPointSuggestDAOTest
{
	@Autowired
	AddressPointSuggestDAO addressPointSuggestDAO;

	/**
	 * Main method
	 * 
	 * @param args String of arguments
	 */
	public static void main(String[] args)
	{
		AddressPointSuggestService svc = new AddressPointSuggestServiceImpl();
		AddressPointSuggestDAO dao = new AddressPointSuggestDAOImpl();

		for (int i=0; i<args.length; i++) {
			if (args[i].equalsIgnoreCase("null")) {
				args[i] = "";
			}
		}

		List<String> addList = new ArrayList<String>();
		AddressPoint inputPointPartial = svc.parsePointStr(args[0] + ", " + args[1] + " " + args[2]);
		// default country is US
		if(StringUtils.isBlank(inputPointPartial.getCountry())){
			inputPointPartial.setCountry(args[3]);
		}
		List<AddressPoint> addressPointList = dao.getAddress(inputPointPartial, 10);
		if(addressPointList.size() == 0) {
			addList.add(FrameworkConstant.NO_POINTS_FOUND);
		}

		StringBuffer sb;
		for (AddressPoint ap : addressPointList) {
			sb = new StringBuffer(); 
			sb.append(ap.getCity().trim()).append(", ");
			sb.append(ap.getState().trim()).append(" ");
			sb.append(ap.getZip().trim());
			addList.add(sb.toString());
			System.out.println("Address: " + sb.toString());
		}
	} // main
}
