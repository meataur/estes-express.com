package src.test.java;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

import com.estes.myestes.commoditylibrary.utils.OnlineReportingUtil;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub		
	//	System.out.println("formatted date is "+OnlineReportingUtil.formatDate("2012-03-01"));
	//	System.out.println("Create timestamp is " + OnlineReportingUtil.formatDate((new java.sql.Date(Timestamp.valueOf("2012-03-01 18:19:39.215083").getTime()).toString())));
		
		String email = "lks@maa.com";
		System.out.println(email.matches(".+@.+\\.[a-zA-Z]++"));
		
		System.out.println("Date is "+new SimpleDateFormat("yyyyMMdd").format(new Date("11/21/18")));
	}

}
