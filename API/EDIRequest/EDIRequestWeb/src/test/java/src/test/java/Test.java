package src.test.java;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Test {

	public static void main(String[] args) {
		String email = "lks@maa.com";
		System.out.println(email.matches(".+@.+\\.[a-zA-Z]++"));
		
		System.out.println("Date is "+new SimpleDateFormat("yyyyMMdd").format(new Date("11/21/18")));
	}

}
