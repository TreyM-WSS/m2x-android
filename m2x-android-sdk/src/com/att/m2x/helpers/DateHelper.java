package com.att.m2x.helpers;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class DateHelper {
	
	private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss'Z'";

	public static Date stringToDate(String dateString) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		Date value = null;
		try {
			value = sdf.parse(dateString);
		} catch (ParseException e) {
			value = null;
		}
		return value;
	}

	public static String dateToString(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_FORMAT, Locale.US);
		String value = (date != null) ? sdf.format(date) : null;
		return value;
	}

}
