package com.att.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {

	public static Date stringToDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'", Locale.US);
		Date value = null;
		try {
			value = sdf.parse(dateString);
		} catch (ParseException e) {
			value = null;
		}
		return value;
	}
	
}
